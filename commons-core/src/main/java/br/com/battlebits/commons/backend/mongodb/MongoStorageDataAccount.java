package br.com.battlebits.commons.backend.mongodb;

import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.backend.DataAccount;
import br.com.battlebits.commons.backend.mongodb.pojo.ModelAccount;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import java.util.UUID;

public class MongoStorageDataAccount implements DataAccount {

    private MongoCollection<ModelAccount> collection;

    public MongoStorageDataAccount(MongoDatabase storage) {
        com.mongodb.client.MongoDatabase database = storage.getDb();
        collection = database.getCollection("account", ModelAccount.class);
    }

    @Override
    public BattleAccount getAccount(UUID uuid) {
        ModelAccount account = collection.find(Filters.eq("_id", uuid)).first();
        if(account == null)
            return null;
        return new BattleAccount(account);
    }

    @Override
    public void saveAccount(BattleAccount account) {
        collection.insertOne(new ModelAccount(account));
    }
}
