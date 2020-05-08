package me.ayush_03.tokenapi;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	public static HashMap<UUID, Integer> tokens = new HashMap<UUID, Integer>();
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		getCommand("tadmin").setExecutor(new TAdminCommand());
		getCommand("tbal").setExecutor(new TBalCommand());
		saveDefaultConfig();
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (getConfig().getConfigurationSection(p.getUniqueId().toString()) != null) {
				if (!tokens.containsKey(p.getUniqueId())) {
					tokens.put(p.getUniqueId(), getConfig().getInt(p.getUniqueId().toString() + ".balance"));
				}
			}
		}
	}
	
	@Override
	public void onDisable() {
		for (UUID id : tokens.keySet()) {
			getConfig().set(id.toString() + ".balance", tokens.get(id));
		}
		saveConfig();
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		if (getConfig().getConfigurationSection(e.getPlayer().getUniqueId().toString()) == null) {
			tokens.put(e.getPlayer().getUniqueId(), 0);
		
		} else {
			tokens.put(e.getPlayer().getUniqueId(), getConfig().getInt(e.getPlayer().getUniqueId().toString() + ".balance"));
		}
		
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		getConfig().set(p.getUniqueId().toString() + ".balance", tokens.get(p.getUniqueId()));
		saveConfig();
		reloadConfig();
		tokens.remove(p.getUniqueId());
	}

}
