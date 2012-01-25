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

public class StealthLogin extends JavaPlugin {
	
	public String name; // "StealthLogin"
	public String version;
	
	private YamlConfiguration config;
	
	private boolean permissions;
	private boolean displayName;
	private boolean hideKick;
	
	private StealthListener listener;

	public void onDisable() {
		getCommand("stealthlogin").setExecutor(null);
		listener = null;
		config = null;

		Logger.info("Version " + this.version + " disabled.");
	}

	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.name = pdfFile.getName();
		this.version = pdfFile.getVersion();
		
		Logger.setup(this);
		Logger.info("Initializing version " + this.version + ".");
		
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
	
	private void loadConfig() {
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
				"\n silentkick: Whether or not to hide leave messages when kicked");
		
		if (!config.isSet("permissions")) {
			config.set("permissions", true);
		}
		
		if (!config.isSet("display-name")) {
			config.set("display-name", false);
		}
		
		config.addDefault("permissions", true);
		config.addDefault("display-name", false);
		config.addDefault("silentkick", true);
		
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

}
