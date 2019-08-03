package br.com.battlebits.commons.backend.sql;

import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.backend.DataAccount;
import br.com.battlebits.commons.backend.model.ModelAccount;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class SqlStorageDataAccount implements DataAccount {

    private final SqlDatabase database;

    @Override
    public ModelAccount getAccount(UUID uuid) {
        return null;
    }

    @Override
    public void saveAccount(BattleAccount account) {

    }

    @Override
    public void saveAccount(BattleAccount account, String field) {

    }

    @Override
    public void saveConfiguration(BattleAccount account, String field) {

    }
}
