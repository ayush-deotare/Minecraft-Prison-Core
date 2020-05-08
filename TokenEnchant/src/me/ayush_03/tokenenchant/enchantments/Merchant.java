package me.ayush_03.tokenenchant.enchantments;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import me.ayush_03.tokenapi.TokenAPI;
import me.ayush_03.tokenenchant.CustomEnchant;
import me.ayush_03.tokenenchant.CustomEvent;

public class Merchant extends EnchantmentEffect implements Listener {
	
	@EventHandler
	public void onCustom(CustomEvent e) {
		
		Player p = e.getPlayer();
		ItemStack stack = p.getItemInHand();
		
		if (stack != null && stack.getType() == Material.DIAMOND_PICKAXE) {
			if (CustomEnchant.hasEnchantment(stack, CustomEnchant.MERCHANT)) {
				int level = CustomEnchant.getLevel(stack, CustomEnchant.MERCHANT);
				
				int tokens = getTokens(level);
				
				TokenAPI api = TokenAPI.getInstance();
				api.depositTokens(p, tokens);
			}
		}
	}
	
	@EventHandler
	public void onCustom(BlockBreakEvent e) {
		
		// CHECK FOR WG!
		
		if (!getWorldGuard().canBuild(e.getPlayer(), e.getBlock())) return;
		
//		if (e.isCancelled()) return;
		
		Player p = e.getPlayer();
		ItemStack stack = p.getItemInHand();
		
		if (stack != null && stack.getType() == Material.DIAMOND_PICKAXE) {
			if (CustomEnchant.hasEnchantment(stack, CustomEnchant.MERCHANT)) {
				int level = CustomEnchant.getLevel(stack, CustomEnchant.MERCHANT);
				
				int tokens = getTokens(level);
				
				TokenAPI api = TokenAPI.getInstance();
				api.depositTokens(p, tokens);
			}
		}
	}
	
	private int getTokens(int level) {
		// 1-999 = 1
		// 1000-1999 = 2
		if (level <= 999) return 1;
		if (level <= 1999) return 2;
		if (level <= 2999) return 3;
		if (level <= 3999) return 4;
		if (level <= 4999) return 5;
		if (level <= 5999) return 6;
		if (level <= 6999) return 7;
		if (level <= 7999) return 8;
		if (level <= 8999) return 9;
		if (level <= 9999) return 10;
		
		return 11;
		
	}

}
