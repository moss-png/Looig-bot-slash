package bot.modules.unused;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import net.dv8tion.jda.api.managers.GuildManager;

import java.awt.*;
import java.util.Objects;

import static bot.main.Main.getGuildPrefix;

public class Moderator {
    public Moderator(MessageReceivedEvent event){
        roleCommands(event);
    }
    public void roleCommands(MessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        if (content.contains(getGuildPrefix(event) + "role")) {
            if (content.equals(getGuildPrefix(event) + "role")) {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("You can choose your role by typing `" + getGuildPrefix(event) + "role <YOUR ROLE HERE>`");
                builder.setImage("https://i.imgur.com/f6nUBsH.png");
                builder.setColor(Color.BLACK);
                builder.setDescription("You can remove any role by typing `" + getGuildPrefix(event) + "removeRole <YOUR ROLE HERE>`");
                event.getMessage().replyEmbeds(builder.build()).mentionRepliedUser(false).queue();
            } else {
                if ((content.contains(getGuildPrefix(event) + "role"))) {
                    String help = content.substring((getGuildPrefix(event) + "role ").length());
                    GuildManager manager = event.getGuild().getManager();

                    try {
                        manager.getGuild().addRoleToMember(Objects.requireNonNull(event.getMember()), event.getGuild().getRolesByName(help, true).get(0)).queue(null, ErrorResponseException -> {
                            EmbedBuilder builder = new EmbedBuilder();
                            builder.setTitle("I do not have permission to remove this role \u274C");
                            builder.setColor(Color.RED);
                            event.getMessage().replyEmbeds(builder.build()).mentionRepliedUser(false).queue();
                        });

                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle("Success \u2705");
                        builder.setColor(Color.GREEN);
                        event.getMessage().replyEmbeds(builder.build()).mentionRepliedUser(false).queue();
                    } catch (IndexOutOfBoundsException e) {
                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle("Looks like this role doesn't exist \u274C");
                        builder.setColor(Color.RED);
                        event.getMessage().replyEmbeds(builder.build()).mentionRepliedUser(false).queue();
                    } catch (HierarchyException e) {
                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle("I do not have permission to give you this role \u274C");
                        builder.setColor(Color.RED);
                        event.getMessage().replyEmbeds(builder.build()).mentionRepliedUser(false).queue();
                    }
                }
            }
        }

        if ((content.contains(getGuildPrefix(event) + "removeRole"))) {
            String help = content.substring((getGuildPrefix(event) + "removeRole ").length());
            GuildManager manager = event.getGuild().getManager();
            try {
                if (Objects.requireNonNull(event.getMember()).getRoles().contains(event.getGuild().getRolesByName(help, true).get(0))) {

                    manager.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRolesByName(help, true).get(0)).queue(null, ErrorResponseException -> {
                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle("I do not have permission to remove this role \u274C");
                        builder.setColor(Color.RED);
                        event.getMessage().replyEmbeds(builder.build()).mentionRepliedUser(false).queue();
                    });

                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("Success \u2705");
                    builder.setColor(Color.GREEN);
                    event.getMessage().replyEmbeds(builder.build()).mentionRepliedUser(false).queue();

                } else {

                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("Looks like you don't have that role \u274C");
                    builder.setColor(Color.RED);
                    event.getMessage().replyEmbeds(builder.build()).mentionRepliedUser(false).queue();
                }
            } catch (IndexOutOfBoundsException e) {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("Looks like this role doesn't exist \u274C");
                builder.setColor(Color.RED);
                event.getMessage().replyEmbeds(builder.build()).mentionRepliedUser(false).queue();
            } catch (HierarchyException e) {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("I do not have permission to remove this role \u274C");
                builder.setColor(Color.RED);
                event.getMessage().replyEmbeds(builder.build()).mentionRepliedUser(false).queue();
            }
        }
    }
}
