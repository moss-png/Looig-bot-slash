package bot.modules;

import bot.main.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.regex.Pattern;

import static bot.main.Main.getGuildPrefix;
import static bot.main.Main.*;

public class Stealth {

    public Stealth(){}

    public void trigger(MessageReceivedEvent event){
        makeCheekiHappyCommand(event);
        activeRelay(event);
        passiveRelay(event);
    }

    public void makeCheekiHappyCommand(MessageReceivedEvent event) {
        try {
            if (event.getGuild().getId().equals("428944990249680896")) {
                Pattern p = Pattern.compile("\\bmush\\b", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
                String content = event.getMessage().getContentRaw();
                if (p.matcher(content).find()) {
                    String name = event.getAuthor().getName();
                    String channelName = event.getChannel().getName();

                    User moss = event.getGuild().getJDA().retrieveUserById(Main.moss).complete();
                    moss.openPrivateChannel().queue((channel) -> channel.sendMessage(name + " used the word mush in " + channelName + ":\n\n" + content).queue());
                    User cheeki = event.getGuild().getJDA().retrieveUserById(261470924274925568L).complete();
                    cheeki.openPrivateChannel().queue((channel) -> channel.sendMessage(name + " used the word mush in " + channelName + ":\n\n" + content).queue());

                    event.getMessage().delete().queue();
                }
            }
        } catch (IllegalStateException ignored) {
            //it's a dm go cry about it
        }
    }

    public void activeRelay(MessageReceivedEvent event) {
        if (Arrays.stream(whitelist).anyMatch(event.getAuthor().getId()::contains)) {
            JDA jda = event.getJDA();
            try {
                String content = event.getMessage().getContentRaw();
                if (content.contains(getGuildPrefix(event) + "relay")) {
                    int bLength = (getGuildPrefix(event) + "relay ").length();
                    String id = content.substring(bLength, bLength + 18);
                    String message = content.substring(bLength + 19);
                    User user = event.getJDA().retrieveUserById(id).complete();
                    user.openPrivateChannel().queue((channel) -> channel.sendMessage(message).queue());
                    event.getMessage().addReaction("\u2705").queue();
                }
            }catch (Exception e) {
                Main.dmException(jda, e);
            }
        }
    }

    public void passiveRelay(MessageReceivedEvent event) {
        if (!event.isFromGuild() && Arrays.stream(whitelist).noneMatch(event.getAuthor().getId()::contains)) {
            String content = event.getMessage().getContentRaw();
            User moss = event.getJDA().retrieveUserById(Main.moss).complete();
            moss.openPrivateChannel().queue((channel) ->
                    channel.sendMessage("`" + event.getAuthor().getName() + "`\n" + content).queue());
        }
    }
}


