package cah.secretary;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;

public class DotDerole extends ListenerAdapter {
    public void derole(MessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        String content = event.getMessage().getContentRaw().substring(8);
        List<Role> rolesList = event.getGuild().getRoles();
        Role role = null;
        boolean match = false;


        String[] restrictedRoles = Variables.getRestrictedRoles();
        //checks if role is in rolesList
        List<Role> userRolesList = event.getMember().getRoles();
        //checks if user has Accepted, Committed, or Undergrad roles
        for (Role aRole : userRolesList) {
            for (String bRole : restrictedRoles) {
                if (aRole.getName().equals(bRole)) {
                    channel.sendMessage("Sorry! " + aRole.getName() + " students can't self remove roles; please contact staff directly if you would like to change your roles!").queue();
                    return;
                }
            }
        }
        //checks if role is in rolesList
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
        if (role != null) {
            try {
                if (userRolesList.contains(role)) {
                    event.getGuild().getController().removeSingleRoleFromMember(event.getMember(), role).queue();
                    channel.sendMessage("You no longer have the " + content + " role!").queue();
                } else {
                    channel.sendMessage("You didn't have " + content + " role. Was there a mistake?").queue();
                }
            } catch (PermissionException e) {
                channel.sendMessage("You don't have permission to self-remove the " + content + " role!").queue();
            }
        }
    }
}