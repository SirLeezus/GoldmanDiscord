package lee.code.discord;

import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.Bukkit;

public class PU {

    public void scheduleOnlinePlayerChecker() {
        GoldmanDiscord plugin = GoldmanDiscord.getPlugin();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            int online = Bukkit.getOnlinePlayers().size();
            plugin.getBot().getPresence().setActivity(Activity.playing("Journey (" + online +"/100)"));
        }), 0L, 500L);
    }
}
