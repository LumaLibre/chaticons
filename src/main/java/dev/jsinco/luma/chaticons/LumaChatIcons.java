package dev.jsinco.luma.chaticons;


import dev.jsinco.luma.chaticons.config.ConfigManager;
import dev.jsinco.luma.chaticons.config.Config;
import dev.jsinco.luma.chaticons.obj.ChatIconPlayerManager;
import dev.jsinco.luma.lumacore.manager.modules.ModuleManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class LumaChatIcons extends JavaPlugin {

    @Getter private static LumaChatIcons instance;
    @Getter private static Config chatIconsConfig;
    private static ModuleManager moduleManager;

    @Override
    public void onLoad() {
        instance = this;
        moduleManager = new ModuleManager(this);
    }

    @Override
    public void onEnable() {
        moduleManager.reflectivelyRegisterModules();
        chatIconsConfig = new ConfigManager().getConfig();

        for (Player player : Bukkit.getOnlinePlayers()) {
            ChatIconPlayerManager.load(player);
        }
    }

    @Override
    public void onDisable() {
        moduleManager.unregisterModules();
    }
}