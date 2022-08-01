package bot.modules;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Scanner;

import static bot.main.Main.getGuildPrefix;
import static bot.main.Main.looigData;

public class Tools {

    public Tools() {
    }

    public Tools(SlashCommandInteractionEvent event) {
        switch (Objects.requireNonNull(event.getSubcommandName())) {
            case "dice":
                rollDiceCommand(event);
                break;
            case "coinflip":
                flipACoinCommand(event);
                break;
            case "asklooig":
                askLooigCommand(event);
                break;
        }
    }

    public void rollDiceCommand(SlashCommandInteractionEvent event) {
        if (event.getOption("d") == null) {
            event.reply("d6: " + ((int) (Math.random() * (6 - 1 + 1)) + 1)).queue();
        } else {
            int maxValue = Objects.requireNonNull(event.getOption("d")).getAsInt();
            event.reply("`d" + maxValue + ":` " + ((int) (Math.random() * (maxValue - 1 + 1)) + 1)).queue();
        }
    }

    public void flipACoinCommand(SlashCommandInteractionEvent event) {
        String response = "tails";
        if (Math.random() < 0.5) response = "heads";
        event.reply(response).mentionRepliedUser(false).queue();
    }

    public void saveMessageCommand(MessageReceivedEvent event) {
        try {
            String message = event.getMessage().getContentDisplay().substring((getGuildPrefix(event) + "saveMessage ").length());
            PrintWriter writer = new PrintWriter(looigData + event.getAuthor().getName() + ".txt");
            writer.println(message);
            writer.close();
            event.getMessage().reply("*saved*").mentionRepliedUser(false).queue();
        } catch (FileNotFoundException | IllegalStateException e) {
            event.getMessage().reply("couldn't access the message directory").mentionRepliedUser(false).queue();
        } catch (StringIndexOutOfBoundsException e) {
            event.getMessage().reply("something went wrong. Did you provide a message?").mentionRepliedUser(false).queue();
        }
    }

    public void giveMessageCommand(MessageReceivedEvent event) {
        try {
            File text = new File(looigData + event.getAuthor().getName() + ".txt");
            Scanner scnr = new Scanner(text);
            event.getMessage().reply(scnr.nextLine()).mentionRepliedUser(false).queue();
        } catch (FileNotFoundException e) {
            event.getMessage().reply("something went wrong. Did you maybe try to retrieve your message without saving it first?").mentionRepliedUser(false).queue();
        }
    }

    public void askLooigCommand(SlashCommandInteractionEvent event) {
        String[] m = {
                "Yes",                         //positive
                "Absolutely",                  //positive
                "Count on it",                 //positive
                "No doubt",                    //positive
                "Very likely",                 //positive
                "Bet on it",                   //positive

                "Maybe",                       //mostly positive
                "Possibly",                    //mostly positive

                "Can't say",                   //inconclusive
                "No idea",                     //inconclusive
                "That is not for me to judge", //inconclusive

                "You're hot",                  //useless
                "You stink",                   //useless

                "No",                          //negative
                "Absolutely not",              //negative
                "Odds aren't good",            //negative
                "No chance",                   //negative
                "Nope",                        //negative
                "Please don't",                //negative

                "Doubtful",                    //mostly negative
                "Probably not"                 //mostly negative
        };

        int v = ((int) (Math.random() * (m.length - 1)) + 1);

        event.reply("`Q` " + Objects.requireNonNull(event.getOption("q")).getAsString() + "\n`A` " + m[v]).queue();
    }
}
