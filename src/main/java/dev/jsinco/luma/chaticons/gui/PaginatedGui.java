package dev.jsinco.luma.chaticons.gui;

import dev.jsinco.luma.chaticons.utility.Couple;
import dev.jsinco.luma.lumacore.utility.Text;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
public class PaginatedGui {

    private final String name;
    private final Inventory base;
    private final List<Inventory> pages = new ArrayList<>();

    public PaginatedGui(String name, Inventory base, List<ItemStack> items, Couple<Integer, Integer> startEndSlots, List<Integer> ignoredSlots) {
        this.name = name;
        this.base = base;

        Inventory currentPage = newPage();
        int currentItem = 0;
        int currentSlot = startEndSlots.getFirst();

        while (currentItem < items.size()) {
            if (ignoredSlots.contains(currentSlot)) {
                currentSlot++;
                continue;
            }

            if (currentSlot == startEndSlots.getSecond()) {
                currentPage = newPage();
                currentSlot = startEndSlots.getFirst();
            }

            if (currentPage.getItem(currentSlot) == null) {
                currentPage.setItem(currentSlot, items.get(currentItem));
                currentItem++;
            }
            currentSlot++;
        }
    }

    private Inventory newPage() {
        Inventory inventory = Bukkit.createInventory(base.getHolder(), base.getSize(), Text.mm(name));
        for (int i = 0; i < base.getSize(); i++) {
            inventory.setItem(i, base.getItem(i));
        }
        pages.add(inventory);
        return inventory;
    }

    public Inventory getPage(int page) {
        return pages.get(page);
    }

    public int indexOf(Inventory page) {
        return pages.indexOf(page);
    }

    @Nullable
    public Inventory getNext(Inventory page) {
        int index = pages.indexOf(page);
        return (index == -1 || index + 1 >= pages.size()) ? null : pages.get(index + 1);
    }

    @Nullable
    public Inventory getPrevious(Inventory page) {
        int index = pages.indexOf(page);
        return (index <= 0) ? null : pages.get(index - 1);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name = "Paginated GUI";
        private Inventory base;
        private List<ItemStack> items = Collections.emptyList();
        private Couple<Integer, Integer> startEndSlots = new Couple<>(0, 0);
        private List<Integer> ignoredSlots = Collections.emptyList();

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder base(Inventory base) {
            this.base = base;
            return this;
        }

        public Builder items(List<ItemStack> items) {
            this.items = items;
            return this;
        }

        public Builder startEndSlots(int start, int end) {
            this.startEndSlots = new Couple<>(start, end);
            return this;
        }

        public Builder ignoredSlots(Integer... ignoredSlots) {
            this.ignoredSlots = Arrays.asList(ignoredSlots);
            return this;
        }

        public PaginatedGui build() {
            return new PaginatedGui(name, base, items, startEndSlots, ignoredSlots);
        }
    }

}
