package br.com.battlebits.commons.tests;

import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.backend.mongodb.MongoDatabase;
import br.com.battlebits.commons.backend.mongodb.MongoStorageDataAccount;

import java.util.UUID;

public class Backend {

    public static void main(String[] args) {
        MongoDatabase db = new MongoDatabase("localhost", "test", "test", "test", 27017);
        db.connect();

        MongoStorageDataAccount dataAccount = new MongoStorageDataAccount(db);

        BattleAccount account = new BattleAccount();
        account.setUniqueId(UUID.randomUUID());
        account.setName("GustavoInacio");

        dataAccount.saveAccount(account);

        BattleAccount acc2 = dataAccount.getAccount(account.getUniqueId());
        acc2.setFakeName("Teste");
        account.setFakeName("Teste2");
        System.out.println(acc2.getFakeName().equals(account.getFakeName()));

        db.disconnect();
    }
}
