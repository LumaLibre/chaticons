package dev.lumas.chaticons.integration;

import dev.lumas.chaticons.ChatIcons;
import dev.lumas.chaticons.obj.ChatIcon;
import dev.lumas.chaticons.obj.ChatIconPlayerManager;
import dev.lumas.chaticons.utility.ChatIconVisibility;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.clip.placeholderapi.expansion.Relational;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ChatIconsExpansion extends PlaceholderExpansion implements Relational {

    @Override
    public @NotNull String getIdentifier() {
        return "chaticons";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Jsinco";
    }

    @Override
    public @NotNull String getVersion() {
        return "1";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public List<String> getPlaceholders() {
        return List.of("%rel_chaticons_icon%");
    }

    @Override
    public String onPlaceholderRequest(Player viewer, Player target, String params) {
        if (viewer == null || target == null || !ChatIconVisibility.canSeeIcons(viewer)) {
            return "";
        }

        ChatIcon icon = ChatIconPlayerManager.getChatIconPlayer(target.getUniqueId()).getIcon();
        if (icon == null) {
            return "";
        }

        return ChatIcons.getChatIconsConfig().getRelationalIconFormat().serialize(icon.getComponent(viewer));
    }
}
