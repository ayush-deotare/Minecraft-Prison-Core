package me.ayush_03.tokenenchant;

import java.util.Collection;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import me.ayush_03.backpacks.Backpack;
import me.ayush_03.backpacks.BackpackItem;

public class Drops2Inv implements Listener {
	
	@EventHandler
	public void onCustom(CustomEvent e) {
		
		if (e.getPlayer().getGameMode() != GameMode.SURVIVAL) return;

		Player p = e.getPlayer();
		Block b = e.getBlock();
		
		Collection<ItemStack> drops = b.getDrops(p.getItemInHand());
		ItemStack stack = null;
		
		for (ItemStack stt : drops) {
			stack = stt;
			break;
		}
			
			if (stack == null) {
				return;
			}
			
			Material mat = stack.getType();
			ItemStack drop;
			
			switch(mat) {
			case IRON_ORE:
				drop = new ItemStack(Material.IRON_INGOT);
				break;
				
			case GOLD_ORE:
				drop = new ItemStack(Material.GOLD_INGOT);
				break;
			
			default:
				drop = stack;
			}
			
			int left = drop.getAmount();
			
			for (ItemStack st : p.getInventory().getContents()) {
				if (st == null || st.getType() == Material.AIR) continue;
				if(BackpackItem.isBackpackItem(st)) {
					Backpack backpack = new BackpackItem(st).getBackpack();
					if (backpack.getOwner().getPlayer().equals(p)) {
						left = backpack.addItem(drop);
						drop.setAmount(left);
					}
				}
			}
			
			if (left != 0 && left > 0) {
				p.getInventory().addItem(drop);
				p.updateInventory();
			}
			
		b.setType(Material.AIR);
		
		if (p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR) return;
		
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBreak(BlockBreakEvent e) {
	
		if (e.getPlayer().getGameMode() != GameMode.SURVIVAL) return;
		
		if (e.isCancelled()) return;
		
		Player p = e.getPlayer();
		Block b = e.getBlock();
		
		Collection<ItemStack> drops = b.getDrops(p.getItemInHand());
		ItemStack stack = null;
		
		for (ItemStack stt : drops) {
			stack = stt;
			break;
		}
		
		if (stack == null) {
			return;
		}
			if (p.getItemInHand() != null && p.getItemInHand().getType() != Material.AIR) {
				
				if (CustomEnchant.hasEnchantment(p.getItemInHand(), CustomEnchant.FORTUNE)) {
					int level = CustomEnchant.getLevel(p.getItemInHand(), CustomEnchant.FORTUNE);
					FileConfiguration fc = FileManager.getInstance().getFortune();
					stack.setAmount(fc.getInt(level + ""));
				}
				
			}
			
 			
			Material mat = stack.getType();
			ItemStack drop;
			
			switch(mat) {
			case IRON_ORE:
				drop = new ItemStack(Material.IRON_INGOT);
				break;
				
			case GOLD_ORE:
				drop = new ItemStack(Material.GOLD_INGOT);
				break;
			
			default:
				drop = stack;
			}
			
			int left = drop.getAmount();
			
			for (ItemStack st : p.getInventory().getContents()) {
				if (st == null || st.getType() == Material.AIR) continue;
				if(BackpackItem.isBackpackItem(st)) {
					Backpack backpack = new BackpackItem(st).getBackpack();
					if (backpack.getOwner().getPlayer().equals(p)) {
						left = backpack.addItem(drop);
						drop.setAmount(left);
					}
				}
			}
			
			if (left != 0 && left > 0) {
				p.getInventory().addItem(drop);
				p.updateInventory();
			}
			
			
		e.setCancelled(true);
		b.setType(Material.AIR);
		
		if (p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR) return;
		
		if (CustomEnchant.hasEnchantment(p.getItemInHand(), CustomEnchant.UNBREAKABLE)) {
			return;
		}
		
		p.getItemInHand().setDurability((short) (p.getItemInHand().getDurability() + 1));
		
	}

}
