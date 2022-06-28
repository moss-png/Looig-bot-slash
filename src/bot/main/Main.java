package bot.main;

import bot.modules.*;
import bot.modules.unused.MM2;
import bot.modules.unused.Moderator;
import bot.modules.unused.Unused;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
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

    public static String catFolder = "/media/pi/usb/funny_cats/public/cats";

    public static String[] whitelist = {"361042800185442305"};
    public static String astaCult = "738880279082565791";
    public static String funnyCats = "770658243159654431";


    public static void main(String[] args) throws LoginException {
        String token = "NTIwMjIwMTM2NTA3MDQ3OTM2.XOvmcg.yB5djcXuJ3Ck2RfqURXOoLrgfg0";
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
        if (!event.getAuthor().getId().equals("520220136507047936")) {

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
            new Cat(event);
        }
    }

    public static String getGuildPrefix(MessageReceivedEvent event) {
        try {
            File text = new File("/home/moss/Desktop/Looig Data/" + event.getGuild().getId() + "_Prefix.txt");
            Scanner scnr = new Scanner(text);
            return scnr.nextLine();
        } catch (FileNotFoundException | NullPointerException | IllegalStateException e) {
            return ">";
        }
    }

    public void shh(MessageReceivedEvent event) {
        if (Arrays.asList(whitelist).contains(event.getAuthor().getId())) {
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
        if(Arrays.asList(whitelist).contains(event.getAuthor().getId())) {
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
