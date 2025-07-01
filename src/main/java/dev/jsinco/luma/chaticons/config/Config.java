package dev.jsinco.luma.chaticons.config;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;

import java.util.List;

@Getter
public class Config extends OkaeriConfig {

    private List<OkaeriChatIcon> chatIcons = List.of(
            OkaeriChatIcon.defaultOkaeriChatIcon()
    );

    private List<DisabledChatHeadsCharacter> disabledChatHeadsCharacters = List.of(
            new DisabledChatHeadsCharacter("group.admin", "<#fb6969>★"),
            new DisabledChatHeadsCharacter("group.mod", "<#C0B7FB>★"),
            new DisabledChatHeadsCharacter("group.helper", "<#AEFF7D>★"),
            new DisabledChatHeadsCharacter("group.content", "<#fb9dc2>★")
    );

}
