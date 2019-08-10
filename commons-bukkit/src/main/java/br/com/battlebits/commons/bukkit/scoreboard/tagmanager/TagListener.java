package br.com.battlebits.commons.bukkit.scoreboard.tagmanager;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.account.Tag;
import br.com.battlebits.commons.bukkit.BukkitMain;
import br.com.battlebits.commons.bukkit.account.BukkitAccount;
import br.com.battlebits.commons.bukkit.event.account.AsyncPlayerChangeTagEvent;
import br.com.battlebits.commons.bukkit.event.account.AsyncPlayerChangedGroupEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

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
        for (Player o : Bukkit.getOnlinePlayers()) {
            BukkitAccount bp = BukkitAccount.getAccount(o.getUniqueId());
            if (bp == null)
                continue;
            joinTeam(p.getScoreboard(), bp.getTag(), o.getName());
            joinTeam(o.getScoreboard(), account.getTag(), p.getName());
        }
    }



    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerChangedGroupEvent(AsyncPlayerChangedGroupEvent event) {
        BukkitAccount bA = event.getBukkitAccount();
        bA.loadTags();
        bA.setTag(bA.getDefaultTag());
    }

    @EventHandler
    public void onPlayerChangeTagListener(AsyncPlayerChangeTagEvent e) {
        Player p = e.getPlayer();
        if (!BukkitMain.getInstance().isTagControlEnabled())
            return;
        if (p == null) {
            return;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                for (final Player o : Bukkit.getOnlinePlayers()) {
                    try {
                        BukkitAccount bp = BukkitAccount.getAccount(o.getUniqueId());
                        if (bp == null)
                            continue;
                        leaveTeam(o.getScoreboard(), e.getOldTag(), p.getName());
                        joinTeam(o.getScoreboard(), e.getNewTag(), p.getName());
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }.runTask(BukkitMain.getInstance());

    }

    private static char[] chars = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    private void joinTeam(Scoreboard board, Tag tag, String name) {
        String id = getTeamName(tag);
        ChatColor color = ChatColor.getByChar(tag.getColor());
        String prefix = color + "" + ChatColor.BOLD + tag.getPrefix();

        Team team = board.getTeam(id);
        if (team == null) {
            team = board.registerNewTeam(id);
        }
        team.setPrefix(prefix + (ChatColor.stripColor(prefix).trim().length() > 0 ? " " : ""));
        team.setSuffix("");
        team.setColor(color);
        if (!team.hasEntry(name)) {
            team.addEntry(name);
        }
    }

    private void leaveTeam(Scoreboard board, Tag tag, String name) {
        String id = getTeamName(tag);
        Team oldTeam = board.getTeam(id);
        if (oldTeam != null) {
            oldTeam.removeEntry(name);
            if (oldTeam.getEntries().isEmpty()) {
                oldTeam.unregister();
            }
        }
    }

    private String getTeamName(Tag tag) {
        return chars[tag.ordinal()] + "";
    }

}
