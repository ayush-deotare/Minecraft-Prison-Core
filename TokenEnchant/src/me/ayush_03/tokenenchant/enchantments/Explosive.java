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
import me.ayush_03.tokenenchant.Main;

public class Explosive extends EnchantmentEffect implements Listener {
	
// Explosive, Dragonbreath is 1% base and 0.001% per lvl
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		
		Player p = e.getPlayer();
		Block b = e.getBlock();
		
		if (getWorldGuard().canBuild(p, b)) {
			if (isValid(p.getItemInHand(), CustomEnchant.EXPLOSIVE)) {
				int level = CustomEnchant.getLevel(p.getItemInHand(), CustomEnchant.EXPLOSIVE);
				double base = 1;
				double chance = base + (level * 0.001);
				
				double random = generateRandom();
				
				if (CustomEnchant.hasEnchantment(p.getItemInHand(), CustomEnchant.OVERCHARGE)) {
					chance += 0.01 * CustomEnchant.getLevel(p.getItemInHand(), CustomEnchant.OVERCHARGE);
				}
				
				if (random <= chance) {
					// Enchantment effect...
					List<Integer> dimensions;
					int index = level / 250;
					dimensions = Main.dimensions.get(index);
					p.sendMessage(dimensions.toString());
					
					List<Block> list = getExplosive(b.getLocation(), dimensions.get(0), dimensions.get(1), dimensions.get(2));
					
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
	
    private List<Block> getExplosive(Location location, int l, int b, int h) {
        List<Block> blocks = new ArrayList<Block>(); // 10, 5, 11
        for(int x = location.getBlockX() - l + 1; x <= location.getBlockX(); x++) { // length always greater than outputs
            for(int y = location.getBlockY() - h + 1; y <= location.getBlockY(); y++) { // -1 = one layer down
                for(int z = location.getBlockZ() - b + 1; z <= location.getBlockZ(); z++) { // length always greater than outputs
                   blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }
    
    // 2x2x1 up to 12x12x6
    
    /*
     * 2x2x1 = 1-99
	   2x2x2 = 100-199
	   3x2x2 = 200-299
     */
}
