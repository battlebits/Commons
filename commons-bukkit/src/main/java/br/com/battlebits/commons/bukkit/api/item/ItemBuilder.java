package br.com.battlebits.commons.bukkit.api.item;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ItemBuilder {

    private ItemStack itemStack;

    private ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public static ItemBuilder create(Material material) {
        return new ItemBuilder(new ItemStack(material));
    }

    public ItemBuilder changeItem(Consumer<ItemStack> consumer) {
        consumer.accept(this.itemStack);
        return this;
    }

    public ItemBuilder changeMeta(Consumer<ItemMeta> consumer) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        consumer.accept(itemMeta);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder name(String displayName) {
        return changeMeta(itemMeta -> itemMeta.setDisplayName(displayName));
    }

    public ItemBuilder amount(int amount) {
        return changeItem(item -> item.setAmount(amount));
    }

    public ItemBuilder durability(int amount) {
        return changeMeta(meta -> ((Damageable) meta).setDamage(amount));
    }

    public ItemBuilder enchantment(Enchantment enchantment, int level) {
        return changeItem(item -> item.addEnchantment(enchantment, level));
    }

    public ItemBuilder lore(List<String> lore) {
        return changeMeta(itemMeta -> itemMeta.setLore(lore));
    }

    public ItemBuilder lore(String... lore) {
        return changeMeta(itemMeta -> itemMeta.setLore(Arrays.asList(lore)));
    }

    public ItemBuilder lore(Consumer<List<String>> consumer) {
        List<String> lore = new ArrayList<>();
        consumer.accept(lore);
        return changeMeta(itemMeta -> itemMeta.setLore(lore));
    }

    public ItemBuilder flag(ItemFlag... flags) {
        return changeMeta(itemMeta -> itemMeta.addItemFlags(flags));
    }

    public ItemBuilder removeFlag(ItemFlag... flags) {
        return changeMeta(itemMeta -> itemMeta.removeItemFlags(flags));
    }

    public ItemBuilder hideEnchantments(boolean b) {
        return changeMeta(itemMeta -> itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS));
    }

    public ItemBuilder unbreakable(boolean b) {
        return changeMeta(itemMeta -> itemMeta.setUnbreakable(b));
    }

    public ItemBuilder with(Consumer<ItemBuilder> consumer) {
        consumer.accept(this);
        return this;
    }

    public ItemStack build() {
        return this.itemStack;
    }
}
