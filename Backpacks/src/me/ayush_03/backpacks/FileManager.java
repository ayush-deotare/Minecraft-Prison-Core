package me.ayush_03.backpacks;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class FileManager {
	
	File f;
	FileConfiguration fc;
	
	File f2;
	FileConfiguration fc2;
	
	Plugin plugin = Bukkit.getPluginManager().getPlugin("Backpacks");
	
	private static FileManager instance = new FileManager();
	
	public static FileManager getIntance() {
		return instance;
	}
	
	public FileConfiguration getUses() {
		this.f2 = new File(plugin.getDataFolder() + File.separator + "uses.yml");
		
		if (!f2.exists()) {
			try {
				f2.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		fc2 = YamlConfiguration.loadConfiguration(f2);
		return fc2;
	}
	
	public File getUsesFile() {
		this.f2 = new File(plugin.getDataFolder() + File.separator + "uses.yml");
		return f2;
	}
	
	public FileConfiguration getBackpack(int id) {
		
		this.f = new File(plugin.getDataFolder() + File.separator + "bp" + id + ".yml");
		
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		this.fc = YamlConfiguration.loadConfiguration(f);
		
		return this.fc;
	}
	
	public File getBackpackFile(int id) {
		this.f = new File(plugin.getDataFolder() + File.separator + "bp" + id + ".yml");
		return f;
	}

}