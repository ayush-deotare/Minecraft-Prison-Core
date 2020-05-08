package me.ayush_03.tokenapi;

import java.util.UUID;

import org.bukkit.entity.Player;

public class TokenAPI {
	
	private static TokenAPI instance = new TokenAPI();
	
	public static TokenAPI getInstance() {
		return instance;
	}
	
	public boolean isRegistered(Player p) {
		
		if (p == null) return false;
		
		UUID id = p.getUniqueId();
		
		if (!Main.tokens.containsKey(id)) return false;
		return true;
		
	}
	
	public int getBalance(Player p) {
		
		if (isRegistered(p)) 
			return Main.tokens.get(p.getUniqueId());
		else return 0;
		
	}
	
	public void setBalance(Player p, int newBalance) {
		
		if (isRegistered(p)) {
			Main.tokens.put(p.getUniqueId(), newBalance);
		}
		
	}
	
	public boolean withdrawTokens(Player p, int tokens) {
		
		if (isRegistered(p)) {
			
			int balance = getBalance(p);
			
			if (balance >= tokens) {
				Main.tokens.put(p.getUniqueId(), balance - tokens);
				return true;
			}
			
		}
		
		return false;
		
	}
	
	public void depositTokens(Player p, int tokens) {
		
		if (isRegistered(p)) {		
			Main.tokens.put(p.getUniqueId(), Main.tokens.get(p.getUniqueId()) + tokens);
		}
		
	}

 }
