package me.ayush_03.tokenenchant.enchantments;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import me.ayush_03.tokenenchant.CustomEnchant;

public class EnchantmentEffect {
	
	public boolean isValid(ItemStack item, CustomEnchant ce) {
		if (item != null && item.getType() == Material.DIAMOND_PICKAXE) {
			if (CustomEnchant.hasEnchantment(item, ce)) {
				return true;
			}
		}
		return false;
	}
	
    public WorldGuardPlugin getWorldGuard() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }
        return (WorldGuardPlugin) plugin;
    }
    
    public double generateRandom() {
    	
    	double r =  ThreadLocalRandom.current().nextDouble(0, 100);
    	double scale = Math.pow(10, 3);
		r = Math.round(r * scale) / scale;
		
		return r;
    }
    
    public boolean equalsLoc(Location l1, Location l2) {
    	if (l1.getWorld().getName().equals(l2.getWorld().getName())) {
    		if (l1.getX() == l2.getX() && l1.getY() == l2.getY() && l1.getZ() == l2.getZ()) {
    		return true;
    		}
    	}
    	
    	return false;
    }

}
