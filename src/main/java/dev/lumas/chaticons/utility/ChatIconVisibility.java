package dev.lumas.chaticons.utility;

import dev.jsinco.chatheads.obj.CachedPlayer;
import org.bukkit.entity.Player;

public final class ChatIconVisibility {

    private ChatIconVisibility() {
    }

    public static boolean canSeeIcons(Player viewer) {
        // Geyser's Bedrock pack does not report Java resource-pack status to Paper.
        return CachedPlayer.checkBedrockPlayer(viewer.getUniqueId()) || viewer.hasResourcePack();
    }
}
