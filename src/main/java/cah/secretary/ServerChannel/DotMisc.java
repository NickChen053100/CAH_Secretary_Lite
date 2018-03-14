package cah.secretary.ServerChannel;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class DotMisc extends ListenerAdapter {
    public void dotMisc(MessageReceivedEvent event) {
        Message message = event.getMessage();
        String content = message.getContentRaw();
        MessageChannel channel = event.getChannel();
        if (content.equals(".ping")) {
            channel.sendMessage("christ lake").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }
        if (content.equals("CHINA")) {
            channel.sendMessage("NUMBAH 1!").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }
        if (content.toLowerCase().equals("bot sucks")) {
            channel.sendMessage("u suck!").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }
        if (content.toLowerCase().equals("zexu")) {
            channel.sendMessage("yes?").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }
    }
}