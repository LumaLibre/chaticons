package dev.jsinco.luma.chaticons.config;

import dev.jsinco.luma.chaticons.LumaChatIcons;
import eu.okaeri.configs.serdes.standard.StandardSerdes;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import lombok.Getter;

import java.nio.file.Path;

@Getter
public class ConfigManager {

    private final Config config;

    public ConfigManager() {
        Path configPath = LumaChatIcons.getInstance().getDataPath().resolve("config.yml");

        this.config = eu.okaeri.configs.ConfigManager.create(Config.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new StandardSerdes());
            it.withRemoveOrphans(false);
            it.withBindFile(configPath);
            it.saveDefaults();
            it.load(true);
        });
    }
}