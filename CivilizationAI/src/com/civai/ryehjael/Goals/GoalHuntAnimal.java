package com.civai.ryehjael.Goals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import com.civai.ryehjael.CivNPC.CivNPC;
import com.civai.ryehjael.CivNPC.NPCState;

import net.citizensnpcs.api.ai.Goal;
import net.citizensnpcs.api.ai.GoalSelector;
import net.citizensnpcs.api.npc.NPC;

public class GoalHuntAnimal implements Goal {
	NPC npc;
	NPCState state;
	CivNPC civNPC = new CivNPC();

	public GoalHuntAnimal(NPC npc, NPCState state) {
		this.npc = npc;
		this.state = state;
	}

	public Entity findNearbyPrey(NPC npc) {
		int pigs = 0;
		int cows = 0;
		int chickens = 0;
		int rabbits = 0;
		int sheep = 0;
		EntityType[] animals = { EntityType.PIG, EntityType.COW, EntityType.CHICKEN, EntityType.RABBIT,
				EntityType.SHEEP };
		List<Entity> nearbyEntities = npc.getEntity().getNearbyEntities(15, 15, 15);
		List<Entity> nearbyAnimals = new ArrayList<Entity>();
		if (nearbyEntities != null) {
			for (Entity entity : nearbyEntities) {
				for (EntityType animal : animals) {
					if (animal.equals(entity.getType())) {
						if (nearbyAnimals.add(entity)) {
							if (animal.equals(EntityType.PIG)) {
								pigs++;
							} else if (animal.equals(EntityType.COW)) {
								cows++;
							} else if (animal.equals(EntityType.CHICKEN)) {
								chickens++;
							} else if (animal.equals(EntityType.RABBIT)) {
								rabbits++;
							} else if (animal.equals(EntityType.SHEEP)) {
								sheep++;
							}
						} else
							return entity;
					}
				}
			}
		}

		return nearbyAnimals.get(0);
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void run(GoalSelector selector) {
		if (state.getState().equals("hungry")){
			npc.getNavigator().setTarget(findNearbyPrey(npc), true);
			state.setHunting();
		}
		System.out.println(npc.getName()+ " is hunting");
		if (!civNPC.isPreyNearby(npc)) {
			npc.getNavigator().cancelNavigation();
			state.setIdle();
			selector.finishAndRemove();
		}
		Map<Integer, ItemStack> rawFood = civNPC.hasRawFoodInInventory(npc);
		if (!npc.getNavigator().isNavigating()) {
			System.out.println("hunting ended");
			if (rawFood != null) {
				System.out.println("food Obtained");
			}
			npc.getNavigator().cancelNavigation();
			selector.finishAndRemove();
		}
		if (npc.getNavigator().isNavigating()) {
			System.out.println(npc.getName()+ " is navigating");
		}

	}

	@Override
	public boolean shouldExecute(GoalSelector selector) {
		if (state.getState().equals("hungry") || state.getState().equals("hunting")) {
			return true;
		}
		return false;
	}

}
