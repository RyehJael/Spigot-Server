package com.civai.ryehjael.Goals;

import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.inventory.ItemStack;

import com.civai.ryehjael.CivNPC.NPCState;

import net.citizensnpcs.api.ai.Goal;
import net.citizensnpcs.api.ai.GoalSelector;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.util.DataKey;

public class EquipItemFromInventory implements Goal {
	Map<Integer, ItemStack> inventorySlotAndItem;
	NPC npc;
	NPCState state;

	public EquipItemFromInventory(NPC npc, NPCState state, Map<Integer, ItemStack> inventorySlotAndItem) {
		this.inventorySlotAndItem = inventorySlotAndItem;
		this.npc = npc;
		this.state = state;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void run(GoalSelector selector) {
		ItemStack[] inv = npc.getTrait(net.citizensnpcs.api.trait.trait.Inventory.class).getContents();
		ItemStack oldHand = inv[0];
		for (Entry<Integer, ItemStack> entry : inventorySlotAndItem.entrySet()) {
			inv[0] = entry.getValue();
			inv[entry.getKey()] = oldHand;
		}
		npc.getTrait(net.citizensnpcs.api.trait.trait.Inventory.class).setContents(inv);
		if (state.getState().equals("hungry")) {
			selector.select(new GoalEatFood(npc, state));
		}
		if (state.getState().equals("assulted")) {
			selector.select(new FightOrFlight(npc, state, null));
		}
		selector.finishAndRemove();
	}

	@Override
	public boolean shouldExecute(GoalSelector selector) {
		if (inventorySlotAndItem != null) {
			return true;
		}
		return false;
	}
}
