package br.com.battlebits.commons.bukkit.scoreboard.tagmanager;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.account.Tag;
import br.com.battlebits.commons.bukkit.BukkitMain;
import br.com.battlebits.commons.bukkit.account.BukkitAccount;
import br.com.battlebits.commons.bukkit.event.account.PlayerChangeTagEvent;
import br.com.battlebits.commons.bukkit.scoreboard.ScoreboardAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TagListener implements Listener {
    private TagManager manager;

    public TagListener(TagManager manager) {
        this.manager = manager;
        for (Player p : Bukkit.getOnlinePlayers()) {
            BattleAccount player = Commons.getAccount(p.getUniqueId());
            if (player == null)
                continue;
            player.setTag(player.getTag());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        manager.removePlayerTag(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoinListener(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        BukkitAccount account = BukkitAccount.getAccount(e.getPlayer().getUniqueId());
        if (account == null)
            return;
        if (!BukkitMain.getInstance().isTagControlEnabled())
            return;
        account.setTag(account.getTag());
        for (Player o : Bukkit.getOnlinePlayers()) {
            if (!o.getUniqueId().equals(p.getUniqueId())) {
                BukkitAccount bp = BukkitAccount.getAccount(o.getUniqueId());
                if (bp == null)
                    continue;
                String id = getTeamName(bp.getTag());
                String tag = bp.getTag().getPrefix();
                ScoreboardAPI.joinTeam(ScoreboardAPI.createTeamIfNotExistsToPlayer(p, id,
                        tag + (ChatColor.stripColor(tag).trim().length() > 0 ? " " : ""), ""), o);
            }
        }
    }

    @EventHandler
    public void onPlayerChangeTagListener(PlayerChangeTagEvent e) {
        Player p = e.getPlayer();
        if (!BukkitMain.getInstance().isTagControlEnabled())
            return;
        if (p == null) {
            return;
        }
        BukkitAccount account = BukkitAccount.getAccount(p.getUniqueId());
        if (account == null)
            return;
        String id = getTeamName(e.getNewTag());
        String oldId = getTeamName(e.getOldTag());
        for (final Player o : Bukkit.getOnlinePlayers()) {
            try {
                BukkitAccount bp = BukkitAccount.getAccount(o.getUniqueId());
                if (bp == null)
                    continue;
                String tag = e.getNewTag().getPrefix();
                ScoreboardAPI.leaveTeamToPlayer(o, oldId, p);
                ScoreboardAPI.joinTeam(ScoreboardAPI.createTeamIfNotExistsToPlayer(o, id,
                        tag + (ChatColor.stripColor(tag).trim().length() > 0 ? " " : ""), ""), p);
            } catch (Exception e2) {
            }
        }
    }

    private static char[] chars = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public static String getTeamName(Tag tag) {
        return chars[tag.ordinal()] + "";
    }

}