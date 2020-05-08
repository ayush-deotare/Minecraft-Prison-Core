package me.ayush_03.tokenenchant;

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
	
	Plugin p = Bukkit.getPluginManager().getPlugin("TokenEnchant");
	
	private static FileManager instance = new FileManager();
	
	public static FileManager getInstance() {
		return instance;
	}
	
	public FileConfiguration getGUI() {
		this.f2 = new File(p.getDataFolder() + File.separator + "gui.yml");
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
	
	public FileConfiguration getFortune() {
		
		if (!p.getDataFolder().exists()) p.getDataFolder().mkdir();
		
		this.f = new File(p.getDataFolder() + File.separator + "fortune.yml");
		if (!f.exists())
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		fc = YamlConfiguration.loadConfiguration(f);
		
		return fc;
	}
	
	public File getFortuneFile() {
		
		if (!p.getDataFolder().exists()) p.getDataFolder().mkdir();
		
		this.f = new File(p.getDataFolder() + File.separator + "fortune.yml");
		if (!f.exists())
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return f;
	}

}
