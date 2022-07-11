package bot.modules.unused;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import static bot.main.Main.getGuildPrefix;
import static bot.main.Main.primed;

public class Unused {
    public Unused(MessageReceivedEvent event){
        archiveCommand(event);
        jackMoment(event);
        lunchCommand(event);
    }

    public void archiveCommand(MessageReceivedEvent event) {
        try {
            String content = event.getMessage().getContentRaw();
            if (content.contains(getGuildPrefix(event) + "archive")) {
                if (Objects.requireNonNull(event.getGuild().getMemberById(event.getAuthor().getId())).hasPermission(Permission.ADMINISTRATOR)) {
                    if (event.getMessage().getAttachments().isEmpty()) {
                        event.getMessage().reply("no attachment").mentionRepliedUser(false).queue();
                    } else {
                        String message = content.substring((getGuildPrefix(event) + "archive").length());
                        if (!message.isEmpty()) {
                            if (!message.substring((getGuildPrefix(event) + "dd/mm/yyyy").length()).isEmpty()) {
                                InputStream in = new URL(event.getMessage().getAttachments().get(0).getUrl()).openStream();
                                Files.copy(in, Paths.get("/home/moss/Desktop/Looig Data/" + message.substring(1) + ".png"), StandardCopyOption.REPLACE_EXISTING);
                                event.getMessage().reply("worked(?)").mentionRepliedUser(false).queue();
                            } else {
                                EmbedBuilder builder = new EmbedBuilder();
                                builder.setTitle("Looks like you forgot to add the name of the area you're trying to archive");
                                builder.setColor(Color.RED);
                                event.getMessage().replyEmbeds(builder.build()).mentionRepliedUser(false).queue();
                            }
                        } else {
                            EmbedBuilder builder = new EmbedBuilder();
                            builder.setTitle("Please provide a date and the name of the area you're trying to archive");
                            builder.setColor(Color.RED);
                            event.getMessage().replyEmbeds(builder.build()).mentionRepliedUser(false).queue();
                        }
                    }
                }
            }
        }catch (IOException ignored){}
    }

    public void jackMoment(MessageReceivedEvent event) {
        //if there is an embed in the message (if the list of embeds in the message is not empty)
        if (!(event.getMessage().getEmbeds().isEmpty())) {
            //449513064241561601 Jack ID
            //425128120509661184 suggestion voting id
            //I used a channel from my server and my own ID for testing

            //if the bottom text of the embed contains the ID we're looking for AND it's located in the right channel AND we have activated the command
            if ((Objects.requireNonNull(Objects.requireNonNull(event.getMessage().getEmbeds().get(0).getFooter()).getText()).contains("361042800185442305")) && (event.getChannel().getId().contains("738880279565041827")) && primed) {
                //delete the message
                event.getMessage().delete().queue();
                //send me a dm that a suggestion has been deleted
                User user = event.getGuild().getJDA().getUserById(361042800185442305L);
                //this is a lambda expression and I hate it
                assert user != null;
                user.openPrivateChannel().queue((channel) -> {
                    channel.sendMessage("Jack moment deleted").queue();
                });
            }
        }
        //if the suggestion bot cries for help when its embed gets deleted, make it shut up
        if ((event.getMessage().getAuthor().getId().equals("474051954998509571")) && (event.getMessage().getContentRaw().equals("An error occurred: **Unknown Message**"))) {
            event.getMessage().delete().queue();
        }
        //if either an administrator or me sends ">jackMomentStart"
        if ((event.getMessage().getContentRaw().contains(getGuildPrefix(event) + "jackMomentStart"))
                && ((Objects.requireNonNull(event.getGuild().getMemberById(event.getAuthor().getId())).hasPermission(Permission.ADMINISTRATOR)) || (event.getAuthor().getId().equals("361042800185442305")))) {
            //prime the command for detection and react to the command with a check mark
            primed = true;
            event.getMessage().addReaction("\u2705").queue();
        }
        //the same thing but with ">stopJackMoment"
        if ((event.getMessage().getContentRaw().contains(getGuildPrefix(event) + "jackMomentStop"))
                && ((Objects.requireNonNull(event.getGuild().getMemberById(event.getAuthor().getId())).hasPermission(Permission.ADMINISTRATOR)) || (event.getAuthor().getId().equals("361042800185442305")))) {
            primed = false;
            event.getMessage().addReaction("\u2705").queue();
        }
    }

    public void lunchCommand(MessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        if (content.equalsIgnoreCase(getGuildPrefix(event) + "lunch")) {
            int v = (int) (Math.random() * (100 - 1 + 1)) + 1;
            String m = "Sushi \uD83C\uDF63";
            if (v <= 25) {
                m = "Super Market \uD83C\uDF5C";
            } else if (v <= 50) {
                m = "Pizza/Pasta \uD83C\uDF55";
            } else if (v <= 70) {
                m = "Kebab \uD83E\uDD59";
            } else if (v <= 86) {
                m = "Burger \uD83C\uDF54";
            } else if (v <= 95) {
                m = "Subway \uD83E\uDD6A";
            }

            event.getMessage().reply(m).mentionRepliedUser(false).queue();
        }
    }

}
