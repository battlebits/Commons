package br.com.battlebits.commons.team;

import br.com.battlebits.commons.Commons;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

public class TeamCommon {

    /**
     * Sistema de Clans que ir§ fazer mais jogadores entrar no Battle para criar
     * seus pr§prios clans e continuar a jogar no Battle
     * <p>
     * Sistema de clans do Battle, cada clan possui informa§§es para
     * rankeamento, etc
     */

    private HashMap<UUID, Team> teams;

    public TeamCommon() {
        teams = new HashMap<>();
    }

    public void loadClan(Team team) {
        teams.put(team.getUniqueId(), team);
    }

    public void unloadClan(UUID uniqueId) {
        if (teams.containsKey(uniqueId))
            teams.remove(uniqueId);
        else
            Commons.getLogger().log(Level.SEVERE, "NAO FOI POSSIVEL ENCONTRAR CLAN " + uniqueId.toString());
    }

    public Team getTeam(UUID uniqueId) {
        return teams.get(uniqueId);
    }

    public Team getTeam(String clanName) {
        for (Team team : teams.values()) {
            if (team.getName().equalsIgnoreCase(clanName))
                return team;
        }
        return null;
    }

    public Collection<Team> getClans() {
        return teams.values();
    }

}
