package dev.lumas.chaticons.config;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import java.util.function.Function;

public enum RelationalIconFormat {

    // keeps the glyph's font, use with plugins that parse minimessage
    // strict so closing tags are emitted, otherwise the font bleeds into whatever follows the placeholder
    MINIMESSAGE(MiniMessage.builder().strict(true).build()::serialize),
    // drops the font, glyphs must be in minecraft:default
    LEGACY(LegacyComponentSerializer.legacySection()::serialize),
    // raw glyph characters only
    PLAIN(PlainTextComponentSerializer.plainText()::serialize);

    private final Function<Component, String> serializer;

    RelationalIconFormat(Function<Component, String> serializer) {
        this.serializer = serializer;
    }

    public String serialize(Component component) {
        return serializer.apply(component);
    }
}
