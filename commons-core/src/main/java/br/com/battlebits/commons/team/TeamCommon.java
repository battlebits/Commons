package br.com.battlebits.commons.team;

import br.com.battlebits.commons.Commons;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

public class TeamCommon {

    /**
     * Sistema de teams do Battle
     */

    private HashMap<UUID, Team> teams;

    public TeamCommon() {
        teams = new HashMap<>();
    }

    public void loadTeam(Team team) {
        teams.put(team.getUniqueId(), team);
    }

    public void unloadTeam(UUID uniqueId) {
        if (teams.containsKey(uniqueId))
            teams.remove(uniqueId);
        else
            Commons.getLogger().log(Level.SEVERE, "Couldn't find team " + uniqueId.toString());
    }

    public Team getTeam(UUID uniqueId) {
        return teams.get(uniqueId);
    }

    public Team getTeam(String teamName) {
        for (Team team : teams.values()) {
            if (team.getName().equalsIgnoreCase(teamName))
                return team;
        }
        return null;
    }

    public Collection<Team> getTeams() {
        return teams.values();
    }

}
