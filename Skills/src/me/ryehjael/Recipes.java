package me.ryehjael;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Recipes {
	ItemCategories categories = new ItemCategories();
	Items items = new Items();

	public Map<List<ItemStack>, ItemStack> getRecipeList() {
		Map<List<ItemStack>, ItemStack> allRecipes = new HashMap<List<ItemStack>, ItemStack>();

		{
			List<ItemStack> woodcuttingAxe1Recipe = new ArrayList<ItemStack>();
			woodcuttingAxe1Recipe.add(0, null);
			woodcuttingAxe1Recipe.add(1, items.STONE);
			woodcuttingAxe1Recipe.add(2, items.STONE);
			woodcuttingAxe1Recipe.add(3, null);
			woodcuttingAxe1Recipe.add(4, items.OAK_STICK);
			woodcuttingAxe1Recipe.add(5, items.STONE);
			woodcuttingAxe1Recipe.add(6, null);
			woodcuttingAxe1Recipe.add(7, items.OAK_STICK);
			woodcuttingAxe1Recipe.add(8, null);
			allRecipes.put(woodcuttingAxe1Recipe, items.WOOD_AXE1);
		}

		{
			List<ItemStack> pickaxe1Recipe = new ArrayList<ItemStack>();
			pickaxe1Recipe.add(0, items.STONE);
			pickaxe1Recipe.add(1, items.STONE);
			pickaxe1Recipe.add(2, items.STONE);
			pickaxe1Recipe.add(3, null);
			pickaxe1Recipe.add(4, items.OAK_STICK);
			pickaxe1Recipe.add(5, null);
			pickaxe1Recipe.add(6, null);
			pickaxe1Recipe.add(7, items.OAK_STICK);
			pickaxe1Recipe.add(8, null);
			allRecipes.put(pickaxe1Recipe, items.WOOD_PICKAXE1);
		}


		{
			List<ItemStack> sword1Recipe = new ArrayList<ItemStack>();
			sword1Recipe.add(0, null);
			sword1Recipe.add(1, items.STONE);
			sword1Recipe.add(2, null);
			sword1Recipe.add(3, null);
			sword1Recipe.add(4, items.STONE);
			sword1Recipe.add(5, null);
			sword1Recipe.add(6, null);
			sword1Recipe.add(7, items.OAK_STICK);
			sword1Recipe.add(8, null);
			allRecipes.put(sword1Recipe, items.WOOD_SWORD1);
		}


		{
			List<ItemStack> oakStickRecipe = new ArrayList<ItemStack>();
			oakStickRecipe.add(0, null);
			oakStickRecipe.add(1, null);
			oakStickRecipe.add(2, null);
			oakStickRecipe.add(3, null);
			oakStickRecipe.add(4, items.OAK_PLANK);
			oakStickRecipe.add(5, null);
			oakStickRecipe.add(6, null);
			oakStickRecipe.add(7, items.OAK_PLANK);
			oakStickRecipe.add(8, null);
			ItemStack oakSticks = items.OAK_STICK;
			oakSticks.setAmount(2);
			allRecipes.put(oakStickRecipe, oakSticks);
		}

		{
			List<ItemStack> oakPlankRecipe = new ArrayList<ItemStack>();
			oakPlankRecipe.add(0, null);
			oakPlankRecipe.add(1, null);
			oakPlankRecipe.add(2, null);
			oakPlankRecipe.add(3, null);
			oakPlankRecipe.add(4, items.OAK_LOG);
			oakPlankRecipe.add(5, null);
			oakPlankRecipe.add(6, null);
			oakPlankRecipe.add(7, null);
			oakPlankRecipe.add(8, null);
			allRecipes.put(oakPlankRecipe, items.OAK_PLANK);
		}


		return allRecipes;
	}

	public void recipeHandler(PrepareItemCraftEvent e) {

		ItemStack[] matrix = e.getInventory().getMatrix();

		for (Entry<List<ItemStack>, ItemStack> allRecipes : getRecipeList().entrySet()) {
			List<ItemStack> recipe = allRecipes.getKey();

			ItemStack result = null;
			int numberOfRecipeItems = 0;
			float totalMultiplier = 0;
			boolean isCompleteRecipe = false;

			for (int i = 0; i < matrix.length; i++) {
				ItemStack ri = recipe.get(i);
				if (matrix[i] == null && ri == null) {
					;
				} else if (matrix[i] != null && ri == null) {
					isCompleteRecipe = false;
					break;
				} else if (ri != null && matrix[i] == null) {
					isCompleteRecipe = false;
					break;
				} else if (matrix[i] != null && ri != null) {
					String catName = categories.getCategoryName(ri);
					if (catName == "Misc") {
						if (matrix[i].isSimilar(ri)) {
							isCompleteRecipe = true;
						}
					} else if (catName != null) {
						ItemStack finalItem = null;
						Map<ItemStack, Float> catItem = categories.getCategoryItems(catName);
						if (catItem != null) {
							finalItem = matchRecipeMatrix(matrix[i], catItem);
							if (finalItem != null & matrix[i].isSimilar(finalItem)) {
								isCompleteRecipe = true;
								totalMultiplier += getMultiplier(finalItem, categories.getCategoryItems(catName));
								numberOfRecipeItems += 1;	
							} else {
								isCompleteRecipe = false;
								break;
							}
						} 
					}
				}
			}
			if (isCompleteRecipe == true) {
				totalMultiplier = (totalMultiplier/numberOfRecipeItems);
				totalMultiplier = round(totalMultiplier, 2);
				result = allRecipes.getValue();
				if (categories.getCategoryName(result) == "Tools") {
					ItemMeta meta = result.getItemMeta();
					String name = meta.getDisplayName();
					name = (getItemTierChatColor(totalMultiplier) + name + " " + totalMultiplier);
					meta.setDisplayName(name);
					
					List<String> lore = meta.getLore();
					meta.setLore(setLoreMultiplier(lore, totalMultiplier));
					
					result.setItemMeta(meta);
					e.getInventory().setResult(result);
					
				} else if (categories.getCategoryName(result) != "Misc"){
					int amount =result.getAmount();
					Map<ItemStack, Float> cat = categories.getCategoryItems(categories.getCategoryName(result));
					for (Entry<ItemStack, Float> map : cat.entrySet()) {
						
						Float f = map.getValue(); 
						
						if (totalMultiplier - .12 <= f && totalMultiplier - .12 >= f - .25) {
							result = map.getKey();
							result.setAmount(amount);
							e.getInventory().setResult(result);
						}
					}
				} else {
					
				}

				

			}
		}
	}
	
	public List<String> setLoreMultiplier(List<String> l, float m) {
		List<String> lore = l;
		int x = 0;
		int y = 0;
		for (int i = 0; i < l.size(); i++) {

			String[] split = l.get(i).split(" ");
			for (int j = 0; j < split.length; j++) {
				if ((split[j].matches(".*\\d+.*")) && (!split[j].contains(ChatColor.GRAY.toString()))) {
					x = Integer.parseInt(split[j]);
					y = (int) (x * m);
				}
			}
			if (lore.get(i).contains("Dependability")) {
				y = (int) (x * (Math.pow(m, 4)));
			}
			String newLore = lore.get(i).replaceAll("\\b" + Integer.toString(x) + "\\b", Integer.toString(y));
			lore.set(i, newLore);
		}
		return lore;
	}
	
	public ChatColor getItemTierChatColor(float f) {
		ChatColor c = ChatColor.GRAY;
		if (f >= 2.25) {
			c = ChatColor.DARK_PURPLE;
		} else if (f >= 2.0) {
			c = ChatColor.LIGHT_PURPLE;
		} else if (f >= 1.75) {
			c = ChatColor.DARK_BLUE;
		} else if (f >= 1.5) {
			c = ChatColor.BLUE;
		} else if (f >= 1.25) {
			c = ChatColor.GREEN;
		}
		
		return c;
	}

	public float getMultiplier(ItemStack matrixItem, Map<ItemStack, Float> category) {
		float f = 0;
		for (Entry<ItemStack, Float> categoryItem : category.entrySet()) {
			ItemStack item = categoryItem.getKey();
			float multiplier = categoryItem.getValue();
			if (matrixItem.isSimilar(item)) {
				f = multiplier;
				break;
			}
		}
		return f;
	}

	public ItemStack matchRecipeMatrix(ItemStack item, Map<ItemStack, Float> category) {
		ItemStack matchedItem = null;
		if (category != null) {
			for (ItemStack rItem : category.keySet()) {
				if (item.isSimilar(rItem)) {
					matchedItem = rItem;
					break;
				}
			}
		}

		return matchedItem;
	}
	
    public float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
}
