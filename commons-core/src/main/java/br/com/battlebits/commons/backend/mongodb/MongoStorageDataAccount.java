package br.com.battlebits.commons.backend.mongodb;

import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.backend.DataAccount;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import java.util.UUID;

public class MongoStorageDataAccount implements DataAccount {

    private MongoCollection<BattleAccount> collection;

    public MongoStorageDataAccount(MongoDatabase storage) {
        com.mongodb.client.MongoDatabase database = storage.getDb();
        collection = database.getCollection("account", BattleAccount.class);
    }

    @Override
    public BattleAccount getAccount(UUID uuid) {
        BattleAccount account = collection.find(Filters.eq("uniqueId", uuid)).first();
        return account;
    }

    @Override
    public void saveAccount(BattleAccount account) {
        collection.insertOne(account);
    }
}
