package me.ayush_03.tokenenchant.enchantments;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.ayush_03.tokenenchant.CustomEnchant;
import me.ayush_03.tokenenchant.CustomEvent;

public class Dragonsbreath extends EnchantmentEffect implements Listener {
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		
		Player p = e.getPlayer();
		Block b = e.getBlock();
		
		if (getWorldGuard().canBuild(p, b)) {
			if (isValid(p.getItemInHand(), CustomEnchant.DRAGONSBREATH)) {
				int level = CustomEnchant.getLevel(p.getItemInHand(), CustomEnchant.DRAGONSBREATH);
				double base = 1;
				double chance = base + (level * 0.001);
				
				double random = generateRandom();

				if (CustomEnchant.hasEnchantment(p.getItemInHand(), CustomEnchant.OVERCHARGE)) {
					chance += 0.01 * CustomEnchant.getLevel(p.getItemInHand(), CustomEnchant.OVERCHARGE);
				}

				if (random <= chance) {
					
					if (CustomEnchant.hasEnchantment(p.getItemInHand(), CustomEnchant.DRAGONSBREATHP)) {
						List<Block> list = getNearbyBlocks2(b.getLocation(), 30);
						
						for (Block bl : list) {
							if (getWorldGuard().canBuild(p, bl)) {
								if (bl.getType() != Material.AIR && !equalsLoc(b.getLocation(), bl.getLocation())) {
									
									CustomEvent ev = new CustomEvent(e.getPlayer(), bl);
									Bukkit.getPluginManager().callEvent(ev);
									
								}
							}
						}
						
					} else {
						List<Block> list = getNearbyBlocks(b.getLocation(), 30);
						
						for (Block bl : list) {
							if (getWorldGuard().canBuild(p, bl)) {
								if (bl.getType() != Material.AIR && !equalsLoc(b.getLocation(), bl.getLocation())) {
									
									CustomEvent ev = new CustomEvent(e.getPlayer(), bl);
									Bukkit.getPluginManager().callEvent(ev);
									
								}
							}
						}
						
					}

				}
			}
		}
	}
	
    private List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<Block>();
        for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
//            for(int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                   blocks.add(location.getWorld().getBlockAt(x, location.getBlockY(), z));
                }
//            }
        }
        return blocks;
    }
    
    private List<Block> getNearbyBlocks2(Location location, int radius) {
        List<Block> blocks = new ArrayList<Block>();
        for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
//            for(int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                   blocks.add(location.getWorld().getBlockAt(x, location.getBlockY(), z));
                   blocks.add(location.getWorld().getBlockAt(x, location.getBlockY() - 1, z));
                }
//            }
        }
        return blocks;
    }
}
