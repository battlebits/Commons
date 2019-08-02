package br.com.battlebits.commons.bukkit.scoreboard;

import br.com.battlebits.commons.bukkit.scoreboard.modules.Line;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;
import java.util.function.Consumer;

public interface BattleScoreboard {

    Player getPlayer();
    List<Line> getLines();

    void setDisplayName(String name);
    void addLine(Line line);
    void addLine(String prefix, String name, String suffix);
    void update();

    BattleScoreboard with(Consumer<BattleScoreboard> consumer);
    BattleScoreboard setLineConsumer(Consumer<List<Line>> consumer);

    Scoreboard getScoreboard();
    Objective getObjective();
}
