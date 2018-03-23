package com.civai.ryehjael.Goals;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;

import com.civai.ryehjael.CivNPC.CivNPC;
import com.civai.ryehjael.CivNPC.NPCState;
import com.civai.ryehjael.Goals.GoalWalkToCrops;

import net.citizensnpcs.api.ai.Goal;
import net.citizensnpcs.api.ai.GoalSelector;
import net.citizensnpcs.api.ai.goals.WanderGoal;
import net.citizensnpcs.api.npc.NPC;

public class GoalIdle implements Goal{
	NPC npc;
	NPCState state;
	CivNPC civNPC = new CivNPC();
	
	public GoalIdle(NPC npc, NPCState state) {
		this.npc = npc;
		this.state = state;
	}
	
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run(GoalSelector selector) {
		Location loc = civNPC.getLocationOfNearbyBlock(npc, Material.CROPS, -2, 3, 15, 7);
		if (loc != null) {
			state.setFarming();
			npc.getNavigator().cancelNavigation();
			npc.getNavigator().setTarget(loc);
			selector.select(new GoalWalkToCrops(npc, state));
		} else {
			selector.select(WanderGoal.createWithNPCAndRange(npc, 15, 15));
		}

	}

	@Override
	public boolean shouldExecute(GoalSelector selector) {
		if (state.getState().equals("idle")) {
			return true;
		}
		return false;
	}

}
