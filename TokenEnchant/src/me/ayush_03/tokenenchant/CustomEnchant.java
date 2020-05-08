package me.ayush_03.tokenenchant;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public enum CustomEnchant {
	
	EXPLOSIVE("Explosive", 5000, ChatColor.GRAY), 
	DRAGONSBREATH("Dragonsbreath", 5000, ChatColor.GRAY),
	DRAGONSBREATHP("Dragonsbreath+", 1, ChatColor.RED),
	OVERCHARGE("Overcharge", 10000, ChatColor.LIGHT_PURPLE),
	FORTUNE("Fortune", 10000, ChatColor.GRAY), 
	EFFICIENCY("Efficiency", 1000, ChatColor.GRAY),
	UNBREAKABLE("Unbreakable", 1, ChatColor.GRAY), 
	NUKE("Nuke", 1, ChatColor.GOLD), 
	MERCHANT("Merchant", 10000, ChatColor.GRAY);
	
	int max;
	ChatColor color;
	int tokens;
	String display;
	FileConfiguration config = Bukkit.getPluginManager().getPlugin("TokenEnchant").getConfig();
	
	private CustomEnchant(String display, int max, ChatColor color) {
		this.max = max;
		this.color = color;
//		this.tokens = tokens;
		this.tokens = config.getInt(toString().toLowerCase());
		this.display = display;
	}
	
	public String getDisplay() {
		return color + display;
	}
	
	public int getMaxLevel() {
		return max;
	}
	
	public ChatColor getChatColor() {
		return color;
	}
	
	public int getCostPerLevel() {
		return tokens;
	}
	
	public static CustomEnchant fromDisplay(String display) {
		for (CustomEnchant ce : values()) {
			if (display.equals(ce.getDisplay())) return ce;
		}
		
		return null;
	}
	
	public static boolean hasCustomEnchantments(ItemStack item) {
		
		if (item.getItemMeta().hasLore()) {
			for (String str : item.getItemMeta().getLore()) {
				for (CustomEnchant ce : values()) {
					if (str.contains(ce.getDisplay())) return true;
				}
			}
		}
		return false;
	}
	
	public static boolean hasEnchantment(ItemStack item, CustomEnchant ce) {
		
		if (item.getItemMeta().hasLore()) {
			for (String str : item.getItemMeta().getLore()) {
				if (str.contains(ce.getDisplay())) return true;
			}
		}
		return false;
	}
	
	public static int getLevel(ItemStack item, CustomEnchant ce) {
		
		if (item.getItemMeta().hasLore()) {
			List<String> lore = item.getItemMeta().getLore();
			
			String str = lore.get(getEnchantmentIndex(item, ce));
			str = ChatColor.stripColor(str);
			str = str.replaceAll("[^0-9]", "");
			
			return Integer.parseInt(str);
			
		}		
			return 0;
	}
	
	public static int getEnchantmentIndex(ItemStack item, CustomEnchant ce) {
		if  (item.getItemMeta().hasLore()) {
			int index = -1;
			for (String str : item.getItemMeta().getLore()) {
				index++;
				if (str.contains(ce.getDisplay())) {
					return index;
				}
			}
		}
		
		return -1;
	}
	
	public static boolean canUpgrade(ItemStack item, CustomEnchant ce) {
		int level = getLevel(item, ce);
		
		if (level < ce.getMaxLevel()) return true;
		
		return false;
	}
	
	public static boolean canAddLevels(ItemStack item, CustomEnchant ce, int toAdd) {
		int level = getLevel(item, ce) + toAdd;
		
		if (level <= ce.getMaxLevel()) return true;
		
		return false;
	}
	
	public List<String> getGUILore() {
		
		List<String> lore = FileManager.getInstance().getGUI().getStringList(toString().toLowerCase());
		List<String> local = new ArrayList<String>();
		
		for (String str : lore) {
			str = str.replace("%max%", max + "");
			str = str.replace("%price%", tokens + "");
			str = ChatColor.translateAlternateColorCodes('&', str);
			local.add(str);
		}
		
		return local;
	}
}