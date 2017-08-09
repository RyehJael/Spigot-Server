package me.ryehjael;

import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items
{
  ItemStack WOOD_AXE1 = getWoodAxe1();
  ItemStack WOOD_PICKAXE1 = getWoodPickaxe1();
  ItemStack WOOD_SWORD1 = getWoodSword1();
  
  //Wood Planks
  ItemStack OAK_PLANK = getOakPlank();
  ItemStack SPRUCE_PLANK = getSprucePlank();
  ItemStack BIRCH_PLANK = getBirchPlank();
  ItemStack MAHOGANY_PLANK = getMahoganyPlank();
  ItemStack ACACIA_PLANK = getAcaciaPlank();
  ItemStack BLACKWOOD_PLANK = getBlackwoodPlank();
  
  //Stones
  ItemStack GRANITE = getGranite();
  ItemStack DIORITE = getDiorite();
  ItemStack ANDESITE = getAndesite();
  
  //Ores
  ItemStack IRON_ORE = getIronOre();
  ItemStack COAL_ORE = getCoalOre();
  ItemStack GOLD_ORE = getGoldOre();
  
  
  //Metals
  ItemStack STONE = getStone();
  ItemStack IRON_INGOT = getIronIngot();
  ItemStack GOLD_INGOT = getGoldIngot();
  ItemStack REDSTONE_BLOCK = getRedstoneBlock();
  ItemStack DIAMOND = getDiamond();
  ItemStack EMERALD = getEmerald();
  
  //Sticks
  ItemStack OAK_STICK = getOakStick();
  ItemStack SPRUCE_STICK = getSpruceStick();
  ItemStack BIRCH_STICK = getBirchStick();
  ItemStack MAHOGANY_STICK = getMahoganyStick();
  ItemStack ACACIA_STICK = getAcaciaStick();
  ItemStack BLACKWOOD_STICK = getBlackwoodStick();
  
  //Logs
  ItemStack OAK_LOG = getOakLog();
  ItemStack SPRUCE_LOG = getSpruceLog();
  ItemStack BIRCH_LOG = getBirchLog();
  ItemStack MAHOGANY_LOG = getMahoganyLog();
  ItemStack ACACIA_LOG = getAcaciaLog();
  ItemStack BLACKWOOD_LOG = getBlackwoodLog();
  
  //Fish
  ItemStack RAW_SCRAWNY_FISH = getRawScrawnyFish();
  ItemStack RAW_TROUT = getRawTrout();

public ItemStack getWoodAxe1()
  {
    ItemStack woodAxe1 = new ItemStack(Material.WOOD_AXE, 1, (short)1);
    ItemMeta meta = woodAxe1.getItemMeta();
    meta.setUnbreakable(true);
    meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE });
    meta.setLore(createLore(new String[] { "", "When in main hand:", " 1 Attack Speed", " 60 Dependability" }));
    meta.setDisplayName("Woodcutting Axe");
    woodAxe1.setItemMeta(meta);
    return woodAxe1;
  }
  
  public ItemStack getWoodPickaxe1()
  {
    ItemStack woodPickaxe1 = new ItemStack(Material.WOOD_PICKAXE, 1, (short)1);
    ItemMeta meta = woodPickaxe1.getItemMeta();
    meta.setUnbreakable(true);
    meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE });
    meta.setLore(createLore(new String[] { "", "When in main hand:", " 1 Attack Speed", " 1 Attack Damage", " 60 Dependability" }));
    woodPickaxe1.setItemMeta(meta);
    return woodPickaxe1;
  }
  
  public ItemStack getWoodSword1()
  {
    ItemStack woodSword1 = new ItemStack(Material.WOOD_SWORD, 1, (short)1);
    ItemMeta meta = woodSword1.getItemMeta();
    meta.setUnbreakable(true);
    meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE });
    meta.setLore(createLore(new String[] { "", "When in main hand:", " 1 Attack Speed", " 2 Attack Damage", " 60 Dependability" }));
    woodSword1.setItemMeta(meta);
    return woodSword1;
  }
  
  public ItemStack getOakLog()
  {
    ItemStack oakLog = new ItemStack(Material.LOG, 1);
    ItemMeta meta = oakLog.getItemMeta();
    meta.setDisplayName("Oak Log");
    oakLog.setItemMeta(meta);
    return oakLog;
  }
  
  public ItemStack getSpruceLog()
  {
    ItemStack spruceLog = new ItemStack(Material.LOG, 1, (short)1);
    ItemMeta meta = spruceLog.getItemMeta();
    meta.setDisplayName("Spruce Log");
    spruceLog.setItemMeta(meta);
    return spruceLog;
  }
  
  public ItemStack getBirchLog()
  {
    ItemStack log = new ItemStack(Material.LOG, 1, (short)2);
    ItemMeta meta = log.getItemMeta();
    meta.setDisplayName("Birch Log");
    log.setItemMeta(meta);
    return log;
  }
  
  public ItemStack getMahoganyLog()
  {
    ItemStack log = new ItemStack(Material.LOG, 1, (short)3);
    ItemMeta meta = log.getItemMeta();
    meta.setDisplayName("Mahogany Log");
    log.setItemMeta(meta);
    return log;
  }
  
  public ItemStack getAcaciaLog()
  {
    ItemStack log = new ItemStack(Material.LOG_2, 1, (short)0);
    ItemMeta meta = log.getItemMeta();
    meta.setDisplayName("Acacia Log");
    log.setItemMeta(meta);
    return log;
  }
  
  public ItemStack getBlackwoodLog()
  {
    ItemStack log = new ItemStack(Material.LOG_2, 1, (short)1);
    ItemMeta meta = log.getItemMeta();
    meta.setDisplayName("Blackwood Log");
    log.setItemMeta(meta);
    return log;
  }
  
  public ItemStack getStone()
  {
    ItemStack stone = new ItemStack(Material.STONE, 1, (short)0);
    ItemMeta meta = stone.getItemMeta();
    stone.setItemMeta(meta);
    return stone;
  }
  
  public ItemStack getGranite()
  {
    ItemStack stone = new ItemStack(Material.STONE, 1, (short)1);
    ItemMeta meta = stone.getItemMeta();
    stone.setItemMeta(meta);
    return stone;
  }
  
  public ItemStack getDiorite()
  {
    ItemStack stone = new ItemStack(Material.STONE, 1, (short)3);
    ItemMeta meta = stone.getItemMeta();
    stone.setItemMeta(meta);
    return stone;
  }

  public ItemStack getAndesite()
  {
    ItemStack stone = new ItemStack(Material.STONE, 1, (short)5);
    ItemMeta meta = stone.getItemMeta();
    stone.setItemMeta(meta);
    return stone;
  }
  
  public ItemStack getIronOre()
  {
    ItemStack item = new ItemStack(Material.IRON_ORE, 1);
    ItemMeta meta = item.getItemMeta();
    item.setItemMeta(meta);
    return item;
  }
  
  public ItemStack getCoalOre()
  {
    ItemStack item = new ItemStack(Material.COAL_ORE, 1);
    ItemMeta meta = item.getItemMeta();
    item.setItemMeta(meta);
    return item;
  }
  
  public ItemStack getGoldOre()
  {
    ItemStack item = new ItemStack(Material.GOLD_ORE, 1);
    ItemMeta meta = item.getItemMeta();
    item.setItemMeta(meta);
    return item;
  }
  
  public ItemStack getIronIngot()
  {
    ItemStack ironIngot = new ItemStack(Material.IRON_INGOT, 1);
    ItemMeta meta = ironIngot.getItemMeta();
    ironIngot.setItemMeta(meta);
    return ironIngot;
  }
  
  public ItemStack getGoldIngot()
  {
    ItemStack goldIngot = new ItemStack(Material.GOLD_INGOT, 1);
    ItemMeta meta = goldIngot.getItemMeta();
    goldIngot.setItemMeta(meta);
    return goldIngot;
  }
  
  public ItemStack getDiamond()
  {
    ItemStack diamond = new ItemStack(Material.DIAMOND, 1);
    ItemMeta meta = diamond.getItemMeta();
    diamond.setItemMeta(meta);
    return diamond;
  }
  
  public ItemStack getRedstoneBlock()
  {
    ItemStack redstoneBlock = new ItemStack(Material.REDSTONE_BLOCK, 1);
    ItemMeta meta = redstoneBlock.getItemMeta();
    redstoneBlock.setItemMeta(meta);
    return redstoneBlock;
  }
  
  public ItemStack getEmerald()
  {
    ItemStack emerald = new ItemStack(Material.EMERALD, 1);
    ItemMeta meta = emerald.getItemMeta();
    emerald.setItemMeta(meta);
    return emerald;
  }
  
  public ItemStack getOakStick()
  {
    ItemStack oakStick = new ItemStack(Material.STICK, 1);
    ItemMeta meta = oakStick.getItemMeta();
    meta.setDisplayName("Oak Stick");
    oakStick.setItemMeta(meta);
    return oakStick;
  }

public ItemStack getSpruceStick()
  {
    ItemStack spruceStick = new ItemStack(Material.STICK, 1);
    ItemMeta meta = spruceStick.getItemMeta();
    meta.setDisplayName("Spruce Stick");
    spruceStick.setItemMeta(meta);
    return spruceStick;
  }

public ItemStack getBirchStick()
{
  ItemStack birchStick = new ItemStack(Material.STICK, 1);
  ItemMeta meta = birchStick.getItemMeta();
  meta.setDisplayName("Birch Stick");
  birchStick.setItemMeta(meta);
  return birchStick;
}

public ItemStack getMahoganyStick()
{
  ItemStack mahogany = new ItemStack(Material.STICK, 1);
  ItemMeta meta = mahogany.getItemMeta();
  meta.setDisplayName("Mahogany Stick");
  mahogany.setItemMeta(meta);
  return mahogany;
}

public ItemStack getAcaciaStick()
{
  ItemStack acacia = new ItemStack(Material.STICK, 1);
  ItemMeta meta = acacia.getItemMeta();
  meta.setDisplayName("Acacia Stick");
  acacia.setItemMeta(meta);
  return acacia;
}

public ItemStack getBlackwoodStick()
{
  ItemStack blackwood = new ItemStack(Material.STICK, 1);
  ItemMeta meta = blackwood.getItemMeta();
  meta.setDisplayName("Blackwood Stick");
  blackwood.setItemMeta(meta);
  return blackwood;
}

public ItemStack getOakPlank()
  {
    ItemStack oakPlank = new ItemStack(Material.WOOD, 1, (short)0);
    ItemMeta meta = oakPlank.getItemMeta();
    meta.setDisplayName("Oak Planks");
    oakPlank.setItemMeta(meta);
    return oakPlank;
  }

public ItemStack getSprucePlank()
{
  ItemStack sprucePlank = new ItemStack(Material.WOOD, 1, (short)1);
  ItemMeta meta = sprucePlank.getItemMeta();
  meta.setDisplayName("Spruce Planks");
  sprucePlank.setItemMeta(meta);
  return sprucePlank;
}

public ItemStack getBirchPlank()
{
  ItemStack birchPlank = new ItemStack(Material.WOOD, 1, (short)2);
  ItemMeta meta = birchPlank.getItemMeta();
  meta.setDisplayName("Birch Planks");
  birchPlank.setItemMeta(meta);
  return birchPlank;
}

public ItemStack getMahoganyPlank()
{
  ItemStack mahoganyPlank = new ItemStack(Material.WOOD, 1, (short)3);
  ItemMeta meta = mahoganyPlank.getItemMeta();
  meta.setDisplayName("Mahogany Planks");
  mahoganyPlank.setItemMeta(meta);
  return mahoganyPlank;
}

public ItemStack getAcaciaPlank()
{
  ItemStack acaciaPlank = new ItemStack(Material.WOOD, 1, (short)4);
  ItemMeta meta = acaciaPlank.getItemMeta();
  meta.setDisplayName("Acacia Planks");
  acaciaPlank.setItemMeta(meta);
  return acaciaPlank;
}

public ItemStack getBlackwoodPlank()
{
  ItemStack blackwoodPlank = new ItemStack(Material.WOOD, 1, (short)5);
  ItemMeta meta = blackwoodPlank.getItemMeta();
  meta.setDisplayName("Blackwood Planks");
  blackwoodPlank.setItemMeta(meta);
  return blackwoodPlank;
}

public ItemStack getRawScrawnyFish()
{
  ItemStack fish = new ItemStack(Material.RAW_FISH, 1, (short)0);
  ItemMeta meta = fish.getItemMeta();
  meta.setDisplayName("Raw Scrawny Fish");
  fish.setItemMeta(meta);
  return fish;
}

public ItemStack getRawTrout()
{
  ItemStack fish = new ItemStack(Material.RAW_FISH, 1, (short)0);
  ItemMeta meta = fish.getItemMeta();
  meta.setDisplayName("Raw Trout");
  fish.setItemMeta(meta);
  return fish;
}


public List<String> createLore(String... lines)
  {
    List<String> lore = new ArrayList<String>();
    for (int i = 0; i < lines.length; i++) {
      lore.add(ChatColor.GRAY + lines[i]);
    }
    return lore;
  }
}
