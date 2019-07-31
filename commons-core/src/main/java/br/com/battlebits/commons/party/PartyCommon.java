package br.com.battlebits.commons.party;

import java.util.*;

public class PartyCommon {

    private Map<UUID, Party> partys;

    public PartyCommon() {
        this.partys = new HashMap<>();
    }

    public boolean inParty(UUID uuid) {
        return getParty(uuid) != null;
    }

    public Party getParty(UUID uuid) {
        return partys.values().stream().filter(p -> p.contains(uuid)).findFirst().orElse(null);
    }

    public Party getByOwner(UUID owner) {
        return partys.values().stream().filter(p -> p.getOwner().equals(owner)).findFirst().orElse(null);
    }

    public void loadParty(Party party) {
        partys.put(party.getOwner(), party);
    }

    public void removeParty(Party party) {
        Objects.requireNonNull(party, "Party is null.");
        partys.remove(party.getOwner());
    }

    public void removeParty(UUID owner) {
        Objects.requireNonNull(owner, "UUID is null.");
        partys.remove(owner);
    }

    public Collection<Party> getPartys() {
        return partys.values();
    }

}
