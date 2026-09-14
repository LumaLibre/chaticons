package dev.lumas.chaticons.config;

import dev.lumas.chaticons.ChatIcons;
import dev.lumas.chaticons.integration.IconComponentProvider;
import dev.lumas.chaticons.integration.ItemsAdderEmojiProvider;
import dev.lumas.chaticons.obj.ChatIcon;
import dev.lumas.chaticons.utility.Util;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Getter
public class OkaeriChatIcon extends OkaeriConfig {


    private static final transient List<Material> BANNER_PATTERNS = List.of(
        Material.FIELD_MASONED_BANNER_PATTERN,
        Material.BORDURE_INDENTED_BANNER_PATTERN,
        Material.FLOWER_BANNER_PATTERN,
        Material.CREEPER_BANNER_PATTERN,
        Material.SKULL_BANNER_PATTERN,
        Material.MOJANG_BANNER_PATTERN,
        Material.GLOBE_BANNER_PATTERN,
        Material.PIGLIN_BANNER_PATTERN,
        Material.FLOW_BANNER_PATTERN,
        Material.GUSTER_BANNER_PATTERN
    );

    private String name;
    private List<String> description;

    @Nullable
    public Component getProvidedIcon() {
        if (name == null || name.isEmpty()) {
            return null;
        }

        IconComponentProvider provider = ChatIcons.getIconComponentProvider();
        if (provider instanceof ItemsAdderEmojiProvider) {
            String namespace = ChatIcons.getChatIconsConfig().getNamespace();
            return provider.getComponent(namespace, name);
        }
        return provider.getComponent(null, name);
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
        // random pattern
        return BANNER_PATTERNS.get(Math.abs(name.hashCode()) % BANNER_PATTERNS.size());
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
        okaeriChatIcon.name = "icon";
        okaeriChatIcon.description = List.of("Default icon");
        return okaeriChatIcon;
    }
}
