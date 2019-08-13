package br.com.battlebits.commons.backend.nullable;

import br.com.battlebits.commons.backend.DataClan;
import br.com.battlebits.commons.backend.DataTeam;
import br.com.battlebits.commons.clan.Clan;
import br.com.battlebits.commons.team.Team;

import java.util.UUID;

public class VoidDataClan implements DataClan {

    @Override
    public Clan getClan(UUID uniqueId) {
        return null;
    }

    @Override
    public void saveClan(Clan clan) {

    }

    @Override
    public void saveClan(Clan team, String fieldName) {

    }
}
