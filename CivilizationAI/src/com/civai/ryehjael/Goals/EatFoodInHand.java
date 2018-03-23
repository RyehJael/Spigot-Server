package com.civai.ryehjael.Goals;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.civai.ryehjael.Main;
import com.civai.ryehjael.CivNPC.NPCState;

import net.citizensnpcs.api.ai.Goal;
import net.citizensnpcs.api.ai.GoalSelector;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.util.DataKey;

public class EatFoodInHand implements Goal{
	private NPC npc;
	private NPCState state;
	
	public EatFoodInHand(NPC npc, NPCState state) {
		this.npc = npc;
		this.state = state;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run(GoalSelector selector) {
		state.setEating();
		System.out.println(state);
		EatingFoodThread eat = new EatingFoodThread(npc);
		eat.start();
		state.setIdle();
		selector.finishAndRemove();
	}

	@Override
	public boolean shouldExecute(GoalSelector selector) {
		if (state.getState().equals("hungry")) {
			System.out.println("Running EatFoodInHand");
			return true;
		}
		return false;
	}
}
