package dev.lumas.chaticons.integration;

import java.util.function.Supplier;


public enum IconComponentProviders {

    ITEMSADDER(ItemsAdderEmojiProvider::new),
    NEXO(NexoGlyphProvider::new);


    private final Supplier<IconComponentProvider> providerSupplier;

    IconComponentProviders(Supplier<IconComponentProvider> providerSupplier) {
        this.providerSupplier = providerSupplier;
    }

    public IconComponentProvider getProvider() {
        return providerSupplier.get();
    }
}
