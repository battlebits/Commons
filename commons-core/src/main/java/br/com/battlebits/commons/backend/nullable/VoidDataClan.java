package br.com.battlebits.commons.backend.nullable;

import br.com.battlebits.commons.backend.DataTeam;
import br.com.battlebits.commons.team.Team;

import java.util.UUID;

public class VoidDataTeam implements DataTeam {

    @Override
    public Team getTeam(UUID uniqueId) {
        return null;
    }

    @Override
    public void saveTeam(Team team) {

    }

    @Override
    public void saveTeam(Team team, String fieldName) {

    }
}
