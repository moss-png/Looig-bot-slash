package bot.modules;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Objects;

import static bot.main.Main.*;

public class Management {
    private final CommandData[] commands =
            {
                    Commands.slash("yeah", "yeah"),
                    Commands.slash("funnycat", "get a funny cat")
                            .addSubcommands(
                                    new SubcommandData("random","random cat"),
                                    new SubcommandData("specific", "pick a number and see where it leads")
                                            .addOption(OptionType.INTEGER,"id","numerical id of the cat",true),
                                    new SubcommandData("count","get the total count and filesize of all cats")),
                    Commands.slash("management","internal")
                            .addSubcommands(new SubcommandData("logging","on at launch")
                                .addOption(OptionType.BOOLEAN,"toggle","logging yay/nay?",true)),
                    Commands.slash("math","left-overs from high school :nauseated_face:")
                            .addSubcommands(
                                    new SubcommandData("gcd","greatest common divisor")
                                            .addOption(OptionType.INTEGER,"a","first number",true)
                                            .addOption(OptionType.INTEGER,"b","second number",true),
                                    new SubcommandData("lcm","least common multiplier")
                                            .addOption(OptionType.INTEGER,"a","first number",true)
                                            .addOption(OptionType.INTEGER,"b","second number",true),
                                    new SubcommandData("isprime","is the provided number prime or not")
                                            .addOption(OptionType.INTEGER, "x", "the number in question")),
                    Commands.slash("ping","get the ping"),
                    Commands.slash("tools","every day stuff")
                            .addSubcommands(
                                    new SubcommandData("dice","roll a die")
                                            .addOption(OptionType.INTEGER,"d","if left blank a d6 will be used"),
                                    new SubcommandData("coinflip", "flip a coin"))
            };


    public Management(MessageReceivedEvent event){
        prefixCommand(event);
        updateSlashCommands(event);
        deleteSlashCommands(event);
        updateSlashCommandsGlobal(event);
        loggingCommand(event);
    }

    public Management(SlashCommandInteractionEvent event){
        //switch (event.getOption("command").getAsString())
    }

    public void prefixCommand(MessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        if (content.contains(getGuildPrefix(event) + "changePrefix")) {

            try {
                if (Objects.requireNonNull(event.getMessage().getMember()).hasPermission(Permission.ADMINISTRATOR)) {

                    Guild guild = event.getGuild();
                    try {
                        String myPrefix = getGuildPrefix(event);
                        PrintWriter writer = new PrintWriter(looigData + guild.getId() + "_Prefix.txt");
                        writer.println(event.getMessage().getContentRaw().substring((myPrefix + "changePrefix ").length()));
                        writer.close();
                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle("You successfully changed the prefix to `" + getGuildPrefix(event) + "`");
                        builder.setColor(Color.GREEN);
                        event.getMessage().replyEmbeds(builder.build()).mentionRepliedUser(false).queue();
                    } catch (FileNotFoundException e) {
                        System.err.println("That shouldn't have happened");
                    }
                } else {
                    event.getMessage().reply("you need admin permissions to do that").mentionRepliedUser(false).queue();
                }
            } catch (NullPointerException e) {
                event.getMessage().reply("sorry you can't change the prefix in a direct message").mentionRepliedUser(false).queue();
            }
        }
        if (content.contains("<@!" + looig + "> changePrefix")) {
            try {
                if (Objects.requireNonNull(event.getMessage().getMember()).hasPermission(Permission.ADMINISTRATOR)) {

                    Guild guild = event.getGuild();
                    try {
                        PrintWriter writer = new PrintWriter(looigData + guild.getId() + "_Prefix.txt");
                        writer.println(event.getMessage().getContentRaw().substring(("<@!" + looig + "> changePrefix ").length()));
                        writer.close();
                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle("You successfully changed the prefix to `" + getGuildPrefix(event) + "`");
                        builder.setColor(Color.GREEN);
                        event.getMessage().replyEmbeds(builder.build()).mentionRepliedUser(false).queue();
                    } catch (FileNotFoundException e) {
                        System.err.println("That shouldn't have happened");
                    }
                } else {
                    event.getMessage().reply("you need admin permissions to do that").mentionRepliedUser(false).queue();
                }
            } catch (NullPointerException e) {
                event.getMessage().reply("sorry you can't change the prefix in a direct message").mentionRepliedUser(false).queue();
            }
        }
    }


    public void updateSlashCommands(MessageReceivedEvent event){
        String content = event.getMessage().getContentRaw();
        if (content.contains(getGuildPrefix(event) + "updateSlash") &&
                event.getAuthor().getId().contains(moss)) {
            event.getGuild().updateCommands().addCommands(commands).queue();

        }
    }

    public void deleteSlashCommands(MessageReceivedEvent event){
        String content = event.getMessage().getContentRaw();
        if (content.contains(getGuildPrefix(event) + "deleteSlash") &&
                event.getAuthor().getId().contains(moss)) {
            for(Command command : event.getGuild().retrieveCommands().complete()) {
                event.getGuild().deleteCommandById(command.getId()).queue();
            }
        }
    }

    public void updateSlashCommandsGlobal(MessageReceivedEvent event){
        String content = event.getMessage().getContentRaw();
        if (content.contains(getGuildPrefix(event) + "updateSlashGlobal") &&
                event.getAuthor().getId().contains(moss)) {
            event.getJDA().updateCommands().addCommands(commands).queue();
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
