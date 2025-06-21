package dev.jsinco.luma.chaticons.gui;

import dev.jsinco.luma.chaticons.LumaChatIcons;
import dev.jsinco.luma.chaticons.config.OkaeriChatIcon;
import dev.jsinco.luma.chaticons.obj.ChatIcon;
import dev.jsinco.luma.chaticons.obj.ChatIconPlayer;
import dev.jsinco.luma.chaticons.obj.ChatIconPlayerManager;
import dev.jsinco.luma.chaticons.utility.Util;
import dev.jsinco.luma.lumacore.manager.guis.AbstractGui;
import dev.jsinco.luma.lumacore.manager.guis.GuiItemAction;
import dev.jsinco.luma.lumacore.manager.guis.items.IndexedGuiItem;
import dev.jsinco.luma.lumacore.utility.Text;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ChatIconsGui extends AbstractGui {

    private final IndexedGuiItem PREVIOUS_PAGE = IndexedGuiItem.of(
            39,
            Util.createItem(Material.PAPER, meta -> {
                meta.displayName(Text.mmNoItalic("<green><b>Previous Page"));
                meta.lore(Text.mmlNoItalic(
                        "",
                        "<white>| <gray>Click to go to the",
                        "<white>| <gray>previous page."
                ));
            }),
            (event, guiItem) -> {
                Inventory prev = this.paginatedGui.getPrevious(event.getInventory());
                if (prev != null) event.getWhoClicked().openInventory(prev);
            }
    );

    private final IndexedGuiItem CLEAR_ICON = IndexedGuiItem.of(
            40,
            Util.createItem(Material.BARRIER, meta -> {
                meta.displayName(Text.mmNoItalic("<red><b>Clear Icon"));
                meta.lore(Text.mmlNoItalic(
                        "",
                        "<white>| <gray>Click to clear your",
                        "<white>| <gray>active chat icon."
                ));
            }),
            (event, guiItem) -> {
                event.setCancelled(true);
                Player player = (Player) event.getWhoClicked();
                ChatIconPlayer chatIconPlayer = ChatIconPlayerManager.getChatIconPlayer(player.getUniqueId());
                chatIconPlayer.setIcon(null);
                Text.msg(player, Component.text("You cleared your chat icon."));

                Bukkit.getScheduler().runTaskLater(LumaChatIcons.getInstance(), () -> {
                    player.closeInventory();
                }, 1L);
            }
    );

    private final IndexedGuiItem NEXT_PAGE = IndexedGuiItem.of(
            41,
            Util.createItem(Material.PAPER, meta -> {
                meta.displayName(Text.mmNoItalic("<green><b>Next Page"));
                meta.lore(Text.mmlNoItalic(
                        "",
                        "<white>| <gray>Click to go to the",
                        "<white>| <gray>next page."
                ));
            }),
            (event, guiItem) -> {
                Inventory next = this.paginatedGui.getNext(event.getInventory());
                if (next != null) event.getWhoClicked().openInventory(next);
            }
    );

    private final GuiItemAction iconAction = (event, abstractGuiItem) -> {
        event.setCancelled(true);
        ChatIconGuiItem guiItem = (ChatIconGuiItem) abstractGuiItem;

        ChatIcon chatIcon = guiItem.getChatIcon();
        Player player = (Player) event.getWhoClicked();
        ChatIconPlayer chatIconPlayer = ChatIconPlayerManager.getChatIconPlayer(player.getUniqueId());

        if (chatIcon.getPermission() != null && !player.hasPermission(chatIcon.getPermission())) {
            Text.msg(player, "You don't have permission to use this icon.");
            return;
        }
        chatIconPlayer.setIcon(chatIcon);
        Text.msg(player, Component.text("You changed your chat icon to ").append(chatIcon.getComponent()));

        Bukkit.getScheduler().runTaskLater(LumaChatIcons.getInstance(), () -> {
            player.closeInventory();
        }, 1L);
    };


    private final Inventory inventory;
    private PaginatedGui paginatedGui;

    public ChatIconsGui(Player player) {
        this.inventory = Util.defaultGui(this);
        this.autoRegister();

        List<ChatIcon> chatIcons = LumaChatIcons.getChatIconsConfig().getChatIcons().values().stream()
                .filter(icon -> icon.getPermission() == null || player.hasPermission(icon.getPermission()))
                .map(OkaeriChatIcon::toChatIcon)
                .toList();
        List<ItemStack> items = getItemStacks(chatIcons);

        this.paginatedGui = PaginatedGui.builder()
                .name("Chat Icons")
                .base(this.inventory)
                .items(items)
                .startEndSlots(9, 35)
                .build();
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event) {

    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }

    @Override
    public void open(HumanEntity humanEntity) {
        humanEntity.openInventory(this.paginatedGui.getPage(0));
    }

    private @NotNull List<ItemStack> getItemStacks(List<ChatIcon> chatIcons) {
        List<ItemStack> items = new ArrayList<>();

        for (ChatIcon chatIcon : chatIcons) {
            ItemStack itemStack = new ItemStack(chatIcon.getMaterial());
            itemStack.editMeta(meta -> {
                meta.displayName(chatIcon.getComponent().color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false).append(Text.mmNoItalic(" <dark_gray>Icon")));
                meta.lore(Text.mmlNoItalic(
                        "",
                        "<white>| <green>Left-click<gray> to change",
                        "<white>| <gray>this to your active",
                        "<white>| <gray>chat icon."
                ));
                meta.addEnchant(Enchantment.UNBREAKING, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            });
            ChatIconGuiItem guiItem = new ChatIconGuiItem(itemStack, chatIcon, iconAction);
            this.addItem(guiItem);
            items.add(itemStack);
        }
        return items;
    }
}
