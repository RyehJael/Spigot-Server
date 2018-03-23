package com.civai.ryehjael.Goals;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.civai.ryehjael.Traits.TraitSurvivalist;

import net.citizensnpcs.api.npc.NPC;

public class EatingFoodThread extends Thread {
	private NPC npc;

	public EatingFoodThread(NPC npc) {
		this.npc = npc;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
        for(int i=0; i<6; i++) {
            try {
            	Thread.sleep(200);
            	npc.getEntity().getWorld().playSound(npc.getEntity().getLocation(), Sound.ENTITY_GENERIC_EAT, 1f, 1f);
            } catch (InterruptedException e) {
                break;
            }
        }
		ItemStack[] inv = npc.getTrait(net.citizensnpcs.api.trait.trait.Inventory.class).getContents();
		Bukkit.getServer().broadcastMessage(npc.getName() + " is eating " + inv[0].getType());
		if (inv[0].getAmount() > 1) {
			ItemStack newStack = inv[0];
			int amount = newStack.getAmount();
			newStack.setAmount(amount - 1);
			inv[0] = newStack;
			npc.getTrait(net.citizensnpcs.api.trait.trait.Inventory.class).setContents(inv);
		}
		else {
			inv[0] = null;
			npc.getTrait(net.citizensnpcs.api.trait.trait.Inventory.class).setContents(inv);
		}
		int hunger = npc.getTrait(TraitSurvivalist.class).getFoodLevel(npc) + 6;
		npc.getTrait(TraitSurvivalist.class).setFoodLevel(hunger);
	}
}
