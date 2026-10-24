package dev.lumas.chaticons.obj;

import dev.jsinco.chatheads.obj.CachedPlayer;
import dev.lumas.chaticons.ChatIcons;
import dev.lumas.chaticons.utility.ChatIconVisibility;
import dev.lumas.chaticons.utility.Util;
import dev.lumas.core.util.Text;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChatIcon {

    private String name;
    private String overrideName;
    private Component component;
    private String permission;
    private Material material;
    private List<String> description;

    private Component hoverableComponent;


    @Builder
    public ChatIcon(String name, String overrideName, Component component, String permission, Material material, List<String> description) {
        this.name = name;
        this.overrideName = overrideName;
        this.component = component;
        this.permission = permission;
        this.material = material;
        this.description = description;
        buildHoverableComponent();
    }

    public Component getComponent(Player viewer) {
        if (CachedPlayer.checkBedrockPlayer(viewer.getUniqueId())) {
            return ChatIcons.getIconComponentProvider().renderForViewer(viewer, component);
        }
        return component;
    }

    public Component getDisplayComponent(Player viewer) {
        return ChatIconVisibility.canSeeIcons(viewer) ? getComponent(viewer) : Component.text(getFormattedName());
    }

    public String getFormattedName() {
        return overrideName != null && !overrideName.isBlank()
                ? overrideName
                : Util.formatSnakeCase(name);
    }

    public List<String> getDescription() {
        return description != null ? description : List.of("No description.");
    }

    private void buildHoverableComponent() {
        List<Component> components = new ArrayList<>();

        components.add(Text.mm("<#9de24f><b>\"" + getFormattedName() + "\" <white>").append(component));
        components.addAll(getDescription().stream()
                .map(desc -> Text.mm("<white>| <gray>" + desc))
                .toList());

        Component asOne = Component.join(JoinConfiguration.newlines(), components);

        hoverableComponent = component.hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, asOne));
    }

    public void setComponent(Component component) {
        this.component = component;
        buildHoverableComponent();
    }

}
