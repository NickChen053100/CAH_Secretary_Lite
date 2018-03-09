package cah.secretary;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;

public class DotRole extends ListenerAdapter {
    private static MessageChannel channel;
    private static String content;
    private static List<Role> rolesList;
    private static List<String> lockedRoles;
    private static String[] restrictedRoles;
    private static List<Role> userRolesList;
    private static Role role = null;

    public void role(MessageReceivedEvent event) {
        channel = event.getChannel();
        content = event.getMessage().getContentRaw().substring(6);
        rolesList = event.getGuild().getRoles();
        lockedRoles = Variables.getLockedRoles();
        role = null;
        restrictedRoles = Variables.getRestrictedRoles();
        userRolesList = event.getMember().getRoles();

        if (restricted())
            return;
        findRole();
        applyRole(event);
    }

    //checks if user has Accepted, Committed, or Undergrad roles
    private boolean restricted() {
        boolean verdict = false;
        for (Role aRole : userRolesList) {
            for (String bRole : restrictedRoles) {
                if (aRole.getName().equals(bRole)) {
                    channel.sendMessage("Sorry! " + aRole.getName() + " students can't self role; please contact staff directly if you would like to change your roles!").queue();
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

    //if there is a valid role, it is applied
    private void applyRole(MessageReceivedEvent event) {
        if (role != null) {
            try {
                if (userRolesList.contains(role)) {
                    channel.sendMessage("You already have the " + content + " role!").queue();
                } else {
                    if (!lockedRoles.contains(role.getName())) {
                        event.getGuild().getController().addSingleRoleToMember(event.getMember(), role).queue();
                        channel.sendMessage("You now have the " + content + " role!").queue();

                    } else
                        throw new PermissionException("");
                }
            } catch (PermissionException e) {
                channel.sendMessage("You don't have permission to self-assign the " + content + " role!").queue();
            }
        }
    }
}