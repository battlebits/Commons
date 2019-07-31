package br.com.battlebits.commons.backend;

import br.com.battlebits.commons.account.BattleAccount;

import java.util.UUID;

public interface DataAccount {

    public BattleAccount getAccount(UUID uuid);

    public void saveAccount(BattleAccount account);
}
