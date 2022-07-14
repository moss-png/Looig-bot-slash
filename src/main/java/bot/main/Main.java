package bot.main;

import bot.modules.*;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main extends ListenerAdapter {

    public static boolean primed = false;
    public static boolean logging = false;

    public static boolean catModule = true;
    public static boolean managementModule = false;
    public static boolean standardModule = false;
    public static boolean stealthModule = true;
    public static boolean toolsModule = true;
    public static boolean toysModule = true;

    private static final Dotenv dotenv = Dotenv.load();
    public static String catFolder = dotenv.get("CAT_FOLDER");
    public static String astaCult = dotenv.get("ASTA_CULT");
    public static String funnyCats = dotenv.get("FUNNY_CATS");
    public static String moss = dotenv.get("MOSS");
    public static String looig = dotenv.get("LOOIG");
    public static String looigData = dotenv.get("LOOIG_DATA_PATH");

    public Cat cat = new Cat();
    public Toys toys = new Toys();
    public Standard standard = new Standard();
    public Tools tools = new Tools();
    public Management management = new Management();
    public Stealth stealth = new Stealth();

    public static void main(String[] args) throws LoginException {
        String token = dotenv.get("TOKEN");
        System.out.println(token);
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

            if (logging) {
                System.out.println(event.getAuthor().getName() + " said: " + content);
            }
            System.setProperty("http.agent", "Mozilla/5.0 (X11; Linux armv7l) AppleWebKit/537.36 (KHTML, like Gecko) Raspbian Chromium/74.0.3729.157 Chrome/74.0.3729.157 Safari/537.36");

            if (catModule) {
                if (content.equalsIgnoreCase("gwa gwa")) {
                    cat.gwagwaCommand(event);
                } else if (content.equalsIgnoreCase(getGuildPrefix(event) + "deleteLast") &&
                        (event.getAuthor().getId().contains(moss))) {
                    cat.deleteLastCat(event);
                } else {
                    cat.funnyCatCommand(event);
                }
            }
            if (standardModule) {
                if(content.contains(getGuildPrefix(event) + "help") || content.contains(getGuildPrefix(event) + "commands")){
                    standard.helpCommand(event);
                } else if (content.equalsIgnoreCase("<@!" + looig + ">")) {
                    standard.mentionCommand(event);
                } else if (content.contains(getGuildPrefix(event) + "todo")){
                    standard.todoCommand(event);
                } else if (content.equalsIgnoreCase(getGuildPrefix(event) + "changelog")){
                    standard.changelogCommand(event);
                }
            }
            if (toolsModule) {
                if(content.contains(getGuildPrefix(event) + "saveMessage")){
                    tools.saveMessageCommand(event);
                } else if (content.contains(getGuildPrefix(event) + "giveMessage")) {
                    tools.giveMessageCommand(event);
                }
            }
            if (managementModule && event.getAuthor().getId().contains(moss)) {
                if(content.equalsIgnoreCase(getGuildPrefix(event) + "updateSlash")) {
                    management.updateSlashCommands(event);
                }else if (content.equalsIgnoreCase(getGuildPrefix(event) + "updateSlashGlobal")) {
                    management.updateSlashCommandsGlobal(event);
                }else if (content.equalsIgnoreCase(getGuildPrefix(event) + "deleteSlash")) {
                    management.deleteSlashCommands(event);
                }
            }

            if (toysModule) toys.trigger(event);
            if (stealthModule) stealth.trigger(event);

            toggle(event);
        } else {
            if (logging) {
                System.out.println(">>> " + content);
            }
        }
    }


    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("yeah")) {
            event.reply("yeah").queue();
        }else{
            try{
                switch (event.getName()){
                    case "funnycat":
                        new Cat(event);
                        break;
                    case "internal":
                        new Management(event);
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
            } catch (Exception e){
                User moss = event.getJDA().retrieveUserById(Main.moss).complete();
                moss.openPrivateChannel().queue((channel) -> {
                    String error = Arrays.toString(e.getStackTrace());
                    channel.sendMessage(error.substring(0,Math.min(error.length(),1000))).queue();
                });
            }
        }
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

    public void toggle(MessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        if(event.getAuthor().getId().contains(moss)) {
            if (content.equalsIgnoreCase(getGuildPrefix(event) + "toggle")) {
                event.getMessage().reply(
                        "```cat        " + catModule + "\n" +
                                "management " + managementModule + "\n" +
                                "standard   " + standardModule + "\n" +
                                "stealth    " + stealthModule + "\n" +
                                "tools      " + toolsModule + "\n" +
                                "toys       " + toysModule + "\n" + "```").mentionRepliedUser(false).queue();
            }else if(content.contains(getGuildPrefix(event) + "toggle ")){
                String message = content.substring((getGuildPrefix(event) + "toggle ").length());
                switch (message) {
                    case "cat" : catModule = !catModule; break;
                    case "management" : managementModule = !managementModule; break;
                    case "standard" : standardModule = !standardModule; break;
                    case "stealth" : stealthModule = !stealthModule; break;
                    case "tools" : toolsModule = !toolsModule; break;
                    case "toys" : toysModule = !toysModule; break;
                }
                event.getMessage().addReaction("\uD83C\uDD97").queue();
            }
        }
    }
}
