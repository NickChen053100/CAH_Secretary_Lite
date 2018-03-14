package cah.secretary.PrivateChannel;

import cah.secretary.Control.Variables;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class dotQuizme extends ListenerAdapter {
    public static void sendPrivateMessage(User user, String content) {
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

    //returns the correct letter
    public void askQ(int qNum, MessageReceivedEvent event) {
        switch (qNum) {
            case 1:
                sendPrivateMessage(event.getAuthor(), "According to Rule 1, what does excessive use of @ " +
                        "mentions fall under?\n" +
                        "'A': Doxxing \n'B': Not Harassment \n'C': Harassment");
                break;
            case 2:
                sendPrivateMessage(event.getAuthor(), "What is rule 2?\n" +
                        "'A': No impersonation of other server members at any time for any purpose.\n'B': No going against the autocracy" +
                        "\n'C': Be respectful to your fellow members of the server.");
                break;
            case 3:
                sendPrivateMessage(event.getAuthor(), "Which of the following isn't directly forbidden by rule " +
                        "3?\n" +
                        "'A': Flooding messages \n'B': Spamming. \n'C': Congratulatory messages");
                break;
            case 4:
                sendPrivateMessage(event.getAuthor(), "Using process of elimination, which of the following  " +
                        "is not forbidden by rule 4 and should be used at all times?\n" +
                        "'A': Hate Speech \n'B': Racism \n'C': Common Sense");
                break;
            case 5:
                sendPrivateMessage(event.getAuthor(), "According to rule 5, what is CAH's policy on personal " +
                        "information??\n" +
                        "'A': Doxxing \n'B': User's Discretion \n'C': Harassment");
                break;
        }
        return;
    }

    public void scoreUser(User user, boolean correct, MessageReceivedEvent event) {
        String id = user.getId();
        if (Variables.quizIDs.contains(id)) {
            int index = Variables.quizIDs.indexOf(id);
            if (correct) {

                Variables.quizScores.set(index, Variables.quizScores.get(index) + 1);
                if (Variables.quizScores.get(index) == 3) {
                    sendPrivateMessage(user, "CONGRATS!! \n your score is now 3/3.\n" +
                            "You've passed and can now access the rest of the server! :D ");
                    Variables.quizIDs.remove(index);
                    Variables.quizScores.remove(index);
                    pass(user, event);
                } else {
                    sendPrivateMessage(user, "Nice, another one down! \n your score is now 2/3.\n" +
                            "Just one more! :triumph: ");
                }
            } else {
                sendPrivateMessage(user, "Oops you missed a question... \n your score is now 0/3.\n" +
                        "type .quizme to restart the quiz and receive your next question! (**hint:** read the" +
                        " rules in the rules channel");
                Variables.quizScores.set(index, 0);
            }
        } else {
            if (correct) {
                sendPrivateMessage(user, "Nice, you got it right! \n your score is now 1/3.\n" +
                        "1 down, 2 to go!");
                Variables.quizIDs.add(id);
                Variables.quizScores.add(1);
            }
        }
        return;
    }

    public void pass(User user, MessageReceivedEvent event) {
        //event.getAuthor().getMutualGuilds().getMemb[event.getAuthor().getMutualGuilds().indexOf("")];
        Variables.guild.getController().removeSingleRoleFromMember(Variables.guild.getMember(event.getAuthor()), Variables.guild.getRolesByName(Variables.getTrialRole(), true).get(0)).queue();

        //event.getGuild().getController().removeSingleRoleFromMember(event.getMember(), event.getGuild().getRolesByName(Variables.getTrialRole(), true).get(0)).queue();
    }
}
