package de.corruptedbytes.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.corruptedbytes.GriefBot;

public class Config {
	
	public static File griefPicture = new File("griefed.png");
	private static File configFile = new File("config.json");
	
	public static void initConfig() throws IOException {
		if (!configFile.exists()) {
			createTemplateConfig();
			System.out.println("Please edit " + configFile);
			System.exit(-1);
		}
		
		String s = null;
		StringBuilder configFileString = new StringBuilder();
		InputStream in = new FileInputStream(configFile);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		
	    try {
	        while ((s = reader.readLine()) != null) {
	            configFileString.append(s).append("\n");
	        }
		} catch (IOException e) {
			createTemplateConfig();
			System.out.println("Please edit " + configFile);
			System.exit(-1);
		}

        if (!griefPicture.exists())
			try {
				Utils.extractResource(griefPicture.getName(), griefPicture.getName());
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
        	
        GriefBot.instance.botToken = getJsonKey(configFileString.toString(), "botToken");
        GriefBot.instance.griefCommand = getJsonKey(configFileString.toString(), "griefCommand");
        GriefBot.instance.grieferUserID = getJsonKey(configFileString.toString(), "grieferUserID");
        GriefBot.instance.activityDescription = getJsonKey(configFileString.toString(), "activityDescription");
        GriefBot.instance.griefMessage = getJsonKey(configFileString.toString(), "griefMessage");
	}
	
	private static void createTemplateConfig() throws IOException {
	    BufferedWriter writer = new BufferedWriter(new FileWriter(configFile));
	    writer.write("{\n \"botToken\": \"YOUR-DISCORD-BOT-TOKEN-HERE\",\n \"griefCommand\": \"%start\",\n \"grieferUserID\": \"YOUR-USERID-HERE\",\n \"activityDescription\": \"Music\",\n \"griefMessage\": \"FUCKED-BY-%NAME%\"\n}");
	    writer.close();
	}
	
	private static String getJsonKey(String jsonString, String wantedKey) {
        Pattern jsonPattern = Pattern.compile("\"" + wantedKey + "\": \".*\"");
        Matcher matcher = jsonPattern.matcher(jsonString);

        if (matcher.find()) {
            return matcher.group(0).split("\"")[3];
        }
        return null;
    }
	
}