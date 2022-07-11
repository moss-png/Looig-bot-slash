package bot.modules;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import static bot.main.Main.getGuildPrefix;
import static bot.main.Main.looigData;

public class Tools {
    public Tools (MessageReceivedEvent event){
        rollDiceCommand(event);
        messageSaving(event);
        askLooigCommand(event);
        flipACoinCommand(event);
    }


    public void rollDiceCommand(MessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        if (content.equalsIgnoreCase(getGuildPrefix(event) + "dice")) {
            event.getMessage().reply("You got " + ((int) (Math.random() * (6 - 1 + 1)) + 1) + ".").mentionRepliedUser(false).queue();
        } else {
            if (content.contains(getGuildPrefix(event) + "dice")) {
                new Thread(() -> {
                    event.getChannel().sendTyping().complete();
                }).start();
                try {
                    int maxValue = Integer.parseInt(content.substring((getGuildPrefix(event) + "dice ").length()));
                    event.getMessage().reply("You got " + ((int) (Math.random() * (maxValue - 1 + 1)) + 1) + ".").mentionRepliedUser(false).queue();
                } catch (NumberFormatException e) {
                    event.getMessage().reply("You got " + ((int) (Math.random() * (6 - 1 + 1)) + 1) + ".").mentionRepliedUser(false).queue();
                }
            }

        }
    }

    public void flipACoinCommand(MessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        if (content.equalsIgnoreCase(getGuildPrefix(event) + "coinflip") ||
                content.equalsIgnoreCase(getGuildPrefix(event) + "coin")) {
            String response = "tails";
            if(Math.random()<0.5) response = "heads";
            event.getMessage().reply(response).mentionRepliedUser(false).queue();
        }
    }

    private void messageSaving(MessageReceivedEvent event) {
        try {
            if (event.getMessage().getContentRaw().equalsIgnoreCase(getGuildPrefix(event) + "saveMessage")) {
                new Thread(() -> {
                    event.getChannel().sendTyping().complete();
                }).start();
                event.getMessage().reply("Are you kidding me? There's nothing to save in there.").mentionRepliedUser(false).queue();
            } else {
                if (event.getMessage().getContentRaw().contains(getGuildPrefix(event) + "saveMessage") || event.getMessage().getContentRaw().contains(getGuildPrefix(event) + "savemessage")) {
                    new Thread(() -> {
                        event.getChannel().sendTyping().complete();
                    }).start();
                    PrintWriter writer = new PrintWriter(looigData + event.getAuthor().getName() + ".txt");
                    writer.println(event.getMessage().getContentDisplay().substring((getGuildPrefix(event) + "saveMessage ").length()));
                    writer.close();
                    event.getMessage().reply("*saved*").mentionRepliedUser(false).queue();
                }

                if (event.getMessage().getContentRaw().contains(getGuildPrefix(event) + "giveMessage") || event.getMessage().getContentRaw().contains(getGuildPrefix(event) + "givemessage")) {
                    new Thread(() -> {
                        event.getChannel().sendTyping().complete();
                    }).start();
                    File text = new File(looigData + event.getAuthor().getName() + ".txt");
                    Scanner scnr = new Scanner(text);
                    event.getMessage().reply(scnr.nextLine()).mentionRepliedUser(false).queue();
                }
            }
        } catch (FileNotFoundException | IllegalStateException e) {
            System.err.println("I messed up papa");
        }
    }

    public void askLooigCommand(MessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        if (content.contains(getGuildPrefix(event) + "8ball")
                || content.contains(getGuildPrefix(event) + "8Ball")
                || content.contains(getGuildPrefix(event) + "ohMagicConchShell")
                || content.contains(getGuildPrefix(event) + "askLooig")) {

            String[] m = {
                    "Yes",
                    "Absolutely",
                    "Possibly",
                    "You're hot ( ͡° ͜ʖ ͡°)",
                    "It will pass",
                    "Count on it",
                    "No doubt",
                    "Maybe",
                    "Act now",
                    "Very likely",
                    "Bet on it",
                    "Can't say",
                    "No",
                    "Go for it",
                    "Ask Again",
                    "Odds aren't good"
            };

            int v = ((int) (Math.random() * (m.length - 1)) + 1);

            event.getMessage().reply(m[v]).mentionRepliedUser(false).queue();
        }
    }
}
