package lee.code.discord;

import lee.code.core.util.files.CustomYML;
import lee.code.core.util.files.FileManager;
import lee.code.discord.files.ConfigFile;
import org.bukkit.configuration.file.YamlConfiguration;

public class Data {

    public void loadData() {
        FileManager fileManager = GoldmanDiscord.getPlugin().getFileManager();
        String fileName = "config";
        fileManager.createYML(fileName);
        CustomYML configYML = fileManager.getYML(fileName);
        YamlConfiguration fileConfig = configYML.getFile();
        for (ConfigFile value : ConfigFile.values()) {
            if (!fileConfig.contains(value.getPath())) fileConfig.set(value.getPath(), value.getString());
        }
        configYML.saveFile();
    }
}
