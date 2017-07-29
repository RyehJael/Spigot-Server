package me.ryehjael;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

public class ItemCategories {
	Items items = new Items();

	Map<ItemStack, Float> METALS = getMetals();
	Map<ItemStack, Float> STICKS = getSticks();
	Map<ItemStack, Float> PLANKS = getPlanks();
	Map<ItemStack, Float> LOGS = getLogs();
	Map<ItemStack, Float> MISC = getMisc();
	Map<ItemStack, Float> TOOLS = getTools();

	public Map<String, Map<ItemStack, Float>> getAllCategories() {
		Map<String, Map<ItemStack, Float>> allCategories = new HashMap<String, Map<ItemStack, Float>>();
		allCategories.put("Metals", METALS);
		allCategories.put("Sticks", STICKS);
		allCategories.put("Planks", PLANKS);
		allCategories.put("Logs", LOGS);
		allCategories.put("Misc", MISC);
		allCategories.put("Tools", TOOLS);

		return allCategories;

	}

	public Map<ItemStack, Float> getMisc() {
		Map<ItemStack, Float> misc = new HashMap<ItemStack, Float>();

		return misc;

	}

	public Map<ItemStack, Float> getMetals() {
		Map<ItemStack, Float> metals = new HashMap<ItemStack, Float>();

		metals.put(items.STONE, 1.0f);
		metals.put(items.IRON_INGOT, 1.25f);
		metals.put(items.GOLD_INGOT, 1.5f);
		metals.put(items.REDSTONE_BLOCK, 1.75f);
		metals.put(items.DIAMOND, 2.0f);
		metals.put(items.EMERALD, 2.25f);

		return metals;
	}

	public Map<ItemStack, Float> getSticks() {
		Map<ItemStack, Float> sticks = new HashMap<ItemStack, Float>();
		sticks.put(items.OAK_STICK, 1.0f);
		sticks.put(items.SPRUCE_STICK, 1.25f);
		sticks.put(items.BIRCH_STICK, 1.5f);
		sticks.put(items.MAHOGANY_STICK, 1.75f);
		sticks.put(items.ACACIA_STICK, 2.0f);
		sticks.put(items.BLACKWOOD_STICK, 2.25f);

		return sticks;

	}

	public Map<ItemStack, Float> getPlanks() {
		Map<ItemStack, Float> planks = new HashMap<ItemStack, Float>();
		planks.put(items.OAK_PLANK, 1.0f);
		planks.put(items.SPRUCE_PLANK, 1.25f);
		planks.put(items.BIRCH_PLANK, 1.5f);
		planks.put(items.MAHOGANY_PLANK, 1.75f);
		planks.put(items.ACACIA_PLANK, 2.0f);
		planks.put(items.BLACKWOOD_PLANK, 2.25f);

		return planks;

	}

	public Map<ItemStack, Float> getLogs() {
		Map<ItemStack, Float> log = new HashMap<ItemStack, Float>();
		log.put(items.OAK_LOG, 1.0f);
		log.put(items.SPRUCE_LOG, 1.25f);
		log.put(items.BIRCH_LOG, 1.5f);
		log.put(items.MAHOGANY_LOG, 1.75f);
		log.put(items.ACACIA_LOG, 2.0f);
		log.put(items.BLACKWOOD_LOG, 2.25f);

		return log;

	}
	
	public Map<ItemStack, Float> getTools() {
		Map<ItemStack, Float> m = new HashMap<ItemStack, Float>();
		m.put(items.WOOD_AXE1, 1.0f);
		m.put(items.WOOD_PICKAXE1, 1.0f);

		return m;

	}

	public String getCategoryName(ItemStack item) {
		String name = null;
		for (Map.Entry<String, Map<ItemStack, Float>> categories : getAllCategories().entrySet()) {
			String key = categories.getKey();
			Map<ItemStack, Float> value = categories.getValue();
			for (ItemStack cItem : value.keySet()) {
				if (cItem.isSimilar(item)) {
					name = key;
				}
			}

		}
		return name;

	}
	
	public Map<ItemStack, Float> getCategoryItems(String name) {
		Map<ItemStack, Float> catItem = null;
		for (Map.Entry<String, Map<ItemStack, Float>> categories : getAllCategories().entrySet()) {
			String key = categories.getKey();
			Map<ItemStack, Float> value = categories.getValue();
			if (key == name) {
				catItem = value;
			} 
		}
		return catItem;
	}

	
	
}
