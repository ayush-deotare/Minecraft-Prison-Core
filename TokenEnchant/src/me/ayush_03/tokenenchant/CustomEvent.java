package me.ayush_03.tokenenchant;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CustomEvent extends Event {
	
	Player p;
	Block b;

	private static final HandlerList HANDLERS = new HandlerList();
	
	public CustomEvent(Player p, Block b) {
		this.p = p;
		this.b = b;
	}
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
	
	public Player getPlayer() {
		return p;
	}
	
	public Block getBlock() {
		return b;
	}

}
