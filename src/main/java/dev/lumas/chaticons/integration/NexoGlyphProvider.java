package dev.lumas.chaticons.integration;

import com.nexomc.nexo.NexoPlugin;
import com.nexomc.nexo.glyphs.Glyph;
import dev.lumas.chaticons.ChatIcons;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

public final class NexoGlyphProvider implements IconComponentProvider {

    private final BiFunction<Player, Component, Component> projection = NexoComponentProjection.load(
            NexoPlugin.class.getClassLoader(), ChatIcons.getInstance().getLogger());

    @Override
    public Component renderForViewer(Player viewer, Component component) {
        return projection.apply(viewer, component);
    }

    @Override
    public @Nullable Component getComponent(@Nullable String ignored, @NotNull String identifier) {
        // ignore namespace for Nexo glyphs
        Glyph glyph = NexoPlugin.instance().fontManager().glyphFromID(identifier);
        if (glyph != null) {
            return glyph.getComponent();
        }
        return null;
    }
}
