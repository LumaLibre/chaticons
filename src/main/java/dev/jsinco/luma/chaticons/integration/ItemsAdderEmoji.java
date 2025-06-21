package dev.jsinco.luma.chaticons.integration;

import dev.lone.itemsadder.api.FontImages.FontImageWrapper;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;

public final class ItemsAdderEmoji {

    @Nullable
    public static String getEmojiCharacters(String namespace, String name) {
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

    @Nullable
    public static Component getEmojiComponent(String namespace, String name) {
        String emojiCharacters = getEmojiCharacters(namespace, name);
        if (emojiCharacters == null) {
            return null;
        }
        return Component.text(emojiCharacters);
    }
}
