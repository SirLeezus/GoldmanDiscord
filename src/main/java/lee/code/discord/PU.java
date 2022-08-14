package lee.code.discord;

import lee.code.core.util.bukkit.BukkitUtils;
import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.Bukkit;

public class PU {

    public void scheduleOnlinePlayerChecker() {
        GoldmanDiscord plugin = GoldmanDiscord.getPlugin();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            plugin.getBot().getPresence().setActivity(Activity.playing("Journey (" + BukkitUtils.getGlobalPlayerCount() +"/500)"));
        }), 0L, 500L);
    }
}
