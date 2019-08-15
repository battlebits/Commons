package br.com.battlebits.commons.bukkit.inventory;

import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.account.Tag;
import br.com.battlebits.commons.bukkit.api.item.ItemBuilder;
import br.com.battlebits.commons.bukkit.api.menu.ClickType;
import br.com.battlebits.commons.bukkit.api.menu.MenuClickHandler;
import br.com.battlebits.commons.bukkit.api.menu.MenuInventory;
import br.com.battlebits.commons.bukkit.api.menu.MenuItem;
import br.com.battlebits.commons.translate.Language;
import br.com.battlebits.commons.util.DateUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static br.com.battlebits.commons.translate.TranslateTag.*;

public class MenuAccount extends MenuInventory {

    private UUID accountUuid;
    private Language l;

    public MenuAccount(BattleAccount account, Language l) {
        super(l.tl(MENU_ACCOUNT_TITLE, account.getName()), 6);
        accountUuid = account.getUniqueId();
        this.l = l;
        ItemBuilder builder = ItemBuilder.create(Material.PLAYER_HEAD).name(account.getName());
        ItemStack stack = builder.build();
        SkullMeta meta = (SkullMeta) stack.getItemMeta();
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(account.getUniqueId()));
        stack.setItemMeta(meta);
        MenuItem item = new MenuItem(stack);
        setItem(13, item);
        String clanName = l.tl(MENU_ACCOUNT_DONT_HAVE_TEAM);
        if (account.getTeam() != null)
            clanName = account.getTeam().getName();
        stack = ItemBuilder.create(Material.BLAZE_POWDER).name(ChatColor.GOLD + "" + ChatColor.BOLD + "Team").lore(l.tl(MENU_ACCOUNT_TEAM_NAME, clanName)).build();
        setItem(19, stack);
        stack = ItemBuilder.create(Material.GOLD_INGOT).name(ChatColor.GOLD + "" + ChatColor.BOLD + "BattleMoney").lore(ChatColor.GRAY + "" + account.getBattleMoney()).build();
        setItem(21, stack);
        stack = ItemBuilder.create(Material.EMERALD).name(ChatColor.BOLD + "BattleCoins").lore(ChatColor.GRAY + "" + account.getBattleCoins()).build();
        setItem(22, stack);
        stack = ItemBuilder.create(Material.EXPERIENCE_BOTTLE).name(ChatColor.RED + "" + ChatColor.BOLD + "Xp").lore(ChatColor.GRAY + "" + account.getXp()).build();
        setItem(23, stack);
        builder = ItemBuilder.create(Material.CLOCK).name(l.tl(MENU_ACCOUNT_TIME_INFO));
        Date date = new Date(account.getFirstTimePlaying());
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:SS");
        StringBuilder loreBuilder = new StringBuilder();
        loreBuilder.append("\n" + l.tl(MENU_ACCOUNT_FIRST_LOGIN, df.format(date)));
        date = new Date(account.getLastLoggedIn());
        loreBuilder.append("\n" + l.tl(MENU_ACCOUNT_LAST_LOGIN, df.format(date)));
        loreBuilder.append("\n" + l.tl(MENU_ACCOUNT_TOTAL_ONLINE, DateUtils.formatDifference(l, account.getOnlineTime() / 1000)) + "\n");
        if (account.isOnline()) {
            date = new Date(System.currentTimeMillis() - account.getJoinTime());
            loreBuilder.append("\n" + l.tl(MENU_ACCOUNT_CURRENT_ONLINE, DateUtils.formatDifference(l, (System.currentTimeMillis() - account.getJoinTime()) / 1000)));
        }
        builder.lore(loreBuilder.toString());
        setItem(25, builder.build());

        setItem(49, ItemBuilder.create(Material.ENDER_EYE).name(l.tl(MENU_ACCOUNT_ACTUAL_GROUP, Tag.valueOf(account.getServerGroup().toString()).getPrefix())).build());

        setItem(51, new MenuItem(ItemBuilder.create(Material.BARRIER).name(l.tl(MENU_ACCOUNT_BANS)).lore(l.tl(MENU_ACCOUNT_BANS_LORE)).build(), new MenuClickHandler() {

            @Override
            public void onClick(Player p, Inventory inv, ClickType type, ItemStack stack, int slot) {
                // TODO Open Ban Menu
            }
        }));

        setItem(52, new MenuItem(ItemBuilder.create(Material.WRITABLE_BOOK).name(l.tl(MENU_ACCOUNT_MUTES)).lore(l.tl(MENU_ACCOUNT_MUTES_LORE)).build(), new MenuClickHandler() {

            @Override
            public void onClick(Player p, Inventory inv, ClickType type, ItemStack stack, int slot) {
                // TODO Open Mute Menu
            }
        }));
        ItemStack nullItem = ItemBuilder.create(Material.GLASS_PANE).durability(15).name(" ").build();
        for (int i = 0; i < getInventory().getSize(); i++) {
            if (getItem(i) == null)
                setItem(i, nullItem);
        }
    }

    @Override
    public void open(Player p) {
        if (p.getUniqueId().equals(accountUuid))
            setItem(47, new MenuItem(ItemBuilder.create(Material.COMPARATOR).name(l.tl(MENU_ACCOUNT_SETTINGS)).lore(l.tl(MENU_ACCOUNT_SETTINGS_LORE)).build(), new MenuClickHandler() {
                @Override
                public void onClick(Player p, Inventory inv, ClickType type, ItemStack stack, int slot) {
                    // TODO Open Preferences menu
                }
            }));
        else
            setItem(47, ItemBuilder.create(Material.GLASS_PANE).durability(15).name(" ").build());
        super.open(p);
    }
}
