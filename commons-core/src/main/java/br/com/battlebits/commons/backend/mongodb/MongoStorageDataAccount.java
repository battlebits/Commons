package br.com.battlebits.commons.backend.mongodb;

import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.backend.DataAccount;
import br.com.battlebits.commons.backend.model.ModelAccount;
import com.google.gson.JsonObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.UUID;

import static br.com.battlebits.commons.util.json.JsonUtils.elementToBson;
import static br.com.battlebits.commons.util.json.JsonUtils.jsonTree;

public class MongoStorageDataAccount implements DataAccount {

    private MongoCollection<ModelAccount> collection;

    public MongoStorageDataAccount(MongoDatabase storage) {
        com.mongodb.client.MongoDatabase database = storage.getDb();
        collection = database.getCollection("account", ModelAccount.class);
    }

    /**
     *  Used to receive account information
     * @param uuid
     * @return battlePlayer without sendMessage()
     */
    @Override
    public ModelAccount getAccount(UUID uuid) {
        ModelAccount account = collection.find(Filters.eq("_id", uuid)).first();
        return account;
    }

    @Override
    public void saveAccount(BattleAccount account) {
        collection.insertOne(new ModelAccount(account));
    }

    @Override
    public void saveAccount(BattleAccount account, String fieldName) {
        try {
            ModelAccount model = new ModelAccount(account);
            JsonObject object = jsonTree(model);
            if (object.has(fieldName)) {
                Object value = elementToBson(object.get(fieldName));
                collection.updateOne(Filters.eq("_id", account.getUniqueId()),
                        new Document("$set", new Document(fieldName, value)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveConfiguration(BattleAccount account, String fieldName) {
        try {
            JsonObject object = jsonTree(account.getConfiguration());
            if (object.has(fieldName)) {
                Object value = elementToBson(object.get(fieldName));
                collection.updateOne(Filters.eq("_id", account.getUniqueId()),
                        new Document("$set", new Document("configuration." + fieldName, value)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
