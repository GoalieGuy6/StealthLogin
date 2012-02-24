package com.goalieguy6.stealthlogin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.goalieguy6.stealthlogin.commands.Stealth;
import com.goalieguy6.stealthlogin.util.Logger;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Plugin to hide player join/quit/kick messages
 * 
 * @author GoalieGuy6 <goalieguy6@goalieguy6.com>
 */
public class StealthLogin extends JavaPlugin {
	
	public String name; // "StealthLogin"
	public String version;
	
	private YamlConfiguration config;
	
	private boolean permissions;
	private boolean displayName;
	private boolean hideKick;
	
	private String joinMessage;
	private String quitMessage;
	
	private StealthListener listener;

	public void onDisable() {
		getCommand("stealthlogin").setExecutor(null);
		listener = null;
		config = null;
	}

	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.name = pdfFile.getName();
		this.version = pdfFile.getVersion();
		
		Logger.setup(this);
		
		loadConfig();
		setupCommands();
		
		PluginManager pm = getServer().getPluginManager();

		listener = new StealthListener(this);
		pm.registerEvents(listener, this);
		
		Logger.info("Version " + this.version + " enabled.");
		
		if (this.version.endsWith("SNAPSHOT")) {
			Logger.info("You are currently running a development build!");
			Logger.info("Please report any issues on the plugin page.");
		}
	}
	
	public void loadConfig() {
		config = new YamlConfiguration();
		createConfig();
		
		File file = new File(getDataFolder(), "config.yml");
				
		try {
			config.load(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Logger.warning("Could not load config, using default settings!");
		} catch (IOException e) {
			e.printStackTrace();
			Logger.warning("Could not load config, using default settings!");
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
			Logger.warning("Could not load config, using default settings!");
		}
		
		permissions = config.getBoolean("permissions", true);
		displayName = config.getBoolean("display-name", false);
		hideKick = config.getBoolean("silentkick", true);
		joinMessage = config.getString("join-message");
		quitMessage = config.getString("quit-message");
	}
	
	private void createConfig() {
		getDataFolder().mkdirs();
		File file = new File(getDataFolder(), "config.yml");
		
		try {
			config.load(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		
		config.options().header("StealthLogin Config\n" +
				"\n permissions: Whether or not to use superperms (OP only if false)" +
				"\n display-name: Whether or not to use display names in the login/logout message" +
				"\n silentkick: Whether or not to hide leave messages when kicked" +
				"\n join-message: The format of manually displayed join messages" +
				"\n quit-message: The format of manually displayed quit messages");
		
		config.addDefault("permissions", true);
		config.addDefault("display-name", false);
		config.addDefault("silentkick", true);
		config.addDefault("join-message", "&e%player% joined the game.");
		config.addDefault("quit-message", "&e%player% left the game.");
		
		config.options().copyDefaults(true);
		
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
			Logger.warning("Error saving config file!");
		}
	}
	
	private void setupCommands() {
		getCommand("stealthlogin").setExecutor(new Stealth(this));
	}
	
	public boolean usePermissions() {
		return permissions;
	}
	
	public boolean useDisplayName() {
		return displayName;
	}
	
	public boolean hideKick() {
		return hideKick;
	}
	
	public String getJoinMessage() {
		return joinMessage;
	}
	
	public String getQuitMessage() {
		return quitMessage;
	}

}
