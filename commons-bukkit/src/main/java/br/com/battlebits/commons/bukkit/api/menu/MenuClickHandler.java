package br.com.battlebits.commons.bukkit.api.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface MenuClickHandler {
    void onClick(Player p, Inventory inv, ClickType type, ItemStack stack, int slot);
}
