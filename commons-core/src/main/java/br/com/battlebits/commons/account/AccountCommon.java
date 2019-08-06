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

    public void loadBattleAccount(BattleAccount account) {
        accounts.put(account.getUniqueId(), account);
    }

    public BattleAccount getBattleAccount(UUID uuid) {
        if (!accounts.containsKey(uuid)) {
            return null;
        }
        return accounts.get(uuid);
    }

    public void unloadBattleAccount(UUID uuid) {
        if (accounts.containsKey(uuid))
            accounts.remove(uuid);
        else
            Commons.getLogger().log(Level.SEVERE, "Couldn't find account " + uuid.toString());
    }

    public Collection<BattleAccount> getAccounts() {
        return accounts.values();
    }
}
