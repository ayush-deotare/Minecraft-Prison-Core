package me.ayush_03.tokenenchant;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.ayush_03.backpacks.Main;

public class GUIManager {
	
	private static GUIManager instance = new GUIManager();
	
	public static GUIManager getInstance() {
		return instance;
	}
	
	public Inventory createEnchantGUI() {
		
		Inventory inv = Bukkit.createInventory(null, 45, ChatColor.AQUA + "Enchantments");
		
		ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
		ItemMeta gMeta = glass.getItemMeta();
		
		gMeta.setDisplayName(ChatColor.BLACK + "");
		glass.setItemMeta(gMeta);
		
		for (int i = 0; i < 45; i++) {
			inv.setItem(i, glass);
		}
		
		ItemStack blaze = new ItemStack(Material.BLAZE_POWDER);
		ItemMeta bMeta = blaze.getItemMeta();
		bMeta.setDisplayName(ChatColor.GRAY + "Dragonsbreath");
		bMeta.setLore(CustomEnchant.DRAGONSBREATH.getGUILore());
		blaze.setItemMeta(bMeta);
		
		ItemStack charge = new ItemStack(Material.FIREBALL);
		ItemMeta cMeta = charge.getItemMeta();
		cMeta.setDisplayName(ChatColor.RED + "Dragonsbreath+");
		cMeta.setLore(CustomEnchant.DRAGONSBREATHP.getGUILore());
		charge.setItemMeta(cMeta);
		
		ItemStack book = new ItemStack(Material.BOOK);
		ItemMeta boMeta = book.getItemMeta();
		boMeta.setDisplayName(ChatColor.GRAY + "Fortune");
		boMeta.setLore(CustomEnchant.FORTUNE.getGUILore());
		book.setItemMeta(boMeta);
		
		ItemStack gap = new ItemStack(Material.GOLDEN_APPLE);
		ItemMeta gaMeta = gap.getItemMeta();
		gaMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Overcharge");
		gaMeta.setLore(CustomEnchant.OVERCHARGE.getGUILore());
		gap.setItemMeta(gaMeta);
		
		ItemStack gPick = new ItemStack(Material.GOLD_PICKAXE);
		ItemMeta gpMeta = gPick.getItemMeta();
		gpMeta.setDisplayName(ChatColor.GRAY + "Efficiency");
		gpMeta.setLore(CustomEnchant.EFFICIENCY.getGUILore());
		gPick.setItemMeta(gpMeta);
		
		ItemStack tnt = new ItemStack(Material.TNT);
		ItemMeta tMeta = tnt.getItemMeta();
		tMeta.setDisplayName(ChatColor.GRAY + "Explosive");
		tMeta.setLore(CustomEnchant.EXPLOSIVE.getGUILore());
		tnt.setItemMeta(tMeta);
		
		ItemStack beacon = new ItemStack(Material.BEACON);
		ItemMeta beMeta = beacon.getItemMeta();
		beMeta.setDisplayName(ChatColor.GOLD + "Nuke");
		beMeta.setLore(CustomEnchant.NUKE.getGUILore());
		beacon.setItemMeta(beMeta);
		
		ItemStack anvil = new ItemStack(Material.ANVIL);
		ItemMeta aMeta = anvil.getItemMeta();
		aMeta.setDisplayName(ChatColor.GRAY + "Unbreakable");
		aMeta.setLore(CustomEnchant.UNBREAKABLE.getGUILore());
		anvil.setItemMeta(aMeta);
		
		ItemStack emerald = new ItemStack(Material.EMERALD);
		ItemMeta eMeta = emerald.getItemMeta();
		eMeta.setDisplayName(ChatColor.GRAY + "Merchant");
		eMeta.setLore(CustomEnchant.MERCHANT.getGUILore());
		emerald.setItemMeta(eMeta);
		
		inv.setItem(10, blaze);
		inv.setItem(11, charge);
		inv.setItem(15, book);
		inv.setItem(16, gPick);
		inv.setItem(22, emerald);
		inv.setItem(28, gap);
		inv.setItem(29, tnt);
		inv.setItem(33, beacon);
		inv.setItem(34, anvil);
		
		return inv;
		
	}
	
	public Inventory createMenu() {
		
		Inventory inv = Bukkit.createInventory(null, 36, ChatColor.AQUA + "Menu");
		
		ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
		ItemMeta gMeta = glass.getItemMeta();
		
		gMeta.setDisplayName(ChatColor.BLACK + "");
		glass.setItemMeta(gMeta);
		
		for (int i = 0; i < 36; i++) {
			inv.setItem(i, glass);
		}
		
		ItemStack magma = new ItemStack(Material.MAGMA_CREAM);
		ItemMeta mMeta = magma.getItemMeta();
		mMeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "Export Tokens");
		
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "Left click: Export 5k tokens to an item");
		lore.add(ChatColor.GREEN + "Right click: Export 320k tokens to a stack of items");
		mMeta.setLore(lore);
		magma.setItemMeta(mMeta);
		
		ItemStack chest = new ItemStack(Material.CHEST);
		ItemMeta cMeta = chest.getItemMeta();
		cMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Upgrade Backpack");
		
		chest.setItemMeta(cMeta);
		
		ItemStack pick = new ItemStack(Material.DIAMOND_PICKAXE);
		ItemMeta pMeta = pick.getItemMeta();
		pMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Enchantments");
		pick.setItemMeta(pMeta);
		
		inv.setItem(11, magma);
		inv.setItem(15, chest);
		inv.setItem(22, pick);
		
		return inv;
		
		
	}

	public Inventory createBackpackGUI() {
		Inventory inv = Bukkit.createInventory(null, 45, ChatColor.AQUA + "Upgrade Backpack");
		
		ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
		ItemMeta gMeta = glass.getItemMeta();
		
		gMeta.setDisplayName(ChatColor.BLACK + "");
		glass.setItemMeta(gMeta);
		
		for (int i = 0; i < 45; i++) {
			inv.setItem(i, glass);
		}
		
		ItemStack s1 = new ItemStack(Material.CHEST);
		ItemMeta m1 = s1.getItemMeta();
		m1.setDisplayName(ChatColor.AQUA + "Add 1 slot");
		List<String> l1 = new ArrayList<String>();
		l1.add(ChatColor.YELLOW + "Cost: " + Main.slotCost + " tokens");
		m1.setLore(l1);
		s1.setItemMeta(m1);
		
		ItemStack s2 = new ItemStack(Material.CHEST);
		s2.setAmount(5);
		ItemMeta m2 = s2.getItemMeta();
		m2.setDisplayName(ChatColor.AQUA + "Add 5 slots");
		List<String> l2 = new ArrayList<String>();
		l2.add(ChatColor.YELLOW + "Cost: " + Main.slotCost * 5 + " tokens");
		m2.setLore(l2);
		s2.setItemMeta(m2);
		
		ItemStack s3 = new ItemStack(Material.CHEST);
		s3.setAmount(10);
		ItemMeta m3 = s3.getItemMeta();
		m3.setDisplayName(ChatColor.AQUA + "Add 10 slots");
		List<String> l3 = new ArrayList<String>();
		l3.add(ChatColor.YELLOW + "Cost: " + Main.slotCost * 10 + " tokens");
		m3.setLore(l3);
		s3.setItemMeta(m3);
		
		ItemStack s4 = new ItemStack(Material.CHEST);
		s4.setAmount(50);
		ItemMeta m4 = s4.getItemMeta();
		m4.setDisplayName(ChatColor.AQUA + "Add 50 slots");
		List<String> l4 = new ArrayList<String>();
		l4.add(ChatColor.YELLOW + "Cost: " + Main.slotCost * 50 + " tokens");
		m4.setLore(l4);
		s4.setItemMeta(m4);
		
		inv.setItem(19, s1);
		inv.setItem(21, s2);
		inv.setItem(23, s3);
		inv.setItem(25, s4);
		
		return inv;
	}
	

}
