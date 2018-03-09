package cah.secretary;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ControlCenter extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        String content = event.getMessage().getContentRaw().toLowerCase();
        if (!content.startsWith(".")) return;
        //DotDeRole
        if (content.startsWith(".derole ")) {
            DotDerole instance = new DotDerole();
            instance.derole(event);
        }
        //DotRole
        if (content.startsWith(".role ")) {
            DotRole instance = new DotRole();
            instance.role(event);
        }
        //DotRoles
        if (content.startsWith(".roles")) {
            DotRoles instance = new DotRoles();
            instance.roles(event);
        }
        //DotMisc
        else {
            DotMisc instance = new DotMisc();
            instance.dotMisc(event);
        }

    }
}
