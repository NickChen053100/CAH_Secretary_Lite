package cah.secretary.PrivateChannel;


import cah.secretary.Control.Bot;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class dotQuizme extends ListenerAdapter {
    public static List<String> quizIDs = new ArrayList();
    public static List<Integer> quizScores = new ArrayList();
    public static String trialRole = "Trial";
    Guild guild = Bot.getGuild();

    private static void sendPrivateMessage(User user, String content) {
        user.openPrivateChannel().queue((channel) ->
        {
            channel.sendMessage(content).queue();
        });
    }

    //returns the correct letter
    public void askQ(int qNum, MessageReceivedEvent event) {
        guild = event.getGuild();
        User user = event.getAuthor();
        switch (qNum) {
            case 1:
                sendPrivateMessage(user, "According to Rule 1, what does excessive use of @ " +
                        "mentions fall under?\n" +
                        "'A': Doxxing \n'B': Not Harassment \n'C': Harassment");
                break;
            case 2:
                sendPrivateMessage(user, "What is rule 2?\n" +
                        "'A': No impersonation of other server members at any time for any purpose.\n'B': No going against the autocracy" +
                        "\n'C': Be respectful to your fellow members of the server.");
                break;
            case 3:
                sendPrivateMessage(user, "Which of the following isn't directly forbidden by rule " +
                        "3?\n" +
                        "'A': Flooding messages \n'B': Spamming. \n'C': Congratulatory messages");
                break;
            case 4:
                sendPrivateMessage(user, "Using process of elimination, which of the following  " +
                        "is not forbidden by rule 4 and should be used at all times?\n" +
                        "'A': Hate Speech \n'B': Racism \n'C': Common Sense");
                break;
            case 5:
                sendPrivateMessage(user, "According to rule 5, what is CAH's policy on personal " +
                        "information??\n" +
                        "'A': Doxxing \n'B': User's Discretion \n'C': Harassment");
                break;
        }
        return;
    }

    public void scoreUser(User user, boolean correct, MessageReceivedEvent event) {
        String id = user.getId();
        if (quizIDs.contains(id)) {
            int index = quizIDs.indexOf(id);
            if (correct == true) {

                quizScores.set(index, quizScores.get(index) + 1);
                if (quizScores.get(index) == 3) {
                    sendPrivateMessage(user, "CONGRATS!! \n your score is now 3/3.\n" +
                            "You've passed and can now access the rest of the server! :D ");
                    quizIDs.remove(index);
                    quizScores.remove(index);
                    pass(user, event);
                } else {
                    sendPrivateMessage(user, "Nice, another one down! \n your score is now 2/3.\n" +
                            "Just one more! :triumph: ");
                }
            } else if (correct == false) {
                sendPrivateMessage(user, "Oops you missed a question... \n your score is now 0/3.\n" +
                        "type .quizme to restart the quiz and receive your next question! (**hint:** read the" +
                        " rules in the rules channel)");
                quizScores.set(index, 0);
            }
        } else {
            if (correct) {
                sendPrivateMessage(user, "Nice, you got it right! \n your score is now 1/3.\n" +
                        "1 down, 2 to go!");
                quizIDs.add(id);
                quizScores.add(1);
            }
        }
    }

    private void pass(User user, MessageReceivedEvent event) {
        //event.getAuthor().getMutualGuilds().getMemb[event.getAuthor().getMutualGuilds().indexOf("")];
        Member member = guild.getMember(event.getAuthor());
        guild.getController().removeSingleRoleFromMember(member, guild.getRolesByName(trialRole, true).get(0)).queue();
        /**MessageChannel channel = Variables.guild.getDefaultChannel();
         channel.sendMessage("Welcome to the server, " + event.getMember().getAsMention() +"!" +
         "Ya so this it need to add actual welcoming message l8tr");**/
        //event.getGuild().getController().removeSingleRoleFromMember(event.getMember(), event.getGuild().getRolesByName(Variables.getTrialRole(), true).get(0)).queue();
    }
}
