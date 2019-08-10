package br.com.battlebits.commons.bukkit.listener;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.bukkit.account.BukkitAccount;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuitListener(PlayerQuitEvent e) {
        Scoreboard board = e.getPlayer().getScoreboard();
        if (board != null) {
            for (Team t : board.getTeams()) {
                t.unregister();
            }
            for (Objective ob : board.getObjectives()) {
                ob.unregister();
            }
        }
        e.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoinListener(PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        BukkitAccount account = (BukkitAccount) Commons.getAccount(player.getUniqueId());
        player.setScoreboard(account.getBattleboard().getScoreboard());
    }

}
