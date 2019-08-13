package br.com.battlebits.commons.backend;

import br.com.battlebits.commons.clan.Clan;
import br.com.battlebits.commons.team.Team;

import java.util.UUID;

public interface DataClan {

    Clan getClan(UUID uniqueId);

    void saveClan(Clan clan);

    void saveClan(Clan team, String fieldName);
}
