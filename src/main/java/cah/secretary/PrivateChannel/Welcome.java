package cah.secretary.PrivateChannel;

import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;


public class Welcome extends ListenerAdapter {
    private static final String trialRole = "Trial";
    public static TextChannel textChannel;
    private static MessageChannel channel;
    private static Member member;
    private static User user;
    private static Guild guild;

    private static void sendPrivateMessage(User user, String content) {
        user.openPrivateChannel().queue((channel) ->
                channel.sendMessage(content).queue());
    }

    //@Override
    public void onGuildMemberJoin​(GuildMemberJoinEvent event) {
        channel = event.getMember().getDefaultChannel();

        member = event.getMember();
        user = event.getUser();
        guild = event.getGuild();
        TextChannel textChannel = guild.getTextChannelsByName("college_roles", true).get(0);
        //temp, will find better solution
        guild.getController().addSingleRoleToMember(member, guild.getRolesByName(trialRole, true).get(0)).queue();
        textChannel.sendMessage("Welcome to the College Admissions Hub server, " + member.getAsMention() +
                "! This is the " + member.getDefaultChannel().getAsMention() + " channel.  \n" +
                "After reading the rules, please check your direct messages and complete a short quiz with me. " +
                "**You will not be able to access the rest of the server until you can achieve a passing grade.** " +
                "(Don't worry it's only 3 questions and you can try as many times as you need to!)\n" +
                "You will be able to role yourself once you pass the quiz!").queue();
        sendPrivateMessage(user, "Hello, " + member.getAsMention() + "!, welcome to College Admissions Hub! Before you can access the rest of the server, please take this short 2 question questionnaire and 10 question quiz and achieve 70% or higher!\n" +
                "\n" +
                "You're currently at 0/3 questions correct in a row! To receive you first question, type ```.quizme```!");
    }
}
