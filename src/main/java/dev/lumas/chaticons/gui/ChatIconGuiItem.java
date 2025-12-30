package dev.lumas.chaticons.gui;

import dev.lumas.chaticons.ChatIcons;
import dev.lumas.chaticons.obj.ChatIcon;
import dev.lumas.lumacore.manager.guis.GuiItemAction;
import dev.lumas.lumacore.manager.guis.items.KeyedGuiItem;
import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

@Getter
public class ChatIconGuiItem extends KeyedGuiItem {

    private final ChatIcon chatIcon;

    public ChatIconGuiItem(ItemStack itemStack, ChatIcon chatIcon, GuiItemAction action) {
        super(new NamespacedKey(ChatIcons.getInstance(), "icon-" + chatIcon.getName()), itemStack, action);
        this.chatIcon = chatIcon;
    }
}
