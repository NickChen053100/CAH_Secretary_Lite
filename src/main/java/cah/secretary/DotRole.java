package cah.secretary;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;

public class DotRole extends ListenerAdapter {
    public void role(MessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw().substring(6);
        List<Role> rolesList = event.getGuild().getRoles();
        List<String> lockedRoles = Variables.getLockedRoles();
        Role role = null;
        MessageChannel channel = event.getChannel();


        String[] restrictedRoles = Variables.getRestrictedRoles();
        //checks if role is in rolesList
        List<Role> userRolesList = event.getMember().getRoles();

        //checks if user has Accepted, Committed, or Undergrad roles
        for (Role aRole : userRolesList) {
            for (String bRole : restrictedRoles) {
                if (aRole.getName().equals(bRole)) {
                    channel.sendMessage("Sorry! " + aRole.getName() + " students can't self role; please contact staff directly if you would like to change your roles!").queue();
                    return;
                }
            }
        }
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
                //channel.sendMessage(e.toString()).queue();
            }
        }
    }
}