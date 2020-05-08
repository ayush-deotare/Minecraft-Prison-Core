package me.ayush_03.backpacks;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	public static int total;
	public static int slotCost;
	
	public static List<Backpack> backpacks = new ArrayList<Backpack>();
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		total = getConfig().getInt("total");
		slotCost = getConfig().getInt("cost-per-slot");
		initialize();
		getServer().getPluginManager().registerEvents(this, this);
		getCommand("bp").setExecutor(new BPCommand());
	}
	
	@Override
	public void onDisable() { 
		getConfig().set("total", total);
		saveConfig();
		
		for (Backpack bp : backpacks) {
			FileConfiguration fc = FileManager.getIntance().getBackpack(bp.getID());
			fc.set("slots", bp.getSlots());
			fc.set("content", null);
			
			HashMap<String, Integer> map = bp.getContents();
			
			for (String str : map.keySet()) {
				fc.set("content." + str, map.get(str));
			}
			
			try {
				fc.save(FileManager.getIntance().getBackpackFile(bp.getID()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
//	@EventHandler
//	public void onChat(AsyncPlayerChatEvent e) {
//		Player p = e.getPlayer();
//		
//		if (e.getMessage().equals("bp")) {
//			Backpack bp = Backpack.createBackpack(p);
//			BackpackItem item = new BackpackItem(bp);
//			
//			p.getInventory().addItem(item.getItem());
//			p.updateInventory();
//		}
//		
//		if (e.getMessage().equals("check")) {
//			if (BackpackItem.isBackpackItem(p.getItemInHand())) {
//				BackpackItem bpi = new BackpackItem(p.getItemInHand());
//				Backpack bp = bpi.getBackpack();
//				bp.addSlots(1);
//			}
//			
//		}
//		
//		if (e.getMessage().equals("clear")) {
//			if (BackpackItem.isBackpackItem(p.getItemInHand())) {
//				BackpackItem bpi = new BackpackItem(p.getItemInHand());
//				Backpack bp = bpi.getBackpack();
//				bp.getContents().clear();
//			}
//		}
//	}
	
	private void initialize() {
		
		File[] files = getDataFolder().listFiles();
		
		for (File f : files) {
			if (f.getName().contains("config") || f.getName().contains("uses")) continue;
			
			String name = f.getName();
			name = name.replace("bp", "");
			name = name.replace(".yml", "");
			
			int id = Integer.parseInt(name);
			
			Backpack bp = new Backpack(id);
			
			FileConfiguration fc = FileManager.getIntance().getBackpack(id);
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			
			if (fc.getConfigurationSection("content") != null) {
				for (String str : fc.getConfigurationSection("content").getKeys(false)) {
					map.put(str, fc.getInt("content." + str));
				}
			}
			
			bp.setContents(map);
			bp.setSlots(fc.getInt("slots"));
			backpacks.add(bp);
		}
	}

}
