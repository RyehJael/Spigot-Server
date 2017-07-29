package me.ryehjael;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreakHandler {
	
	Map<String, List<Object>> SkillBlocks = getSkillBlocks();
  
	public void blockBreakHandler(BlockBreakEvent e) {
	  ExperienceCalculator calc = new ExperienceCalculator();
	  ItemCheck itemCheck = new ItemCheck();
	  Player p = e.getPlayer();
	  Block b = e.getBlock();
	  if (p.isOp() && !isSkillBlock(b)) {
		  p.sendRawMessage(ChatColor.GREEN + b.getType().toString() + " " + b.getData());
		  }
	  if (p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE && isSkillBlock(b)) {
		  for (Entry<String, List<Object>> skillBlock : this.SkillBlocks.entrySet()) {
			  List<Object> data = (List<Object>) skillBlock.getValue();
			  ItemStack item = (ItemStack) data.get(0);

			  if (b.getType() == item.getType() && b.getData() == item.getData().getData()) {
				  if (calc.getSkillLevel(p, (String) data.get(3)) < (int) data.get(1)) {
					  e.setCancelled(true);
					  return;
				  }
				  calc.addExperience(p, (String) data.get(3), (int) data.get(2));
				  if (data.size() >= 6 && data.get(4) != null) {
					  p.getInventory().addItem((ItemStack) data.get(4));
				  } else {
					  giveBlockDrop(e);
				  }
			  }
		  }
		  itemCheck.dependabilityHandler(p);
	  }
	}
	
	public boolean isSkillBlock(Block b) {
		boolean bool = false;
		for (Entry<String, List<Object>> skillBlock : this.SkillBlocks.entrySet()) {
			List<Object> data = (List<Object>) skillBlock.getValue();
			ItemStack item = (ItemStack) data.get(0);
			if (b.getType() == item.getType() && b.getData() == item.getData().getData()) {
				bool = true;
			}
		}
		return bool;
	}
	
	
	public void giveBlockDrop(BlockBreakEvent e) {
	  Player p = e.getPlayer();
	  Block block = e.getBlock();
	  Collection<ItemStack> drops = block.getDrops(p.getInventory().getItemInMainHand());
	  for (ItemStack d : drops) {
		  p.getInventory().addItem(d);
		  }
	  }
  
  
  
  public Map<String, List<Object>> getSkillBlocks() {
	  
	  Items items = new Items();
	  Map<String, List<Object>> skillBlocks = new HashMap<String, List<Object>>();
	  
	  
	  //MINING
	  
	  List<Object> stone = new ArrayList<Object>();
	  stone.add(0, new ItemStack(Material.STONE, 1, (short)0));
	  stone.add(1, (int) 0);
	  stone.add(2, (int)2);
	  stone.add(3, "Mining");
	  stone.add(4, items.STONE);
	  stone.add(5, (int)2);
	  skillBlocks.put("stone", stone);
	  
	  List<Object> granite = new ArrayList<Object>();
	  granite.add(0, new ItemStack(Material.STONE, 1, (short)1));
	  granite.add(1, (int) 0);
	  granite.add(2, (int)4);
	  granite.add(3, "Mining");
	  granite.add(4, items.GRANITE);
	  granite.add(5, (int)40);
	  skillBlocks.put("granite", granite);
	  
	  List<Object> diorite = new ArrayList<Object>();
	  diorite.add(0, new ItemStack(Material.STONE, 1, (short)3));
	  diorite.add(1, (int) 0);
	  diorite.add(2, (int)8);
	  diorite.add(3, "Mining");
	  diorite.add(4, items.DIORITE);
	  diorite.add(5, (int)80);
	  skillBlocks.put("diorite", diorite);
	  
	  List<Object> andesite = new ArrayList<Object>();
	  andesite.add(0, new ItemStack(Material.STONE, 1, (short)5));
	  andesite.add(1, (int) 0);
	  andesite.add(2, (int)8);
	  andesite.add(3, "Mining");
	  andesite.add(4, items.ANDESITE);
	  andesite.add(5, (int)80);
	  skillBlocks.put("andesite", andesite);
	  
	  List<Object> iron = new ArrayList<Object>();
	  iron.add(0, new ItemStack(Material.IRON_ORE, 1, (short)0));
	  iron.add(1, (int) 75);
	  iron.add(2, (int)16);
	  iron.add(3, "Mining");
	  iron.add(4, items.IRON_ORE);
	  iron.add(5, (int)160);
	  skillBlocks.put("iron", iron);
	  
	  List<Object> coal = new ArrayList<Object>();
	  coal.add(0, new ItemStack(Material.COAL_ORE, 1, (short)0));
	  coal.add(1, (int) 0);
	  coal.add(2, (int)32);
	  coal.add(3, "Mining");
	  coal.add(4, items.COAL_ORE);
	  coal.add(5, (int)320);
	  skillBlocks.put("coal", coal);
	  
	  List<Object> gold = new ArrayList<Object>();
	  gold.add(0, new ItemStack(Material.GOLD_ORE, 1, (short)0));
	  gold.add(1, (int) 150);
	  gold.add(2, (int)64);
	  gold.add(3, "Mining");
	  gold.add(4, items.GOLD_ORE);
	  gold.add(5, (int)640);
	  skillBlocks.put("gold", gold);
	  
	  List<Object> redstone = new ArrayList<Object>();
	  redstone.add(new ItemStack(Material.REDSTONE_ORE, 1, (short)0));
	  redstone.add((int) 225);
	  redstone.add((int)96);
	  redstone.add("Mining");
	  redstone.add((int)960);
	  skillBlocks.put("redstone", redstone);
	  
	  List<Object> lapis = new ArrayList<Object>();
	  lapis.add(new ItemStack(Material.LAPIS_ORE, 1, (short)0));
	  lapis.add((int) 225);
	  lapis.add((int)96);
	  lapis.add("Mining");
	  lapis.add((int)960);
	  skillBlocks.put("lapis", lapis);
	  
	  List<Object> diamond = new ArrayList<Object>();
	  diamond.add(new ItemStack(Material.DIAMOND_ORE, 1, (short)0));
	  diamond.add((int) 300);
	  diamond.add((int)128);
	  diamond.add("Mining");
	  diamond.add((int)1280);
	  skillBlocks.put("diamond", diamond);
	  
	  List<Object> emerald = new ArrayList<Object>();
	  emerald.add(new ItemStack(Material.EMERALD_ORE, 1, (short)0));
	  emerald.add((int) 375);
	  emerald.add((int)256);
	  emerald.add("Mining");
	  emerald.add((int)2560);
	  skillBlocks.put("emerald", emerald);
	  
	  //WOODCUTTING
	  
	  List<Object> oakLog = new ArrayList<Object>();
	  oakLog.add(0, new ItemStack(Material.LOG, 1, (short)0));
	  oakLog.add(1, (int) 0);
	  oakLog.add(2, (int)2);
	  oakLog.add(3, "Woodcutting");
	  oakLog.add(4, items.OAK_LOG);
	  oakLog.add(5, (int)200);
	  skillBlocks.put("oakLog", oakLog);
	  
	  List<Object> spruceLog = new ArrayList<Object>();
	  spruceLog.add(0, new ItemStack(Material.LOG, 1, (short)1));
	  spruceLog.add(1, (int) 75);
	  spruceLog.add(2, (int)4);
	  spruceLog.add(3, "Woodcutting");
	  spruceLog.add(4, items.SPRUCE_LOG);
	  spruceLog.add(5, (int)400);
	  skillBlocks.put("spruceLog", spruceLog);
	  
	  List<Object> birchLog = new ArrayList<Object>();
	  birchLog.add(0, new ItemStack(Material.LOG, 1, (short)2));
	  birchLog.add(1, (int) 150);
	  birchLog.add(2, (int)4);
	  birchLog.add(3, "Woodcutting");
	  birchLog.add(4, items.BIRCH_LOG);
	  birchLog.add(5, (int)400);
	  skillBlocks.put("birchLog", birchLog);
	  
	  List<Object> mahoganyLog = new ArrayList<Object>();
	  mahoganyLog.add(0, new ItemStack(Material.LOG, 1, (short)3));
	  mahoganyLog.add(1, (int) 225);
	  mahoganyLog.add(2, (int)4);
	  mahoganyLog.add(3, "Woodcutting");
	  mahoganyLog.add(4, items.MAHOGANY_LOG);
	  mahoganyLog.add(5, (int)100);
	  skillBlocks.put("mahoganyLog", mahoganyLog);
	  
	  List<Object> acaciaLog = new ArrayList<Object>();
	  acaciaLog.add(0, new ItemStack(Material.LOG_2, 1, (short)0));
	  acaciaLog.add(1, (int) 300);
	  acaciaLog.add(2, (int)4);
	  acaciaLog.add(3, "Woodcutting");
	  acaciaLog.add(4, items.ACACIA_LOG);
	  acaciaLog.add(5, (int)100);
	  skillBlocks.put("acaciaLog", acaciaLog);
	  
	  List<Object> blackwoodLog = new ArrayList<Object>();
	  blackwoodLog.add(0, new ItemStack(Material.LOG_2, 1, (short)1)); //Material
	  blackwoodLog.add(1, (int) 375); //Level Required
	  blackwoodLog.add(2, (int) 4); //Experience Gained
	  blackwoodLog.add(3, "Woodcutting"); //Name
	  blackwoodLog.add(4, items.BLACKWOOD_LOG); //Returned Item
	  blackwoodLog.add(5, (int) 200); //Time till Re-spawn
	  skillBlocks.put("blackwoodLog", blackwoodLog);
	  
	  return (Map<String, List<Object>>) skillBlocks;
  }
}
