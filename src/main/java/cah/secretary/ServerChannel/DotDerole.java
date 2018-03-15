package cah.secretary.ServerChannel;

import cah.secretary.Control.Variables;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;

public class DotDerole extends ListenerAdapter {
    private static MessageChannel channel;
    private static String content;
    private static List<Role> rolesList;
    private static String[] restrictedRoles;
    private static List<Role> userRolesList;
    private static Role role = null;

    public void derole(MessageReceivedEvent event) {
        channel = event.getChannel();
        content = event.getMessage().getContentRaw().substring(8);
        rolesList = event.getGuild().getRoles();

        userRolesList = event.getMember().getRoles();
        restrictedRoles = Variables.getRestrictedRoles();
        if (restricted())
            return;
        findRole();
        applyDerole(event);
    }

    //checks if user has Accepted, Committed, or Undergrad roles
    private boolean restricted() {
        boolean verdict = false;
        for (Role aRole : userRolesList) {
            for (String bRole : restrictedRoles) {
                if (aRole.getName().equals(bRole)) {
                    channel.sendMessage("Sorry! " + aRole.getName() + " students can't self derole; please contact staff directly if you would like to change your roles!").queue();
                    verdict = true;
                }
            }
        }
        return verdict;
    }

    //checks if role is in rolesList
    private void findRole() {
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
    }

    //if there is a valid role, it is removed
    private void applyDerole(MessageReceivedEvent event) {
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