package bot.modules;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.time.Instant;
import java.util.Arrays;

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
        if ((event.getMessage().getContentRaw().equals("<@520220136507047936>")) || event.getMessage().getContentRaw().equals("<@!520220136507047936>")) {
            event.getMessage().reply("yo what up my prefix is `" + getGuildPrefix(event) + "`\nUse `" + getGuildPrefix(event) + "help` to get started").mentionRepliedUser(false).queue();
        }
    }

    public void todoCommand(MessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        if (content.contains(getGuildPrefix(event) + "todo")) {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("To do list:");
            builder.setDescription("- make it so the bot doesn't accept files larger than 8 mb\n");

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
            builder.setDescription("**0.9.10 (current version):** \n "
                    + "- substantial background code clean-up that has long been overdue");
//            builder.setDescription("**BETA 9.9.4:** \n "
//                    + "- `" + getGuildPrefix(event) + "funnyCat specific` has been added, see `" + getGuildPrefix(event) + "help` for more info\n");
//            builder.appendDescription("**BETA 9.9.3:** \n "
//                    + "- `" + getGuildPrefix(event) + "funnyCat count` shows amount of files and total file size\n");
//            builder.appendDescription("**BETA 9.9.2:**\n"
//                    + "- added funny cats, try it out with `" + getGuildPrefix(event) + "funnyCat`\n"
//                    + "- cleaned up help command\n");
//            builder.appendDescription("**BETA 9.8.7:**\n"
//                    + "- Bot uses reply feature for almost every command now\n");
//            builder.appendDescription("**BETA 9.8.5:**\n"
//                    + "- Discord updates, I fix old stinky JDA 3.5 code and write shiny new JDA 4.2 code\n");
//            builder.appendDescription("**BETA 9.8.1:**\n"
//                    + "- The bot now works in DMs again. Prefix is `>`\n"
//                    + "- I figured out how to make it look like it's streaming\n");
//            builder.appendDescription("**BETA 9.8.0:**\n"
//                    + "- Made the prefix server-specific\n"
//                    + "- You can alternatively set it with `@Looig changePrefix <PREFIX>` now\n"
//                    + "- Pinging Looig will return the current prefix now instead of a stupid memey answer\n"
//                    + "- The playing tag no longer shows the current prefix (since it's no longer global there's no point)\n"
//                    + "- Side Effect: The bot no longer responds to dms (I might fix that eventually)\n"
//                    + "- cleaned up both `changelog` and `help`\n");
//            builder.appendDescription("**BETA 9.7.0:**\n" +
//                    "- Fixed the ping command, it will no longer show negative numbers (shoutouts to the code snippet I yoinked from the bot support server)\n");
//            builder.appendDescription("**BETA 9.6.0:**\n" +
//                    "- Removed the oof reaction (because it gets extremely annoying real quick)\n" +
//                    "- `^` causes a reaction only in isolation\n" +
//                    "- Memey reaction on mention\n" +
//                    "- Archive command wip \n");
//            builder.appendDescription("**BETA 9.2.0:**\n" +
//                    "- Added an abilities command that shows you the common ability for gear in Splatoon 2 (use `" + getGuildPrefix(event) + "abilities` to do so)\n");
//            builder.appendDescription("**BETA 9.1.0:**\n" +
//                    "- Playing tag now \"listens\" to the help command (looks better imo)\n");
//            builder.appendDescription("**BETA 9.0.0:**\n" +
//                    "- The bot now runs on a Raspberry Pi 4b instead of my old laptop\n");
//            builder.appendDescription("**BETA 8.5.0:**\n" +
//                    "- The usual bugfixes\n");
//            builder.appendDescription("**BETA 8.4.0:**\n" +
//                    "- Added a ping command so you can test the reaction speed of the bot (use `" + getGuildPrefix(event) + "ping` to do so)\n");
//            builder.appendDescription("**BETA 8.2.0:**\n" +
//                    "- Added the Maker Profile (still wip) (use `" + getGuildPrefix(event) + "makerprofile` for more info)\n");
//            builder.appendDescription("**BETA 6.0.0:**\n" +
//                    "- biiiiiiig code cleanup");
            builder.setFooter("Made with \u2764 by moss#0059", null);
            event.getMessage().replyEmbeds(builder.build()).mentionRepliedUser(false).queue();
        }
    }

    public void loggingCommand(MessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        if (content.equalsIgnoreCase(getGuildPrefix(event) + "logging Off") && Arrays.asList(whitelist).contains(event.getAuthor().getId())) {
            logging = false;
            System.out.println("logging is now off");
            event.getMessage().reply("message logging has been turned off").mentionRepliedUser(false).queue();
        } else if (content.equalsIgnoreCase(getGuildPrefix(event) + "logging On") && Arrays.asList(whitelist).contains(event.getAuthor().getId())) {
            logging = true;
            System.out.println("logging is now back on");
            event.getMessage().reply("message logging has been turned on").mentionRepliedUser(false).queue();
        } else if (content.equalsIgnoreCase(getGuildPrefix(event) + "logging status")) {
            event.getMessage().reply("message logging is set to " + logging).mentionRepliedUser(false).queue();
        }
    }

}
