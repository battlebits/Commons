package br.com.battlebits.commons.clan;

import br.com.battlebits.commons.Commons;

import java.util.*;
import java.util.logging.Level;

public class ClanCommon {

    /**
     * Sistema de Clans que ir� fazer mais jogadores entrar no Battle para criar
     * seus pr�prios clans e continuar a jogar no Battle
     * <p>
     * Sistema de clans do Battle, cada clan possui informa��es para
     * rankeamento, etc
     */

    private Map<UUID, Clan> clans;

    public ClanCommon() {
        clans = new HashMap<>();
    }

    public void loadClan(Clan clan) {
        Objects.requireNonNull(clan, "clan can't be null.");
        clans.put(clan.getUniqueId(), clan);
    }

    public void unloadClan(UUID uniqueId) {
        if (!clans.containsKey(uniqueId)) {
            Commons.getLogger().log(Level.SEVERE, "Clan " + uniqueId.toString() + " not found.");
            return;
        }
        clans.remove(uniqueId);
    }

    public Clan getClan(UUID uniqueId) {
        return clans.get(uniqueId);
    }

    public Clan getClan(String clanName) {
        return getClans().stream().filter(clan -> clan.getName().equalsIgnoreCase(clanName)).findFirst().orElse(null);
    }

    public Collection<Clan> getClans() {
        return clans.values();
    }
}
