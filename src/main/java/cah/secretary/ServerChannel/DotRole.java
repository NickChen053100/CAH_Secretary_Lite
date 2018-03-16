package cah.secretary.ServerChannel;


import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;

import static java.util.Arrays.asList;

public class DotRole extends ListenerAdapter {
    private static final String[] restrictedRoles = {
            "Accepted", "Committed", "Undergrad"
    };
    private static final List<String> lockedRoles = asList(
            "Admin",
            "Dyno",
            "UB3R-B0T",
            "Moderator",
            "Partner",
            "Admin Emeritus",
            "Mod Emeritus",
            "Muted",
            "Verified",
            "Committed",
            "Accepted",
            "Admissions",
            "Contributor",
            "Pulitzer Winner",
            "Bot Commander",
            "Bot",
            "ApplyingToCollegeBot",
            "Cyan",
            "(ﾉ◕ヮ◕)ﾉ✧･ﾟ*✧spoo.py✧*･ﾟ✧ヽ(◕ヮ◕)ﾉ",
            "Tatsumaki",
            "MathBot",
            "Botless",
            "IT Guy",
            "@everyone"
    );
    private static MessageChannel channel;
    private static String content;
    private static List<Role> rolesList;
    private static List<Role> userRolesList;
    private static Member member;
    private static Guild guild;
    private static Role role = null;

    public void role(MessageReceivedEvent event) {
        channel = event.getChannel();
        content = event.getMessage().getContentRaw().substring(6);
        rolesList = event.getGuild().getRoles();
        userRolesList = event.getMember().getRoles();
        guild = event.getGuild();
        member = event.getMember();
        if (restricted())
            return;
        findRole();
        applyRole();
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
                if (r.getName().toLowerCase().equals(content.toLowerCase())) {
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
    private void applyRole() {
        if (role != null && rolesList.contains(role)) {
            try {
                if (userRolesList.contains(role)) {
                    channel.sendMessage("You already have the " + content + " role!").queue();
                } else {
                    if (!lockedRoles.contains(role.getName())) {
                        guild.getController().addSingleRoleToMember(member, role).queue();
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