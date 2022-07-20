package bot.modules;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.Objects;

import static bot.main.Main.*;

public class Internal {

    private final CommandData[] commands =
            {
                    Commands.slash("yeah", "yeah"),
                    Commands.slash("funnycat", "get a funny cat")
                            .addSubcommands(
                            new SubcommandData("get", "posts funny cat image/video")
                                    .addOption(OptionType.INTEGER, "id", "numerical id of the cat, random if left blank")
                                    .addOption(OptionType.BOOLEAN, "aslink", "get the file as a link instead of a direct upload"),
                            new SubcommandData("count", "get the total count and filesize of all cats")),
                    Commands.slash("internal", "internal bot settings")
                            .addSubcommands(
                            new SubcommandData("logging", "off at launch, used for debugging only")
                                    .addOption(OptionType.BOOLEAN, "toggle", "yay/nay?"),
                            new SubcommandData("modules", "toggle modules")),
                    Commands.slash("math", "left-overs from high school :nauseated_face:")
                            .addSubcommands(
                            new SubcommandData("gcd", "greatest common divisor")
                                    .addOption(OptionType.INTEGER, "a", "first number", true)
                                    .addOption(OptionType.INTEGER, "b", "second number", true),
                            new SubcommandData("lcm", "least common multiplier")
                                    .addOption(OptionType.INTEGER, "a", "first number", true)
                                    .addOption(OptionType.INTEGER, "b", "second number", true),
                            new SubcommandData("isprime", "is the provided number prime or not")
                                    .addOption(OptionType.INTEGER, "x", "the number in question", true)),
                    Commands.slash("ping", "get the ping"),
                    Commands.slash("tools", "every day stuff")
                            .addSubcommands(
                            new SubcommandData("dice", "roll a die")
                                    .addOption(OptionType.INTEGER, "d", "if left blank a d6 will be used"),
                            new SubcommandData("coinflip", "flip a coin (riveting!)"),
                            new SubcommandData("asklooig", "magic 8ball type beat")
                                    .addOption(OptionType.STRING, "q", "the question in question", true))
            };


    public Internal(SlashCommandInteractionEvent event) {
        switch (Objects.requireNonNull(event.getSubcommandName())) {
            case "logging":
                loggingCommand(event);
                break;
            case "modules":
                toggleModulesCommand(event);
                break;
        }
    }

    public Internal() {
    }

    public void updateSlashCommands(MessageReceivedEvent event) {
        event.getGuild().updateCommands().addCommands(commands).queue();
    }

    public void deleteSlashCommands(MessageReceivedEvent event) {
        event.getGuild().updateCommands().queue();
    }

    public void updateSlashCommandsGlobal(MessageReceivedEvent event) {
        event.getJDA().updateCommands().addCommands(commands).queue();
    }

    public void loggingCommand(SlashCommandInteractionEvent event) {
        try {
            logging = Objects.requireNonNull(event.getOption("toggle")).getAsBoolean();
            System.out.println("logging is now set to " + logging);
        } catch (NullPointerException e) {
            if (logging) System.out.println("no option provided assuming status");
        } finally {
            event.reply("message logging `" + logging + "`").queue();
        }

    }

    public void toggleModulesCommand(SlashCommandInteractionEvent event) {
        event.reply(
                        "```cat        " + catModule + "\n" +
                                "standard   " + standardModule + "\n" +
                                "stealth    " + stealthModule + "\n" +
                                "tools      " + toolsModule + "\n" +
                                "toys       " + toysModule + "\n" + "```")
                .addActionRow(
                        Button.secondary("modulecat", "cat"),
                        Button.secondary("modulestandard", "standard"),
                        Button.secondary("modulestealth", "stealth"),
                        Button.secondary("moduletools", "tools"),
                        Button.secondary("moduletoys", "toys")).setEphemeral(true).queue();

    }

    public void toggleModulesCommand(ButtonInteractionEvent event) {
        String compID = event.getComponentId().substring(6);
        switch (compID) {
            case "cat":
                catModule = !catModule;
                break;
            case "standard":
                standardModule = !standardModule;
                break;
            case "stealth":
                stealthModule = !stealthModule;
                break;
            case "tools":
                toolsModule = !toolsModule;
                break;
            case "toys":
                toysModule = !toysModule;
                break;
        }

        event.editMessage(
                "```cat        " + catModule + "\n" +
                        "standard   " + standardModule + "\n" +
                        "stealth    " + stealthModule + "\n" +
                        "tools      " + toolsModule + "\n" +
                        "toys       " + toysModule + "\n" + "```").queue();


    }
}

