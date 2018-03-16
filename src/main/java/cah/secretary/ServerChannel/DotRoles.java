package cah.secretary.ServerChannel;


import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;

import static java.util.Arrays.asList;

public class DotRoles extends ListenerAdapter {
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
    private static List<Role> rolesList;

    public void roles(MessageReceivedEvent event) {
        channel = event.getChannel();
        rolesList = event.getGuild().getRoles();
        channel.sendMessage("The following roles are available for self-assigning: ").queue();
        printRoles(0, 2);
        printRoles(rolesList.size() / 2, 1);
    }

    private void printRoles(int a, int b) {
        StringBuilder roles = new StringBuilder();
        for (int i = a; i < rolesList.size() / b; i++) {
            if (!(lockedRoles.contains(rolesList.get(i).getName())))
                roles.append(rolesList.get(i).getName()).append(", ");
        }
        channel.sendMessage(roles.toString().substring(0, roles.length() - 2)).queue();
    }
}
