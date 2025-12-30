package dev.lumas.chaticons.config;

import eu.okaeri.configs.OkaeriConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DisabledChatHeadsCharacter extends OkaeriConfig {
    private String permission;
    private String string;
}
