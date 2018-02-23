package cah.secretary;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;

public class DotRoles extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        Message message = event.getMessage();
        String content = message.getContentRaw();
        if (content.equals(".roles")) {
            MessageChannel channel = event.getChannel();
            List<Role> rolesList = event.getGuild().getRoles();
            //channel.sendMessage(String.valueOf(rolesList.size())).queue(); //test line
            String roles = rolesList.get(0).getName();
            for (int i = 1; i < rolesList.size(); i++) {
                roles += ", " + rolesList.get(i).getName();
            }
            channel.sendMessage("The following roles are available for self-assigning: ```" + roles + "```").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }
    }
}
