package bot.main;

import bot.modules.*;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Main extends ListenerAdapter {

    public static boolean primed = false;
    public static boolean logging = false;

    public static boolean catModule = true;
    public static boolean standardModule = false;
    public static boolean stealthModule = true;
    public static boolean toolsModule = true;
    public static boolean toysModule = true;

    private static final Dotenv dotenv = Dotenv.load();
    public static String catFolder = dotenv.get("CAT_FOLDER");
    public static String astaCult = dotenv.get("ASTA_CULT");
    public static String funnyCats = dotenv.get("FUNNY_CATS");
    public static String moss = dotenv.get("MOSS");
    public static String[] whitelist = dotenv.get("WHITELIST").split(",");
    public static String looig = dotenv.get("LOOIG");
    public static String looigData = dotenv.get("LOOIG_DATA_PATH");
    public static String blacklist = dotenv.get("BLACKLIST");

    public Cat cat = new Cat();
    public Toys toys = new Toys();
    public Standard standard = new Standard();
    public Tools tools = new Tools();
    public Internal internal = new Internal();
    public Stealth stealth = new Stealth();

    public static void main(String[] args) throws LoginException {
        //the token found in old commits doesn't work anymore, don't bother trying
        String token = dotenv.get("TOKEN");
        JDABuilder builder = JDABuilder.createDefault(token);

        builder.setToken(token);
        builder.addEventListeners(new Main());
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.streaming("/funnycat", "https://www.twitch.tv/bobross"));
        builder.build();

    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        if (!event.getAuthor().getId().contains(looig)) {

            if(!event.getAuthor().getId().contains(blacklist)) {
                if (logging && event.isFromGuild()) {
                    System.out.println("(" + event.getGuild().getName() + ") " + event.getAuthor().getName() + " said: " + content);
                } else if (logging) {
                    System.out.println(event.getAuthor().getName() + " said: " + content);
                }
                System.setProperty("http.agent", "Mozilla/5.0 (X11; Linux armv7l) AppleWebKit/537.36 (KHTML, like Gecko) Raspbian Chromium/74.0.3729.157 Chrome/74.0.3729.157 Safari/537.36");

                if (catModule) {
                    if (content.equalsIgnoreCase("gwa gwa")) {
                        cat.gwagwaCommand(event);
                    } else if (content.equalsIgnoreCase(getGuildPrefix(event) + "deleteLast") &&
                            Arrays.stream(whitelist).anyMatch(event.getAuthor().getId()::contains)) {
                        cat.deleteLastCat(event);
                    } else {
                        cat.funnyCatCommand(event);
                    }
                }
                if (standardModule) {
                    if (content.contains(getGuildPrefix(event) + "help") || content.contains(getGuildPrefix(event) + "commands")) {
                        standard.helpCommand(event);
                    } else if (content.equalsIgnoreCase("<@!" + looig + ">")) {
                        standard.mentionCommand(event);
                    } else if (content.contains(getGuildPrefix(event) + "todo")) {
                        standard.todoCommand(event);
                    } else if (content.equalsIgnoreCase(getGuildPrefix(event) + "changelog")) {
                        standard.changelogCommand(event);
                    }
                }
                if (toolsModule) {
                    if (content.contains(getGuildPrefix(event) + "saveMessage")) {
                        tools.saveMessageCommand(event);
                    } else if (content.contains(getGuildPrefix(event) + "giveMessage")) {
                        tools.giveMessageCommand(event);
                    }
                }
                if (Arrays.stream(whitelist).anyMatch(event.getAuthor().getId()::contains)) {
                    if (content.equalsIgnoreCase(getGuildPrefix(event) + "updateSlash")) {
                        internal.updateSlashCommands(event);
                    } else if (content.equalsIgnoreCase(getGuildPrefix(event) + "updateSlashGlobal")) {
                        internal.updateSlashCommandsGlobal(event);
                    } else if (content.equalsIgnoreCase(getGuildPrefix(event) + "deleteSlash")) {
                        internal.deleteSlashCommands(event);
                    }
                }

                if (toysModule) toys.trigger(event);
            }
            if (stealthModule) stealth.trigger(event);
        } else {
            if (content.contains("<https://cta.pet/cats/")){
                cat.catchCatLink(event);
            }
            if (logging) {
                System.out.println(">>> " + content);
            }
        }
    }


    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(!event.getUser().getId().contains(blacklist)) {
            if (logging && event.isFromGuild()) {
                System.out.println("(" + Objects.requireNonNull(event.getGuild()).getName() + ") " + event.getUser().getName() + " used /" + event.getName());
            } else if (logging) {
                System.out.print(event.getUser().getName() + " used /" + event.getName());
                if (event.getSubcommandName() != null) {
                    System.out.println(" " + event.getSubcommandName());
                } else {
                    System.out.println();
                }

            }
            if (event.getName().equals("yeah")) {
                event.reply("yeah").queue();
            } else {
                switch (event.getName()) {
                    case "funnycat":
                        new Cat(event);
                        break;
                    case "internal":
                        if (Arrays.stream(whitelist).noneMatch(event.getUser().getId()::contains)) {
                            event.reply("you don't get to do that").queue();
                        } else {
                            new Internal(event);
                        }
                        break;
                    case "math":
                        new MathStuff(event);
                        break;
                    case "ping":
                        new Standard(event);
                        break;
                    case "tools":
                        new Tools(event);
                        break;
                }
            }
        }else{
            event.reply("you can't do that").setEphemeral(true).queue();
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().contains("module")) {
            internal.toggleModulesCommand(event);
        }
    }

    public void onModalInteraction( ModalInteractionEvent event) {
        User moss = event.getJDA().retrieveUserById(Main.moss).complete();
        moss.openPrivateChannel().queue((channel) -> {
            channel.sendMessage("cat submission by " + event.getUser().getName() + "#" + event.getUser().getDiscriminator() + ":\n" +
                    "`link: `" + event.getValue("link").getAsString() + "\n" +
                    "`reason: `" + event.getValue("reason").getAsString()).queue();
        });
        event.reply("cat has been submitted for review").setEphemeral(true).queue();
    }

    public static String getGuildPrefix(MessageReceivedEvent event) {
        try {
            File text = new File(looigData + event.getGuild().getId() + "_Prefix.txt");
            Scanner scnr = new Scanner(text);
            return scnr.nextLine();
        } catch (FileNotFoundException | NullPointerException | IllegalStateException e) {
            return ">";
        }
    }

    public static void dmException(JDA jda, Exception e){
        User moss = jda.retrieveUserById(Main.moss).complete();
        moss.openPrivateChannel().queue((channel) -> {
            String error = Arrays.toString(e.getStackTrace());
            channel.sendMessage(error.substring(0,Math.min(error.length(),1000))).queue();
        });
    }
}
