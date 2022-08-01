package bot.modules;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Objects;

public class MathStuff {

    public MathStuff(SlashCommandInteractionEvent event) {
        switch (Objects.requireNonNull(event.getSubcommandName())) {
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

            long a = Objects.requireNonNull(event.getOption("a")).getAsInt();
            long b = Objects.requireNonNull(event.getOption("b")).getAsInt();
            long help;

            while (b != 0) {
                help = b;
                b = a % b;
                a = help;
            }

            event.reply("`gcd "+ Objects.requireNonNull(event.getOption("a")).getAsString() + " " + Objects.requireNonNull(event.getOption("b")).getAsString() + "` " + a).queue();
        } catch (ArithmeticException e) {
            event.reply("the fact that discord considers both of these two numbers integers, concerns me").queue();
        }
    }


    public void lcmCommand(SlashCommandInteractionEvent event) {
        try {
            long a = Objects.requireNonNull(event.getOption("a")).getAsInt();
            long b = Objects.requireNonNull(event.getOption("b")).getAsInt();

            long absNumber1 = Math.abs(a);
            long absNumber2 = Math.abs(b);
            long absHigherNumber = Math.max(absNumber1, absNumber2);
            long absLowerNumber = Math.min(absNumber1, absNumber2);
            long lcm = absHigherNumber;
            while (lcm % absLowerNumber != 0) {
                lcm += absHigherNumber;
            }

            event.getChannel().sendMessage("`lcm "+ Objects.requireNonNull(event.getOption("a")).getAsString() + " " + Objects.requireNonNull(event.getOption("b")).getAsString() + "` " + lcm).queue();
        } catch (ArithmeticException e) {
            event.reply("the fact that discord considers both of these two numbers integers, concerns me").queue();
        }

    }

    public void isPrimeCommand(SlashCommandInteractionEvent event) {
        try {
            long number = Objects.requireNonNull(event.getOption("x")).getAsInt();
            long i = 2;
            while ((i < number) && (number % i != 0)) {
                i++;
            }
            event.reply("`isPrime " + number + "` " + (i == number)).queue();
        } catch (ArithmeticException e) {
            event.reply("the fact that discord somehow considers this number an integer, concerns me").queue();
        }

    }
}
