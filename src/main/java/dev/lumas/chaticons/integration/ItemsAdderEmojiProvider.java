package dev.lumas.chaticons.integration;

import com.google.common.base.Preconditions;
import dev.lone.itemsadder.api.FontImages.FontImageWrapper;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ItemsAdderEmojiProvider implements IconComponentProvider {

    @Nullable
    private static String getEmojiCharacters(String namespace, String name) {
        String identifier = namespace + ":" + name;
        try {
            FontImageWrapper fontImageWrapper = new FontImageWrapper(identifier);
            if (!fontImageWrapper.exists()) {
                return null;
            }

            return fontImageWrapper.getString();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public @Nullable Component getComponent(@NotNull String namespace, @NotNull String identifier) {
        Preconditions.checkNotNull(namespace, "Namespace cannot be null");

        String emojiCharacters = getEmojiCharacters(namespace, identifier);
        if (emojiCharacters == null) {
            return null;
        }
        return Component.text(emojiCharacters);
    }

}
