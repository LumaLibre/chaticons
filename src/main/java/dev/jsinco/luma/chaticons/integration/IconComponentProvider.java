package dev.jsinco.luma.chaticons.integration;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IconComponentProvider {

    @Nullable Component getComponent(String namespace, @NotNull String identifier);
}
