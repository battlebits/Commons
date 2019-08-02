package br.com.battlebits.commons.backend;

import br.com.battlebits.commons.account.BattleAccount;

import java.util.UUID;

/**
 * Handle the save/load of accounts
 */
public interface DataAccount {

    /**
     * Handle the account creation and store
     * @param uuid
     * @return new/current account
     */
    BattleAccount getAccount(UUID uuid);

    /**
     * Save a full account
     * @param account
     */
    void saveAccount(BattleAccount account);

    /**
     * Save a field account
     * @param account
     * @param field
     */
    void saveAccount(BattleAccount account, String field);

    /**
     * Save a field from account configuration
     * @param account
     * @param field
     */
    void saveConfiguration(BattleAccount account, String field);
}
