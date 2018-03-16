package cah.secretary.Control;

import cah.secretary.PrivateChannel.dotQuizme;
import cah.secretary.ServerChannel.DotDerole;
import cah.secretary.ServerChannel.DotMisc;
import cah.secretary.ServerChannel.DotRole;
import cah.secretary.ServerChannel.DotRoles;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;
import java.util.Random;

class ControlCenter extends ListenerAdapter {
    private static final String trialRole = "Trial";
    private static long startTime = 0;
    @SuppressWarnings("PointlessArithmeticExpression")
    private final Guild guild = Bot.getGuild();
    private JDA jda;
    // --Commented out by Inspection (3/15/2018 8:25 PM):private long responseNumber;

    private static void sendPrivateMessage(User user, String content) {
        user.openPrivateChannel().queue((channel) ->
                channel.sendMessage(content).queue());
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        jda = event.getJDA();
        long responseNumber = event.getResponseNumber();

        User user = event.getAuthor();
        Message message = event.getMessage();
        MessageChannel channel = event.getChannel();

        String content = message.getContentRaw().toLowerCase();

        if (!event.isFromType(ChannelType.PRIVATE)) {
            TextChannel textChannel = event.getTextChannel();
            Member member = event.getMember();

            //DotDeRole
            if (content.startsWith(".derole ")) {
                DotDerole instance = new DotDerole();
                instance.derole(event);
            }
            //DotRole
            else if (content.startsWith(".role ")) {
                DotRole instance = new DotRole();
                instance.role(event);
            }

            //DotRoles
            else if (content.equals(".roles")) {
                DotRoles instance = new DotRoles();
                instance.roles(event);
            }
            //DotMisc
            else if (content.startsWith(".")) {
                DotMisc instance = new DotMisc();
                instance.dotMisc(event);
            }
            //Misc Commands
            else if (content.equals("bot sucks")) {
                channel.sendMessage("u suck!").queue();
            }

        } else {

            if (System.currentTimeMillis() - startTime < 2 * 1000) {
                sendPrivateMessage(user, "Hey hey hey! Calm down, either you're sending messages too fast, or " +
                        "the server is under high load! Try again in a second!");
                return;
            }
            startTime = System.currentTimeMillis();
            PrivateChannel privateChannel = event.getPrivateChannel();
            //add if user doesn't have Trial role respond with you've already passed the quiz!
            if (!content.equals(".quizme") && !content.equals("a") && !content.equals("b") && !content.equals("c"))
                return;
            Member member = guild.getMember(event.getAuthor());
            List<Role> memberRolesList = member.getRoles();
            boolean match = false;
            for (Role r : memberRolesList) {
                if (r.getName().equals(trialRole)) {
                    match = true;
                    break;
                }
            }
            if (!match) {
                sendPrivateMessage(user, "Hey hey hey! You've already passed the test, you can already access " +
                        "the server!");
                return;
            }
            dotQuizme instance = new dotQuizme();
            if (!instance.correctOrder(event))
                return;
            if (content.equals(".quizme")) {
                Random random = new Random();
                int qNum = random.nextInt(5 - 0 + 1) + 0;

                instance.askQ(qNum, event);
            }
            if (content.equals("a") || content.equals("b")) {
                instance.scoreUser(user, false, event);
            } else if (content.equals("c")) {
                instance.scoreUser(user, true, event);

            }
        }
    }
}
