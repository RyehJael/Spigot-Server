package com.civai.ryehjael.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;

public class CommandNewCiv implements CommandExecutor{
	List<String> names = new ArrayList<String>();
	{
		names.add("Adam");
		names.add("Eve");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			if (command.getName().equalsIgnoreCase("newciv")) {
				for (String name : names) {
					NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, name);
					npc.addTrait(com.civai.ryehjael.Traits.TraitSurvivalist.class);
					npc.spawn(((Player) sender).getLocation());
				}
			}
		} 

		// If the player (or console) uses our command correct, we can return
		// true
		return true;
	}
}
