package dev.jsinco.luma.chaticons.utility;

import dev.jsinco.luma.chaticons.ChatIcons;
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

    public static final NamespacedKey ICON_VOUCHER_KEY = new NamespacedKey(ChatIcons.getInstance(), "icon-voucher");
    private static final NamespacedKey DISABLED_ICON_KEY = new NamespacedKey(ChatIcons.getInstance(), "luma-chat-icons");
    private static final Map<ItemStack, int[]> DEFAULT_GUI_ITEMS = new HashMap<>();

    static {
        DEFAULT_GUI_ITEMS.put(ItemStack.of(Material.GRAY_STAINED_GLASS_PANE), new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 27, 28, 29, 30, 31, 32, 33, 34, 35});
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
        Inventory inventory = Bukkit.createInventory(owner, 36, Text.mm("Chat Icons"));
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

    public static boolean isIconVoucher(ItemStack itemStack) {
        return itemStack != null && itemStack.getType() != Material.AIR && itemStack.getPersistentDataContainer().has(ICON_VOUCHER_KEY);
    }

    public static String getIconVoucherPermission(ItemStack itemStack) {
        return  itemStack.getPersistentDataContainer().get(ICON_VOUCHER_KEY, PersistentDataType.STRING);
    }

    public static String formatSnakeCase(String s) {
        String name = s.toLowerCase().replace("_", " ");
        name = name.substring(0, 1).toUpperCase() + name.substring(1);

        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) == ' ' && i + 1 < name.length()) {
                name = name.substring(0, i + 1)
                        + Character.toUpperCase(name.charAt(i + 1))
                        + name.substring(i + 2);
            }
        }
        return name;
    }
}
