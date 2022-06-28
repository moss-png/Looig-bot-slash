package bot.modules.unused;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import static bot.main.Main.getGuildPrefix;

public class MM2 {
    public MM2(MessageReceivedEvent event){
        creatorCommand(event);
    }

    public void creatorCommand(MessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        if ((content.contains(getGuildPrefix(event) + "addMaker")) || (content.contains(getGuildPrefix(event) + "addmaker"))) {
            if (content.equalsIgnoreCase(getGuildPrefix(event) + "addMaker")) {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("How to add yourself as a maker to the database:");
                builder.appendDescription("- Use **" + getGuildPrefix(event) + "addMaker <CREATOR-ID>** \n");
                builder.appendDescription("- Your creator Id should look something like this: **XXX-XXX-XXX** \n ");
                builder.appendDescription("- Dashes (-) are required \n");
                builder.appendDescription("- Don't put spaces in the creator Id \n");
                builder.appendDescription("- Use uppercase letters for style points :J");
                builder.setFooter("<CREATOR-ID> needs to be replaced by your creator Id :U", null);
                builder.setColor(Color.PINK);
                event.getMessage().replyEmbeds(builder.build()).mentionRepliedUser(false).queue();
            } else {

                try {
                    String dist2FirstDash = content.substring((getGuildPrefix(event) + "addMaker XXX").length());
                    String dist2SecondDash = content.substring((getGuildPrefix(event) + "addMaker XXX-XXX").length());
                    String has2BeEmpty = content.substring((getGuildPrefix(event) + "addMaker XXX-XXX-XXX").length());

                    if ((dist2FirstDash.contains("-")) && (dist2SecondDash.contains("-")) && (has2BeEmpty.equals(""))) {
                        try {
                            PrintWriter writer = new PrintWriter("/home/moss/Desktop/Looig Data/Maker Id " + event.getMessage().getAuthor().getId() + ".txt");
                            writer.println(event.getMessage().getContentRaw().substring((getGuildPrefix(event) + "addMaker ").length()));
                            writer.close();

                            EmbedBuilder builder = new EmbedBuilder();
                            builder.setTitle("You have been added as a maker with the following Id \n"
                                    + event.getMessage().getContentRaw().substring((getGuildPrefix(event) + "addMaker ").length()));
                            builder.setColor(Color.GREEN);
                            event.getMessage().replyEmbeds(builder.build()).mentionRepliedUser(false).queue();

                        } catch (FileNotFoundException e) {
                            System.err.println("I messed up Papa. Something with the creatorCommand(maker Id) went unexpectedly wrong");
                        }
                    } else if ((!dist2FirstDash.contains("-")) || (!dist2SecondDash.contains("-"))) {
                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle("Your creator Id doesn't have dashes where they're supposed to be");
                        builder.setColor(Color.RED);
                        event.getMessage().replyEmbeds(builder.build()).mentionRepliedUser(false).queue();

                    } else if ((!has2BeEmpty.equals(""))) {
                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle("Your Creator Id is too long");
                        builder.setColor(Color.RED);
                        event.getMessage().replyEmbeds(builder.build()).mentionRepliedUser(false).queue();
                    }
                } catch (IndexOutOfBoundsException e) {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("Your creator Id was too short");
                    builder.setColor(Color.RED);
                    event.getMessage().replyEmbeds(builder.build()).mentionRepliedUser(false).queue();
                }

            }

        }

        if ((content.contains(getGuildPrefix(event) + "addLevel")) || (content.contains(getGuildPrefix(event) + "addlevel"))) {
            if (content.equalsIgnoreCase(getGuildPrefix(event) + "addLevel")) {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("How to add a level to your maker profile");
                builder.appendDescription("- You can add up to 3 levels to your profile.\n");
                builder.appendDescription("- Use **" + getGuildPrefix(event) + "addLevel1 <LC>**, **" + getGuildPrefix(event) + "addLevel2 <LC>** and **" + getGuildPrefix(event) + "addLevel3 <LC>** respectively \n");
                builder.appendDescription("- Your level code should look something like this: **XXX-XXX-XXX** \n");
                builder.appendDescription("- Dashes (-) are required \n");
                builder.appendDescription("- Don't put spaces in the level code \n");
                builder.appendDescription("- Use uppercase letters for style points :J");
                builder.setFooter("<LC> needs to be replaced by your level code :U", null);
                builder.setColor(Color.PINK);
                event.getMessage().replyEmbeds(builder.build()).mentionRepliedUser(false).queue();
            } else {
                try {
                    String dist2FirstDash = content.substring((getGuildPrefix(event) + "addLevelX XXX").length());
                    String dist2SecondDash = content.substring((getGuildPrefix(event) + "addLevelX XXX-XXX").length());
                    String has2BeEmpty = content.substring((getGuildPrefix(event) + "addLevelX XXX-XXX-XXX").length());

                    boolean b = (dist2FirstDash.contains("-")) && (dist2SecondDash.contains("-")) && (has2BeEmpty.equals(""));
                    if ((content.contains(getGuildPrefix(event) + "addLevel1")) || (content.contains(getGuildPrefix(event) + "addlevel1"))) {
                        if (b) {
                            try {
                                PrintWriter writer = new PrintWriter("/home/moss/Desktop/Looig Data/level code 1 " + event.getMessage().getAuthor().getId() + ".txt");
                                writer.println(event.getMessage().getContentRaw().substring((getGuildPrefix(event) + "addLevelX ").length()));
                                writer.close();

                                EmbedBuilder builder = new EmbedBuilder();
                                builder.setTitle("Your code has been added as level 1 with the following Id: \n"
                                        + event.getMessage().getContentRaw().substring((getGuildPrefix(event) + "addLevelX ").length()));
                                builder.setColor(Color.GREEN);
                                event.getMessage().replyEmbeds(builder.build()).mentionRepliedUser(false).queue();

                            } catch (FileNotFoundException e) {
                                System.err.println("I messed up Papa. Something with the creatorCommand(level1 code) went unexpectedly wrong");
                            }
                        } else {
                            mm2TextFilter(event, dist2FirstDash, dist2SecondDash, has2BeEmpty);
                        }
                    } else if ((content.contains(getGuildPrefix(event) + "addLevel2")) || (content.contains(getGuildPrefix(event) + "addlevel2"))) {
                        if (b) {
                            try {
                                PrintWriter writer = new PrintWriter("/home/moss/Desktop/Looig Data/level code 2 " + event.getMessage().getAuthor().getId() + ".txt");
                                writer.println(event.getMessage().getContentRaw().substring((getGuildPrefix(event) + "addLevelX ").length()));
                                writer.close();

                                EmbedBuilder builder = new EmbedBuilder();
                                builder.setTitle("Your code has been added as level 2 with the following Id: \n"
                                        + event.getMessage().getContentRaw().substring((getGuildPrefix(event) + "addLevelX ").length()));
                                builder.setColor(Color.GREEN);
                                event.getMessage().replyEmbeds(builder.build()).mentionRepliedUser(false).queue();

                            } catch (FileNotFoundException e) {
                                System.err.println("I messed up Papa. Something with the creatorCommand(level2 code) went unexpectedly wrong");
                            }
                        } else mm2TextFilter(event, dist2FirstDash, dist2SecondDash, has2BeEmpty);
                    } else if ((content.contains(getGuildPrefix(event) + "addLevel3")) || (content.contains(getGuildPrefix(event) + "addlevel3"))) {
                        if (b) {
                            try {
                                PrintWriter writer = new PrintWriter("/home/moss/Desktop/Looig Data/Level code 3" + event.getMessage().getAuthor().getId() + ".txt");
                                writer.println(event.getMessage().getContentRaw().substring((getGuildPrefix(event) + "addLevelX ").length()));
                                writer.close();

                                EmbedBuilder builder = new EmbedBuilder();
                                builder.setTitle("Your code has been added as level 3 with the following Id: \n"
                                        + event.getMessage().getContentRaw().substring((getGuildPrefix(event) + "addLevelX ").length()));
                                builder.setColor(Color.GREEN);
                                event.getMessage().replyEmbeds(builder.build()).mentionRepliedUser(false).queue();

                            } catch (FileNotFoundException e) {
                                System.err.println("I messed up Papa. Something with the creatorCommand(level3 code) went unexpectedly wrong");
                            }
                        } else mm2TextFilter(event, dist2FirstDash, dist2SecondDash, has2BeEmpty);
                    }
                } catch (IndexOutOfBoundsException e) {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("Your level code was too short");
                    builder.setColor(Color.RED);
                    event.getMessage().replyEmbeds(builder.build()).mentionRepliedUser(false).queue();
                }
            }
        }

        if ((content.equalsIgnoreCase(getGuildPrefix(event) + "makerprofile")) || (content.equalsIgnoreCase(getGuildPrefix(event) + "creatorprofile"))) {

            EmbedBuilder builder = new EmbedBuilder();
            try {
                builder.appendDescription("Creator Id: " + (new Scanner(new File("/home/moss/Desktop/Looig Data/Maker Id " + event.getAuthor().getId() + ".txt")).nextLine()));
            } catch (FileNotFoundException e) {
                builder.appendDescription("Creator Id: ///////////");
            }
            try {
                builder.appendDescription("\n\nLevel 1: " + (new Scanner(new File("/home/moss/Desktop/Looig Data/level code 1 " + event.getAuthor().getId() + ".txt")).nextLine()));
            } catch (FileNotFoundException e) {
                builder.appendDescription("\n\nLevel 1: ///////////");
            }
            try {
                builder.appendDescription("\nLevel 2: " + (new Scanner(new File("/home/moss/Desktop/Looig Data/level code 2 " + event.getAuthor().getId() + ".txt")).nextLine()));
            } catch (FileNotFoundException e) {
                builder.appendDescription("\nLevel 2: ///////////");
            }
            try {
                builder.appendDescription("\nLevel 3: " + (new Scanner(new File("/home/moss/Desktop/Looig Data/level code 3 " + event.getAuthor().getId() + ".txt")).nextLine()));
            } catch (FileNotFoundException e) {
                builder.appendDescription("\nLevel 3: ///////////");
            }
            builder.setAuthor(event.getMessage().getAuthor().getName(), null, event.getMessage().getAuthor().getAvatarUrl());
            builder.setFooter("see " + getGuildPrefix(event) + "addMaker and " + getGuildPrefix(event) + "addLevel for help", null);
            builder.setColor(Color.GRAY);
            builder.setThumbnail("https://i.imgur.com/VOQr7Fx.png");
            event.getMessage().replyEmbeds(builder.build()).mentionRepliedUser(false).queue();
        }
    }

    private void mm2TextFilter(MessageReceivedEvent event, String dist2FirstDash, String dist2SecondDash, String has2BeEmpty) {
        if ((!dist2FirstDash.contains("-")) || (!dist2SecondDash.contains("-"))) {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Your level code doesn't have dashes where they're supposed to be");
            builder.setColor(Color.RED);
            event.getMessage().replyEmbeds(builder.build()).mentionRepliedUser(false).queue();

        } else if ((!has2BeEmpty.equals(""))) {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Your level code is too long");
            builder.setColor(Color.RED);
            event.getMessage().replyEmbeds(builder.build()).mentionRepliedUser(false).queue();
        }
    }

}
