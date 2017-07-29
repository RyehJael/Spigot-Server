package me.ryehjael;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Inventories
{
  public static Inventory myInventory = Bukkit.createInventory(null, 9, "My custom Inventory!");
  
  static
  {
    myInventory.setItem(0, new ItemStack(Material.DIRT));
    myInventory.setItem(8, new ItemStack(Material.GOLD_BLOCK, 1));
  }
  
  public void onInventoryClick(InventoryClickEvent event)
  {
    Player player = (Player)event.getWhoClicked();
    ItemStack clicked = event.getCurrentItem();
    Inventory inventory = event.getInventory();
    if ((inventory.getName().equals(myInventory.getName())) && 
      (clicked.getType() == Material.DIRT))
    {
      event.setCancelled(true);
      player.closeInventory();
      player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.DIRT, 1) });
    }
  }
  
  public void openInv(Player p)
  {
    p.openInventory(myInventory);
  }
}
