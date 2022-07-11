package bot.main;

import bot.modules.*;
import bot.modules.unused.MM2;
import bot.modules.unused.Moderator;
import bot.modules.unused.Unused;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import okhttp3.internal.Internal;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Main extends ListenerAdapter {

    public static boolean shh = false;
    public static boolean primed = false;
    public static boolean logging = true;

    public static boolean catModule = true;
    public static boolean managementModule = true;
    public static boolean mathModule = true;
    public static boolean mm2Module = false;
    public static boolean moderatorModule = false;
    public static boolean standardModule = true;
    public static boolean stealthModule = true;
    public static boolean toolsModule = true;
    public static boolean toysModule = true;
    public static boolean unusedModule = false;

    private static final Dotenv dotenv = Dotenv.load();
    public static String catFolder = dotenv.get("CAT_FOLDER");
    public static String astaCult = dotenv.get("ASTA_CULT");
    public static String funnyCats = dotenv.get("FUNNY_CATS");
    public static String moss = dotenv.get("MOSS");
    public static String looig = dotenv.get("LOOIG");
    public static String looigData = dotenv.get("LOOIG_DATA_PATH");



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

            if (!shh) {
                if (catModule) new Cat(event);
                if (standardModule) new Standard(event);
                if (toolsModule) new Tools(event);
                if (toysModule) new Toys(event);
                if (managementModule) new Management(event);
                if (mathModule) new MathStuff(event);
                if (mm2Module) new MM2(event);
                if (moderatorModule) new Moderator(event);
                if (unusedModule) new Unused(event);
            }
            if (stealthModule) new Stealth(event);

            toggle(event);
            shh(event);
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
//            switch (event.getName()){
//                case "funnycat":
//                    new Cat(event);
//                    break;
//                case "management":
//                    new Management(event);
//                    break;
//                case "internal":
//                    new BotInternal(event);
//                    break;
//                case "math":
//                    new MathStuff(event);
//                    break;
//                case "standard":
//                    new Standard(event);
//                    break;
//                case "tools":
//                    new Tools(event);
//                    break;
//                default:
//                    break;
//            }
            new Cat(event);
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

    public void shh(MessageReceivedEvent event) {
        if (event.getAuthor().getId().contains(moss)) {
            String content = event.getMessage().getContentRaw();
            if (content.contains(getGuildPrefix(event) + "shh")) {
                shh = !shh;
                if(shh){
                    event.getMessage().addReaction("\uD83D\uDCA4").queue();
                }else{
                    event.getMessage().addReaction("\u2757").queue();
                }
            }
        }
    }

    public void toggle(MessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        if(event.getAuthor().getId().contains(moss)) {
            if (content.equalsIgnoreCase(getGuildPrefix(event) + "toggle")) {
                event.getMessage().reply(
                        "```cat        " + catModule + "\n" +
                                "management " + managementModule + "\n" +
                                "math       " + mathModule + "\n" +
                                "mm2        " + mm2Module + "\n" +
                                "moderator  " + moderatorModule + "\n" +
                                "standard   " + standardModule + "\n" +
                                "stealth    " + stealthModule + "\n" +
                                "tools      " + toolsModule + "\n" +
                                "toys       " + toysModule + "\n" +
                                "unused     " + unusedModule + "```").mentionRepliedUser(false).queue();
            }else if(content.contains(getGuildPrefix(event) + "toggle ")){
                String message = content.substring((getGuildPrefix(event) + "toggle ").length());
                switch (message) {
                    case "cat" : catModule = !catModule; break;
                    case "management" : managementModule = !managementModule; break;
                    case "math" : mathModule = !mathModule; break;
                    case "mm2" : mm2Module = !mm2Module; break;
                    case "moderator" : moderatorModule = !moderatorModule; break;
                    case "standard" : standardModule = !standardModule; break;
                    case "stealth" : stealthModule = !stealthModule; break;
                    case "tools" : toolsModule = !toolsModule; break;
                    case "toys" : toysModule = !toysModule; break;
                    case "unused" : unusedModule = !unusedModule; break;
                }
                event.getMessage().addReaction("\uD83C\uDD97").queue();
            }
        }
    }
}
