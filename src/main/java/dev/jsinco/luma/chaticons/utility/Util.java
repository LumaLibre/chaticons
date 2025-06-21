package dev.jsinco.luma.chaticons.utility;

import dev.jsinco.luma.chaticons.LumaChatIcons;
import dev.jsinco.luma.lumacore.utility.Text;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

public final class Util {

    private static final NamespacedKey DISABLED_ICON_KEY = new NamespacedKey(LumaChatIcons.getInstance(), "luma-chat-icons");
    private static final Map<ItemStack, int[]> DEFAULT_GUI_ITEMS = new HashMap<>();

    static {
        DEFAULT_GUI_ITEMS.put(ItemStack.of(Material.GRAY_STAINED_GLASS_PANE), new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 36, 37, 38, 39, 40, 41, 42, 43, 44});
    }

    public static boolean hasDisabledIcons(Player player) {
        return player.getPersistentDataContainer().has(DISABLED_ICON_KEY);
    }

    public static void setDisabledIcons(Player player, boolean disabled) {
        if (disabled) {
            player.getPersistentDataContainer().set(DISABLED_ICON_KEY, PersistentDataType.BOOLEAN, true);
        } else {
            player.getPersistentDataContainer().remove(DISABLED_ICON_KEY);
        }
    }

    public static Inventory defaultGui(InventoryHolder owner) {
        Inventory inventory = Bukkit.createInventory(owner, 45, Text.mm("Chat Icons"));
        for (Map.Entry<ItemStack, int[]> entry : DEFAULT_GUI_ITEMS.entrySet()) {
            ItemStack item = entry.getKey();
            for (int slot : entry.getValue()) {
                inventory.setItem(slot, item);
            }
        }
        return inventory;
    }

    public static ItemStack createItem(Material material, EditMeta editMeta) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return itemStack;
        }
        editMeta.edit(itemMeta);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
