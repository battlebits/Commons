package br.com.battlebits.commons.bukkit.scoreboard.objective;

import lombok.Getter;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ObjectiveBase {

    @Getter
    private Objective objective;

    @Getter
    private Scoreboard scoreboard;

    public ObjectiveBase(Scoreboard scoreboard, DisplaySlot slot, String displayName) {
        this.objective = scoreboard.registerNewObjective(slot.name(), "dummy", displayName);
        this.objective.setDisplaySlot(slot);
        this.scoreboard = scoreboard;
    }

    public void setDisplayName(String name) {
        if (name != null && !name.isEmpty()) {
            if (!name.equals(objective.getDisplayName())) {
                objective.setDisplayName(name);
            }
        }
    }

}
