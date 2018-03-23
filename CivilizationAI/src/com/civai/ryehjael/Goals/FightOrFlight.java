package com.civai.ryehjael.Goals;

import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import com.civai.ryehjael.CivNPC.CivNPC;
import com.civai.ryehjael.CivNPC.NPCState;

import net.citizensnpcs.api.ai.Goal;
import net.citizensnpcs.api.ai.GoalSelector;
import net.citizensnpcs.api.npc.NPC;

public class FightOrFlight implements Goal{
	NPCState state;
	NPC npc;
	Entity enemy;
	CivNPC civNPC = new CivNPC();
	
	public FightOrFlight(NPC npc, NPCState state, Entity enemy) {
		this.state = state;
		this.npc = npc;
		this.enemy = enemy;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run(GoalSelector selector) {
		state.setIdle();
		Map<Integer, ItemStack> weapon = civNPC.hasWeaponInInventory(npc);
		if (weapon != null) {
			selector.selectAdditional(new EquipItemFromInventory(npc,  state, weapon));
		}
		
		if (civNPC.getHealth(npc) > 8 && state.getState().equals("idle")) {
			state.setFighting();
			npc.getNavigator().setTarget(enemy, true);
			selector.finishAndRemove();
		} else if (state.getState().equals("idle")) {
			state.setFleeing();
			double x = ( (npc.getEntity().getLocation().getX() - enemy.getLocation().getX()) * 30 ) + npc.getEntity().getLocation().getX();
			double y = npc.getEntity().getLocation().getY();
			double z = ((npc.getEntity().getLocation().getZ()- enemy.getLocation().getZ()) * 30) + npc.getEntity().getLocation().getZ();
			Location fleeLocation = new Location(npc.getEntity().getWorld(), x, y, z);
			npc.getNavigator().getDefaultParameters().baseSpeed(1.1f);
			npc.getNavigator().setTarget(fleeLocation);
			for (Entity entity : npc.getEntity().getNearbyEntities(6, 6, 6)) {
				
			}
			selector.finishAndRemove();
			
		}
		
		
	}

	@Override
	public boolean shouldExecute(GoalSelector selector) {
		if (state.getState().equals("assaulted")) {
			return true;
		}
		return false;
	}

}
