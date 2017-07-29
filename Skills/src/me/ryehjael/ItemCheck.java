package me.ryehjael;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class ItemCheck {
	Map<String, Attribute> ScoreboardStats = getScoreboardStats();
	Map<String, Integer> DefaultStats = getDefaultStats();

	public Map<String, Attribute> getScoreboardStats() {
		Map<String, Attribute> scoreboardStats = new HashMap<String, Attribute>();

		scoreboardStats.put("AttackSpeed", Attribute.GENERIC_ATTACK_SPEED);
		scoreboardStats.put("AttackDamage", Attribute.GENERIC_ATTACK_DAMAGE);
		scoreboardStats.put("Armor", Attribute.GENERIC_ARMOR);
		scoreboardStats.put("Toughness", Attribute.GENERIC_ARMOR_TOUGHNESS);
		scoreboardStats.put("Knockback", Attribute.GENERIC_KNOCKBACK_RESISTANCE);
		scoreboardStats.put("Luck", Attribute.GENERIC_LUCK);
		scoreboardStats.put("MaxHealth", Attribute.GENERIC_MAX_HEALTH);
		scoreboardStats.put("MovementSpeed", Attribute.GENERIC_MOVEMENT_SPEED);

		return scoreboardStats;
	}

	public Map<String, Integer> getDefaultStats() {
		Map<String, Integer> defaultStats = new HashMap<String, Integer>();

		defaultStats.put("AttackSpeed", Integer.valueOf(400));
		defaultStats.put("AttackDamage", Integer.valueOf(200));
		defaultStats.put("Armor", Integer.valueOf(0));
		defaultStats.put("Toughness", Integer.valueOf(0));
		defaultStats.put("Knockback", Integer.valueOf(0));
		defaultStats.put("Luck", Integer.valueOf(0));
		defaultStats.put("MaxHealth", Integer.valueOf(600));
		defaultStats.put("MovementSpeed", Integer.valueOf(200));

		return defaultStats;
	}

	public void setAttributesToPlayer(Player p) {
		for (Map.Entry<String, Attribute> stat : this.ScoreboardStats.entrySet()) {
			String key = (String) stat.getKey();
			Object value = stat.getValue();
			if (key == "MovementSpeed") {
				float ms = (float) getScoreValue(p, key) / 10.0F;
				if (ms >= 1.0F) {
					ms = 1.0F;
				}
				p.setWalkSpeed(ms);
			} else {
				p.getAttribute((Attribute) value).setBaseValue(getScoreValue(p, key));
			}
		}
	}

	public ArrayList<ItemStack> getPlayerEquipment(Player p) {
		PlayerInventory inv = p.getInventory();
		ArrayList<ItemStack> equipment = new ArrayList<ItemStack>();
		equipment.add(inv.getItemInMainHand());
		equipment.add(inv.getItemInOffHand());
		equipment.add(inv.getHelmet());
		equipment.add(inv.getChestplate());
		equipment.add(inv.getLeggings());
		equipment.add(inv.getBoots());
		return equipment;
	}

	public void resetPlayerStats(Player p) {
		Scoreboard sc = p.getScoreboard();
		for (Map.Entry<String, Integer> entry : this.DefaultStats.entrySet()) {
			String obj = (String) entry.getKey();
			Object value = entry.getValue();
			Score score = sc.getObjective(obj).getScore(p.getName().toString());
			score.setScore(((Integer) value).intValue());
		}
	}

	public void loreHandler(ItemStack item, Player p) {
		List<String> lore = item.getItemMeta().getLore();
		for (int i = 0; i < lore.size(); i++) {
			addStats((String) lore.get(i), p);
		}
	}

	public void addStats(String loreLine, Player p) {
		for (String key : this.ScoreboardStats.keySet()) {
			if (loreLine.replaceAll("\\s", "").contains(key)) {
				double statValue = getStatValue(loreLine);
				addStatToPlayerScore(p, key, statValue);
				break;
			}
		}
	}

	public double getStatValue(String s) {
		String[] split = s.split(" ");
		double dbl = 0.0D;
		for (int i = 0; i < split.length; i++) {
			if ((split[i].matches(".*\\d+.*")) && (!split[i].contains(ChatColor.GRAY.toString()))) {
				dbl = Double.parseDouble(split[i]);
			}
		}
		return dbl;
	}

	public void addStatToPlayerScore(Player p, String objective, double value) {
		Score score = p.getScoreboard().getObjective(objective).getScore(p.getName().toString());
		value *= 100.0D;
		value += score.getScore();
		int newValue = (int) value;
		score.setScore(newValue);
	}

	public double getScoreValue(Player p, String objective) {
		int score = p.getScoreboard().getObjective(objective).getScore(p.getName().toString()).getScore();
		double value = score / 100.0D;
		return value;
	}

	public void updatePlayerStats(Player p) {
		ArrayList<ItemStack> playerEquipment = getPlayerEquipment(p);
		resetPlayerStats(p);
		for (int i = 0; i < playerEquipment.size(); i++) {
			if ((playerEquipment.get(i) != null) && (((ItemStack) playerEquipment.get(i)).getItemMeta() != null)
					&& (((ItemStack) playerEquipment.get(i)).getItemMeta().hasLore())) {
				loreHandler((ItemStack) playerEquipment.get(i), p);
			}
		}
	}

	public void updatePlayerStats(Player p, int newSlot) {
		ArrayList<ItemStack> playerEquipment = getPlayerEquipment(p);
		ItemStack hand = p.getInventory().getItem(newSlot);
		playerEquipment.set(0, hand);
		resetPlayerStats(p);
		for (int i = 0; i < playerEquipment.size(); i++) {
			if ((playerEquipment.get(i) != null) && (((ItemStack) playerEquipment.get(i)).getItemMeta() != null)) {
				if (((ItemStack) playerEquipment.get(i)).getItemMeta().hasLore()) {
					loreHandler((ItemStack) playerEquipment.get(i), p);
				}
			}
		}
		setAttributesToPlayer(p);
	}

	public void dependabilityHandler(Player p) {
		ItemStack item = p.getInventory().getItemInMainHand();
		List<String> lore = item.getItemMeta().getLore();
		if (lore == null) {
			return;
		}
		for (int i = 0; i < lore.size(); i++) {
			if (((String) lore.get(i)).contains("Dependability")) {
				int random = getRandomNumber(getStatValue((String) lore.get(i)));
				if (random == 1) {
					p.getInventory().getItemInMainHand().setAmount(-1);
					p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
				}
			}
		}
	}

	public int getRandomNumber(double max) {
		int random = (int) (Math.random() * max + 1.0D);
		return random;
	}
}
