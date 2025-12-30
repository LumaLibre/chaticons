package dev.lumas.chaticons.commands;

import dev.lumas.chaticons.ChatIcons;
import dev.lumas.chaticons.gui.ChatIconsGui;
import dev.lumas.lumacore.manager.commands.AbstractCommandManager;
import dev.lumas.lumacore.manager.commands.CommandInfo;
import dev.lumas.lumacore.manager.modules.AutoRegister;
import dev.lumas.lumacore.manager.modules.RegisterType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@AutoRegister(RegisterType.COMMAND)
@CommandInfo(
        name = "chaticons",
        aliases = {"icons"},
        description = "Main command for LumaChatIcons",
        usage = "/<command> <subcommand?>",
        permission = "chaticons.command.base"
)
public class CommandManager extends AbstractCommandManager<@NotNull ChatIcons, SubCommand> {

    public CommandManager() {
        super(ChatIcons.getInstance());
    }

    @Override
    public boolean handle(@NotNull CommandSender sender, @NotNull String label, String[] args) {
        if (args.length == 0 && sender instanceof Player player) {
            ChatIconsGui gui = new ChatIconsGui(player);
            gui.open(player);
            return true;
        }
        return super.handle(sender, label, args);
    }
}
