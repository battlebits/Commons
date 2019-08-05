package br.com.battlebits.commons.bukkit.scoreboard.objective;

import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;

public class ObjectiveBelowName extends ObjectiveBase {

    public ObjectiveBelowName(Scoreboard scoreboard) {
        super(scoreboard, DisplaySlot.BELOW_NAME, "");
    }

    public void setScore(String name, int score) {
        getObjective().getScore(name).setScore(score);
    }

    public void unregisterScore(String name) {
        getScoreboard().resetScores(name);
    }

}
