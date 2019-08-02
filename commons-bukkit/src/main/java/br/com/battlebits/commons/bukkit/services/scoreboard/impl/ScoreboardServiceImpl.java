package br.com.battlebits.commons.bukkit.services.scoreboard.impl;

import br.com.battlebits.commons.bukkit.scoreboard.BattleScoreboard;
import br.com.battlebits.commons.bukkit.services.scoreboard.ScoreboardService;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ScoreboardServiceImpl implements ScoreboardService {

    private Map<Player, BattleScoreboard> scoreboardMap;

    public ScoreboardServiceImpl() {
        scoreboardMap = new HashMap<>();
    }

    @Override
    public BattleScoreboard getScoreboard(Player player) {
        return this.scoreboardMap.get(player);
    }

    @Override
    public Stream<BattleScoreboard> stream() {
        return this.scoreboardMap.values().stream();
    }

    @Override
    public void removeScoreboard(Player player) {
        Validate.notNull(player, "player can't be null.");
        this.scoreboardMap.remove(player);
    }

    @Override
    public void setScoreboard(Player player, BattleScoreboard scoreboard) {
        Validate.notNull(player, "player can't be null.");
        Validate.notNull(scoreboard, "scoreboard can't be null.");
        this.scoreboardMap.put(player, scoreboard);
    }
}
