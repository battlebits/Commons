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
import java.util.Map;

public class VanishAPI {
    private static Map<Player, Group> vanishedToGroup = new HashMap<>();


    public static void setPlayerVanishToGroup(Player player, Group group) {
        if (group == null)
            vanishedToGroup.remove(player);
        else
            vanishedToGroup.put(player, group);
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.equals(player))
                continue;
            BattleAccount onlineP = Commons.getAccount(online.getUniqueId());
            if (group != null && onlineP.getServerGroup().ordinal() < group.ordinal()) {
                callHideToPlayerEvent(online, player);
                continue;
            }
            callShowToPlayerEvent(online, player);
        }
    }

    public static void updateVanishToPlayer(Player player) {
        BattleAccount bP = Commons.getAccount(player.getUniqueId());
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.equals(player))
                continue;
            Group group = vanishedToGroup.get(online);
            if (group != null) {
                if (bP.getServerGroup().ordinal() < group.ordinal()) {
                    callHideToPlayerEvent(player, online);
                    continue;
                }
            }
            callShowToPlayerEvent(player, online);
        }
    }

    private static void callShowToPlayerEvent(Player player, Player toShow) {
        PlayerShowToPlayerEvent event = new PlayerShowToPlayerEvent(player, toShow);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            if (player.canSee(toShow))
                player.hidePlayer(BukkitMain.getInstance(), toShow);
        } else if (!player.canSee(toShow))
            player.showPlayer(BukkitMain.getInstance(), toShow);
    }

    private static void callHideToPlayerEvent(Player player, Player toHide) {
        PlayerHideToPlayerEvent event = new PlayerHideToPlayerEvent(player, toHide);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            if (!player.canSee(toHide))
                player.showPlayer(BukkitMain.getInstance(), toHide);
        } else if (player.canSee(toHide))
            player.hidePlayer(BukkitMain.getInstance(), toHide);
    }

    public static Group hidePlayer(Player player) {
        BattleAccount bA = Commons.getAccount(player.getUniqueId());
        setPlayerVanishToGroup(player, bA.getServerGroup());
        return bA.getServerGroup().ordinal() - 1 >= 0 ? Group.values()[bA.getServerGroup().ordinal() - 1]
                : Group.DEFAULT;
    }

    public static void showPlayer(Player player) {
        setPlayerVanishToGroup(player, null);
    }

    public static Group getVanishedToGroup(Player player) {
        return vanishedToGroup.get(player);
    }

    public static void removeVanish(Player p) {
        vanishedToGroup.remove(p);
    }

}
