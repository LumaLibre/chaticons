package dev.lumas.chaticons.commands;

import dev.lumas.chaticons.ChatIcons;
import dev.lumas.chaticons.config.OkaeriChatIcon;
import dev.lumas.chaticons.obj.ChatIcon;
import dev.lumas.chaticons.obj.ChatIconPlayer;
import dev.lumas.chaticons.obj.ChatIconPlayerManager;
import dev.lumas.lumacore.manager.commands.CommandInfo;
import dev.lumas.lumacore.manager.modules.AutoRegister;
import dev.lumas.lumacore.manager.modules.RegisterType;
import dev.lumas.lumacore.utility.Text;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@AutoRegister(RegisterType.SUBCOMMAND)
@CommandInfo(
        name = "forceset",
        description = "Forcefully sets the chat icon of a player, regardless of permissions",
        usage = "/<command> forceset <player> <icon_name>",
        permission = "chaticons.command.forceset",
        parent = CommandManager.class
)
public class ForceSetIconCommand implements SubCommand {
    @Override
    public boolean execute(@NotNull ChatIcons chatIcons, CommandSender commandSender, String s, String[] strings) {
        Player target = Bukkit.getPlayerExact(strings[0]);

        if (target == null) {
            Text.msg(commandSender, "<red>Player not found!");
            return true;
        }

        OkaeriChatIcon okaeriChatIcon = ChatIcons.getChatIconsConfig().getChatIcons().stream()
                .filter(icon -> icon.getName().equalsIgnoreCase(strings[1]))
                .findFirst()
                .orElse(null);

        ChatIconPlayer chatIconPlayer = ChatIconPlayerManager.getChatIconPlayer(target.getUniqueId());

        if (strings[1].equalsIgnoreCase("none") && okaeriChatIcon == null) {
            chatIconPlayer.setIcon(null);
            Text.msg(target, "Your chat icon has been removed.");
            Text.msg(commandSender, "Removed " + target.getName() + "'s chat icon.");
            return true;
        }

        if (okaeriChatIcon == null) {
            Text.msg(commandSender, "<red>Chat icon not found!");
            return true;
        }

        ChatIcon chatIcon = okaeriChatIcon.toChatIcon();

        chatIconPlayer.setIcon(chatIcon);

        Text.msg(target, Component.text("Your chat icon has been changed to ").append(chatIcon.getDisplayComponent(target)).append(Text.mm(".")));
        Text.msg(commandSender, "Set " + target.getName() + "'s chat icon to " + chatIcon.getName() + ".");
        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull ChatIcons chatIcons, CommandSender commandSender, String[] strings) {
        if (strings.length == 1) {
            return null;
        } else if (strings.length == 2) {
            List<String> names = new java.util.ArrayList<>(ChatIcons.getChatIconsConfig().getChatIcons().stream()
                .map(OkaeriChatIcon::getName)
                .toList());
            names.add("none");
            return names;
        }
        return List.of();
    }
}
