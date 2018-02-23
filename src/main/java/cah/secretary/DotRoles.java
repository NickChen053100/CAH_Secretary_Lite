package cah.secretary;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class DotRoles extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        Message message = event.getMessage();
        String content = message.getContentRaw();
        if (content.equals(".roles")) {
            MessageChannel channel = event.getChannel();
            List<Role> rolesList = event.getGuild().getRoles();
            //List of roles that aren't self assignable
            List<String> invalidRolesList = new ArrayList<>();
            invalidRolesList.add("Admin");
            invalidRolesList.add("Dyno");
            invalidRolesList.add("UB3R-B0T");
            invalidRolesList.add("Moderator");
            invalidRolesList.add("Partner");
            invalidRolesList.add("Admin Emeritus");
            invalidRolesList.add("Mod Emeritus");
            invalidRolesList.add("Muted");
            invalidRolesList.add("Verified");
            invalidRolesList.add("Committed");
            invalidRolesList.add("Accepted");
            invalidRolesList.add("Admissions");
            invalidRolesList.add("Contributor");
            invalidRolesList.add("Pulitzer Winner");
            invalidRolesList.add("Bot Commander");
            invalidRolesList.add("Bot");
            invalidRolesList.add("ApplyingToCollegeBot");
            invalidRolesList.add("Cyan");
            invalidRolesList.add("(ﾉ◕ヮ◕)ﾉ✧･ﾟ*✧spoo.py✧*･ﾟ✧ヽ(◕ヮ◕)ﾉ");
            invalidRolesList.add("Tatsumaki");
            invalidRolesList.add("MathBot");
            invalidRolesList.add("Botless");
            invalidRolesList.add("IT Guy");
            invalidRolesList.add("@everyone");
            String roles = "| ";
            for (int i = 0; i < rolesList.size(); i++) {
                if (!(invalidRolesList.contains(rolesList.get(i).getName())))
                    roles += rolesList.get(i).getName() + " | ";
            }
            channel.sendMessage("The following roles are available for self-assigning: ```" + roles + "```").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }
    }
}
