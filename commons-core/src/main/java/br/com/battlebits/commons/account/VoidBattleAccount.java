package br.com.battlebits.commons.account;

import br.com.battlebits.commons.backend.model.ModelAccount;

public class VoidBattleAccount extends BattleAccount {
    public VoidBattleAccount(ModelAccount account) {
        super(account);
    }

    @Override
    public void sendMessage(String tag, Object... objects) {

    }
}
