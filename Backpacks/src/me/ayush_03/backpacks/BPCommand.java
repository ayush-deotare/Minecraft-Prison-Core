package me.ayush_03.backpacks;

import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

public class BPCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		// /bp give <player> <slots>

		if (cmd.getName().equalsIgnoreCase("bp")) {

			if (args.length == 0) {

				if (sender instanceof Player) {

					Player p = (Player) sender;
					FileConfiguration fc = FileManager.getIntance().getUses();

					if (fc.getInt(p.getUniqueId().toString()) == 0 || p.hasPermission("bp.unlimited")) {

						// allow

						Backpack bp = Backpack.createBackpack(p);
						BackpackItem item = new BackpackItem(bp);

						if (p.getInventory().firstEmpty() == -1) {
							p.getWorld().dropItem(p.getLocation(), item.getItem());
							p.sendMessage(ChatColor.RED
									+ "Since your inventory is full, the backpack is dropped on the ground near you!");
							return true;
						}

						p.getInventory().addItem(item.getItem());
						p.updateInventory();

						p.sendMessage(ChatColor.GREEN + "Backpack added to your inventory!");

						fc.set(p.getUniqueId().toString(), fc.getInt(p.getUniqueId().toString()) + 1);

						try {
							fc.save(FileManager.getIntance().getUsesFile());
						} catch (IOException e) {
							e.printStackTrace();
						}

					} else {
						p.sendMessage(ChatColor.RED + "You cannot execute this command more than once!");
						return true;
					}
				}
			} else if (args.length == 3) {
				
				if (args[0].equalsIgnoreCase("give")) {
					if (sender.hasPermission("bp.give")) {
						
						Player target = Bukkit.getPlayer(args[1]);
						
						if (target == null) {
							sender.sendMessage(ChatColor.RED + "Specified player is not online!");
							return true;
						}
						
						int slots;
						
						try {
							slots = Integer.parseInt(args[2]);
						} catch (NumberFormatException ex) {
							sender.sendMessage(ChatColor.RED + "Slots argument must be an integer!");
							return true;
						}
						
						if (slots < 0) {
							sender.sendMessage(ChatColor.RED + "Slot argument must be greater than or equal to 0!");
							return true;
						}
						
						Backpack bp = Backpack.createBackpack(target);
						BackpackItem item = new BackpackItem(bp);
						
						bp.addSlots(slots);
						
						ItemMeta meta = item.getItem().getItemMeta();
						List<String> lore = meta.getLore();
						lore.set(0, ChatColor.YELLOW + "Slots: " + ChatColor.AQUA + bp.getSlots() + HiddenStringUtils.encodeString("bp:" + bp.getID()));
						meta.setLore(lore);
						
						item.getItem().setItemMeta(meta);
						
						target.sendMessage(ChatColor.GREEN + "You have received a backpack with " + slots + " slots!");
						
						if (target.getInventory().firstEmpty() == -1) {
							target.getWorld().dropItem(target.getLocation(), item.getItem());
							target.sendMessage(ChatColor.RED
									+ "Since your inventory is full, the backpack is dropped on the ground near you!");
						} else {
							target.getInventory().addItem(item.getItem());
							target.updateInventory();
						}
						
						sender.sendMessage(ChatColor.GREEN + "Backpack given to the specified player!");
					}
				} else {
					sender.sendMessage(ChatColor.YELLOW + "Available commands: ");
					sender.sendMessage(ChatColor.AQUA + "/bp");
					sender.sendMessage(ChatColor.AQUA + "/bp give <player> <slots>");
					return true;
				}
					
			} else {
				sender.sendMessage(ChatColor.YELLOW + "Available commands: ");
				sender.sendMessage(ChatColor.AQUA + "/bp");
				sender.sendMessage(ChatColor.AQUA + "/bp give <player> <slots>");
				return true;
			}
		}
		return true;
	}

}
