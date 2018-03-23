package com.civai.ryehjael.Traits;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.ItemStack;

import com.civai.ryehjael.Main;
import com.civai.ryehjael.CivNPC.CivNPC;
import com.civai.ryehjael.CivNPC.NPCState;
import com.civai.ryehjael.Events.HourPassedEvent;
import com.civai.ryehjael.Goals.GoalEatFood;
import com.civai.ryehjael.Goals.GoalHarvestCrops;
import com.civai.ryehjael.Goals.GoalIdle;
import com.civai.ryehjael.Goals.FightOrFlight;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.ai.event.NavigationCompleteEvent;
import net.citizensnpcs.api.ai.goals.WanderGoal;
import net.citizensnpcs.api.event.NPCDamageByEntityEvent;
import net.citizensnpcs.api.event.NPCDeathEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.persistence.Persist;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;
import net.citizensnpcs.api.util.DataKey;

@TraitName("survivalist")
public class TraitSurvivalist extends Trait {
	
	public Trait trait;
	public DataKey key;
	Main plugin = null;

	@Persist("max_health")
	double max_health = 20;
	@Persist("hunger")
	int hunger = 20;
	@Persist("gender")
	String gender = "N";
	@Persist("health")
	double health = 20;

	public NPCState state = new NPCState(npc, "idle");
	public Entity attacker;
	CivNPC civNPC = new CivNPC();

	public TraitSurvivalist() {
		super("survivalist");
		plugin = (Main) Bukkit.getServer().getPluginManager().getPlugin("Main");
	}

	public void load(DataKey key) {
		// state = key.getString("state");
		hunger = key.getInt("hunger");
		gender = key.getString("gender");
		health = key.getDouble("health");
		max_health = key.getDouble("max_health");
		this.key = key;
	}

	// Save settings for this NPC. These values will be added to the citizens
	// saves.yml under this NPC.
	public void save(DataKey key) {
		// key.setString("state", state);
		key.setDouble("health", health);
		key.setInt("hunger", hunger);
		key.setString("gender", gender);
		key.setDouble("max_health", max_health);
	}

	public void run() {

	}

	@EventHandler
	public void entityDeath(EntityDeathEvent e) {
		if (CitizensAPI.getNPCRegistry().isNPC(e.getEntity().getKiller())) {
			NPC killer = CitizensAPI.getNPCRegistry().getNPC(e.getEntity().getKiller());
			if (killer == this.npc) {
				civNPC.setItemsInInventory(killer, e.getDrops());
				e.getDrops().clear();
				if (state.getState().equals("hunting")) {
					state.setIdle();
					npc.getNavigator().cancelNavigation();
				}
			}
		}
	}

	@EventHandler
	public void hourPassed(HourPassedEvent e) {

		if (CitizensAPI.getNPCRegistry().getNPC(npc.getEntity()) == npc) {
			Location npcLoc = npc.getEntity().getLocation();
			Location chunk = new Location(npc.getEntity().getWorld(), npcLoc.getX(), npcLoc.getY(), npcLoc.getZ());
			if (!chunk.getChunk().isLoaded()) {
				chunk.getChunk().load();
			}
			hunger--;
			if (!state.getState().equals("hungry") || !state.getState().equals("hunting")) {
				if (hunger < 1) {
					hunger = 0;
					state.setHungry();
					npc.getNavigator().cancelNavigation();
					npc.getDefaultGoalController().clear();
					npc.getDefaultGoalController().addGoal(new GoalEatFood(npc, state), 4);
				} else if (hunger < 14) {
					state.setHungry();
					npc.getDefaultGoalController().addGoal(new GoalEatFood(npc, state), 2);
				} else if (state.getState().equals("idle")) {
					npc.getDefaultGoalController().clear();
					npc.getDefaultGoalController().addGoal(new GoalIdle(npc, state), 2);
				} else {
					npc.getDefaultGoalController().addGoal(WanderGoal.createWithNPCAndRange(npc, 15, 15), 1);
				}
			}

		}

	}
	
	public void runIdleGoal(){
		npc.getDefaultGoalController().clear();
		npc.getNavigator().cancelNavigation();
		npc.getDefaultGoalController().addGoal(new GoalIdle(npc, state),  1);
	}
	
	@EventHandler
    public void onItemSpawn(ItemSpawnEvent e){
		ItemStack item = e.getEntity().getItemStack();
		for (Entity entity : e.getEntity().getNearbyEntities(3, 3, 3)){
			if (CitizensAPI.getNPCRegistry().isNPC(entity) && CitizensAPI.getNPCRegistry().getNPC(entity) == this.npc){
				List<ItemStack> toInv = new ArrayList<ItemStack>();
				toInv.add(item);
				civNPC.setItemsInInventory(npc, toInv);
				e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
				e.getEntity().remove();
				break;
			}
		}
	}

	@EventHandler
	public void navigationEnd(NavigationCompleteEvent e) {
		if (e.getNPC() == this.npc) {
			if (state.getState().equals("harvesting")) {
				state.setHarvesting();
				npc.getDefaultGoalController().addGoal(new GoalHarvestCrops(npc, state), 3);
			}
		}
	}

	@EventHandler
	public void death(NPCDeathEvent e) {
		if (e.getNPC() == this.npc && e.getNPC() != null) {
			npc.getDefaultGoalController().clear();
			npc.getNavigator().cancelNavigation();
			npc.destroy();
		}
	}

	@EventHandler
	public void unloadChunk(ChunkUnloadEvent e) {
		if (e.getChunk().equals(npc.getEntity().getLocation().getChunk())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void damagedByEntity(NPCDamageByEntityEvent e) {
		if (e.getNPC() == this.npc && npc.isSpawned()) {
			state.setAssaulted();
			e.getNPC().getDefaultGoalController().clear();
			e.getNPC().getDefaultGoalController().addGoal(new FightOrFlight(npc, state, e.getDamager()), 5);
		}
	}

	@EventHandler
	public void entityDamage(EntityDamageByEntityEvent e) {
		if (CitizensAPI.getNPCRegistry().isNPC(e.getDamager())) {
			NPC damager = CitizensAPI.getNPCRegistry().getNPC(e.getDamager());
			if (damager == this.npc && npc.isSpawned()) {
				// this damage value needs to be calculated by equipment
				// variables
				e.setDamage(4);
			}
		}
	}

	@EventHandler
	public void rightClick(NPCRightClickEvent e) {
		if (e.getNPC() == this.npc) {
			npc.getDefaultGoalController().clear();
			npc.getNavigator().cancelNavigation();
			if (civNPC.getLocationOfNearbyBlock(npc, Material.CROPS, 0, 2, 15, 7) != null){
			}
		}
	}

	public int getFoodLevel(NPC npc) {
		if (this.npc == npc) {
			return hunger;
		}
		return -1;
	}

	public void setFoodLevel(int hunger) {
		this.hunger = hunger;
	}

	public double getHealthLevel(NPC npc) {
		if (this.npc == npc) {
			return health;
		}
		return -1;
	}

	public void setHealthLevel(double health) {
		this.health = health;
	}

	public double getMaxHealthLevel(NPC npc) {
		if (this.npc == npc) {
			return max_health;
		}
		return -1;
	}

	@Override
	public void onAttach() {
		state.setIdle();
	}

	// Run code when the NPC is spawned. Note that npc.getBukkitEntity() will be
	// null until this method is called.
	// This is called AFTER onAttach and AFTER Load when the server is started.
	@Override
	public void onSpawn() {
		npc.setProtected(false);
		npc.getNavigator().getDefaultParameters().avoidWater(true);
		npc.getNavigator().getDefaultParameters().baseSpeed(.8f);
		npc.getDefaultGoalController().addGoal(new GoalIdle(npc, state), 1);
	}

	@Override
	public void onDespawn() {
	}

	// run code when the NPC is removed. Use this to tear down any repeating
	// tasks.
	@Override
	public void onRemove() {
	}
}
