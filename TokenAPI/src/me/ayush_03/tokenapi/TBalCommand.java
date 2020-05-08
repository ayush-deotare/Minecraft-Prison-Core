package me.ayush_03.tokenapi;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TBalCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("tbal")) {
			if (sender instanceof Player) {
				
				Player p = (Player) sender;
				TokenAPI api = TokenAPI.getInstance();
				
				int balance = api.getBalance(p);
				
				p.sendMessage(ChatColor.YELLOW + "Balance: " + ChatColor.AQUA + "" + balance + ChatColor.GREEN + " tokens");
			}
		}
		return true;
	}
}
