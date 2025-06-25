package dev.jsinco.luma.chaticons.events;

import dev.jsinco.chatheads.api.ChatHeadsInjectEvent;
import dev.jsinco.luma.chaticons.LumaChatIcons;
import dev.jsinco.luma.chaticons.config.Config;
import dev.jsinco.luma.chaticons.config.DisabledChatHeadsCharacter;
import dev.jsinco.luma.chaticons.obj.ChatIcon;
import dev.jsinco.luma.chaticons.obj.ChatIconPlayer;
import dev.jsinco.luma.chaticons.obj.ChatIconPlayerManager;
import dev.jsinco.luma.lumacore.manager.modules.AutoRegister;
import dev.jsinco.luma.lumacore.manager.modules.RegisterType;
import dev.jsinco.luma.lumacore.utility.Text;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.Nullable;

@AutoRegister(RegisterType.LISTENER)
public class ChatHeadsHandler implements Listener {

    private static final Component SPACE = Component.text(" ");


    @EventHandler(priority = EventPriority.NORMAL)
    public void onChatHeadsInject(ChatHeadsInjectEvent event) {
        Player player = event.getPlayer();
        if (player == null) return;

        Component avatar = event.getAvatar();
        ChatIconPlayer chatIconPlayer = ChatIconPlayerManager.getChatIconPlayer(player.getUniqueId());
        ChatIcon chatIcon = chatIconPlayer.getIcon();


        if (chatIcon == null) {
            Component noIconPrefix = this.getNoIconPrefix(player);

            if (noIconPrefix != null) {
                if (event.isCancelled()) {
                    event.setAvatar(noIconPrefix);
                    event.setCancelled(false);
                } else {
                    event.setAvatar(avatar.append(noIconPrefix));
                }
            }
            return;
        }

        Component icon = SPACE.append(chatIcon.getComponent());
        event.setAvatar(avatar.append(icon));
    }

    @Nullable
    private Component getNoIconPrefix(Player player) {
        String newPrefix = null;
        Config cfg = LumaChatIcons.getChatIconsConfig();
        for (DisabledChatHeadsCharacter disabledChatHeadsCharacter : cfg.getDisabledChatHeadsCharacters()) {
            if (player.hasPermission(disabledChatHeadsCharacter.getPermission())) {
                newPrefix = disabledChatHeadsCharacter.getString();
                break;
            }
        }
        if (newPrefix != null) {
            return Text.mm(newPrefix);
        }
        return null;
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
