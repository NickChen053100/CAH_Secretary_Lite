package cah.secretary;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;

public class DotRoles extends ListenerAdapter {
    public void roles(MessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        List<Role> rolesList = event.getGuild().getRoles();
        //List of roles that aren't self assignable
        List<String> lockedRoles = Variables.getLockedRoles();
        channel.sendMessage("Locked roles size: " + lockedRoles.size()).queue(); // test

        String roles1 = "";
        for (int i = 0; i < rolesList.size() / 2; i++) {
            if (!(lockedRoles.contains(rolesList.get(i).getName())))
                roles1 += rolesList.get(i).getName() + ", ";
        }
        channel.sendMessage("The following roles are available for self-assigning: ").queue();
        channel.sendMessage("'" + roles1.substring(0, roles1.length() - 2) + "`").queue();

        String roles2 = "";
        for (int i = 0; i < rolesList.size() / 2; i++) {
            if (!(lockedRoles.contains(rolesList.get(i + rolesList.size() / 2).getName())))
                roles2 += rolesList.get(i + rolesList.size() / 2).getName() + ", ";
        }
        channel.sendMessage("`" + roles2.substring(0, roles1.length() - 2) + "`").queue();

        channel.sendMessage("Locked roles size: " + lockedRoles.size()).queue(); // test
        channel.sendMessage("roles list size: " + rolesList.size()).queue(); // test

    }
}
