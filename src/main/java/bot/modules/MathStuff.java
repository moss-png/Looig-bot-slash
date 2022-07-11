package bot.modules;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import static bot.main.Main.getGuildPrefix;

public class MathStuff {
    public MathStuff(MessageReceivedEvent event){
        gcdCommand(event);
        lcmCommand(event);
        isPrimeCommand(event);
    }

    public void gcdCommand(MessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        if (content.contains(getGuildPrefix(event) + "gcd")) {
            try {
                String message = content.substring((getGuildPrefix(event) + "gcd ").length());
                String[] split = message.split("\\s+");

                int a = Integer.parseInt(split[0]);
                int b = Integer.parseInt(split[1]);
                int help;

                while (b != 0) {
                    help = b;
                    b = a % b;
                    a = help;
                }

                event.getMessage().reply(a + "").mentionRepliedUser(false).queue();
            } catch (IndexOutOfBoundsException e) {
                event.getChannel().sendMessage("It seems like you didn't give me two numbers. Might just be me screwing it up, who knows").queue();
            } catch (NumberFormatException e) {
                event.getChannel().sendMessage("It seems like at least one of these numbers is too big for me to handle, sorry about that").queue();
            }
        }
    }

    public void lcmCommand(MessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        if (content.contains(getGuildPrefix(event) + "lcm")) {
            try {
                String message = content.substring((getGuildPrefix(event) + "lcm ").length());
                String[] split = message.split("\\s+");

                int a = Integer.parseInt(split[0]);
                int b = Integer.parseInt(split[1]);

                int absNumber1 = Math.abs(a);
                int absNumber2 = Math.abs(b);
                int absHigherNumber = Math.max(absNumber1, absNumber2);
                int absLowerNumber = Math.min(absNumber1, absNumber2);
                int lcm = absHigherNumber;
                while (lcm % absLowerNumber != 0) {
                    lcm += absHigherNumber;
                }

                event.getChannel().sendMessage(lcm + "").queue();
            } catch (IndexOutOfBoundsException e) {
                event.getMessage().reply("It seems like you didn't give me two numbers. Might just be me screwing it up, who knows").mentionRepliedUser(false).queue();
            } catch (NumberFormatException e) {
                event.getMessage().reply("It seems like at least one of these numbers is too big for me to handle, sorry about that").mentionRepliedUser(false).queue();
            }
        }
    }

    public void isPrimeCommand(MessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        if (content.contains(getGuildPrefix(event) + "isPrime")) {
            try {
                int number = Integer.parseInt(content.substring((getGuildPrefix(event) + "isPrime ").length()));
                int i = 2;
                while ((i < number) && (number % i != 0)) {
                    i++;
                }
                event.getMessage().reply((i == number) + "").mentionRepliedUser(false).queue();
            } catch (IndexOutOfBoundsException e) {
                event.getMessage().reply("It seems like you didn't give me a number. Might just be me screwing it up , who knows").mentionRepliedUser(false).queue();
            } catch (NumberFormatException e) {
                event.getMessage().reply("It seems like this number is too big for me to handle, sorry about that").mentionRepliedUser(false).queue();
            }
        }
    }
}
