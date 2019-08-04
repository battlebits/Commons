package br.com.battlebits.commons.backend;

import br.com.battlebits.commons.team.Team;

import java.util.UUID;

public interface DataTeam {

    Team getTeam(UUID uniqueId);

    void saveTeam(Team team);

    void saveTeam(Team team, String fieldName);
}
