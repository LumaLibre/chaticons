package dev.jsinco.luma.chaticons.integration;

import com.nexomc.nexo.NexoPlugin;
import com.nexomc.nexo.glyphs.Glyph;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class NexoGlyphProvider implements IconComponentProvider {

    @Override
    public @Nullable Component getComponent(@Nullable String ignored, @NotNull String identifier) {
        // ignore namespace for Nexo glyphs
        Glyph glyph = NexoPlugin.instance().getFontManager$core().glyphFromID(identifier);
        if (glyph != null) {
            return glyph.getComponent();
        }
        return null;
    }
}
