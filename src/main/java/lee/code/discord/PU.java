package lee.code.discord;

import lee.code.core.util.bukkit.ping.Pinger;
import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.Bukkit;

public class PU {

    private int globalPlayerCount;

    public void scheduleOnlinePlayerChecker() {
        GoldmanDiscord plugin = GoldmanDiscord.getPlugin();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            plugin.getBot().getPresence().setActivity(Activity.playing("Journey (" + globalPlayerCount +"/500)"));
        }), 0L, 500L);
    }

    public void schedulePingServers() {
        GoldmanDiscord plugin = GoldmanDiscord.getPlugin();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            Pinger advancementPinger = new Pinger("advancement", "184.95.51.250", 25530, 5000);
            Pinger chaosPinger = new Pinger("chaos", "184.95.51.250", 25554, 5000);
            Pinger hubPinger = new Pinger("hub", "184.95.51.250", 25547, 5000);
            //Pinger vanillaPinger = new Pinger("vanilla", "184.95.51.250", 25554, 5000);

            int count = 0;

            advancementPinger.ping();
            count = count + advancementPinger.getOnlinePlayers();

            chaosPinger.ping();
            count = count + chaosPinger.getOnlinePlayers();

            hubPinger.ping();
            count = count + hubPinger.getOnlinePlayers();

            globalPlayerCount = count;
        }), 0L, 100L);
    }

}
