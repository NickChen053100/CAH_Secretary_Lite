package cah.secretary.Control;

import cah.secretary.PrivateChannel.Welcome;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ReadyEvent;


/**
 * @author ZeXu
 */
public class Bot {
    public static Guild guild;
    public static void main(String[] args) throws Exception {
        JDA api = new JDABuilder(AccountType.BOT).setToken(Variables.getToken()).buildBlocking();
        guild = api.getGuildById("421816521447309324");
        api.addEventListener(new ControlCenter());
        api.addEventListener(new Welcome());
        api.getPresence().setGame(Game.playing("as ZeXu's slave"));
    }

    public static Guild getGuild() {
        return guild;
    }

    public void onReady(ReadyEvent event) {
        Variables.guild = event.getJDA().getGuildById(Variables.id);
    }
}


