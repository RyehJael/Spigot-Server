package com.civai.ryehjael.Goals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.civai.ryehjael.CivNPC.CivNPC;
import com.civai.ryehjael.CivNPC.NPCState;

import net.citizensnpcs.api.ai.Goal;
import net.citizensnpcs.api.ai.GoalSelector;
import net.citizensnpcs.api.npc.NPC;

public class GoalPrepareBread implements Goal {
	NPC npc;
	NPCState state;
	CivNPC civNPC = new CivNPC();

	public GoalPrepareBread(NPC npc, NPCState state) {
		this.npc = npc;
		this.state = state;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void run(GoalSelector selector) {
		int totalWheat = 0;
		int totalBread = 0;
		ItemStack wheatStack = null;
		List<ItemStack> addItems = new ArrayList<ItemStack>();
		Map<Integer, ItemStack> wheatStacks = civNPC.hasItemsInInventory(npc, Material.WHEAT, 3);
		for (Entry<Integer, ItemStack> map : wheatStacks.entrySet()) {
			ItemStack stack = map.getValue();
			wheatStack = stack;
			totalWheat += stack.getAmount();
			if (totalWheat > 3) {
				totalBread = ((totalWheat - (totalWheat % 3)) / 3);
				totalWheat -= totalWheat - (totalWheat % 3);
				if (totalBread >= 64) {
					ItemStack bread = new ItemStack(Material.BREAD, 1);
					bread.setAmount(64);
					addItems.add(bread);
					totalBread -= 64;
				}
			}
		}
		if (totalWheat > 0) {
			wheatStack.setAmount(totalWheat);
			addItems.add(wheatStack);
		}
		if (totalBread > 0){
			ItemStack bread = new ItemStack(Material.BREAD, 1);
			bread.setAmount(totalBread);
			addItems.add(bread);
		}
		civNPC.setItemsInInventory(npc, addItems);
		if (state.getState().equals("hungry")){
			selector.select(new GoalEatFood(npc, state));
		}
		selector.finishAndRemove();

	}

	@Override
	public boolean shouldExecute(GoalSelector selector) {
		if (civNPC.hasItemsInInventory(npc, Material.WHEAT, 3) != null) {
			return true;
		}
		return false;
	}

}
