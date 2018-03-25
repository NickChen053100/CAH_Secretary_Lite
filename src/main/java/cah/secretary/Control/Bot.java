package cah.secretary.Control;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;

/**
 * @author ZeXu
 */
public class Bot {
    private static Guild guild;

    public static void main(String[] args) throws Exception {
        JDA api = new JDABuilder(AccountType.BOT).setToken(System.getenv("Token")).buildBlocking();
        guild = api.getGuildById("421816521447309324");
        api.addEventListener(new ControlCenter());
        api.getPresence().setGame(Game.playing("as ZeXu's slave"));
    }

    public static Guild getGuild() {
        return guild;
    }
}


