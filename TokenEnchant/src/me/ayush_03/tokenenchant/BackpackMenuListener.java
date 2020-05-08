package me.ayush_03.tokenenchant;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.ayush_03.backpacks.Backpack;
import me.ayush_03.backpacks.BackpackItem;
import me.ayush_03.backpacks.HiddenStringUtils;
import me.ayush_03.backpacks.Main;
import me.ayush_03.tokenapi.TokenAPI;

public class BackpackMenuListener implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			Inventory inv = e.getInventory();
			
			if (inv.getTitle().equals(ChatColor.AQUA + "Upgrade Backpack")) {
				e.setCancelled(true);
				TokenAPI api = TokenAPI.getInstance();
				
				ItemStack current = e.getCurrentItem();
				if (current != null && current.getType() != Material.AIR) {
					
					if (current.getType() == Material.CHEST) {
						if (current.getItemMeta().hasDisplayName() && current.getItemMeta().getDisplayName().contains(ChatColor.AQUA + "Add")) {
							int toAdd = current.getAmount();
							int cost = Main.slotCost * toAdd;
							Backpack bp = new BackpackItem(p.getItemInHand()).getBackpack();

							if (api.withdrawTokens(p, cost)) {
								bp.addSlots(toAdd);
								
								ItemMeta meta = p.getItemInHand().getItemMeta();
								
								List<String> lore = p.getItemInHand().getItemMeta().getLore();
								lore.set(0, ChatColor.YELLOW + "Slots: " + ChatColor.AQUA + bp.getSlots() + HiddenStringUtils.encodeString("bp:" + bp.getID()));
								meta.setLore(lore);
								p.getItemInHand().setItemMeta(meta);
								p.updateInventory();
								p.sendMessage(ChatColor.GREEN + "You have added " + toAdd + " slots to your backpack!");
							} else {
								p.sendMessage(ChatColor.RED + "You do not have enough tokens!");
								return;
							}
						}
					}
				}
			}
		}
	}

}
