package com.civai.ryehjael.CivNPC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.Trait;

public class CivNPC {

	Material[] weaponArray = { Material.DIAMOND_SWORD, Material.DIAMOND_AXE, Material.DIAMOND_SPADE,
			Material.DIAMOND_HOE, Material.GOLD_SWORD, Material.GOLD_AXE, Material.GOLD_SPADE, Material.GOLD_HOE,
			Material.IRON_SWORD, Material.IRON_AXE, Material.IRON_SPADE, Material.IRON_HOE, Material.STONE_SWORD,
			Material.STONE_AXE, Material.STONE_SPADE, Material.STONE_HOE };
	List<Material> weaponList = Arrays.asList(weaponArray);

	public Map<Integer, ItemStack> hasWeaponInInventory(NPC npc) {
		ItemStack[] inventory = npc.getTrait(net.citizensnpcs.api.trait.trait.Inventory.class).getContents();
		for (Material mat : weaponList) {
			for (int i = 0; i < inventory.length; i++) {
				if (inventory[i] != null) {
					if (inventory[i].getType().equals(mat)) {
						Map<Integer, ItemStack> itemAndSlot = new HashMap<Integer, ItemStack>();
						itemAndSlot.put(i, inventory[i]);
						return itemAndSlot;
					}
				}
			}
		}
		return null;
	}

	public Map<Integer, ItemStack> hasFoodInInventory(NPC npc) {
		ItemStack[] inventory = npc.getTrait(net.citizensnpcs.api.trait.trait.Inventory.class).getContents();
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] != null) {
				if (checkFoodList(inventory[i])) {
					Map<Integer, ItemStack> itemAndSlot = new HashMap<Integer, ItemStack>();
					itemAndSlot.put(i, inventory[i]);
					return itemAndSlot;
				}
			}
		}
		return null;
	}

	public Map<Integer, ItemStack> hasRawFoodInInventory(NPC npc) {
		ItemStack[] inventory = npc.getTrait(net.citizensnpcs.api.trait.trait.Inventory.class).getContents();
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] != null) {
				if (checkRawFoodList(inventory[i])) {
					Map<Integer, ItemStack> itemAndSlot = new HashMap<Integer, ItemStack>();
					itemAndSlot.put(i, inventory[i]);
					return itemAndSlot;
				}
			}
		}
		return null;
	}

	public Map<Integer, ItemStack> hasItemInInventory(NPC npc, Material item) {
		ItemStack[] inventory = npc.getTrait(net.citizensnpcs.api.trait.trait.Inventory.class).getContents();
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] != null) {
				if (inventory[i].getType().equals(item)) {
					Map<Integer, ItemStack> itemAndSlot = new HashMap<Integer, ItemStack>();
					itemAndSlot.put(i, inventory[i]);
					return itemAndSlot;
				}
			}
		}
		return null;
	}

	public Map<Integer, ItemStack> hasItemsInInventory(NPC npc, Material item, int amount) {
		ItemStack[] inventory = npc.getTrait(net.citizensnpcs.api.trait.trait.Inventory.class).getContents();
		Map<Integer, ItemStack> itemAndSlot = new HashMap<Integer, ItemStack>();
		int currentAmount = 0;
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] != null) {
				if (inventory[i].getType().equals(item)) {
					currentAmount += inventory[i].getAmount();
					if (currentAmount >= amount) {
						itemAndSlot.put(i, inventory[i]);
						return itemAndSlot;
					} else {
						itemAndSlot.put(i, inventory[i]);
					}

				}
			}
		}
		return null;
	}

	public double getHealth(NPC npc) {
		Player p = (Player) npc.getEntity();
		double health = p.getHealth();
		return health;

	}

	public boolean checkFoodList(ItemStack item) {
		Material[] foodArray = { Material.APPLE, Material.BAKED_POTATO, Material.BREAD, Material.COOKED_BEEF,
				Material.COOKED_CHICKEN, Material.COOKED_FISH, Material.GRILLED_PORK, Material.COOKED_MUTTON,
				Material.COOKED_RABBIT, Material.COOKIE, Material.GOLDEN_APPLE, Material.MUSHROOM_SOUP, Material.MELON,
				Material.PUMPKIN_PIE, Material.RABBIT_STEW };
		List<Material> foodList = new ArrayList<Material>();
		foodList.addAll(Arrays.asList(foodArray));
		for (Material food : foodList) {
			if (food.equals(item.getType())) {
				return true;
			}
		}
		return false;
	}

	public boolean checkRawFoodList(ItemStack item) {
		Material[] foodArray = { Material.RAW_BEEF, Material.RAW_CHICKEN, Material.RAW_FISH, Material.PORK,
				Material.MUTTON, Material.WHEAT };
		List<Material> foodList = new ArrayList<Material>();
		foodList.addAll(Arrays.asList(foodArray));
		for (Material food : foodList) {
			if (food.equals(item.getType())) {
				return true;
			}
		}
		return false;
	}

	public int adjustFoodLevel(NPC npc, int adjustable) {
		if (npc.getEntity() instanceof Player) {
			Player p = (Player) npc.getEntity();
			int newHunger = p.getFoodLevel();
			newHunger = newHunger + adjustable;
			if (newHunger < 0) {
				newHunger = 0;
			}
			p.setFoodLevel(newHunger);
			return newHunger;
		}
		return 0;
	}

	public double adjustHealthLevel(NPC npc, int adjustable) {
		if (npc.getEntity() instanceof Player) {
			Player p = (Player) npc.getEntity();
			double newHealth = p.getHealth();
			newHealth = newHealth + adjustable;
			if (newHealth < 1) {
				return 0;
			}
			p.setHealth(newHealth);
			return newHealth;
		}
		return 0;
	}

	public List<Entity> findNearbyPrey(NPC npc) {
		EntityType[] animals = { EntityType.PIG, EntityType.COW, EntityType.CHICKEN, EntityType.RABBIT,
				EntityType.SHEEP };
		List<Entity> nearbyAnimalList = new ArrayList<Entity>();
		for (Entity entity : npc.getEntity().getNearbyEntities(15, 15, 15)) {
			for (EntityType type : animals) {
				if (entity.getType().equals(type)) {
					nearbyAnimalList.add(entity);
				}
			}
		}
		return nearbyAnimalList;
	}

	public boolean isPreyNearby(NPC npc) {
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

	public Entity selectPrey(List<Entity> prey) {
		int cows = 0;
		int pigs = 0;
		int sheep = 0;
		int rabbits = 0;
		EntityType currentAnimal;
		for (Entity animal : prey) {
			currentAnimal = animal.getType();
			if (currentAnimal.equals(EntityType.COW)) {
				if (cows > 2) {
					return animal;
				}
				cows++;
			}
			if (currentAnimal.equals(EntityType.PIG)) {
				if (pigs > 2) {
					return animal;
				}
				pigs++;
			}
			if (currentAnimal.equals(EntityType.RABBIT)) {
				if (rabbits > 2) {
					return animal;
				}
				rabbits++;
			}
			if (currentAnimal.equals(EntityType.SHEEP)) {
				if (sheep > 2) {
					return animal;
				}
				sheep++;
			}
		}
		return prey.get(0);
	}

	public void setItemsInInventory(NPC npc, List<ItemStack> items) {
		ItemStack[] inv = npc.getTrait(net.citizensnpcs.api.trait.trait.Inventory.class).getContents();
		for (ItemStack droppedItem : items) {
			for (int i = 0; i < inv.length; i++) {
				if (droppedItem.isSimilar(inv[i]) && inv[i].getMaxStackSize() > inv[i].getAmount()) {
					int max = droppedItem.getMaxStackSize();
					int total = droppedItem.getAmount() + inv[i].getAmount();
					if (max >= total) {
						inv[i].setAmount(total);

						droppedItem = null;
						break;
					} else if (max < total) {
						total = max - total;
						inv[i].setAmount(max);
						droppedItem.setAmount(total);
					}
				}
			}
			for (int j = 0; j < inv.length; j++) {
				if (inv[j] == null) {
					inv[j] = droppedItem;
					break;
				}
			}
		}
		npc.getTrait(net.citizensnpcs.api.trait.trait.Inventory.class).setContents(inv);
	}

	public Location getLocationOfNearbyBlock(NPC npc, Material blockType, int minY, int maxY, int radius, int data) {
		Block block = npc.getEntity().getLocation().getBlock();
		World world = npc.getEntity().getWorld();
		int y = block.getLocation().getBlockY() + minY;

		for (int j = 0; j < (maxY - minY); j++) {
			int x = block.getLocation().getBlockX(), z = block.getLocation().getBlockZ(), dx = 0, dz = 1, length = 1,
					lenIt = 0, iterations = radius * radius;
			for (int i = 0; i < iterations; i++) {

				Location location = new Location(world, x, y, z);
				if (world.getBlockAt(location).getType().equals(blockType)
						&& world.getBlockAt(location).getData() == data) {
					return location;
				}
				x += dx;
				z += dz;
				lenIt++;

				if (lenIt == length) {
					lenIt = 0;
					int buffer = dx;
					dx = -dz;
					dz = buffer;
					if (dz == 0) {
						length++;
					}
				}

			}
			y++;
		}
		return null;
	}

	public Location getLocationOfNearbyBlock(NPC npc, Material blockType, int minY, int maxY, int radius) {
		Block block = npc.getEntity().getLocation().getBlock();
		World world = npc.getEntity().getWorld();

		int x = block.getLocation().getBlockX(), y = block.getLocation().getBlockY() - minY,
				z = block.getLocation().getBlockZ(), dx = 0, dz = -1, length = 1, lenIt = 0,
				iterations = radius * radius;
		for (int j = 0; j < (maxY - minY); j++) {
			for (int i = 0; i < iterations; i++) {
				Location location = new Location(world, x, y, z);
				if (world.getBlockAt(location).getType().equals(blockType)) {
					return location;
				}
				x += dx;
				z += dz;
				lenIt++;

				if (lenIt == length) {
					lenIt = 0;
				}
				int buffer = dx;
				dx = -dz;
				dz = buffer;
				if (dz == 0) {
					length++;
				}

			}
			y++;
		}
		return null;
	}

	public void delete(NPC npc) {
		npc.destroy();
	}

}
