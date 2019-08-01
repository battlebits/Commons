package br.com.battlebits.commons.backend.mongodb;

import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.backend.mongodb.pojo.ModelAccount;

public class Conversor {

    public static ModelAccount convertAccountToModel(BattleAccount account) {
        return new ModelAccount();
    }

    public static BattleAccount convertModelToAccount(ModelAccount model) {
        return new BattleAccount();
    }

}
