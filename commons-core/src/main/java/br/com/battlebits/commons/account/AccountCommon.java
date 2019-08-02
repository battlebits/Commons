package br.com.battlebits.commons.account;

import br.com.battlebits.commons.Commons;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

public class AccountCommon {

    private HashMap<UUID, BattleAccount> accounts;

    public AccountCommon() {
        accounts = new HashMap<>();
    }

    public void loadBattlePlayer(UUID uuid, BattleAccount player) {
        accounts.put(uuid, player);
    }

    public BattleAccount getBattlePlayer(UUID uuid) {
        if (!accounts.containsKey(uuid)) {
            return null;
        }
        return accounts.get(uuid);
    }

    public void unloadBattlePlayer(UUID uuid) {
        if (accounts.containsKey(uuid))
            accounts.remove(uuid);
        else
            Commons.getLogger().log(Level.SEVERE, "NAO FOI POSSIVEL ENCONTRAR PLAYER " + uuid.toString());
    }

    public Collection<BattleAccount> getPlayers() {
        return accounts.values();
    }
}
