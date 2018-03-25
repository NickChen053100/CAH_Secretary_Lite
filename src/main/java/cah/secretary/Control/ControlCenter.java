package cah.secretary.Control;

import cah.secretary.ServerChannel.DotDerole;
import cah.secretary.ServerChannel.DotRole;
import cah.secretary.ServerChannel.DotRoles;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

class ControlCenter extends ListenerAdapter {
    private final Guild guild = Bot.getGuild();
    private JDA jda;

    private static void sendPrivateMessage(User user, String content) {
        user.openPrivateChannel().queue((channel) ->
                channel.sendMessage(content).queue());
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if (!event.getMessage().getContentRaw().startsWith(".")) return;

        jda = event.getJDA();

        String content = event.getMessage().getContentRaw().toLowerCase();


        if (!event.isFromType(ChannelType.PRIVATE)) {
            //DotDeRole
            if (content.startsWith(".derole ")) {
                DotDerole instance = new DotDerole();
                instance.derole(event);
            }
            //DotRole
            else if (content.startsWith(".role ")) {
                DotRole instance = new DotRole();
                instance.role(event);
            }
            //DotRoles
            else if (content.equals(".roles")) {
                DotRoles instance = new DotRoles();
                instance.roles(event);
            }

        }
    }
}
