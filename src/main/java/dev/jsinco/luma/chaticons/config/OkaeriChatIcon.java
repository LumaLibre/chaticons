package dev.jsinco.luma.chaticons.config;

import dev.jsinco.luma.chaticons.integration.ItemsAdderEmoji;
import dev.jsinco.luma.chaticons.obj.ChatIcon;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;

@Getter
public class OkaeriChatIcon extends OkaeriConfig {

    private String namespace;
    private String name;

    public boolean isResourcePackDependent() {
        return namespace != null && !namespace.isEmpty();
    }

    @Nullable
    public Component getItemsAdderEmoji() {
        if (namespace == null || namespace.isEmpty() || name == null || name.isEmpty()) {
            return null;
        }
        return ItemsAdderEmoji.getEmojiComponent(namespace, name);
    }

    public Component getComponent() {
        Component component = getItemsAdderEmoji();
        if (component == null) {
            component = Component.text(name);
        }
        return component;
    }

    public String getPermission() {
        return "lumachaticons.icon." + name;
    }

    public ChatIcon toChatIcon() {
        return ChatIcon.builder()
                .name(name)
                .component(getComponent())
                .permission(getPermission())
                .material(Material.AMETHYST_SHARD)
                .build();
    }

    public static OkaeriChatIcon defaultOkaeriChatIcon() {
        OkaeriChatIcon okaeriChatIcon = new OkaeriChatIcon();
        okaeriChatIcon.namespace = "example";
        okaeriChatIcon.name = "icon";
        return okaeriChatIcon;
    }
}
