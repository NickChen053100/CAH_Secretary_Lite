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

import java.util.Random;

public class ControlCenter extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        JDA jda = event.getJDA();
        long responseNumber = event.getResponseNumber();

        User user = event.getAuthor();
        Message message = event.getMessage();
        MessageChannel channel = event.getChannel();

        String msg = message.getContentRaw().toLowerCase();

        boolean bot = user.isBot();

        if (!event.isFromType(ChannelType.PRIVATE)) {
            Guild guild = event.getGuild();
            TextChannel textChannel = event.getTextChannel();
            Member member = event.getMember();
            if (!msg.startsWith(".")) return;

            //DotDeRole
            if (msg.startsWith(".derole ")) {
                DotDerole instance = new DotDerole();
                instance.derole(event);
            }
            //DotRole
            if (msg.startsWith(".role ")) {
                DotRole instance = new DotRole();
                instance.role(event);
            }

            //DotRoles
            if (msg.equals(".roles")) {
                DotRoles instance = new DotRoles();
                instance.roles(event);
            }
            //DotMisc
            else {
                DotMisc instance = new DotMisc();
                instance.dotMisc(event);
            }

        } else {
            PrivateChannel privateChannel = event.getPrivateChannel();
            //add if user doesn't have Trial role respond with you've already passed the quiz!
            dotQuizme instance = new dotQuizme();
            if (msg.equals(".quizme")) {
                Random random = new Random();
                int qNum = random.nextInt(5 - 0 + 1) + 0;

                instance.askQ(qNum, event);
            }
            if (msg.equals("a") || msg.equals("b")) {
                instance.scoreUser(user, false, event);
            } else if (msg.equals("c")) {
                instance.scoreUser(user, true, event);
                //if m = a, b, or c, 2d list change values based on answer (answer is always C). If 3/3, delete entry.
                // //Maybe clear every 24 hours

            }
        }
    }
}
