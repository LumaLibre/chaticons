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

import java.util.ArrayList;
import java.util.List;

@Getter
public class OkaeriChatIcon extends OkaeriConfig {

    private String name;
    private List<String> description;
    private List<String> altNames;

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

    public List<String> getPermissions() {
        if (altNames == null || altNames.isEmpty()) return List.of(getPermission());
        List<String> perms = new ArrayList<>(altNames.stream().map(name -> "chaticons.icon." + name).toList());
        perms.add(getPermission());
        return perms;
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
        okaeriChatIcon.name = "icon";
        okaeriChatIcon.description = List.of("Default icon");
        return okaeriChatIcon;
    }
}
