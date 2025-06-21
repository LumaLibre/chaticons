package dev.jsinco.luma.chaticons.config;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;

import java.util.Map;

@Getter
public class Config extends OkaeriConfig {

    private Map<String, OkaeriChatIcon> chatIcons = Map.of(
            "example", OkaeriChatIcon.defaultOkaeriChatIcon()
    );

}
