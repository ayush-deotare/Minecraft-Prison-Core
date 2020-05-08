package me.ayush_03.tokenenchant;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.ayush_03.tokenapi.TokenAPI;

public class EnchantMenuListener implements Listener {
	
	Main plugin;
	public EnchantMenuListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			
			if (e.getInventory().getTitle().equalsIgnoreCase(ChatColor.AQUA + "Enchantments")) {
				e.setCancelled(true);
				if (e.getCurrentItem() != null) {
					ItemStack current = e.getCurrentItem();
					if (current.getItemMeta().hasDisplayName()) {
						
						CustomEnchant ce = CustomEnchant.fromDisplay(current.getItemMeta().getDisplayName());
						if (ce != null) {
							
							int toAdd;
							
							if (e.getClick() == ClickType.LEFT) {
								toAdd = 1;
							} else if (e.getClick() == ClickType.RIGHT) {
								toAdd = 50;
							} else return;
							
							ItemStack hand = p.getItemInHand();
							ItemMeta meta = hand.getItemMeta();
							TokenAPI api = TokenAPI.getInstance();
							int cost = ce.getCostPerLevel() * toAdd;
							
							if (!CustomEnchant.hasEnchantment(hand, ce)) {
								
								if (ce == CustomEnchant.DRAGONSBREATHP) {
									if (!CustomEnchant.hasEnchantment(hand, CustomEnchant.DRAGONSBREATH)) {
										p.sendMessage(ChatColor.RED + "The pickaxe must have the max level of Dragonsbreath enchantment applied!");
										return;
									}
									
									if (CustomEnchant.getLevel(hand, CustomEnchant.DRAGONSBREATHP) != CustomEnchant.DRAGONSBREATH.getMaxLevel()) {
										p.sendMessage(ChatColor.RED + "The pickaxe must have the max level of Dragonsbreath enchantment applied!");
										return;
									}
								}
								
								if (api.withdrawTokens(p, cost)) {
									
									if (toAdd > ce.getMaxLevel()) {
										p.sendMessage(ChatColor.RED + "Enchantment cannot exceed " + ce.getMaxLevel() + " levels!");
										return;
									}
									
									
									if (!meta.hasLore()) {
										if (ce == CustomEnchant.EFFICIENCY) {
											meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
										}
										List<String> lore = new ArrayList<String>();
										lore.add(ce.getDisplay() + " " + toAdd);
										meta.setLore(lore);
										hand.setItemMeta(meta);
										
										if (ce == CustomEnchant.EFFICIENCY) {
											hand.addUnsafeEnchantment(Enchantment.DIG_SPEED, toAdd);
										}
										
										p.sendMessage(ChatColor.GREEN + "You have added " + toAdd + " level(s) of " + ce.getDisplay() + ChatColor.GREEN + ""
												+ " for " + ChatColor.AQUA + "" + cost + ChatColor.GREEN + " tokens.");//										p.closeInventory();
										
									} else {
										
										if (ce == CustomEnchant.EFFICIENCY) {
											meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
										}
										
										List<String> lore = meta.getLore();
										lore.add(ce.getDisplay() + " " + toAdd);
										meta.setLore(lore);
										hand.setItemMeta(meta);
										
										if (ce == CustomEnchant.EFFICIENCY) {
											hand.addUnsafeEnchantment(Enchantment.DIG_SPEED, toAdd);
										}
										p.sendMessage(ChatColor.GREEN + "You have added " + toAdd + " level(s) of " + ce.getDisplay() + ChatColor.GREEN + ""
												+ " for " + ChatColor.AQUA + "" + cost + ChatColor.GREEN + " tokens.");

										
									}
									
								} else {
									p.sendMessage(ChatColor.RED + "You do not have enough tokens!");
									return;
								}

							} else {
								
								List<String> lore = meta.getLore();
								int index = CustomEnchant.getEnchantmentIndex(hand, ce);
								int level = CustomEnchant.getLevel(hand, ce);
								
								if (!CustomEnchant.canUpgrade(hand, ce)) {
									p.sendMessage(ChatColor.RED + "Enchantment already at max level!");
									return;
								}
								
								if (!CustomEnchant.canAddLevels(hand, ce, toAdd)) {
									p.sendMessage(ChatColor.RED + "Enchantment cannot exceed " + ce.getMaxLevel() + " levels!");
									return;
								}
								
								if (api.withdrawTokens(p, cost)) {
									
									if (ce == CustomEnchant.EFFICIENCY) {
										hand.addUnsafeEnchantment(Enchantment.DIG_SPEED, toAdd);
									}
									
									lore.set(index, ce.getDisplay() + " " + (level + toAdd));
									meta.setLore(lore);
									hand.setItemMeta(meta);
									
									if (ce == CustomEnchant.EFFICIENCY) {
										hand.addUnsafeEnchantment(Enchantment.DIG_SPEED, level + toAdd);
									}
									p.sendMessage(ChatColor.GREEN + "You have added " + toAdd + " level(s) of " + ce.getDisplay() + ChatColor.GREEN + ""
											+ " for " + ChatColor.AQUA + "" + cost + ChatColor.GREEN + " tokens.");
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

}
