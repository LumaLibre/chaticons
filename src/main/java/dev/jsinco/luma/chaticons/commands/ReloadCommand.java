package dev.jsinco.luma.chaticons.commands;

import dev.jsinco.luma.chaticons.LumaChatIcons;
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
        description = "Reloads the configuration of LumaChatIcons",
        usage = "/<command> reload",
        permission = "lumachaticons.command.reload",
        parent = CommandManager.class
)
public class ReloadCommand implements SubCommand {

    @Override
    public boolean execute(@NotNull LumaChatIcons lumaChatIcons, CommandSender commandSender, String s, String[] strings) {
        LumaChatIcons.getChatIconsConfig().load();
        Text.msg(commandSender, "Config reloaded.");
        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull LumaChatIcons lumaChatIcons, CommandSender commandSender, String[] strings) {
        return List.of();
    }
}
