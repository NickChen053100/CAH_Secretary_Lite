package cah.secretary.Control;

import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class OnReady extends ListenerAdapter {
    public void onReady(ReadyEvent event) {
        Variables.guild = event.getJDA().getGuildById(Variables.id);
    }
}
