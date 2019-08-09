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
            String id = getTeamName(bp.getTag());
            String tag = ChatColor.getByChar(bp.getTag().getColor()) + "" + ChatColor.BOLD + bp.getTag().getPrefix();
            Team t = o.getScoreboard().getTeam(id);
            if (t == null) {
                t = o.getScoreboard().registerNewTeam(id);
            }
            t.setPrefix(tag + (ChatColor.stripColor(tag).trim().length() > 0 ? " " : ""));
            t.setSuffix("");
            t.setColor(ChatColor.getByChar(bp.getTag().getColor()));
            t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
            if (!t.getEntries().contains(p.getName())) {
                t.addEntry(p.getName());
            }
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
        BukkitAccount account = BukkitAccount.getAccount(p.getUniqueId());
        if (account == null)
            return;
        String id = getTeamName(e.getNewTag());
        String oldId = getTeamName(e.getOldTag());
        ChatColor color = ChatColor.getByChar(e.getNewTag().getColor());
        String tag = color + "" + ChatColor.BOLD + e.getNewTag().getPrefix();
        new BukkitRunnable() {
            @Override
            public void run() {
                for (final Player o : Bukkit.getOnlinePlayers()) {
                    try {
                        BukkitAccount bp = BukkitAccount.getAccount(o.getUniqueId());
                        if (bp == null)
                            continue;
                        Team oldTeam = o.getScoreboard().getTeam(oldId);
                        if (oldTeam != null) {
                            oldTeam.getEntries().remove(p.getName());
                            if (oldTeam.getEntries().isEmpty()) {
                                oldTeam.unregister();
                            }
                        }
                        Team t = o.getScoreboard().getTeam(id);
                        if (t == null) {
                            t = o.getScoreboard().registerNewTeam(id);
                        }
                        t.setPrefix(tag + (ChatColor.stripColor(tag).trim().length() > 0 ? " " : ""));
                        t.setSuffix("");
                        t.setColor(ChatColor.getByChar(bp.getTag().getColor()));
                        t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
                        if (!t.getEntries().contains(p.getName())) {
                            t.addEntry(p.getName());
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }.runTask(BukkitMain.getInstance());

    }

    private static char[] chars = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public static String getTeamName(Tag tag) {
        return chars[tag.ordinal()] + "";
    }

}
