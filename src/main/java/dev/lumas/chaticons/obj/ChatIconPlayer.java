package dev.lumas.chaticons.obj;

import dev.lumas.chaticons.ChatIcons;
import dev.lumas.chaticons.config.OkaeriChatIcon;
import dev.lumas.lumacore.utility.Logging;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

@Getter
public class ChatIconPlayer {

    private static final NamespacedKey ACTIVE_ICON_KEY = new NamespacedKey(ChatIcons.getInstance(), "active-icon");

    private final UUID uuid;
    @Nullable
    private volatile ChatIcon icon;

    public ChatIconPlayer(UUID uuid) {
        this.uuid = uuid;
        this.loadIcon();
    }

    public void setIcon(@Nullable ChatIcon icon) {
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
        OkaeriChatIcon okaeriChatIcon = findConfigIcon(activeIconName);
        if (okaeriChatIcon != null) {
            this.icon = okaeriChatIcon.toChatIcon();
        } else {
            Logging.errorLog("Failed to load chat icon for player " + player.getName() + ". Icon with name '" + activeIconName + "' not found in config.");
        }
    }

    public void refreshIcon() {
        ChatIcon current = this.icon;
        if (current == null) {
            return;
        }
        OkaeriChatIcon okaeriChatIcon = findConfigIcon(current.getName());
        this.icon = okaeriChatIcon != null ? okaeriChatIcon.toChatIcon() : null;
    }

    @Nullable
    private static OkaeriChatIcon findConfigIcon(String name) {
        List<OkaeriChatIcon> okaeriChatIcons = ChatIcons.getChatIconsConfig().getChatIcons();
        return okaeriChatIcons.stream().filter(it -> name.equals(it.getName())).findFirst().orElse(null);
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
