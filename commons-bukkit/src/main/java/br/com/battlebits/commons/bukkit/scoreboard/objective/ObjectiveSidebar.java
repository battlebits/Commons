package br.com.battlebits.commons.bukkit.scoreboard.objective;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Arquivo criado em 07/06/17.
 * Desenvolvido por:
 *
 * @author Luãn Pereira
 */
public class ObjectiveSidebar extends ObjectiveBase {

    private Map<Integer, Score> scores = new HashMap<>();

    public ObjectiveSidebar(Scoreboard scoreboard) {
        super(scoreboard, DisplaySlot.SIDEBAR, "");
    }

    public void setScore(int index, String text) {
        Preconditions.checkArgument(index > 0 && index < 16, "Parameter 'index' must be between 1 and 15");
        if (text != null && !text.isEmpty()) {
            Score score = scores.computeIfAbsent(index, v -> new Score(index));
            if (!text.equals(score.getText())) {
                Team team = getScoreboard().getTeam(score.getTeam());
                if (team == null) {
                    getObjective().getScore(score.getEntry()).setScore(index);
                    team = getScoreboard().registerNewTeam(score.getTeam());
                    if (!team.hasEntry(score.getEntry()))
                        team.addEntry(score.getEntry());
                }
                Iterator<String> iterator = Splitter.fixedLength(16).split(text).iterator();
                String prefix = iterator.next();
                if (prefix.endsWith("§")) {
                    prefix = cutStr(prefix);
                    if (!prefix.equals(team.getPrefix()))
                        team.setPrefix(prefix);
                    if (iterator.hasNext()) {
                        String suffix = cutStr(iterator.next());
                        if (!suffix.equals(team.getSuffix()))
                            team.setSuffix(suffix);
                    } else if (!"".equals(team.getSuffix())) {
                        team.setSuffix("");
                    }
                } else if (iterator.hasNext()) {
                    String suffix = iterator.next();
                    if (!suffix.startsWith("§"))
                        suffix = cutStr(ChatColor.getLastColors(prefix) + suffix);
                    if (!prefix.equals(team.getPrefix()))
                        team.setPrefix(prefix);
                    if (!suffix.equals(team.getSuffix()))
                        team.setSuffix(suffix);
                } else {
                    if (!prefix.equals(team.getPrefix()))
                        team.setPrefix(prefix);
                    if (!"".equals(team.getSuffix()))
                        team.setSuffix("");
                }
                score.setText(text);
            }
        }
    }

    public void setScores(List<String> scores) {
        for (int p = 0, i = 15; i > 0; i--) {
            if (i <= scores.size()) {
                setScore(i, scores.get(p++));
            } else {
                unregisterScore(i);
            }
        }
    }

    public void unregisterScore(int index) {
        Preconditions.checkArgument(index > 0 && index < 16, "Parameter 'index' must be between 1 and 15");
        if (scores.containsKey(index)) {
            Score score = scores.remove(index);
            getScoreboard().resetScores(score.getEntry());
            Team team = getScoreboard().getTeam(score.getTeam());
            if (team != null) team.unregister();
        }
    }

    public void unregisterScores() {
        for (int i = 15; i > 0; i--) {
            unregisterScore(i);
        }
    }

    protected String cutStr(String string) {
        int length = string.length();
        if (length > 16)
            string = string.substring(0, 16);
        if (string.endsWith("§"))
            string = string.substring(0, length-1);
        return string;
    }

    @Getter
    protected class Score {

        @Setter
        private String text;
        private String team;
        private String entry;

        private int index;

        public Score(int index) {
            this.team = "score-" + (index < 10 ? "0" : "") + index;
            ChatColor color = ChatColor.values()[index];
            this.entry = color.toString() + ChatColor.RESET;
            this.index = index;
        }

        @Override
        protected void finalize() throws Throwable {
            text = null;
            team = null;
            entry = null;
            super.finalize();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        scores.clear();
        scores = null;
        super.finalize();
    }

}
