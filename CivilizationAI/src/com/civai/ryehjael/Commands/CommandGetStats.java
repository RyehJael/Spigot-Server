package com.civai.ryehjael.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.civai.ryehjael.Traits.TraitSurvivalist;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;

public class CommandGetStats implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			if (command.getName().equalsIgnoreCase("getstats")) {
				System.out.println("activated");
				NPC npc = CitizensAPI.getDefaultNPCSelector().getSelected(sender);
				if (npc == null) {
					sender.sendMessage("NPC not selected.");
					return false;
				}
				System.out.println(npc.getTrait(TraitSurvivalist.class).getFoodLevel(npc));
				System.out.println(npc.getTrait(TraitSurvivalist.class).getHealthLevel(npc));
				sender.sendMessage("Hunger: " + ChatColor.GREEN + Integer.toString(npc.getTrait(TraitSurvivalist.class).getFoodLevel(npc)));
				sender.sendMessage("Health: " + ChatColor.GREEN + Double.toString(npc.getTrait(TraitSurvivalist.class).getHealthLevel(npc)));
			}
		}

		// If the player (or console) uses our command correct, we can return
		// true
		return true;
	}
}
