package br.com.battlebits.commons.backend.mongodb;

import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.backend.mongodb.pojo.ModelAccount;

public class Conversor {

    public static ModelAccount convertAccountToModel(BattleAccount account) {
        return new ModelAccount(account);
    }

    public static BattleAccount convertModelToAccount(ModelAccount model) {
        BattleAccount account = new BattleAccount();

        return account;
    }

}
