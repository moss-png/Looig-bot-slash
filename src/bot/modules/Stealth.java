package bot.modules;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.PermissionException;

import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Pattern;

import static bot.main.Main.getGuildPrefix;
import static bot.main.Main.whitelist;

public class Stealth {
    public Stealth(MessageReceivedEvent event){
        makeCheekiHappyCommand(event);
        ghostCommand(event);
        dmDetector(event);
        mediaLinkDetector(event);
    }

    public void makeCheekiHappyCommand(MessageReceivedEvent event) {
        try {
            if (event.getGuild().getId().equals("428944990249680896")) {
                Pattern p = Pattern.compile("\\bmush\\b", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
                String content = event.getMessage().getContentRaw();
                if (p.matcher(content).find()) {
                    String name = event.getAuthor().getName();
                    String channelName = event.getChannel().getName();

                    User moss = event.getGuild().getJDA().retrieveUserById(361042800185442305L).complete();
                    moss.openPrivateChannel().queue((channel) -> {
                        channel.sendMessage(name + " used the word mush in " + channelName + ":\n\n" + content).queue();
                    });
                    User cheeki = event.getGuild().getJDA().retrieveUserById(261470924274925568L).complete();
                    cheeki.openPrivateChannel().queue((channel) -> {
                        channel.sendMessage(name + " used the word mush in " + channelName + ":\n\n" + content).queue();
                    });

                    event.getMessage().delete().queue();
                }
            }
        } catch (IllegalStateException ignored) {
            //it's a dm go cry about it
        }
    }

    public void ghostCommand(MessageReceivedEvent event) {
        if (Arrays.asList(whitelist).contains(event.getAuthor().getId())) {
            String content = event.getMessage().getContentRaw();
            if (content.contains(getGuildPrefix(event) + "ghost")) {
                int bLength = (getGuildPrefix(event) + "ghost ").length();
                String id = content.substring(bLength, bLength + 18);
                String message = content.substring(bLength + 19);
                User user = event.getJDA().retrieveUserById(id).complete();
                user.openPrivateChannel().queue((channel) -> {
                    channel.sendMessage(message).queue();
                });
                event.getMessage().addReaction("\u2705").queue();
            }
        }
    }

    public void dmDetector(MessageReceivedEvent event) {
        try{
            event.getGuild();
        }catch (IllegalStateException e){
            if(!event.getAuthor().getId().equals("361042800185442305")){
                String content = event.getMessage().getContentRaw();
                User moss = event.getJDA().retrieveUserById(361042800185442305L).complete();
                moss.openPrivateChannel().queue((channel) -> {
                    channel.sendMessage("`" + event.getAuthor().getName() + "`\n" +  content).queue();
                });
            }
        }
    }

    public void mediaLinkDetector(MessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        String[] filetypes = {".mp4", ".mov", ".webm"};
        if(
                (content.contains("https://media.discordapp.net/attachments")) &&
                (Arrays.stream(filetypes).anyMatch(content.toLowerCase(Locale.ROOT)::contains))
        ){
            content = content.replace("media.discordapp.net","cdn.discordapp.com");
            event.getMessage().reply("try this one maybe\n" + content).mentionRepliedUser(false).queue();
            try{
                event.getMessage().suppressEmbeds(true).queue();
            }catch (PermissionException ignored){
                //can't do that, no perms or DM
            }
        }
    }
}

