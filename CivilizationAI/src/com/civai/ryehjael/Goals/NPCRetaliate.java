package com.civai.ryehjael.Goals;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import com.civai.ryehjael.CivNPC.CivNPC;
import com.civai.ryehjael.CivNPC.NPCState;

import net.citizensnpcs.api.ai.Goal;
import net.citizensnpcs.api.ai.GoalSelector;
import net.citizensnpcs.api.npc.NPC;

public class NPCRetaliate implements Goal{
	Entity enemy;
	NPC npc;
	NPCState state;
	CivNPC civNPC = new CivNPC();
	
	public NPCRetaliate(NPC npc, Entity enemy, NPCState state) {
		this.npc = npc;
		this.enemy = enemy;
		this.state = state;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run(GoalSelector selector) {
		System.out.println("retaliating");
		 Map<Integer, ItemStack> weaponAndSlot = civNPC.hasWeaponInInventory(npc);
		 System.out.println(weaponAndSlot);
		if (weaponAndSlot != null) {
			selector.select(new EquipItemFromInventory(npc, state, weaponAndSlot));
		}
		npc.getNavigator().setTarget(enemy, true);
		while (state.equals("attacking")) {
			if (npc.getEntity().getLocation().distance(enemy.getLocation()) > 10) {
				state.setIdle();
				Bukkit.getServer().broadcastMessage(npc.getName() + " has given up the chase");
				selector.finishAndRemove();
			}
		}
		
	}

	@Override
	public boolean shouldExecute(GoalSelector selector) {
		if (state.equals("attacking")) {
			return true;
		}
		return false;
	}

}
