package bot.modules;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;

import java.awt.*;
import java.time.Instant;

import static bot.main.Main.*;

public class Standard {
    public Standard(MessageReceivedEvent event) {
        helpCommand(event);
        mentionCommand(event);
        todoCommand(event);
        changelogCommand(event);
    }

    public Standard(SlashCommandInteractionEvent event) {
        pingCommand(event);
    }

    public void helpCommand(MessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        if (content.contains(getGuildPrefix(event) + "help") || content.contains(getGuildPrefix(event) + "commands")) {
            EmbedBuilder builder = new EmbedBuilder();

            builder.setTitle("Hi I'm Looig (VER. 0.10.6)\n");
            builder.setDescription("I ain't writing all that)");
            builder.setFooter("Made with \u2764 by moss#0059", null);
            builder.setColor(Color.pink);

            event.getMessage().replyEmbeds(builder.build()).mentionRepliedUser(false).queue();

        }
    }

    public void mentionCommand(MessageReceivedEvent event) {
        if ((event.getMessage().getContentRaw().equals("<@" + looig + ">")) || event.getMessage().getContentRaw().equals("<@!" + looig + ">")) {
            event.getMessage().reply("yo what up my prefix is `" + getGuildPrefix(event) + "`").mentionRepliedUser(false).queue();
        }
    }

    public void todoCommand(MessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        if (content.contains(getGuildPrefix(event) + "todo")) {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("To do list:");
            builder.setDescription("- make code not spaghetti\n");

            builder.setColor(Color.pink);

            event.getMessage().replyEmbeds(builder.build()).mentionRepliedUser(false).queue();
        }
    }

    public void pingCommand(SlashCommandInteractionEvent event) {
        Instant start = Instant.now();
        InteractionHook t = event.reply("pong").complete();
        long end = Instant.now().toEpochMilli() - start.toEpochMilli();
        long difference = end - event.getJDA().getGatewayPing();
        String m = "JDA delay:\n|**" + event.getJDA().getGatewayPing() + "**ms\n"
                + "bot delay:\n|**" + difference + "**ms\n"
                + "for a total ping of:\n|**" + end + "**ms";
        EmbedBuilder b = new EmbedBuilder();
        b.setColor(Color.PINK);
        b.setDescription(m);
        t.editOriginalEmbeds(b.build()).complete();
    }

    public void changelogCommand(MessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        if (content.contains(getGuildPrefix(event) + "changelog")) {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(Color.PINK);
            builder.setTitle("Changelog");
            builder.setDescription("**0.9.11 (current version):** \n "
                    + "- dotenv");
            builder.appendDescription("**0.9.10:**\n"
                    + "- substantial background code clean-up that has long been overdue");
            builder.setFooter("Made with \u2764 by moss#0059", null);
            event.getMessage().replyEmbeds(builder.build()).mentionRepliedUser(false).queue();
        }
    }

}
