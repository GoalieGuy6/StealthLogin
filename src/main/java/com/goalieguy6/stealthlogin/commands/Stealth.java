package com.goalieguy6.stealthlogin.commands;

import com.goalieguy6.stealthlogin.StealthLogin;
import com.goalieguy6.stealthlogin.util.Message;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command handler to manually display join/quit messages.
 * 
 * @author GoalieGuy6 <goalieguy6@goalieguy6.com>
 */
public class Stealth implements CommandExecutor {
	
	private final StealthLogin plugin;
	
	public Stealth(StealthLogin instance) {
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!command.getName().equalsIgnoreCase("stealthlogin")) {
			return false;
		}
		
		if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
			plugin.loadConfig();
			sender.sendMessage(Message.parseColor("StealthLogin config reloaded."));
			return true;
		}
		
		if (!(sender instanceof Player)) {
			sender.sendMessage("Invalid syntax. /" + label + " reload");
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
			message = plugin.getJoinMessage().replace("%player%", name);
			plugin.getServer().broadcastMessage(Message.parseColor(message));
		} else if (args[0].equalsIgnoreCase("quit") || args[0].equalsIgnoreCase("logout")) {
			message = plugin.getQuitMessage().replace("%player%", name);
			plugin.getServer().broadcastMessage(Message.parseColor(message));
		} else {
			message = "&cInvalid syntax. /" + label + " <join/quit>";
			player.sendMessage(Message.parseColor(message));
		}
		
		return true;
	}

}
