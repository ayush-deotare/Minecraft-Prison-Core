package me.ayush_03.backpacks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BackpackItem {
	
	ItemStack item;
	Backpack bp;
	
	public BackpackItem(Backpack bp) {
		
		ItemStack stack = new ItemStack(Material.CHEST);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + "Backpack");
		
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.YELLOW + "Slots: " + ChatColor.AQUA + bp.getSlots() + HiddenStringUtils.encodeString("bp:" + bp.getID()));
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		this.bp = bp;
		this.item = stack;
	}
	
	public BackpackItem(ItemStack stack) {
		
		if (stack.getItemMeta().hasLore() && stack.getItemMeta().hasDisplayName()) {
			
			if (!stack.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Backpack")) return;
			
			List<String> lore = stack.getItemMeta().getLore();
			
			if (HiddenStringUtils.hasHiddenString(lore.get(0))) {
				String str = lore.get(0);
				
				str = HiddenStringUtils.extractHiddenString(str);
				str = str.replace("bp:", "");
				
				int id = Integer.parseInt(str);
				Backpack bp = Backpack.getBackpack(id);
				
				this.bp = bp;
				this.item = stack;
			} 
			
		}
		
	}
	
	public Backpack getBackpack() {
		return bp;
	}
	
	public ItemStack getItem() {
		return item;
	}
	
	public static boolean isBackpackItem(ItemStack stack) {
		
		if (!(stack != null && stack.getType() != Material.AIR)) return false;
		
		if (stack.getItemMeta().hasLore() && stack.getItemMeta().hasDisplayName()) {
			if (stack.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Backpack")) {
				
				String str = stack.getItemMeta().getLore().get(0);
				
				if (HiddenStringUtils.hasHiddenString(str)) {
					if (HiddenStringUtils.extractHiddenString(str).contains("bp:")) return true;
				}
			}
			
		}
		
		return false;
		
	}

}