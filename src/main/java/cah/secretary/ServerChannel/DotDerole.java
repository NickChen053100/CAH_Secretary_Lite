package cah.secretary.ServerChannel;

import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;

public class DotDerole extends ListenerAdapter {
    private static final String[] restrictedRoles = {
            "Accepted", "Committed", "Undergrad"
    };
    private static Guild guild;
    private static Role role = null;
    private MessageChannel channel;
    private String content;
    private List<Role> serverRolesList;
    private List<Role> memberRolesList;
    private Member member;

    public void derole(MessageReceivedEvent event) {
        channel = event.getChannel();
        content = event.getMessage().getContentRaw().substring(8);
        guild = event.getGuild();
        serverRolesList = event.getGuild().getRoles();
        User user = event.getAuthor();
        member = event.getMember();
        memberRolesList = event.getMember().getRoles();
        if (restricted())
            return;
        findRole();
        applyDerole();
    }

    //checks if user has Accepted, Committed, or Undergrad roles
    private boolean restricted() {
        boolean verdict = false;
        for (Role aRole : memberRolesList) {
            for (String bRole : restrictedRoles) {
                if (aRole.getName().toLowerCase().equals(bRole.toLowerCase())) {
                    channel.sendMessage("Sorry! " + aRole.getName() + " students can't self derole; please " +
                            "contact staff directly if you would like to change your roles!").queue();
                    verdict = true;
                }
            }
        }
        return verdict;
    }

    //checks if role is in serverRolesList
    private void findRole() {
        boolean match = false;
        while (!match) {
            for (Role r : serverRolesList) {
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
    private void applyDerole() {
        if (role != null) {
            try {
                if (memberRolesList.contains(role)) {
                    guild.getController().removeSingleRoleFromMember(member, role).queue();
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