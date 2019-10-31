package br.com.battlebits.commons.bukkit.api.item;

import br.com.battlebits.commons.bukkit.BukkitMain;
import br.com.battlebits.commons.bukkit.api.item.glow.Glow;
import br.com.battlebits.commons.bukkit.util.string.StringLoreUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ItemBuilder {

    private ActionItemStack itemStack;
    private boolean loreUtils = true;

    private ItemBuilder(ActionItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public static ItemBuilder create(Material material) {
        return new ItemBuilder(new ActionItemStack(material));
    }

    public void useLoreUtils(boolean value) {
        this.loreUtils = value;
    }

    public ItemBuilder changeItem(Consumer<ActionItemStack> consumer) {
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
        return changeItem(item -> item.setDurability((short) amount));
    }

    public ItemBuilder enchantment(Enchantment enchantment, int level) {
        return changeItem(item -> item.addEnchantment(enchantment, level));
    }

    public ItemBuilder lore(List<String> lore) {
        if(this.loreUtils) {
           List<String> loreFormat = new ArrayList<>();
           for (String text : lore) {
               loreFormat.addAll(StringLoreUtils.formatForLore(text));
           }
           return changeMeta(itemMeta -> itemMeta.setLore(loreFormat));
        }
        return changeMeta(itemMeta -> itemMeta.setLore(lore));
    }

    public ItemBuilder lore(String... lore) {
        return lore(Arrays.asList(lore));
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

    public ItemBuilder glow() {
        Plugin pl = BukkitMain.getInstance();
        return enchantment(new Glow(70), 0);
    }

    public ItemBuilder unbreakable(boolean b) {
        return changeMeta(itemMeta -> itemMeta.setUnbreakable(b));
    }

    public ItemBuilder interact(ActionItemStack.InteractHandler interactHandler) {
        return changeItem(itemMeta -> itemMeta.setInteractHandler(interactHandler));
    }

    public ItemBuilder with(Consumer<ItemBuilder> consumer) {
        consumer.accept(this);
        return this;
    }

    public ActionItemStack build() {
        return this.itemStack;
    }
}
