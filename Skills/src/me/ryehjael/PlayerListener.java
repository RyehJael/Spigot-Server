package me.ryehjael;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
	public PlayerListener(Main plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void prepareRecipe(PrepareItemCraftEvent e) {
		Recipes recipes = new Recipes();
		recipes.recipeHandler(e);
	}

	@EventHandler
	public void playerJoin(PlayerJoinEvent e) {
		ServerScoreboard ssb = new ServerScoreboard();
		ssb.ScoreboardConfigure();
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		BlockBreakHandler handler = new BlockBreakHandler();
		handler.blockBreakHandler(event);
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
	}

	@EventHandler
	public void closeInventory(InventoryCloseEvent e) {
		ItemCheck ic = new ItemCheck();
		Player p = (Player) e.getPlayer();
		ic.updatePlayerStats(p);
	}

	@EventHandler
	public void entityInteract(PlayerInteractEntityEvent e) {
		Items items = new Items();
		Player entity = (Player) e.getRightClicked();
		entity.getInventory().setItemInMainHand(items.SPRUCE_LOG);
	}

	@EventHandler
	public void changeItem(PlayerItemHeldEvent e) {
		ItemCheck ic = new ItemCheck();
		int newSlot = e.getNewSlot();
		Player p = e.getPlayer();
		ic.updatePlayerStats(p, newSlot);
	}

	// @EventHandler
	// public void onInventoryClick(InventoryClickEvent event)
	// {
	// Inventories inv = new Inventories();
	// inv.onInventoryClick(event);
	// }

	@EventHandler
	public void advancement(PlayerAdvancementDoneEvent e) {
		e.getAdvancement().getCriteria();

	}

	@EventHandler
	public void interact(PlayerInteractEntityEvent e) {
		PlayerInteractEntityHandler handler = new PlayerInteractEntityHandler();
		handler.interactHandler(e);
	}

	@EventHandler
	public void fish(PlayerFishEvent e) {
		FishingHandler fish = new FishingHandler();
		fish.fishingHandler(e);
	}

	@EventHandler
	public void onDamageDealt(EntityDamageByEntityEvent e) {
		DamageHandler handler = new DamageHandler();
		handler.damageHandler(e);
	}
}
