package cah.secretary;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;


/**
 * @author ZeXu
 */
public class Bot {
    public static void main(String[] args) throws Exception {
        JDA api = new JDABuilder(AccountType.BOT).setToken("NDE1NjA2OTcyMjA2MDIyNjU5.DW5Hkg.kIof7R10-fKa-XqEfBLflyb262A").buildAsync();
        api.addEventListener(new PingPong());
        api.addEventListener(new WelcomeMessage());
        api.addEventListener(new Roles());
    }
}