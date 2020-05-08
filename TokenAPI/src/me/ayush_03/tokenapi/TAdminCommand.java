package me.ayush_03.tokenapi;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TAdminCommand implements CommandExecutor {

	// /tadmin set [player] [balance]
	// /tadmin

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("tadmin")) {

			if (sender.hasPermission("tadmin.command")) {

				if (args.length != 3 && args.length != 2) {

					sender.sendMessage("Available commands:");

					sender.sendMessage(ChatColor.YELLOW + "/tadmin set <player> <balance>\n"
							+ "/tadmin withdraw <player> <amount>\n" + "/tadmin deposit <player> <amount>\n"
							+ "/tadmin clear <player>");

					return true;
				}

				if (args.length == 2) {
					if (args[0].equalsIgnoreCase("clear")) {
						OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);

						if (!validatePlayer(sender, args[1]))
							return true;

						TokenAPI.getInstance().setBalance(p.getPlayer(), 0);

						sender.sendMessage(ChatColor.GREEN + "The balance of " + p.getName() + " has been set to 0.");
						return true;

					} else {
						sender.sendMessage(ChatColor.YELLOW + "/tadmin set <player> <balance>\n"
								+ "/tadmin withdraw <player> <amount>\n" + "/tadmin deposit <player> <amount>\n"
								+ "/tadmin clear <player>");

						return true;
					}
				}

				if (args.length == 3) {

					if (args[0].equalsIgnoreCase("set")) {

						OfflinePlayer op = Bukkit.getOfflinePlayer(args[1]);

						if (validatePlayer(sender, args[1])) {

							int balance;

							try {
								balance = Integer.parseInt(args[2]);
							} catch (NumberFormatException ex) {
								sender.sendMessage(ChatColor.RED + "Amount must be an integer!");
								return true;
							}

							TokenAPI.getInstance().setBalance(op.getPlayer(), balance);

							sender.sendMessage(ChatColor.GREEN + "The balance of " + op.getName() + " has been set to "
									+ balance + ".");

						}

					} else if (args[0].equalsIgnoreCase("deposit")) {
						
						OfflinePlayer op = Bukkit.getOfflinePlayer(args[1]);

						if (validatePlayer(sender, args[1])) {

							int balance;

							try {
								balance = Integer.parseInt(args[2]);
							} catch (NumberFormatException ex) {
								sender.sendMessage(ChatColor.RED + "Amount must be an integer!");
								return true;
							}

							TokenAPI.getInstance().depositTokens(op.getPlayer(), balance);

							sender.sendMessage(ChatColor.GREEN + "Successfully deposited " + balance + " tokens to " + op.getName() + "'s balance.");

						}

					} else if (args[0].equalsIgnoreCase("withdraw")) {
						
						OfflinePlayer op = Bukkit.getOfflinePlayer(args[1]);

						if (validatePlayer(sender, args[1])) {

							int balance;

							try {
								balance = Integer.parseInt(args[2]);
							} catch (NumberFormatException ex) {
								sender.sendMessage(ChatColor.RED + "Amount must be an integer!");
								return true;
							}
							
							if (TokenAPI.getInstance().withdrawTokens(op.getPlayer(), balance)) {
								sender.sendMessage(ChatColor.GREEN + "Successfully withdrawn " + balance + " tokens from " + op.getName() + "'s balance.");
							} else {
								sender.sendMessage(ChatColor.RED + "Insufficient balance.");
								return true;
							}
						}
						
					} else {
						sender.sendMessage(ChatColor.YELLOW + "/tadmin set <player> <balance>\n"
								+ "/tadmin withdraw <player> <amount>\n" + "/tadmin deposit <player> <amount>\n"
								+ "/tadmin clear <player>");

						return true;
					}

				}
			} else {
				sender.sendMessage(ChatColor.RED + "You do not have permission to execute this command!");
				return true;
			}

		}
		return true;

	}

	@SuppressWarnings("deprecation")
	private boolean validatePlayer(CommandSender sender, String name) {

		OfflinePlayer p = Bukkit.getOfflinePlayer(name);

		if (p == null || !p.hasPlayedBefore()) {
			sender.sendMessage(ChatColor.RED + "Specified player cannot be found.");
			return false;
		}

		if (!TokenAPI.getInstance().isRegistered(p.getPlayer())) {
			sender.sendMessage(ChatColor.RED + "Specified player has not registered.");
			return false;
		}

		return true;

	}

}
