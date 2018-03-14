package cah.secretary.ServerChannel;

import cah.secretary.Control.Variables;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;

public class DotRoles extends ListenerAdapter {
    private static MessageChannel channel;
    private static List<Role> rolesList;
    private static List<String> lockedRoles;

    public void roles(MessageReceivedEvent event) {
        channel = event.getChannel();
        rolesList = event.getGuild().getRoles();
        lockedRoles = Variables.getLockedRoles();
        channel.sendMessage("The following roles are available for self-assigning: ").queue();
        printRoles(0, 2);
        printRoles(rolesList.size() / 2, 1);
    }

    private void printRoles(int a, int b) {
        StringBuilder roles = new StringBuilder();
        for (int i = a; i < Variables.serverRoles.size() / b; i++) {
            if (!(lockedRoles.contains(Variables.serverRoles.get(i).getName())))
                roles.append(Variables.serverRoles.get(i).getName()).append(", ");
        }
        channel.sendMessage(roles.toString()).queue();
    }
}
