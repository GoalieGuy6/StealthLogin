package com.goalieguy6.stealthlogin.util;

/**
 * Message util to parse ampersand color codes
 * 
 * @author GoalieGuy6 <goalieguy6@goalieguy6.com>
 */
public class Message {
	
	public static String stripColor(String message) {
		return message.replaceAll("(&([KkA-Fa-f0-9]))", "").replaceAll("\u00A7([A-Fa-f0-9])", "");
	}
	
	public static String parseColor(String message) {
		return message.replaceAll("(&([KkA-Fa-f0-9]))", "\u00A7" + "$2".toLowerCase());
	}

}
