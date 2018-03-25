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

    private static final List<String> tempRoles = asList(
            //note when adding temp roles, make it all lowercase
            "temprole"
    );

    private static final List<String> restrictedRoles = asList(
            "accepted", "committed", "undergrad"
    );
    private static final List<String> lockedRoles = asList(
            "admin",
            "dyno",
            "ub3r-b0t",
            "moderator",
            "partner",
            "admin emeritus",
            "mod emeritus",
            "muted",
            "verified",
            "accepted",
            "admissions",
            "contributor",
            "pulitzer winner",
            "bot commander",
            "bot",
            "applyingtocollegebot",
            "cyan",
            "(ﾉ◕ヮ◕)ﾉ✧･ﾟ*✧spoo.py✧*･ﾟ✧ヽ(◕ヮ◕)ﾉ",
            "tatsumaki",
            "mathBot",
            "botless",
            "it guy",
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
        content = event.getMessage().getContentRaw().substring(6).toLowerCase();
        rolesList = event.getGuild().getRoles();
        userRolesList = event.getMember().getRoles();
        guild = event.getGuild();
        member = event.getMember();
        if (restricted())
            return;
        findRole();
    }

    //checks if user has Accepted, Committed, or Undergrad roles
    private boolean restricted() {
        boolean verdict = false;
        if (tempRoles.contains(content))
            return verdict;
        for (Role aRole : userRolesList) {
            for (String bRole : restrictedRoles) {
                if (aRole.getName().toLowerCase().equals(bRole)) {
                    if (bRole.equals("undergrad"))
                        channel.sendMessage("Sorry! " + aRole.getName() + " students can't self role schools; please contact staff directly if you would like to add your school or change it!").queue();
                    else
                        channel.sendMessage("Sorry! " + aRole.getName() + " students can't self role schools; please contact staff directly if you would like to change your roles!").queue();
                    verdict = true;
                }
            }
        }
        return verdict;
    }

    //checks if role is in rolesList
    private void findRole() {
        boolean match = false;
        for (Role r : rolesList) {
            if (r.getName().toLowerCase().equals(content)) {
                role = r;
                match = true;
                break;
            }
        }
        if (match)
            applyRole();
        else {
            channel.sendMessage("Hey uh... I can't find the " + content + " role. Was there a typo...?").queue();
        }
    }

    //if there is a valid role, it is applied
    private void applyRole() {
        if (role != null && rolesList.contains(role)) {
            try {
                if (userRolesList.contains(role)) {
                    channel.sendMessage("You already have the " + content + " role!").queue();
                } else {
                    if (!lockedRoles.contains(role.getName().toLowerCase())) {

                        if (role.getName().toLowerCase().equals("undergrad")) {
                            for (Role role : userRolesList) {
                                if (rolesList.contains(role))
                                    guild.getController().removeSingleRoleFromMember(member, role).queue();

                            }
                            channel.sendMessage("If you would like to role yourself with your school, " +
                                    "please contact a staff member and verify your attendance!").queue();
                        }
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