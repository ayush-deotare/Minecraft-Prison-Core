package me.ayush_03.tokenenchant;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TokensCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("tokens")) {
			
			if (sender instanceof Player) {
				Player p = (Player) sender;
				
				if (p.hasPermission("tokens.cmd")) {
					p.openInventory(GUIManager.getInstance().createMenu());
				} else {
					p.sendMessage(ChatColor.RED + "You do not have permission to execute this command!");
					return true;
				}
			}
			
		}
		return true;
		
	}

}
