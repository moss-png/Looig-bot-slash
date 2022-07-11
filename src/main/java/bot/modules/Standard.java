package bot.modules;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.time.Instant;

import static bot.main.Main.*;

public class Standard {
    public Standard(MessageReceivedEvent event) {
        helpCommand(event);
        mentionCommand(event);
        todoCommand(event);
        pingCommand(event);
        changelogCommand(event);
        loggingCommand(event);
    }

    public void helpCommand(MessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        if (content.contains(getGuildPrefix(event) + "help") || content.contains(getGuildPrefix(event) + "commands")) {
            EmbedBuilder builder = new EmbedBuilder();

            builder.setTitle("Hi I'm Looig (VER. 0.10.2)\n");
            builder.setDescription("suck it ;)");
//            builder.setDescription("Here's a list of most commands (you need to add `" + getGuildPrefix(event) + "` before the text): \n\n");
//            builder.appendDescription("""
//                    **help** or **commands**
//                    - displays this list
//                    """);
//            builder.appendDescription("""
//                    **rollDice <your number>**
//                    - rolls a dice from 1 to your number of choice (not adding a number will be handled like a D6)
//                    """);
//            builder.appendDescription("""
//                    **8ball** or **askLooig**
//                    - ask a question and let Looig decide
//                    """);
//            builder.appendDescription("**role <role>** and **removeRole <role>**\n"
//                    + "- give yourself a role or remove one instead (preview not yet guild specific)\n");
//            builder.appendDescription("""
//                    **ping**
//                    - should be below 500ms
//                    """);
//            builder.appendDescription("**changePrefix**\n"
//                    + "- `@Looig changePrefix <PREFIX>` or `" + getGuildPrefix(event) + "changePrefix <PREFIX>`\n");
//            builder.appendDescription("**saveMessage** and **giveMessage**\n"
//                    + "- `" + getGuildPrefix(event) + "saveMessage poopy stinky` -> `" + getGuildPrefix(event) + "giveMessage` will return \"poopy stinky\"\n");
//            builder.appendDescription("**funnyCat**\n"
//                    + "- picks from " + Objects.requireNonNull(new File(catFolder).list()).length + " funny cat pictures and videos, collected by yours truly (new entries are added regularly)\n");
//            builder.appendDescription("""
//                    **funnyCat count**
//                    - shows amount of of files stored
//                    """);
//            builder.appendDescription("""
//                    **funnyCat specific `x`**
//                    - tries to find the number you specify, replace `x` with the number you want.
//                    - Keep in mind, the bot starts counting at 0, so if there were 10 total files the highest number would be 9
//                    """);
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

    public void pingCommand(MessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        if (content.contains(getGuildPrefix(event) + "ping")) {
            Instant start = Instant.now();
            Message t = event.getMessage().reply("pong").mentionRepliedUser(false).complete();
            long end = Instant.now().toEpochMilli() - start.toEpochMilli();
            long difference = end - event.getJDA().getGatewayPing();
            String m = "JDA delay:\n|**" + event.getJDA().getGatewayPing() + "**ms\n"
                    + "bot delay:\n|**" + difference + "**ms\n"
                    + "for a total ping of:\n|**" + end + "**ms";
            EmbedBuilder b = new EmbedBuilder();
            b.setColor(Color.PINK);
            b.setDescription(m);
            t.editMessageEmbeds(b.build()).mentionRepliedUser(false).complete();
        }
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

    public void loggingCommand(MessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        if (content.equalsIgnoreCase(getGuildPrefix(event) + "logging Off") && event.getAuthor().getId().contains(moss)) {
            logging = false;
            System.out.println("logging is now off");
            event.getMessage().reply("message logging has been turned off").mentionRepliedUser(false).queue();
        } else if (content.equalsIgnoreCase(getGuildPrefix(event) + "logging On") && event.getAuthor().getId().contains(moss)) {
            logging = true;
            System.out.println("logging is now back on");
            event.getMessage().reply("message logging has been turned on").mentionRepliedUser(false).queue();
        } else if (content.equalsIgnoreCase(getGuildPrefix(event) + "logging status")) {
            event.getMessage().reply("message logging is set to " + logging).mentionRepliedUser(false).queue();
        }
    }

}
