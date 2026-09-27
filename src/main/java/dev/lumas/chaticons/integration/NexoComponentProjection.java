package dev.lumas.chaticons.integration;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;
import java.util.logging.Level;
import java.util.logging.Logger;

final class NexoComponentProjection {

    private NexoComponentProjection() {}

    static BiFunction<Player, Component, Component> load(ClassLoader loader, Logger logger) {
        try {
            Class<?> api = Class.forName("com.nexomc.nexo.api.ComponentProjections", true, loader);
            Object instance = api.getField("INSTANCE").get(null);
            Method apply = api.getMethod("apply", Player.class, Component.class);
            AtomicBoolean warned = new AtomicBoolean();
            logger.info("Nexo viewer glyph projection enabled for Bedrock chat icons.");
            return (viewer, component) -> {
                try {
                    return (Component) apply.invoke(instance, viewer, component);
                } catch (ReflectiveOperationException | LinkageError exception) {
                    if (warned.compareAndSet(false, true)) {
                        logger.log(Level.WARNING, "Could not project a Bedrock chat icon through Nexo", exception);
                    }
                    return component;
                }
            };
        } catch (ClassNotFoundException ignored) {
            return (viewer, component) -> component;
        } catch (ReflectiveOperationException | LinkageError exception) {
            logger.log(Level.WARNING, "Nexo viewer glyph projection is unavailable", exception);
            return (viewer, component) -> component;
        }
    }
}
