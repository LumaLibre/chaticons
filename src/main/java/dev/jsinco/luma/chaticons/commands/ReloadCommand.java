package dev.jsinco.luma.chaticons.commands;

import dev.jsinco.luma.chaticons.ChatIcons;
import dev.jsinco.luma.lumacore.manager.commands.CommandInfo;
import dev.jsinco.luma.lumacore.manager.modules.AutoRegister;
import dev.jsinco.luma.lumacore.manager.modules.RegisterType;
import dev.jsinco.luma.lumacore.utility.Text;
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
        Text.msg(commandSender, "Config reloaded.");
        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull ChatIcons chatIcons, CommandSender commandSender, String[] strings) {
        return List.of();
    }
}
