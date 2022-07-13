package bot.modules;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class MathStuff {

    public MathStuff(SlashCommandInteractionEvent event) {
        switch (event.getSubcommandName()) {
            case "gcd":
                gcdCommand(event);
                break;
            case "lcm":
                lcmCommand(event);
                break;
            case "isprime":
                isPrimeCommand(event);
                break;
        }
    }

    public void gcdCommand(SlashCommandInteractionEvent event) {
        try {

            int a = event.getOption("a").getAsInt();
            int b = event.getOption("b").getAsInt();
            int help;

            while (b != 0) {
                help = b;
                b = a % b;
                a = help;
            }

            event.reply("`gcd "+ event.getOption("a").getAsString() + " " + event.getOption("b").getAsString() + ":` " + a).queue();
        } catch (ArithmeticException e) {
            event.reply("the fact that discord considers both of these two numbers integers, concerns me").queue();
        }
    }


    public void lcmCommand(SlashCommandInteractionEvent event) {
        try {
            int a = event.getOption("a").getAsInt();
            int b = event.getOption("b").getAsInt();

            int absNumber1 = Math.abs(a);
            int absNumber2 = Math.abs(b);
            int absHigherNumber = Math.max(absNumber1, absNumber2);
            int absLowerNumber = Math.min(absNumber1, absNumber2);
            int lcm = absHigherNumber;
            while (lcm % absLowerNumber != 0) {
                lcm += absHigherNumber;
            }

            event.getChannel().sendMessage("`lcm "+ event.getOption("a").getAsString() + " " + event.getOption("b").getAsString() + ":` " + lcm).queue();
        } catch (ArithmeticException e) {
            event.reply("the fact that discord considers both of these two numbers integers, concerns me").queue();
        }

    }

    public void isPrimeCommand(SlashCommandInteractionEvent event) {
        try {
            int number = event.getOption("x").getAsInt();
            int i = 2;
            while ((i < number) && (number % i != 0)) {
                i++;
            }
            event.reply("`isPrime " + number + ":` " + (i == number)).queue();
        } catch (ArithmeticException e) {
            event.reply("the fact that discord somehow considers this number an integer, concerns me").queue();
        }

    }
}
