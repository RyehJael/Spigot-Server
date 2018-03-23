package com.civai.ryehjael.Goals;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import com.civai.ryehjael.CivNPC.CivNPC;
import com.civai.ryehjael.CivNPC.NPCState;

import net.citizensnpcs.api.ai.Goal;
import net.citizensnpcs.api.ai.GoalSelector;
import net.citizensnpcs.api.ai.goals.MoveToGoal;
import net.citizensnpcs.api.npc.NPC;

public class GoalWalkToCrops implements Goal{
	NPC npc;
	NPCState state;
	CivNPC civNPC = new CivNPC();
	
	public GoalWalkToCrops(NPC npc, NPCState state){
		this.npc = npc;
		this.state = state;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run(GoalSelector selector) {
		Location location = civNPC.getLocationOfNearbyBlock(npc, Material.CROPS, -2, 3, 15, 7);
		if (location == null) {
			state.setIdle();
			selector.finishAndRemove();
		}
		if (location != null && state.getState().equals("farming")){
			location.setX(location.getX() + .5);
			location.setY(location.getY() + .5);
			npc.getNavigator().setTarget(location);
			state.setHarvesting();
			selector.finishAndRemove();
			
		}
		
		
	}

	@Override
	public boolean shouldExecute(GoalSelector selector) {
		if (state.getState().equals("farming")){
			return true;
		}
		return false;
	}

}
