package dev.jsinco.luma.chaticons.config;

import dev.jsinco.luma.chaticons.integration.ItemsAdderEmoji;
import dev.jsinco.luma.chaticons.obj.ChatIcon;
import dev.jsinco.luma.chaticons.utility.Util;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Getter
public class OkaeriChatIcon extends OkaeriConfig {

    private String name;
    private String namespace;
    private List<String> description;

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
        return "chaticons.icon." + name;
    }

    public Material getMaterial() {
        return Material.FLOWER_BANNER_PATTERN;
    }

    public ChatIcon toChatIcon() {
        return ChatIcon.builder()
                .name(name)
                .component(getComponent())
                .permission(getPermission())
                .material(getMaterial())
                .description(description)
                .build();
    }

    public String formattedName() {
        return Util.formatSnakeCase(name);
    }

    public static OkaeriChatIcon defaultOkaeriChatIcon() {
        OkaeriChatIcon okaeriChatIcon = new OkaeriChatIcon();
        okaeriChatIcon.namespace = "example";
        okaeriChatIcon.name = "icon";
        return okaeriChatIcon;
    }
}
