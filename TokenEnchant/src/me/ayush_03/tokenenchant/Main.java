package me.ayush_03.tokenenchant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.ayush_03.tokenenchant.enchantments.Dragonsbreath;
import me.ayush_03.tokenenchant.enchantments.Explosive;
import me.ayush_03.tokenenchant.enchantments.Merchant;

public class Main extends JavaPlugin {
	
	public static List<List<Integer>> dimensions;

	@Override
	public void onEnable() {
		dimensions = initDimensions();
		saveDefaultConfig();
		saveResource("gui.yml", false);
		getServer().getPluginManager().registerEvents(new MenuListener(), this);
		getServer().getPluginManager().registerEvents(new TokenItem(), this);
		getServer().getPluginManager().registerEvents(new Drops2Inv(), this);
		getServer().getPluginManager().registerEvents(new EnchantMenuListener(this), this);
		getServer().getPluginManager().registerEvents(new BackpackMenuListener(), this);
		
		getServer().getPluginManager().registerEvents(new Dragonsbreath(), this);
		getServer().getPluginManager().registerEvents(new Merchant(), this);
		getServer().getPluginManager().registerEvents(new Explosive(), this);
		getCommand("tokens").setExecutor(new TokensCommand());
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				fortune();
				
			}
		}.runTaskLaterAsynchronously(this, 20*1);
	}

	@Override
	public void onDisable() {

	}
	
	public void fortune() {
		
		FileConfiguration fc = FileManager.getInstance().getFortune();
		
		int base = 100;
		int prevDrop = base;
		int nextUpdate = 4;
		
		fc.set("1", base);
		
		for (int i = 2; i <= 10000; i++) {
			
			if (i == nextUpdate) {
				prevDrop++;
				nextUpdate += 3;
				
			}
			
			fc.set(i + "", prevDrop);
			
		}

		try {
			fc.save(FileManager.getInstance().getFortuneFile());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private List<List<Integer>> initDimensions() {
		  /*
	     * 2x2x1 = 1-99
		   2x2x2 = 100-199
		   3x2x2 = 200-299
	     */
		
		List<List<Integer>> dimensions = new ArrayList<List<Integer>>();
		List<Integer> list = new ArrayList<Integer>();
		
		list.add(2);
		list.add(1);
		list.add(1);

		int pointer = 1;
		for (int i = 0; i <= 5000; i+=250) {
			List<Integer> local = new ArrayList<Integer>();
			
			local.add(0); local.add(0); local.add(0);
			
			local.set(0, list.get(0));
			local.set(1, list.get(1));
			local.set(2, list.get(2));
		
			int value = list.get(pointer);
			list.set(pointer, value + 1);
			local.set(pointer, value + 1);

			dimensions.add(local);
			pointer++;
			if (pointer == 3) {
				pointer = 0;
			}

		}
		return dimensions;
	}
}