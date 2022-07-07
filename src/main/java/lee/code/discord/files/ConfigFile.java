package lee.code.discord.files;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.discord.GoldmanDiscord;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
public enum ConfigFile {
    BOT_TOKEN("bot-token", "00000000"),
    GUILD_ID("guild-id", "00000000"),
    REPORT_PLAYER_CATEGORY_ID("report-player-category-id", "00000000"),
    REPORT_BUG_CATEGORY_ID("report-bug-category-id", "00000000"),
    SUPPORT_CATEGORY_ID("support-category-id", "00000000"),
    SUGGESTION_CATEGORY_ID("suggestion-category-id", "00000000"),
    REPORT_PLAYER_NUMBER("report-player-number", "1"),
    REPORT_BUG_NUMBER("report-bug-number", "1"),
    SUPPORT_NUMBER("support-number", "1"),
    SUGGESTION_NUMBER("suggestion-number", "1"),
    ;

    @Getter private final String path;
    @Getter private final String string;

    public String getString(String[] variables) {
        return BukkitUtils.getStringFromFileManager(GoldmanDiscord.getPlugin().getFileManager(), "config", path, variables);
    }

    public int getValue() {
        return BukkitUtils.getValueFromFileManager(GoldmanDiscord.getPlugin().getFileManager(),"config", path);
    }

    public void setValue(int value) {
        BukkitUtils.setValueInFileManager(GoldmanDiscord.getPlugin().getFileManager(),"config", path, value);
    }
}
