package bot.modules;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.File;
import java.util.Objects;

import static bot.main.Main.*;

public class Toys {

    public Toys(){}

    public void trigger(MessageReceivedEvent event){
        loveYouCommand(event);
        trueCommand(event);
        pfpCommand(event);
        sampleEmbedCommand(event);
        typingCommand(event);
        egoCommand(event);
        corruptNCommand(event);
    }

    public void loveYouCommand(MessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        if (content.contains(getGuildPrefix(event) + "loveYou")) {
            event.getMessage().reply("love you too " + event.getAuthor().getAsMention() + "!").mentionRepliedUser(false).queue();
        }
    }

    public void trueCommand(MessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();

        if (content.equals("^")) {
            event.getChannel().sendMessage("^").queue();
        }
    }

    public void pfpCommand(MessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        if (content.contains(getGuildPrefix(event) + "pfp")) {
            event.getMessage().reply("https://i.imgur.com/ZrEmO8D.jpg").mentionRepliedUser(false).queue();

        }
    }

    public void sampleEmbedCommand(MessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        if (content.contains(getGuildPrefix(event) + "sampleEmbed")) {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setImage("https://media.discordapp.net/attachments/347254691966615552/753251643533033566/damrnts-620b2fb1-9c3b-4dda-be03-deccb607b00a.gif");
            builder.setColor(Color.PINK);
            event.getMessage().replyEmbeds(builder.build()).mentionRepliedUser(false).queue();
        }
    }

    public void typingCommand(MessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        if (content.equals(getGuildPrefix(event) + "typing")) {
            System.out.println("yup");
            event.getChannel().sendTyping().complete();
        }
    }

    public void egoCommand(MessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        if (content.equals(getGuildPrefix(event) + "ego")) {
            try{
                event.getGuild();
                EmbedBuilder builder = new EmbedBuilder();
                builder.setThumbnail(Objects.requireNonNull(event.getMessage().getMember()).getEffectiveAvatarUrl());
                builder.setFooter(event.getMessage().getAuthor().getName(), event.getMessage().getMember().getEffectiveAvatarUrl());
                builder.setImage(event.getMessage().getMember().getEffectiveAvatarUrl());
                builder.setAuthor(event.getMessage().getAuthor().getName(), null, event.getMessage().getMember().getEffectiveAvatarUrl());
                event.getMessage().replyEmbeds(builder.build()).mentionRepliedUser(false).queue();
            }catch (IllegalStateException e){
                event.getMessage().reply("this command does not work in DMs, sorry about that").mentionRepliedUser(false).queue();
            }
        }
    }

    public void corruptNCommand(MessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        if (content.equalsIgnoreCase(getGuildPrefix(event) + "n")) {
            event.getMessage().reply(new File(looigData + "n.jpg")).mentionRepliedUser(false).queue();
        }
    }
}
