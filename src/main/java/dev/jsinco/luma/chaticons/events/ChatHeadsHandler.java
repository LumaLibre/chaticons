package dev.jsinco.luma.chaticons.events;

import dev.jsinco.chatheads.api.ChatHeadsInjectEvent;
import dev.jsinco.luma.chaticons.obj.ChatIcon;
import dev.jsinco.luma.chaticons.obj.ChatIconPlayer;
import dev.jsinco.luma.chaticons.obj.ChatIconPlayerManager;
import dev.jsinco.luma.lumacore.manager.modules.AutoRegister;
import dev.jsinco.luma.lumacore.manager.modules.RegisterType;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@AutoRegister(RegisterType.LISTENER)
public class ChatHeadsHandler implements Listener {

    private static final Component SPACE = Component.text(" ");


    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onChatHeadsInject(ChatHeadsInjectEvent event) {
        Player player = event.getPlayer();
        Component avatar = event.getAvatar();

        if (player == null) return;

        ChatIconPlayer chatIconPlayer = ChatIconPlayerManager.getChatIconPlayer(player.getUniqueId());
        ChatIcon chatIcon = chatIconPlayer.getIcon();

        if (chatIcon == null) return;

        Component icon = SPACE.append(chatIcon.getComponent());
        event.setAvatar(avatar.append(icon));
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ChatIconPlayerManager.load(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ChatIconPlayerManager.removeChatIconPlayer(player.getUniqueId());
    }
}
