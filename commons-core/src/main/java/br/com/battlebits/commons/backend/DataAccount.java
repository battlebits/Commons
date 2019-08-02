package br.com.battlebits.commons.backend;

import br.com.battlebits.commons.account.BattleAccount;

import java.util.UUID;

public interface DataAccount {

    BattleAccount getAccount(UUID uuid);

    void saveAccount(BattleAccount account);
}
