package dev.jsinco.luma.chaticons.obj;

import dev.jsinco.luma.chaticons.utility.Util;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

import java.util.List;

@Builder
@Getter
@Setter
public class ChatIcon {

    private String name;
    private Component component;
    private String permission;
    private Material material;
    private List<String> description;


    public ChatIcon(String name, Component component, String permission, Material material, List<String> description) {
        this.name = name;
        this.component = component;
        this.permission = permission;
        this.material = material;
        this.description = description;
    }

    public String getFormattedName() {
        return Util.formatSnakeCase(name);
    }

    public List<String> getDescription() {
        return description != null ? description : List.of("No description.");
    }


}
