package me.ayush_03.tokenenchant;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.ayush_03.backpacks.BackpackItem;
import me.ayush_03.tokenapi.TokenAPI;

public class MenuListener implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		
		if (e.getWhoClicked() instanceof Player) {
			
			Player p = (Player) e.getWhoClicked();
			Inventory inv = e.getInventory();
			
			if (inv.getTitle().equalsIgnoreCase(ChatColor.AQUA + "Menu")) {

				ItemStack current = e.getCurrentItem();
				
				if (current != null) {
					
					e.setCancelled(true);
					
					if (current.getType() == Material.DIAMOND_PICKAXE && current.getItemMeta().hasDisplayName()) {
						if (current.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "" + ChatColor.BOLD + "Enchantments")) {
							
							if (p.getItemInHand() != null && p.getItemInHand().getType() == Material.DIAMOND_PICKAXE) {
								p.closeInventory();
								new BukkitRunnable() {
									
									@Override
									public void run() {
										p.openInventory(GUIManager.getInstance().createEnchantGUI());
										
									}
								}.runTaskLaterAsynchronously(Bukkit.getPluginManager().getPlugin("TokenEnchant"), 1);
							} else {
								p.sendMessage(ChatColor.RED + "You must hold a diamond pickaxe in your hand to open enchantments menu!");
								return;
							}
							
						}
					}
					
					if (current.getType() == Material.CHEST && current.getItemMeta().hasDisplayName()) {
						if (current.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "" + ChatColor.BOLD + "Upgrade Backpack")) {
							
							if (p.getItemInHand() != null && p.getItemInHand().getType() != Material.AIR) {
								
								if (!BackpackItem.isBackpackItem(p.getItemInHand())) {
									p.sendMessage(ChatColor.RED + "You must hold a backpack in your hand to enter this menu!");
									return;
								}
								p.closeInventory();
								new BukkitRunnable() {
									
									@Override
									public void run() {
										p.openInventory(GUIManager.getInstance().createBackpackGUI());
										
									}
								}.runTaskLaterAsynchronously(Bukkit.getPluginManager().getPlugin("TokenEnchant"), 1);
							} else {
								p.sendMessage(ChatColor.RED + "You must hold a diamond pickaxe in your hand to open enchantments menu!");
								return;
							}
							
						}
					}
					
					if (current.getType() == Material.MAGMA_CREAM && current.getItemMeta().hasDisplayName()) {
						if (current.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "" + ChatColor.BOLD + "Export Tokens")) {
							
							// Export the tokens...
							
							TokenAPI api = TokenAPI.getInstance();
							
							if (e.getClick() == ClickType.LEFT) {
								// 5k tokens...
								
								if (api.withdrawTokens(p, 5000)) {
									ItemStack tItem = TokenItem.getItem(1);
									p.getInventory().addItem(tItem);
									p.updateInventory();
									
									p.sendMessage(ChatColor.GREEN + "You exported 5000 tokens to the token item.");
									p.closeInventory();
								} else {
									p.sendMessage(ChatColor.RED + "You do not have enough tokens.");
									return;
								}
							} else if (e.getClick() == ClickType.RIGHT) {
								if (api.withdrawTokens(p, 5000 * 64)) {
									
									ItemStack tItem = TokenItem.getItem(64);
									p.getInventory().addItem(tItem);
									p.updateInventory();
									
									p.sendMessage(ChatColor.GREEN + "You exported 320,000 tokens to 64 token items.");
									p.closeInventory();
								} else {
									p.sendMessage(ChatColor.RED + "You do not have enough tokens.");
									return;
								}
							} else return;
							
							
						}
					}
					
				}
				
			}
			
		}
		
	}

}
