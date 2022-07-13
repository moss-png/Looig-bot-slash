package bot.modules;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import static bot.main.Main.*;


public class Cat {

    public Cat(SlashCommandInteractionEvent event) {
        switch (event.getSubcommandName()) {
            case "get":
                funnyCatGetCommand(event);
                break;
            case "count":
                funnyCatCountCommand(event);
                break;
        }
    }

    public Cat(){}

    public void funnyCatCommand(MessageReceivedEvent event) {
        try {
            String content = event.getMessage().getContentRaw();
            if (event.getChannel().getId().equalsIgnoreCase(funnyCats)) {
                String url = event.getMessage().getContentRaw();
                File targetDir = new File(catFolder);
                int size = Objects.requireNonNull(targetDir.list()).length;
                BufferedInputStream input;
                if (!event.getMessage().getAttachments().isEmpty()) {
                    try {
                        input = new BufferedInputStream(event.getMessage().getAttachments().get(0).retrieveInputStream().get());
                        Files.copy(input, Paths.get(catFolder + "/" + size + "." + event.getMessage().getAttachments().get(0).getFileExtension()), StandardCopyOption.REPLACE_EXISTING);
                    } catch (InterruptedException | ExecutionException | IOException e) {
                        System.err.println("I have been interrupted");
                    }
                } else {
                    try {
                        input = new BufferedInputStream(new URL(url).openStream());
                        Files.copy(input, Paths.get(catFolder + "/" + size + url.substring(url.lastIndexOf("."))), StandardCopyOption.REPLACE_EXISTING);
                    } catch (NullPointerException | IOException e) {
                        System.err.println("invalid path");
                    }
                }
                event.getMessage().addReaction("\uD83D\uDC31").queue();
            } else if (content.contains(getGuildPrefix(event) + "funnycat save") &&
                    event.getAuthor().getId().contains(moss)) {
                if (content.equalsIgnoreCase(getGuildPrefix(event) + "funnyCat save")) {
                    event.getMessage().reply("no url specified").mentionRepliedUser(false).queue();
                } else {
                    try {
                        String url = content.substring((getGuildPrefix(event) + "funnyCat save ").length());
                        BufferedInputStream input = new BufferedInputStream(new URL(url).openStream());
                        File targetDir = new File(catFolder);
                        int size = Objects.requireNonNull(targetDir.list()).length;
                        Files.copy(input, Paths.get(catFolder + "/" + size + url.substring(url.lastIndexOf("."))), StandardCopyOption.REPLACE_EXISTING);
                        event.getMessage().reply("*saved*").mentionRepliedUser(false).queue();
                    } catch (IOException | NullPointerException e) {
                        event.getMessage().reply("error invalid path").mentionRepliedUser(false).queue();
                    }
                }
            } else if (content.contains(getGuildPrefix(event) + "funnycat save")) {
                event.getMessage().reply("sorry you can't use that").mentionRepliedUser(false).queue();
            } else if (content.contains(getGuildPrefix(event) + "funnycat")) {
                event.getMessage().reply("try slash commands").mentionRepliedUser(false).queue();
            }
        } catch (NullPointerException e) {
            event.getMessage().reply("couldn't access the target directory").mentionRepliedUser(false).queue();
        }
    }

    public void funnyCatGetCommand(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        try {
            File targetDir = new File(catFolder);
            int max = Objects.requireNonNull(targetDir.list()).length - 1;
            if (max == -1) {
                event.getHook().sendMessage("no funny cats are in the target folder \uD83D\uDE3F").queue();
            } else {
                if (event.getOption("id") == null) {
                    int index = (int) (Math.random() * (max + 1));
                    if (logging) {
                        System.out.println(index + "");
                    }
                    String file = getCatFromIndex(index);
                    event.getHook().sendMessage("<https://cta.pet/cats/" + file + ">")
                            .addFile(new File(catFolder + "/" + file)).queue();
                } else {
                    try {
                        String file = getCatFromIndex(event.getOption("id").getAsInt());
                        if (logging) {
                            System.out.println(event.getOption("id").getAsInt());
                        }
                        event.getHook().sendMessage("<https://cta.pet/cats/" + file + ">")
                                .addFile(new File(catFolder + "/" + file)).queue();
                    } catch (IndexOutOfBoundsException e) {
                        event.getHook().sendMessage("couldn't find the cat specified").queue();
                    } catch (NumberFormatException e) {
                        event.getHook().sendMessage("what do you want from me").queue();
                    }
                }
            }
        } catch (NullPointerException e) {
            event.getHook().sendMessage("Couldn't access target directory").queue();
        }
    }

    public void funnyCatCountCommand(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        try {
            File targetDir = new File(catFolder);
            int max = Objects.requireNonNull(targetDir.list()).length - 1;
            if (max == -1) {
                event.getHook().sendMessage("no funny cats are in the target folder \uD83D\uDE3F").queue();
            } else {
                int count = Objects.requireNonNull(new File(catFolder).list()).length;
                long size = 0;
                File[] files = new File(catFolder).listFiles();
                for (int i = 0; i < count; i++) {
                    assert files != null;
                    size += files[i].length();
                }
                double sizeMB = size / 1000000.0;
                event.getHook().sendMessage(count + " files taking up " + sizeMB + " megabytes of space").queue();
            }

        } catch (NullPointerException e) {
            event.getHook().sendMessage("Couldn't access target directory").queue();
        }
    }

    public void gwagwaCommand(MessageReceivedEvent event) {
        event.getChannel().sendMessage("https://cta.pet/cats/0000.png").queue();
    }

    public void deleteLastCat(MessageReceivedEvent event) {
        Objects.requireNonNull(
                        Objects.requireNonNull(event.getJDA().getGuildById(astaCult)).getTextChannelById(funnyCats))
                .getHistory().retrievePast(1)
                .map(messages -> messages.get(0))
                .queue(message -> {
                    message.delete().queue();

                    File directory = new File(catFolder);
                    File[] files = directory.listFiles(File::isFile);
                    long lastModifiedTime = Long.MIN_VALUE;
                    File chosenFile = null;

                    if (files != null) {
                        for (File file : files) {
                            if (file.lastModified() > lastModifiedTime) {
                                chosenFile = file;
                                lastModifiedTime = file.lastModified();
                            }
                        }
                    }

                    assert chosenFile != null;
                    boolean worked = chosenFile.delete();
                    if (worked) {
                        event.getMessage().addReaction("\u2705").queue();
                    }
                });
    }


    private String getCatFromIndex(int index) throws IndexOutOfBoundsException {
        File catDirectory = new File(catFolder);
        String[] fileNames;
        fileNames = catDirectory.list();
        assert fileNames != null;
        Arrays.sort(fileNames);
        return fileNames[index];
    }

}
