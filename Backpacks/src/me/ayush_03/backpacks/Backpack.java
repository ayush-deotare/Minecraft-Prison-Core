package me.ayush_03.backpacks;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Backpack {
	
	int id;
	int slots;
	OfflinePlayer owner;
	HashMap<String, Integer> map; // ['TYPE:DATA' : 'AMOUNT']
	
	public Backpack(int id) {
		
		if (id > Main.total) return;
		
		this.id = id;
		this.slots = FileManager.getIntance().getBackpack(id).getInt("slots");
		this.owner = Bukkit.getOfflinePlayer(UUID.fromString(FileManager.getIntance().getBackpack(id).getString("owner")));
		this.map = new HashMap<String, Integer>();
		
	}
	
	public OfflinePlayer getOwner() {
		return owner;
	}
	
	public int getSlots() {
		return slots;
	}
	
	public int getID() {
		return id;
	}
	
	public void setContents(HashMap<String, Integer> map) {
		this.map = map;
	}
	
	public HashMap<String, Integer> getContents() {
		return map;
	}
	
	public void setSlots(int slots) {
		this.slots = slots;
	}
	
	public void addSlots(int slots) {
		setSlots(this.slots + slots);
	}
	
	public static Backpack createBackpack(Player p) {		
		Main.total++;
		FileConfiguration fc = FileManager.getIntance().getBackpack(Main.total);
		
		fc.set("owner", p.getUniqueId().toString());
		fc.set("slots", 0);
		
		try {
			fc.save(FileManager.getIntance().getBackpackFile(Main.total));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Backpack bp = new Backpack(Main.total);
		Main.backpacks.add(bp);
		
		return bp;
	}
	
	public boolean isOwner(Player p) {
		if (owner.getUniqueId().equals(p.getUniqueId())) return true;
		return false;
	}
	
	public static int getCostPerSlot() {
		return Main.slotCost;
	}
	
	public int addItem(ItemStack stack) {
		
		@SuppressWarnings("deprecation")
		byte data = stack.getData().getData();
		Material mat = stack.getType();
		String key = mat.toString() + ":" + data;
		
		int sum = sumFrequency();
		int capacity = slots * 64;
		
		if (sum < capacity) { // We can add..
			int available = capacity - sum;
			int amount = stack.getAmount();
			
			if (map.containsKey(key)) {
				
				if (amount >= available) {
					map.put(key, map.get(key) + available);
					return amount - available;
				} else {
					map.put(key, map.get(key) + amount);
					return 0;
				}
				
			} else {
				if (amount >= available) {
					map.put(key, available);
					return amount - available;
				} else {
					map.put(key, amount);
					return 0;
				}
			}
		}
		
		return stack.getAmount();
	}
	
	private int sumFrequency() {
		
		int sum = 0;
		
		for (int i : map.values()) {
			sum += i;
		}
		
		return sum;
	}
	
	public static Backpack getBackpack(int id) {
		for (Backpack bp : Main.backpacks) {
			if (bp.getID() == id) return bp;
		}
		
		return null;
	}
	
}
