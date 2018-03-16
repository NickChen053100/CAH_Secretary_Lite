package cah.secretary.PrivateChannel;


import cah.secretary.Control.Bot;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class dotQuizme extends ListenerAdapter {
    private static final List<String> quizIDs = new ArrayList();
    private static final List<int[]> quizStats = new ArrayList(); //items = arrays of ints [currScore, qCount, aCount]
    private static final String trialRole = "Trial";
    private static final int[] newEntry = {0, 1, 1};
    private final Guild guild = Bot.getGuild();
    private String id;
    private int index;
    // --Commented out by Inspection (3/15/2018 8:25 PM):private MessageChannel channel;

    private static void sendPrivateMessage(User user, String content) {
        user.openPrivateChannel().queue((channel) ->
                channel.sendMessage(content).queue());
    }

    public boolean correctOrder(MessageReceivedEvent event) {
        User user = event.getAuthor();
        id = user.getId();
        String content = event.getMessage().getContentRaw().toLowerCase();
        if (content.equals(".quizme")) { //asking for a question
            if (!quizIDs.contains(id)) //first question asked
                return true;
            else {
                index = quizIDs.indexOf(id);
                if (quizStats.get(index)[1] > quizStats.get(index)[2]) { //more questions asked than answered
                    sendPrivateMessage(user, "I've already  asked you a question! Scroll up! :eyes: ");
                    return false;
                } else { //(quizStats.get(index)[2] == quizStats.get(index)[3]) same amount questions asked and answered
                    return true;
                }
            }
        } else  //answering
            if (!quizIDs.contains(id)) { //new user, hasn't been asked a question yet
                sendPrivateMessage(user, "Yo I haven't even asked you a question what you tryna answer??");
                return false;
            } else {
                index = quizIDs.indexOf(id);
                if (quizStats.get(index)[1] == quizStats.get(index)[2]) { //same number questions asked and answered
                    sendPrivateMessage(user, "Yo you've already answered my last question :eyes: type ```" +
                            ".quizme``` for your next one!");
                    return false;
                } else {//(quizStats.get(index)[2] > quizStats.get(index)[3]) more questions asked than answered
                    return true;
                }
            }

    }

    //returns the correct letter
    public void askQ(int qNum, MessageReceivedEvent event) {
        User user = event.getAuthor();
        id = user.getId();
        if (!quizIDs.contains(id)) {
            quizIDs.add(id);
            quizStats.add(newEntry);
        }
        index = quizIDs.indexOf(id);

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
        quizStats.get(index)[1] += 1;
    }

    public void scoreUser(User user, boolean correct, MessageReceivedEvent event) {
        id = user.getId();
        //items = arrays of ints [currScore, qCount, aCount]
        index = quizIDs.indexOf(id);
        if (correct) {

            quizStats.get(index)[0] += 1;
            quizStats.get(index)[2] += 1;
            switch (quizStats.get(index)[0]) {
                case 3:
                    sendPrivateMessage(user, "CONGRATS!! \n your score is now 3/3.\n" +
                            "You've passed and can now access the rest of the server! :D ");
                    quizIDs.remove(index);
                    quizStats.remove(index);
                    pass(user, event);
                    break;
                case 2:
                    sendPrivateMessage(user, "Nice, another one down! \n your score is now 2/3.\n" +
                            "Just one more! :triumph: ");

                    break;
                default:
                    sendPrivateMessage(user, "Nice, you got it right! \n your score is now 1/3.\n" +
                            "1 down, 2 to go!");
                    break;
            }

        } else {
            sendPrivateMessage(user, "Oops you missed a question... \n your score is now 0/3.\n" +
                    "type .quizme to restart the quiz and receive your next question! (**hint:** read the" +
                    " rules in the rules channel)");
            quizStats.get(index)[0] = 0;
        }
    }

    private void pass(User user, MessageReceivedEvent event) {
        //event.getAuthor().getMutualGuilds().getMemb[event.getAuthor().getMutualGuilds().indexOf("")];
        Member member = guild.getMember(event.getAuthor());
        guild.getController().removeSingleRoleFromMember(member, guild.getRolesByName(trialRole, true).get(0)).queue();
        TextChannel textChannel = guild.getTextChannelsByName("college_roles", true).get(0);
        textChannel.sendMessage("Congratulations on passing the quiz" + member.getAsMention() + ". Welcome to the " +
                "server! This is the " + member.getDefaultChannel().getAsMention() + " channel. Please give yourself a " +
                "main role according to the roles" +
                " in #rules. \n\n :warning: **A note for Undergrads** :warning: \n\nPlease role your college before you " +
                "role yourself Undergrad, since Undergrads can't change their own role without the aid of staff\n\n" +
                "To tag yourself with a role, use the command `.role` followed by the proper role. To remove a " +
                "role from your list, you can use the command `.derole` followed by the role you want to remove.\n\n" +
                "To find a complete list of our college roles, type `.roles`").queue();
        //event.getGuild().getController().removeSingleRoleFromMember(event.getMember(), event.getGuild().getRolesByName(Variables.getTrialRole(), true).get(0)).queue();
    }
}
