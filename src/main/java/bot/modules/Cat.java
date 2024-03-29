package bot.modules;

import bot.main.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;

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
        switch (Objects.requireNonNull(event.getSubcommandName())) {
            case "get":
                funnyCatGetCommand(event);
                break;
            case "count":
                funnyCatCountCommand(event);
                break;
            case "submit":
                submitNewCat(event);
        }
    }

    public Cat() {}

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
                        input = new BufferedInputStream(event.getMessage().getAttachments().get(0).getProxy().download().get());
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
                    Arrays.stream(whitelist).anyMatch(event.getAuthor().getId()::contains)) {
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
                event.getMessage().reply("try `/funnycat` instead").mentionRepliedUser(false).queue();
            }
        } catch (NullPointerException e) {
            event.getMessage().reply("couldn't access the target directory").mentionRepliedUser(false).queue();
        }
    }

    public void funnyCatGetCommand(SlashCommandInteractionEvent event) {
        JDA jda = event.getJDA();
        try {
            File targetDir = new File(catFolder);
            int max = Objects.requireNonNull(targetDir.list()).length - 1;
            if (max == -1) {
                event.reply("no funny cats are in the target folder \uD83D\uDE3F").queue();
            } else {
                if (event.getOption("id") == null) {
                    int index = (int) (Math.random() * (max + 1));
                    if (logging) {
                        System.out.println(index + "");
                    }
                    sendCat(event, index);

                } else {
                    try {
                        if (logging) {
                            System.out.println(Objects.requireNonNull(event.getOption("id")).getAsInt());
                        }
                        sendCat(event, Objects.requireNonNull(event.getOption("id")).getAsInt());
                    } catch (IndexOutOfBoundsException e) {
                        event.reply("couldn't find the cat specified").queue();
                    } catch (NumberFormatException e) {
                        event.reply("what do you want from me").queue();
                    }
                }
            }
        } catch (NullPointerException e) {
            event.reply("Couldn't access target directory").queue();
        } catch (Exception e) {
            Main.dmException(jda, e);
        }
    }

    public void funnyCatCountCommand(SlashCommandInteractionEvent event) {
        try {
            File targetDir = new File(catFolder);
            int max = Objects.requireNonNull(targetDir.list()).length - 1;
            if (max == -1) {
                event.reply("no funny cats are in the target folder \uD83D\uDE3F").queue();
            } else {
                int count = Objects.requireNonNull(new File(catFolder).list()).length;
                long size = 0;
                File[] files = new File(catFolder).listFiles();
                for (int i = 0; i < count; i++) {
                    assert files != null;
                    size += files[i].length();
                }
                double sizeMB = size / 1000000.0;
                event.reply(count + " files taking up " + sizeMB + " megabytes of space").queue();
            }

        } catch (NullPointerException e) {
            event.reply("Couldn't access target directory").queue();
        }
    }

    public void gwagwaCommand(MessageReceivedEvent event) {
        event.getChannel().sendMessage("https://cta.pet/cats/0000.png").queue();
    }

    public void deleteLastCat(MessageReceivedEvent event) {
        Objects.requireNonNull(
                        Objects.requireNonNull(event.getJDA().getGuildById(astaCult)).getNewsChannelById(funnyCats))
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

    private void sendCat(SlashCommandInteractionEvent event, int index) {
        String file = getCatFromIndex(index);
        if (event.getOption("ephemeral") != null && Objects.requireNonNull(event.getOption("ephemeral")).getAsBoolean()) {
            event.reply("https://cta.pet/cats/" + file).addActionRow(
                    Button.link("https://cta.pet/cats/" + file, "cta.pet | " + index)
                            .withEmoji(Emoji.fromMarkdown("\uD83D\uDC31")),
                    Button.primary("anotherCat","Another")
                            .withEmoji(Emoji.fromMarkdown("\uD83D\uDD00"))).setEphemeral(true).queue();
        } else {
            event.reply("<https://cta.pet/cats/" + file + ">").addActionRow(
                    Button.link("https://cta.pet/cats/" + file, "cta.pet | " + index)
                            .withEmoji(Emoji.fromMarkdown("\uD83D\uDC31")),
                    Button.primary("anotherCatEphemeral","Another")
                            .withEmoji(Emoji.fromMarkdown("\uD83D\uDD00"))).queue();
        }
    }

    private void sendCat(ButtonInteractionEvent event, int index, boolean ephemeral) {
        String file = getCatFromIndex(index);
        if (!ephemeral) {
            event.reply("https://cta.pet/cats/" + file).addActionRow(
                    Button.link("https://cta.pet/cats/" + file, "cta.pet | " + index)
                            .withEmoji(Emoji.fromMarkdown("\uD83D\uDC31")),
                    Button.primary("anotherCat","Another")
                            .withEmoji(Emoji.fromMarkdown("\uD83D\uDD00"))).setEphemeral(true).queue();
        } else {
            event.reply("<https://cta.pet/cats/" + file + ">").addActionRow(
                    Button.link("https://cta.pet/cats/" + file, "cta.pet | " + index)
                            .withEmoji(Emoji.fromMarkdown("\uD83D\uDC31")),
                    Button.primary("anotherCatEphemeral","Another")
                            .withEmoji(Emoji.fromMarkdown("\uD83D\uDD00"))).queue();
        }
    }

    public void catchCatLink(MessageReceivedEvent event) {
        String file = event.getMessage().getContentRaw().substring(("<https://cta.pet/cats/").length(), event.getMessage().getContentRaw().length() - 1);
        event.getMessage().editMessage("\u200B").addFile(new File(catFolder + "/" + file)).queue();
    }

    public void submitNewCat(SlashCommandInteractionEvent event) {
        TextInput link = TextInput.create("link", "Media link of the cat you want to add", TextInputStyle.SHORT)
                .setPlaceholder("link")
                .setRequiredRange(1, 1000)
                .build();

        TextInput reason = TextInput.create("reason", "Why do you think I should add this cat", TextInputStyle.PARAGRAPH)
                .setPlaceholder("reason")
                .setRequiredRange(1, 1000)
                .build();

        Modal modal = Modal.create("catsubmission", "Cat Submission Form")
                .addActionRows(ActionRow.of(link), ActionRow.of(reason))
                .build();

        event.replyModal(modal).queue();
    }

    public void anotherCat(ButtonInteractionEvent event) {
        if (event.getComponentId().contains("anotherCat")) {
            JDA jda = event.getJDA();
            try {
                File targetDir = new File(catFolder);
                int max = Objects.requireNonNull(targetDir.list()).length - 1;
                if (max == -1) {
                    event.reply("no funny cats are in the target folder \uD83D\uDE3F").queue();
                } else {
                    int index = (int) (Math.random() * (max + 1));
                    if (logging) {
                        System.out.println(index + "");
                    }
                    sendCat(event, index, event.getComponentId().contains("Ephemeral"));
                }
            } catch (NullPointerException e) {
                event.reply("Couldn't access target directory").queue();
            } catch (Exception e) {
                Main.dmException(jda, e);
            }
        }
    }

}
