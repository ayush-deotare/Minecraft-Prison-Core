package me.ayush_03.autosell;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.ayush_03.backpacks.Backpack;
import me.ayush_03.backpacks.BackpackItem;
import me.ayush_03.tokenapi.TokenAPI;

public class AutoSell extends JavaPlugin {
	
	@Override
	public void onEnable() {
		
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("sell")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				TokenAPI api = TokenAPI.getInstance();
				
				if (p.hasPermission("autosell.sell")) {
					int tokens = 0;
					
					tokens += sellInventory(p);
					
					for (ItemStack stack : p.getInventory()) {
						if (BackpackItem.isBackpackItem(stack)) {
							Backpack bp = new BackpackItem(stack).getBackpack();
							
							tokens += sellBackpack(bp, p);
						}
					}
					api.depositTokens(p, tokens);
					p.sendMessage(ChatColor.GREEN + "You sold your items in inventory and backpacks for " + ChatColor.AQUA + tokens + ChatColor.GREEN + " tokens.");
				} else {
					p.sendMessage(ChatColor.RED + "You do not have permission to execute this command!");
					return true;
				}
			} else {
				sender.sendMessage(ChatColor.RED + "You must be a player to execute this command!");
				return true;
			}
		}
		return true;
	}
	
	public int sellBackpack(Backpack bp, Player p) {
		
		int tokens = 0;
		
		if (bp.getOwner().getPlayer().equals(p)) {
			
			for (String str : bp.getContents().keySet()) {
				if (getConfig().getInt(str) == 0) continue;
				tokens += (getConfig().getInt(str) * bp.getContents().get(str));
			}
			
			
			bp.getContents().clear();
		}
		
		return tokens;
	}
	
	public int sellInventory(Player p) {
		
		int tokens = 0;
		
		for (ItemStack item : p.getInventory().getContents()) {
			if (item == null || item.getType() == Material.AIR) continue;
			if (BackpackItem.isBackpackItem(item)) continue;
			
			@SuppressWarnings("deprecation")

			String id = item.getType().toString() + ":" + item.getData().getData();
			if (getConfig().getInt(id) == 0) continue;
			tokens += (getConfig().getInt(id) * item.getAmount());
			p.getInventory().remove(item);
			p.updateInventory();
		}
		
		return tokens;
		
	}

}
