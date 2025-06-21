package dev.jsinco.luma.chaticons.obj;

import dev.jsinco.luma.chaticons.LumaChatIcons;
import dev.jsinco.luma.chaticons.config.OkaeriChatIcon;
import dev.jsinco.luma.lumacore.utility.Logging;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;

@Getter
public class ChatIconPlayer {

    private static final NamespacedKey ACTIVE_ICON_KEY = new NamespacedKey(LumaChatIcons.getInstance(), "active-icon");

    private final UUID uuid;
    private ChatIcon icon;

    public ChatIconPlayer(UUID uuid) {
        this.uuid = uuid;
        this.loadIcon();
    }

    public void setIcon(ChatIcon icon) {
        this.icon = icon;
        this.saveIcon();
    }

    @Nullable
    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public void loadIcon() {
        Player player = getPlayer();
        if (player == null) {
            return;
        }
        loadIcon(player);
    }

    public void saveIcon() {
        Player player = getPlayer();
        if (player == null) {
            return;
        }
        saveIcon(player);
    }

    public void loadIcon(Player player) {
        String activeIconName = player.getPersistentDataContainer().get(ACTIVE_ICON_KEY, PersistentDataType.STRING);
        if (activeIconName == null || activeIconName.isEmpty()) {
            this.icon = null;
            return;
        }
        Map<String, OkaeriChatIcon> okaeriChatIconMap = LumaChatIcons.getChatIconsConfig().getChatIcons();
        OkaeriChatIcon okaeriChatIcon = okaeriChatIconMap.values().stream().filter(it -> activeIconName.equals(it.getName())).findFirst().orElse(null);
        if (okaeriChatIcon != null) {
            this.icon = okaeriChatIcon.toChatIcon();
        } else {
            Logging.errorLog("Failed to load chat icon for player " + player.getName() + ". Icon with name '" + activeIconName + "' not found in config.");
        }
    }

    public void saveIcon(Player player) {
        if (this.icon == null) {
            player.getPersistentDataContainer().remove(ACTIVE_ICON_KEY);
            return;
        }

        String activeIconName = this.icon.getName();
        player.getPersistentDataContainer().set(ACTIVE_ICON_KEY, PersistentDataType.STRING, activeIconName);
    }
}
