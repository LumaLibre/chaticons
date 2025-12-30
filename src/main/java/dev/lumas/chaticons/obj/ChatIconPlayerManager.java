package dev.lumas.chaticons.obj;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChatIconPlayerManager {

    private static final Map<UUID, ChatIconPlayer> CHAT_ICON_PLAYER_MAP = new HashMap<>();

    public static void load(@NotNull Player player) {
        ChatIconPlayer chatIconPlayer = new ChatIconPlayer(player.getUniqueId());
        CHAT_ICON_PLAYER_MAP.put(player.getUniqueId(), chatIconPlayer);
    }

    @NotNull
    public static ChatIconPlayer getChatIconPlayer(UUID uuid) {
        return CHAT_ICON_PLAYER_MAP.computeIfAbsent(uuid, ChatIconPlayer::new);
    }

    public static void removeChatIconPlayer(UUID uuid) {
        CHAT_ICON_PLAYER_MAP.remove(uuid);
    }
}
