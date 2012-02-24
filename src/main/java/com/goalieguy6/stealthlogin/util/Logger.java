package com.goalieguy6.stealthlogin.util;

import com.goalieguy6.stealthlogin.StealthLogin;

/**
 * Logger util for sending messages to the console
 * 
 * @author GoalieGuy6 <goalieguy6@goalieguy6.com>
 */
public class Logger {
	
	private static java.util.logging.Logger logger;
	
	public static void setup(StealthLogin plugin) {
		logger = plugin.getLogger();
	}
	
	public static void config(String message) {
		logger.config(message);
	}
	
	public static void info(String message) {
		logger.info(message);
	}
	
	public static void raw(String message) {
		System.out.println(message);
	}
	
	public static void severe(String message) {
		logger.severe(message);
	}
	
	public static void warning(String message) {
		logger.warning(message);
	}

}
