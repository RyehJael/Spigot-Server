package com.civai.ryehjael.Goals;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.civai.ryehjael.Main;
import com.civai.ryehjael.CivNPC.CivNPC;
import com.civai.ryehjael.CivNPC.NPCState;
import com.civai.ryehjael.Traits.TraitSurvivalist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;

import net.citizensnpcs.api.ai.Goal;
import net.citizensnpcs.api.ai.GoalSelector;
import net.citizensnpcs.api.ai.tree.Selector;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.util.DataKey;

public class GoalEatFood implements Goal {

	private NPC npc;
	private GoalSelector selector;
	private NPCState state;
	CivNPC civNPC = new CivNPC();

	public GoalEatFood(NPC npc, NPCState state) {
		this.state = state;
		this.npc = npc;
	}

	public boolean preyNearby(NPC npc) {
		EntityType[] animals = { EntityType.PIG, EntityType.COW, EntityType.CHICKEN, EntityType.RABBIT,
				EntityType.SHEEP };
		for (Entity prey : npc.getEntity().getNearbyEntities(15, 15, 15)) {
			for (EntityType animal : animals) {
				if (animal.equals(prey.getType())) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean needsFood(NPC npc) {
		int hunger = npc.getTrait(TraitSurvivalist.class).getFoodLevel(npc);
		if (hunger < 11) {
			return true;
		} else if (npc.getTrait(TraitSurvivalist.class).getHealthLevel(npc) < 16 && hunger < 16) {
			return true;
		}

		return false;
	}


	@Override
	public void reset() {

	}

	@Override
	public void run(GoalSelector selector) {
		LivingEntity entity = (LivingEntity) npc.getEntity();
		int hunger = npc.getTrait(TraitSurvivalist.class).getFoodLevel(npc);
		if (hunger == 0) {
			double health = npc.getTrait(TraitSurvivalist.class).getHealthLevel(npc) - 1;
			npc.getTrait(TraitSurvivalist.class).setHealthLevel(health);
			double max_health = npc.getTrait(TraitSurvivalist.class).getMaxHealthLevel(npc);
			entity.setHealth(20);
			double damage = (max_health - health) - (max_health - 20);
			if (damage < 0) damage = 0;
			entity.damage(damage);

		}
		Map<Integer, ItemStack> slotWithFood = civNPC.hasFoodInInventory(npc);
		Map<Integer, ItemStack> slotWithRawFood = civNPC.hasRawFoodInInventory(npc);
		int foodSlot = -1;
		if (slotWithFood != null) {
			for (Entry<Integer, ItemStack> entry : slotWithFood.entrySet()) {
				foodSlot = entry.getKey();
			}
		}
		if (foodSlot == 0) {
			selector.select(new EatFoodInHand(npc, state));
			selector.finishAndRemove();
		} else if (slotWithFood != null) {
			selector.select(new EquipItemFromInventory(npc, state, slotWithFood));
			selector.finishAndRemove();
		} else if (civNPC.hasItemsInInventory(npc, Material.WHEAT, 3) != null) {
			System.out.println(npc.getName() + " has wheat");
			selector.select(new GoalPrepareBread(npc, state));
		} else if (civNPC.getLocationOfNearbyBlock(npc, Material.CROPS, -2, 4, 15, 7) != null){
			state.setFarming();
			selector.select(new GoalWalkToCrops(npc, state));
			selector.finishAndRemove();
		} else if (slotWithRawFood != null) {
			if (civNPC.getLocationOfNearbyBlock(npc, Material.FURNACE, -1, 3, 15) != null){
				
			}
			
			
			state.setIdle();
			selector.finishAndRemove();
		} else if (!preyNearby(npc)) {
			state.setIdle();
			selector.finishAndRemove();
		} else if (preyNearby(npc)) {
			npc.getNavigator().cancelNavigation();
			selector.select(new GoalHuntAnimal(npc, state));
		}
		selector.finishAndRemove();
	}

	@Override
	public boolean shouldExecute(GoalSelector selector) {
		if (npc != null && state.getState().equals("hungry")) {
			return true;
		}
		return false;
	}

}
