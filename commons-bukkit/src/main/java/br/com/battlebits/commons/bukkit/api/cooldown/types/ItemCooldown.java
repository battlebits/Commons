package br.com.battlebits.commons.bukkit.api.cooldown.types;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

public class ItemCooldown extends Cooldown {

    @Getter
    private ItemStack item;

    @Getter
    @Setter
    private boolean selected;

    public ItemCooldown(ItemStack item, String name, Long duration) {
        super(name, duration);
        this.item = item;
    }
}
