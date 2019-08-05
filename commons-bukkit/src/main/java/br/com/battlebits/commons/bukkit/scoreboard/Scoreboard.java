package br.com.battlebits.commons.bukkit.scoreboard;

import br.com.battlebits.commons.bukkit.scoreboard.objective.ObjectiveBase;
import br.com.battlebits.commons.bukkit.scoreboard.objective.ObjectiveBelowName;
import br.com.battlebits.commons.bukkit.scoreboard.objective.ObjectivePlayerlist;
import br.com.battlebits.commons.bukkit.scoreboard.objective.ObjectiveSidebar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Scoreboard {

    private org.bukkit.scoreboard.Scoreboard scoreboard;
    private ObjectiveBase sidebar, belowName, playerList;

    public Scoreboard(Player player) {
        org.bukkit.scoreboard.Scoreboard playerScoreboard = player.getScoreboard();
        org.bukkit.scoreboard.Scoreboard mainScoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        player.setScoreboard(this.scoreboard = (!playerScoreboard.equals(mainScoreboard) ? Bukkit.getScoreboardManager().getNewScoreboard() : playerScoreboard));
    }

    public ObjectiveSidebar getSidebar() {
        return (ObjectiveSidebar) (sidebar == null ? sidebar = new ObjectiveSidebar(scoreboard) : sidebar);
    }

    public ObjectiveBelowName getBelowName() {
        return (ObjectiveBelowName) (belowName == null ? belowName = new ObjectiveBelowName(scoreboard) : belowName);
    }

    public ObjectivePlayerlist getPlayerlist() {
        return (ObjectivePlayerlist) (playerList == null ? playerList = new ObjectivePlayerlist(scoreboard) : playerList);
    }

    @Override
    protected void finalize() throws Throwable {
        sidebar = null;
        belowName = null;
        playerList = null;
        scoreboard = null;
        super.finalize();
    }

}
