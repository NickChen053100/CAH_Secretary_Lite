package cah.secretary;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;

public class DotRole extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        String content = event.getMessage().getContentRaw();
        List<Role> rolesList = event.getGuild().getRoles();
        Role role = null;
        MessageChannel channel = event.getChannel();
        if (content.substring(0, 6).equals(".role ")) {
            content = content.substring(6);
            //checks if role is in rolesList
            boolean match = false;
            while (!match) {
                for (Role r : rolesList) {
                    if (r.getName().equals(content)) {
                        role = r;
                        match = true;
                        break;
                    }
                }
                if (match)
                    break;
                else {
                    channel.sendMessage("Hey uh... I can't find the " + content + " role. Was there a typo...?").queue();
                    match = true;
                }
            }
            //checks if user already has the role
            List<Role> userRolesList = event.getMember().getRoles();
            if (role != null) {
                try {
                    if (userRolesList.contains(role)) {
                        channel.sendMessage("You already have the " + content + " role!").queue();
                    } else {
                        event.getGuild().getController().addSingleRoleToMember(event.getMember(), role).queue();
                        channel.sendMessage("You now have the " + content + " role!").queue();
                    }
                } catch (PermissionException e) {
                    channel.sendMessage("You don't have permission to self-assign the " + content + " role!").queue();
                }
            }
        }
    }
}