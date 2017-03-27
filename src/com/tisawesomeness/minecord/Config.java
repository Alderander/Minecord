package com.tisawesomeness.minecord;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.dv8tion.jda.core.entities.Game;

public class Config {

	private static String clientToken;
	private static boolean devMode;
	private static String game;
	private static String name;
	private static String prefix;
	private static int notificationTime;
	private static boolean deleteCommands;
	private static boolean sendTyping;
	private static String invite;
	private static boolean elevatedSkipCooldown;
	
	private static ArrayList<String> elevatedUsers = new ArrayList<String>();

	public Config(File file) {
		
		//Look for client token
		for (String arg : Bot.args) {
			if (arg.matches("{32,}")) {
				System.out.println("Found custom client token: " + arg);
				clientToken = arg;
				break;
			}
		}
		
		try {
			//Parse config JSON
			JSONObject config = new JSONObject(FileUtils.readFileToString(file, "UTF-8"));
			if (clientToken == null) {
				clientToken = config.getString("clientToken");
			}
			devMode = config.getBoolean("devMode");
			game = config.getString("game");
			name = config.getString("name");
			prefix = config.getString("prefix");
			notificationTime = config.getInt("notificationTime");
			deleteCommands = config.getBoolean("deleteCommands");
			sendTyping = config.getBoolean("sendTyping");
			invite = config.getString("invite");
			elevatedSkipCooldown = config.getBoolean("elevatedSkipCooldown");
			
			JSONArray eu = config.getJSONArray("elevatedUsers");
			for (int i = 0; i < eu.length(); i++) {
				elevatedUsers.add(eu.getString(i));
			}
			
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void update() {
		Bot.jda.getPresence().setGame(Game.of(game
			.replaceAll("\\{prefix\\}", prefix)
			.replaceAll("\\{guilds\\}", String.valueOf(Bot.jda.getGuilds().size()))
			.replaceAll("\\{users\\}", String.valueOf(Bot.jda.getUsers().size()))
			.replaceAll("\\{channels\\}", String.valueOf(Bot.jda.getTextChannels().size()))
		));
		if (name != "") {
			Bot.jda.getSelfUser().getManager().setName(name);
		}
	}

	protected static String getClientToken() {return clientToken;}
	public static boolean getDevMode() {return devMode;}
	public static String getGame() {return game;}
	public static String getName() {return name;}
	public static String getPrefix() {return prefix;}
	public static int getNotificationTime() {return notificationTime;}
	public static boolean getDeleteCommands() {return deleteCommands;}
	public static boolean getSendTyping() {return sendTyping;}
	public static String getInvite() {return invite;}
	public static boolean getElevatedSkipCooldown() {return elevatedSkipCooldown;}
	public static ArrayList<String> getElevatedUsers() {return elevatedUsers;}
	
}
