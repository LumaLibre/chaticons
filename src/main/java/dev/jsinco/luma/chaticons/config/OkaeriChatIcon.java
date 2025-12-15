package dev.jsinco.luma.chaticons.config;

import dev.jsinco.luma.chaticons.ChatIcons;
import dev.jsinco.luma.chaticons.integration.IconComponentProvider;
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
    private @Nullable String namespace;
    private List<String> description;

    @Nullable
    public Component getProvidedIcon() {
        if (name == null || name.isEmpty()) {
            return null;
        }

        IconComponentProvider provider = ChatIcons.getIconComponentProvider();
        return provider.getComponent(namespace, name);
    }

    public Component getComponent() {
        Component component = getProvidedIcon();
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
