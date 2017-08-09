package me.ryehjael;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FishingHandler {
	Map<String, List<Object>> Fish = getFish();
	Items items = new Items();
	ExperienceCalculator calc = new ExperienceCalculator();

	public void fishingHandler(PlayerFishEvent e) {
		Player p = e.getPlayer();
		if (e.getCaught() != null && e.getCaught().getType() == EntityType.DROPPED_ITEM) {
			Item d = (Item) e.getCaught();
			ItemStack item = d.getItemStack();
			if (p.getGameMode() == GameMode.SURVIVAL
					|| p.getGameMode() == GameMode.ADVENTURE && item.getType() == Material.RAW_FISH) {
				int plvl = calc.getSkillLevel(p, "Fishing");
				List<Integer> lvlOptions = new ArrayList<Integer>();
				List<List<Object>> potentialFish = new ArrayList<List<Object>>();
				for (Entry<String, List<Object>> fish : this.Fish.entrySet()) {
					List<Object> data = (List<Object>) fish.getValue();
					ItemStack material = (ItemStack) data.get(0);
					int levelReq = (int) data.get(1);
					if (material.isSimilar(item) && plvl >= levelReq) {
						potentialFish.add(data);
						lvlOptions.add(levelReq);
					}
				}
				Random rand = new Random();
				int  n = rand.nextInt(plvl + 1);
				int finalNumber = calc.closest(n, lvlOptions);
				for (List<Object> f : potentialFish) {
					if ((int) f.get(1) == finalNumber) {
						ItemStack newItem = (ItemStack) f.get(4);
						item.setItemMeta(newItem.getItemMeta());
						calc.addExperience(p, "Fishing", (int) f.get(2));
					}
				}
				

			}

		}
	}
	
	public Map<String, List<Object>> getFish() {
		  
		  Items items = new Items();
		  Map<String, List<Object>> fish = new HashMap<String, List<Object>>();
		  
		  List<Object> scrawny_fish = new ArrayList<Object>();
		  scrawny_fish.add(0, new ItemStack(Material.RAW_FISH, 1, (short)0));
		  scrawny_fish.add(1, (int) 0); //lvl required
		  scrawny_fish.add(2, (int)10); //exp gained
		  scrawny_fish.add(3, "Fishing"); //Scoreboard Association
		  scrawny_fish.add(4, items.RAW_SCRAWNY_FISH); //Item given to Player
		  fish.put("scrawny_fish", scrawny_fish);
		  
		  List<Object> trout = new ArrayList<Object>();
		  trout.add(0, new ItemStack(Material.RAW_FISH, 1, (short)0));
		  trout.add(1, (int) 50); //lvl required
		  trout.add(2, (int)50); //exp gained
		  trout.add(3, "Fishing"); //Scoreboard Association
		  trout.add(4, items.RAW_TROUT); //Item given to Player
		  fish.put("trout", trout);
		  

		  
		  return (Map<String, List<Object>>) fish;
	  }
}
