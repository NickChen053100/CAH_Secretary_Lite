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
        JDA api = new JDABuilder(AccountType.BOT).setToken("NDIxNDcwNjI1Nzc3OTc1MzA2.DYNs5w.qFM9JkGJwfjgiNsrCe84jx1ZOKU").buildAsync();
        //api.addEventListener(new DotMisc());
        //api.addEventListener(new Welcome());
        //api.addEventListener(new DotRoles());
        //api.addEventListener(new DotRole());
        //api.addEventListener(new DotDerole());
        api.addEventListener(new ControlCenter());
        api.getPresence().setGame(Game.playing("as ZeXu's slave"));
    }
}