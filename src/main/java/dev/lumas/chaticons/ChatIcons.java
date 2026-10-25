package dev.lumas.chaticons;

import dev.lumas.chaticons.config.ConfigManager;
import dev.lumas.chaticons.config.Config;
import dev.lumas.chaticons.gui.ChatIconsGui;
import dev.lumas.chaticons.integration.ChatIconsExpansion;
import dev.lumas.chaticons.integration.IconComponentProvider;
import dev.lumas.chaticons.obj.ChatIconPlayerManager;
import dev.lumas.lumacore.manager.modules.ModuleManager;
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
    private static ChatIconsExpansion expansion;

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
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            expansion = new ChatIconsExpansion();
            expansion.register();
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            ChatIconPlayerManager.load(player);
        }
    }

    @Override
    public void onDisable() {
        moduleManager.unregisterModules();

        // not managed by LumaCore, would otherwise keep this classloader alive across a plugman reload
        if (expansion != null) {
            expansion.unregister();
            expansion = null;
        }

        // open GUIs reference this plugin instance, close them so nothing clicks into a disabled plugin
        closeOpenGuis();
    }

    public static void reload() {
        chatIconsConfig.load();
        setProviderInstance();
        ChatIconPlayerManager.refreshIcons();
        closeOpenGuis();
    }

    private static void closeOpenGuis() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getScheduler().execute(instance, () -> {
                if (player.getOpenInventory().getTopInventory().getHolder(false) instanceof ChatIconsGui) {
                    player.closeInventory();
                }
            }, null, 1);
        }
    }

    public static void setProviderInstance() {
        iconComponentProvider = chatIconsConfig.getIconComponentProvider().getProvider();
    }
}
