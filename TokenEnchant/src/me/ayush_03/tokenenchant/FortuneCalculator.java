package me.ayush_03.tokenenchant;

import org.bukkit.configuration.file.FileConfiguration;

public class FortuneCalculator {
	
	public static int getDrops(int level) {
		FileConfiguration fc = FileManager.getInstance().getFortune();
		return fc.getInt(level + "");
	}

}
