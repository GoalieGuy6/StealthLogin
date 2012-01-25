package com.goalieguy6.stealthlogin.commands;

import com.goalieguy6.stealthlogin.StealthLogin;
import com.goalieguy6.stealthlogin.util.Logger;
import com.goalieguy6.stealthlogin.util.Message;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Stealth implements CommandExecutor {
	
	private final StealthLogin plugin;
	
	public Stealth(StealthLogin instance) {
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!command.getName().equalsIgnoreCase("stealthlogin")) {
			return false;
		}
		
		if (!(sender instanceof Player)) {
			Logger.raw("Only players can display join/quit messages.");
			return true;
		}
		
		Player player = (Player) sender;
		
		boolean hasPermission = false;
		
		if (plugin.usePermissions()) {
			hasPermission = player.hasPermission("stealthlogin.display");
		} else if (player.isOp()) {
			hasPermission = true;
		}
		
		if (!hasPermission) {
			return true;
		}
		
		String message;
		String name = (plugin.useDisplayName()) ? player.getDisplayName() : player.getName();
		
		if (args.length != 1) {
			message = "&cInvalid syntax. /" + label + " <join/quit>";
			player.sendMessage(Message.parseColor(message));
		} else if (args[0].equalsIgnoreCase("join") || args[0].equalsIgnoreCase("login")) {
			message = "&e" + name + " joined the game.";
			plugin.getServer().broadcastMessage(Message.parseColor(message));
		} else if (args[0].equalsIgnoreCase("quit") || args[0].equalsIgnoreCase("logout")) {
			message = "&e" + name + " left the game.";
			plugin.getServer().broadcastMessage(Message.parseColor(message));
		} else {
			message = "&cInvalid syntax. /" + label + " <join/quit>";
			player.sendMessage(Message.parseColor(message));
		}
		
		return true;
	}

}
