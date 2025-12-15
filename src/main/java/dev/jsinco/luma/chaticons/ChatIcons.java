package dev.jsinco.luma.chaticons;


import dev.jsinco.luma.chaticons.config.ConfigManager;
import dev.jsinco.luma.chaticons.config.Config;
import dev.jsinco.luma.chaticons.integration.IconComponentProvider;
import dev.jsinco.luma.chaticons.obj.ChatIconPlayerManager;
import dev.jsinco.luma.lumacore.manager.modules.ModuleManager;
import lombok.Getter;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChatIcons extends JavaPlugin {

    @Getter private static ChatIcons instance;
    @Getter private static Config chatIconsConfig;
    @Getter private static LuckPerms luckPerms;
    @Getter private static IconComponentProvider iconComponentProvider;
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
        setProviderInstance();

        if (Bukkit.getPluginManager().isPluginEnabled("LuckPerms")) {
            luckPerms = Bukkit.getServicesManager().load(LuckPerms.class);
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            ChatIconPlayerManager.load(player);
        }
    }

    @Override
    public void onDisable() {
        moduleManager.unregisterModules();
    }

    public static void setProviderInstance() {
        iconComponentProvider = chatIconsConfig.getIconComponentProvider().getProvider();
    }
}