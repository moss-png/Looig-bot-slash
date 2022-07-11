package bot.modules;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Objects;

import static bot.main.Main.*;

public class Management {
    private CommandData[] commands =
            {
                    Commands.slash("yeah", "yeah"),
                    Commands.slash("funnycat", "replies with a cat (most of the time)")
                            .addOption(OptionType.INTEGER, "cat", "ID of the cat"),
                    Commands.slash("funnycatcount","replies with the total count and filesize of all cats")
            };


    public Management(MessageReceivedEvent event){
        prefixCommand(event);
        updateSlashCommands(event);
        deleteSlashCommands(event);
        updateSlashCommandsGlobal(event);
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

}
