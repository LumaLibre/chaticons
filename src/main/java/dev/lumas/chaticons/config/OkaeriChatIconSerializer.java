package dev.lumas.chaticons.config;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.jetbrains.annotations.NotNull;

public class OkaeriChatIconSerializer implements ObjectSerializer<OkaeriChatIcon> {

    @Override
    public boolean supports(@NotNull Class<? super OkaeriChatIcon> type) {
        return OkaeriChatIcon.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NotNull OkaeriChatIcon icon, @NotNull SerializationData data, @NotNull GenericsDeclaration generics) {
        data.add("name", icon.getName());
        if (icon.hasOverrideName()) {
            data.add("overrideName", icon.getOverrideName());
        }
        if (icon.getDescription() == null) {
            data.addRaw("description", null);
        } else {
            data.addCollection("description", icon.getDescription(), String.class);
        }
    }

    @Override
    public OkaeriChatIcon deserialize(@NotNull DeserializationData data, @NotNull GenericsDeclaration generics) {
        return OkaeriChatIcon.create(
                data.get("name", String.class),
                data.get("overrideName", String.class),
                data.getAsList("description", String.class)
        );
    }
}
