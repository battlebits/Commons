package br.com.battlebits.commons.backend.nullable;

import br.com.battlebits.commons.backend.DataTeam;
import br.com.battlebits.commons.team.Team;

public class VoidDataTeam implements DataTeam {
    @Override
    public Team getTeam() {
        return null;
    }

    @Override
    public void saveTeam(Team team) {

    }

    @Override
    public void saveTeam(Team team, String fieldName) {

    }
}
