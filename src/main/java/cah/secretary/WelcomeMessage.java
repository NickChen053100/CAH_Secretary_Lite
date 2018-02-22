package cah.secretary;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class WelcomeMessage extends ListenerAdapter {
    //@Override
    public void onGuildMemberJoin​(GuildMemberJoinEvent event) {
        MessageChannel channel = event.getMember().getDefaultChannel();
        channel.sendMessage("Welcome to the College Admissions Hub server, " + event.getMember().getAsMention() + "! This is the " + event.getMember().getDefaultChannel().getAsMention() + " channel. Please give yourself a main role according to the roles in #rules. Check the pins in this channel for more information on what colleges you can be roled with as well. To tag yourself with a role, use the command \".role\" followed by the proper role. To remove a role from your list, you can use the command \".derole\" followed by the role you want to remove.\n" +
                "\n" +
                "After reading the rules, please check your direct messages and answer the Google Forms quiz sent to you by me. **You will not be able to access the rest of the server until you can achieve a passing grade.** (Don't worry it's only 10 questions!)").queue();
        sendPrivateMessage(event.getUser(), "Hello, " + event.getMember().getAsMention() + "!, welcome to College Admissions Hub! Before you can access the rest of the server, please take this short 2 question questionnaire and 10 question quiz and achieve 70% or higher!\n" +
                "\n" +
                "Link: https://goo.gl/forms/rACLBzGS3LENeuNg1");
    }

    public void sendPrivateMessage(User user, String content) {
        // openPrivateChannel provides a RestAction<PrivateChannel>
        // which means it supplies you with the resulting channel
        user.openPrivateChannel().queue((channel) ->
        {
            // value is a parameter for the `accept(T channel)` method of our callback.
            // here we implement the body of that method, which will be called later by JDA automatically.
            channel.sendMessage(content).queue();
            // here we access the enclosing scope variable -content-
            // which was provided to sendPrivateMessage(User, String) as a parameter
        });
    }
}
