package me.ayush_03.tokenenchant;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.ayush_03.tokenapi.TokenAPI;

public class TokenItem implements Listener {
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		
		Player p = e.getPlayer();
		
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (p.getItemInHand() != null && p.getItemInHand().getType() != Material.AIR) {
				ItemStack item = p.getItemInHand();
				
				if (item.isSimilar(getItem(1))) {
					int amount = p.getItemInHand().getAmount();
					int tokens = amount * 5000;
					
					TokenAPI api = TokenAPI.getInstance();
					api.depositTokens(p, tokens);
					
					p.sendMessage(ChatColor.GREEN + "" + tokens + " tokens have been deposited.");
					p.setItemInHand(new ItemStack(Material.AIR));
					
				}
			}
		}
		
	}
	
	public static ItemStack getItem(int amount) {
		
		ItemStack magma = new ItemStack(Material.MAGMA_CREAM, amount);
		ItemMeta meta = magma.getItemMeta();
		
		meta.setDisplayName(ChatColor.BLUE + "Token Item");
		
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.YELLOW + "One item = 5k tokens");
		lore.add(ChatColor.RED + "Right click to get the tokens");
		meta.setLore(lore);
		
		magma.setItemMeta(meta);
		
		return magma;
	}
	
}
