package lee.code.discord;

import lee.code.core.util.files.FileManager;
import lee.code.discord.commands.ReportBugCommand;
import lee.code.discord.commands.ReportPlayerCommand;
import lee.code.discord.commands.SuggestCommand;
import lee.code.discord.commands.SupportCommand;
import lee.code.discord.files.ConfigFile;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

public class GoldmanDiscord extends JavaPlugin {

    @Getter private JDA bot;
    @Getter private Guild guild;
    @Getter private FileManager fileManager;
    @Getter private PU pU;
    @Getter private Data data;

    @Override
    public void onEnable() {
        this.fileManager = new FileManager(this);
        this.pU = new PU();
        this.data = new Data();

        data.loadData();
        setupBot();
        pU.scheduleOnlinePlayerChecker();
    }

    private void setupBot() {
        try {
            this.bot = JDABuilder.createDefault(ConfigFile.BOT_TOKEN.getString(null))
                    .setActivity(Activity.playing("Journey (0/100)"))
                    .addEventListeners(new ReportPlayerCommand(), new ReportBugCommand(), new SupportCommand(), new SuggestCommand())
                    .build().awaitReady();

            this.guild = bot.getGuildById(ConfigFile.GUILD_ID.getString(null));

            if (guild != null) {
                guild.upsertCommand("report-player", "Report a player for breaking the rules.").queue();
                guild.upsertCommand("report-bug", "Report a bug found on the server.").queue();
                guild.upsertCommand("support", "Open a support request.").queue();
                guild.upsertCommand("suggestion", "Suggest a feature request or a change to our systems.").queue();
            }

        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        bot.shutdown();
    }

    public static GoldmanDiscord getPlugin() {
        return GoldmanDiscord.getPlugin(GoldmanDiscord.class);
    }
}
