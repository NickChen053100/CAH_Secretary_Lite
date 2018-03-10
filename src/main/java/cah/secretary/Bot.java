package cah.secretary;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;


/**
 * @author ZeXu
 */
public class Bot {
    public static void main(String[] args) throws Exception {
        JDA api = new JDABuilder(AccountType.BOT).setToken(Variables.getToken()).buildAsync();
        api.addEventListener(new ControlCenter());
        api.getPresence().setGame(Game.playing("as ZeXu's slave"));


    }
}