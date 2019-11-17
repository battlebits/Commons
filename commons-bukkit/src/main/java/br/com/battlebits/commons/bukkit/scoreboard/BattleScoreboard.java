package br.com.battlebits.commons.bukkit.scoreboard;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.bukkit.scoreboard.modules.Line;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class BattleScoreboard {

    private static final int MAX_LINES = 16;
    private static final int MAX_PREFIX_LENGTH = 16;
    private static final int MAX_NAME_LENGTH = 16;
    private static final int MAX_SUFFIX_LENGTH = 16;

    private Player player;
    private List<Line> lines;
    private Scoreboard scoreboard;
    private Objective objective;
    private Consumer<List<Line>> lineConsumer;

    private Set<Line> previousLines;

    public BattleScoreboard(Player player, String displayName) {
        this.player = player;
        this.lines = new ArrayList<>();
        this.previousLines = new HashSet<>();
        try {
            this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            this.objective = this.scoreboard.registerNewObjective(displayName, "dummy");
            this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        } catch (NullPointerException e) {
            Commons.getLogger().warning("No world was loaded");
        }
    }

    public Player getPlayer() {
        return this.player;
    }

    public List<Line> getLines() {
        return this.lines;
    }

    public void setDisplayName(String name) {
        this.objective.setDisplayName(name);
    }

    public void addLine(Line line) {
        Validate.notNull(line, "line can't be null.");
        this.lines.add(line);
    }

    public void addLine(String id, String text) {
        this.lines.add(new Line(id, text));
    }

    public void update() {
        int size = Math.min(MAX_LINES, lines.size());
        AtomicInteger count = new AtomicInteger();
        if (lineConsumer != null) {
            lineConsumer.accept(lines);
        }
        Set<Line> adding = new HashSet<>();
        lines.forEach(line -> {
            String name = line.getName();
            if (name.length() > MAX_NAME_LENGTH) {
                name = name.substring(0, MAX_NAME_LENGTH);
            }
            Team team = scoreboard.getTeam(name);
            if (team == null) team = scoreboard.registerNewTeam(name);
            if (line.getPrefix() != null) {
                team.setPrefix(line.getPrefix().length() > MAX_PREFIX_LENGTH ?
                        line.getPrefix().substring(0,
                                MAX_PREFIX_LENGTH) : line.getPrefix());
            }
            if (line.getSuffix() != null) {
                team.setSuffix(line.getSuffix().length() > MAX_SUFFIX_LENGTH ?
                        line.getSuffix().substring(0,
                                MAX_SUFFIX_LENGTH) : line.getSuffix());
            }
            if (!team.hasEntry(line.getId())) {
                team.addEntry(line.getId());
            }
            objective.getScore(line.getId()).setScore(size - count.getAndIncrement());
            adding.add(line);
        });
        previousLines.removeIf(adding::contains);
        for (Line last : previousLines) {
            scoreboard.resetScores(last.getId());
            Team team = scoreboard.getTeam(last.getName());
            if (team != null) {
                team.removeEntry(last.getId());
                team.unregister();
            }
        }
        previousLines = adding;
    }

    public void update(Consumer<BattleScoreboard> consumer) {
        consumer.accept(this);
        this.update();
    }

    public Line getLineById(String id) {
        return this.lines.stream().filter(line -> line.getName().equals(id)).findFirst().orElse(new Line(id, ""));
    }

    public BattleScoreboard setLineConsumer(Consumer<List<Line>> consumer) {
        this.lineConsumer = consumer;
        return this;
    }

    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    public Objective getObjective() {
        return this.objective;
    }
}