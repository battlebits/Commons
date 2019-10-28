package br.com.battlebits.commons.bukkit.api.vanish;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.account.Group;
import br.com.battlebits.commons.bukkit.BukkitMain;
import br.com.battlebits.commons.bukkit.event.vanish.PlayerHideToPlayerEvent;
import br.com.battlebits.commons.bukkit.event.vanish.PlayerShowToPlayerEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class VanishAPI {
    private HashMap<UUID, Group> vanishedToGroup;

    private final static VanishAPI instance = new VanishAPI();

    public VanishAPI() {
        vanishedToGroup = new HashMap<>();
    }

    public void setPlayerVanishToGroup(Player player, Group group) {
        if (group == null)
            vanishedToGroup.remove(player.getUniqueId());
        else
            vanishedToGroup.put(player.getUniqueId(), group);
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.getUniqueId().equals(player.getUniqueId()))
                continue;
            BattleAccount onlineP = Commons.getAccount(online.getUniqueId());
            if (group != null && onlineP.getServerGroup().ordinal() < group.ordinal()) {
                callHideToPlayerEvent(online, player);
                continue;
            }
            callShowToPlayerEvent(online, player);
        }
    }

    public void updateVanishToPlayer(Player player) {
        BattleAccount bP = Commons.getAccount(player.getUniqueId());
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.getUniqueId().equals(player.getUniqueId()))
                continue;
            Group group = vanishedToGroup.get(online.getUniqueId());
            if (group != null) {
                if (bP.getServerGroup().ordinal() < group.ordinal()) {
                    callHideToPlayerEvent(player, online);
                    continue;
                }
            }
            callShowToPlayerEvent(player, online);
        }
    }

    private void callShowToPlayerEvent(Player player, Player toShow) {
        PlayerShowToPlayerEvent event = new PlayerShowToPlayerEvent(player, toShow);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            if (player.canSee(toShow))
                player.hidePlayer(BukkitMain.getInstance(), toShow);
        } else if (!player.canSee(toShow))
            player.showPlayer(BukkitMain.getInstance(), toShow);
    }

    private void callHideToPlayerEvent(Player player, Player toHide) {
        PlayerHideToPlayerEvent event = new PlayerHideToPlayerEvent(player, toHide);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            if (!player.canSee(toHide))
                player.showPlayer(BukkitMain.getInstance(), toHide);
        } else if (player.canSee(toHide))
            player.hidePlayer(BukkitMain.getInstance(), toHide);
    }

    public Group hidePlayer(Player player) {
        BattleAccount bA = Commons.getAccount(player.getUniqueId());
        setPlayerVanishToGroup(player, bA.getServerGroup());
        return bA.getServerGroup().ordinal() - 1 >= 0 ? Group.values()[bA.getServerGroup().ordinal() - 1]
                : Group.DEFAULT;
    }

    public void showPlayer(Player player) {
        setPlayerVanishToGroup(player, null);
    }

    public void updateVanish(Player player) {
        setPlayerVanishToGroup(player, getVanishedToGroup(player.getUniqueId()));
    }

    public Group getVanishedToGroup(UUID uuid) {
        return vanishedToGroup.get(uuid);
    }

    public void removeVanish(Player p) {
        vanishedToGroup.remove(p.getUniqueId());
    }

    public static VanishAPI getInstance() {
        return instance;
    }
}
