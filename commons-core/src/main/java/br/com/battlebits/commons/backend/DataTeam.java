package br.com.battlebits.commons.backend;

import br.com.battlebits.commons.team.Team;

public interface DataTeam {

    Team getTeam();

    void saveTeam(Team team);

    void saveTeam(Team team, String fieldName);
}
