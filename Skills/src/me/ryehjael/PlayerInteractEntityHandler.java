package me.ryehjael;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import net.md_5.bungee.api.ChatColor;

public class PlayerInteractEntityHandler {
	
	
	
	public void interactHandler(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		Entity entity = e.getRightClicked();
		p.sendMessage(ChatColor.AQUA + entity.getName().toString() + ChatColor.WHITE + " Interactions!");
	}
}
