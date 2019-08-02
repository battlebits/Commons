package br.com.battlebits.commons.bukkit.services.scoreboard;

import br.com.battlebits.commons.bukkit.scoreboard.BattleScoreboard;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public interface ScoreboardService {

    @Nullable
    BattleScoreboard getScoreboard(Player player);

    Stream<BattleScoreboard> stream();
    void removeScoreboard(Player player);
    void setScoreboard(Player player, BattleScoreboard scoreboard);

}
