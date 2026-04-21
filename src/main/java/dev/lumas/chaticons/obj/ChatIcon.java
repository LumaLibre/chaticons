package dev.lumas.chaticons.obj;

import dev.lumas.chaticons.utility.Util;
import dev.lumas.core.util.Text;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChatIcon {

    private String name;
    private Component component;
    private String permission;
    private Material material;
    private List<String> description;

    private Component hoverableComponent;


    @Builder
    public ChatIcon(String name, Component component, String permission, Material material, List<String> description) {
        this.name = name;
        this.component = component;
        this.permission = permission;
        this.material = material;
        this.description = description;
        buildHoverableComponent();
    }

    public String getFormattedName() {
        return Util.formatSnakeCase(name);
    }

    public List<String> getDescription() {
        return description != null ? description : List.of("No description.");
    }

    private void buildHoverableComponent() {
        List<Component> components = new ArrayList<>();

        components.add(Text.mm("<#9de24f><b>\"" + getFormattedName() + "\""));
        components.add(Component.empty());
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
