package dev.lumas.chaticons.events;

import dev.jsinco.chatheads.api.ChatHeadsInjectEvent;
import dev.lumas.chaticons.ChatIcons;
import dev.lumas.chaticons.config.Config;
import dev.lumas.chaticons.config.DisabledChatHeadsCharacter;
import dev.lumas.chaticons.obj.ChatIcon;
import dev.lumas.chaticons.obj.ChatIconPlayer;
import dev.lumas.chaticons.obj.ChatIconPlayerManager;
import dev.lumas.chaticons.utility.Util;
import dev.lumas.lumacore.manager.modules.AutoRegister;
import dev.lumas.lumacore.manager.modules.RegisterType;
import dev.lumas.lumacore.utility.Text;
import net.kyori.adventure.text.Component;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeEqualityPredicate;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

@AutoRegister(RegisterType.LISTENER)
public class Listeners implements Listener {

    private static final Component SPACE = Component.text(" ");


    @EventHandler(priority = EventPriority.NORMAL)
    public void onChatHeadsInject(ChatHeadsInjectEvent event) {
        Player player = event.getPlayer();
        if (player == null) return;

        Component avatar = event.getAvatar();
        ChatIconPlayer chatIconPlayer = ChatIconPlayerManager.getChatIconPlayer(player.getUniqueId());
        ChatIcon chatIcon = chatIconPlayer.getIcon();


        if (chatIcon == null || event.isCancelled()) {
            Component noIconPrefix = this.getNoIconPrefix(player);
            if (noIconPrefix != null) {
                if (event.isCancelled()) {
                    event.setAvatar(noIconPrefix);
                    event.setCancelled(false);
                } else {
                    event.setAvatar(avatar.append(SPACE).append(noIconPrefix));
                }
            }
            return;
        }

        Component icon = SPACE.append(chatIcon.getComponent());
        event.setAvatar(avatar.append(icon));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ChatIconPlayerManager.load(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ChatIconPlayerManager.removeChatIconPlayer(player.getUniqueId());
    }


    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent event) {
        if (!event.hasItem() || !event.getAction().isRightClick()) {
            return;
        }

        ItemStack item = event.getItem();
        if (!Util.isIconVoucher(item)) {
            return;
        }

        LuckPerms lp = ChatIcons.getLuckPerms();
        Player player = event.getPlayer();
        if (lp == null) {
            Text.msg(player, "Unable to use this chat icon voucher right now.");
            return;
        }

        String permission = Util.getIconVoucherPermission(item);
        if (permission == null) {
            Text.msg(player, "This chat icon voucher is invalid.");
            return;
        }

        Node node = Node.builder(permission).value(true).build();

        UserManager userManager = lp.getUserManager();
        User user = userManager.getUser(player.getUniqueId());
        if (user == null) {
            Text.msg(player, "Unable to find your user data. Please try again later.");
            return;
        }

        if (user.data().contains(node, NodeEqualityPredicate.IGNORE_VALUE).asBoolean()) {
            Text.msg(player, "You already have this chat icon unlocked.");
            return;
        }

        user.data().add(node);
        userManager.saveUser(user);

        Text.msg(player, "You redeemed a chat icon voucher! You can now use this icon with <#9de24f>/icons</#9de24f>.");
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        item.setAmount(item.getAmount() - 1);
        event.setCancelled(true);
    }


    @Nullable
    private Component getNoIconPrefix(Player player) {
        String newPrefix = null;
        Config cfg = ChatIcons.getChatIconsConfig();
        for (DisabledChatHeadsCharacter disabledChatHeadsCharacter : cfg.getDisabledChatHeadsCharacters()) {
            if (player.hasPermission(disabledChatHeadsCharacter.getPermission())) {
                newPrefix = disabledChatHeadsCharacter.getString();
                break;
            }
        }
        if (newPrefix != null) {
            return Text.mm(newPrefix);
        }
        return null;
    }
}
