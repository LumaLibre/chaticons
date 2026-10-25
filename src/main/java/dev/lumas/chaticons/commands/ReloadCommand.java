package dev.lumas.chaticons.commands;

import dev.lumas.chaticons.ChatIcons;
import dev.lumas.lumacore.manager.commands.CommandInfo;
import dev.lumas.lumacore.manager.modules.AutoRegister;
import dev.lumas.lumacore.manager.modules.RegisterType;
import dev.lumas.lumacore.utility.Text;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.logging.Level;

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
        try {
            ChatIcons.reload();
        } catch (Exception e) {
            chatIcons.getLogger().log(Level.SEVERE, "Failed to reload config", e);
            Text.msg(commandSender, "<red>Failed to reload config.");
            return true;
        }
        Text.msg(commandSender, "Config reloaded.");
        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull ChatIcons chatIcons, CommandSender commandSender, String[] strings) {
        return List.of();
    }
}
