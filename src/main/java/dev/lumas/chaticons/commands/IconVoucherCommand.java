package dev.lumas.chaticons.commands;

import dev.lumas.chaticons.ChatIcons;
import dev.lumas.chaticons.config.OkaeriChatIcon;
import dev.lumas.chaticons.utility.Util;
import dev.lumas.lumacore.manager.commands.CommandInfo;
import dev.lumas.lumacore.manager.modules.AutoRegister;
import dev.lumas.lumacore.manager.modules.RegisterType;
import dev.lumas.lumacore.utility.Text;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@AutoRegister(RegisterType.SUBCOMMAND)
@CommandInfo(
        name = "iconvoucher",
        description = "Get a physical voucher for a chat icon",
        usage = "/<command> iconvoucher <icon_name>",
        permission = "chaticons.command.iconvoucher",
        parent = CommandManager.class,
        playerOnly = false
)
public class IconVoucherCommand implements SubCommand {

    @Override
    public boolean execute(@NotNull ChatIcons chatIcons, CommandSender commandSender, String s, String[] strings) {
        if (strings.length == 0) return false;

        Player player;

        if (strings.length > 1) {
            player = Bukkit.getPlayerExact(strings[1]);
        } else if (commandSender instanceof Player p) {
            player = p;
        } else {
            Text.msg(commandSender, "<red>You must specify a player when running this command from console.");
            return true;
        }

        if (player == null) {
            Text.msg(commandSender, "<red>Player not found!");
            return true;
        }

        OkaeriChatIcon chatIcon = ChatIcons.getChatIconsConfig().getChatIcons().stream()
                .filter(icon -> icon.getName().equalsIgnoreCase(strings[0]))
                .findFirst()
                .orElse(null);
        if (chatIcon == null) {
            Text.msg(commandSender,"<red>Chat icon not found!");
            return true;
        }

        ItemStack voucher = chatIconVoucher(chatIcon);
        player.give(voucher);
        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull ChatIcons chatIcons, CommandSender commandSender, String[] strings) {
        if (strings.length == 1) {
            return ChatIcons.getChatIconsConfig().getChatIcons().stream()
                .map(OkaeriChatIcon::getName)
                .toList();
        }
        return Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .toList();
    }

    private ItemStack chatIconVoucher(OkaeriChatIcon chatIcon) {
        ItemStack itemStack = new ItemStack(chatIcon.getMaterial());
        itemStack.editMeta(meta -> {
            meta.displayName(chatIcon.getComponent().decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)
                    .append(Text.mmNoItalic(" <#9de24f><b>\"" + chatIcon.formattedName() + "\" Icon")));
            meta.lore(Text.mmlNoItalic("<gray>Right-click to redeem!"));
            meta.addEnchant(Enchantment.LURE, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.getPersistentDataContainer().set(Util.ICON_VOUCHER_KEY, PersistentDataType.STRING, chatIcon.getPermission());
        });
        return itemStack;
    }
}
