package dev.jsinco.luma.chaticons.obj;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

@Builder
@Getter
@Setter
public class ChatIcon {

    private String name;
    private Component component;
    private String permission;
    private Material material;


    public ChatIcon(String name, Component component, String permission, Material material) {
        this.name = name;
        this.component = component;
        this.permission = permission;
        this.material = material;
    }


}
