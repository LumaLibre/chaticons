package dev.lumas.chaticons.commands;

import dev.lumas.chaticons.ChatIcons;
import dev.lumas.chaticons.events.Listeners;
import dev.lumas.chaticons.obj.ChatIcon;
import dev.lumas.chaticons.obj.ChatIconPlayer;
import dev.lumas.chaticons.obj.ChatIconPlayerManager;
import dev.lumas.lumacore.manager.commands.CommandInfo;
import dev.lumas.lumacore.manager.modules.AutoRegister;
import dev.lumas.lumacore.manager.modules.RegisterType;
import dev.lumas.lumacore.utility.Text;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@AutoRegister(RegisterType.SUBCOMMAND)
@CommandInfo(
        name = "reload",
        description = "Reloads the configuration of ChatIcons",
        usage = "/<command> reload",
        permission = "chaticons.command.reload",
        parent = CommandManager.class
)
public class ReloadCommand implements SubCommand {

    @Override
    public boolean execute(@NotNull ChatIcons chatIcons, CommandSender commandSender, String s, String[] strings) {
        ChatIcons.getChatIconsConfig().load();
        ChatIcons.setProviderInstance();
        for (ChatIconPlayer chatIconPlayer : ChatIconPlayerManager.CHAT_ICON_PLAYER_MAP.values()) {
            ChatIcon chatIcon = chatIconPlayer.getIcon();
            if (chatIcon != null) {
                chatIcon.buildHoverableComponent();
            }
        }
        Text.msg(commandSender, "Config reloaded.");
        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull ChatIcons chatIcons, CommandSender commandSender, String[] strings) {
        return List.of();
    }
}
