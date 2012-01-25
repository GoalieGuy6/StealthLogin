package com.goalieguy6.stealthlogin;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class StealthListener implements Listener {
	
	private final StealthLogin plugin;
	
	public StealthListener(StealthLogin instance) {
		this.plugin = instance;
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (hasPermission(event.getPlayer())) {
			event.setJoinMessage(null);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerQuit(PlayerQuitEvent event) {
		if (hasPermission(event.getPlayer())) {
			event.setQuitMessage(null);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerKick(PlayerKickEvent event) {
		if (hasPermission(event.getPlayer()) && plugin.hideKick()) {
			event.setLeaveMessage(null);
		}
	}
	
	private boolean hasPermission(Player player) {
		if (plugin.usePermissions()) {
			return player.hasPermission("stealthlogin.stealth");
		} else if (player.isOp()) {
			return true;
		}
		
		return false;
	}

}
