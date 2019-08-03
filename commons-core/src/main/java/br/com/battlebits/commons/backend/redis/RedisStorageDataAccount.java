package br.com.battlebits.commons.backend.redis;

import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.backend.DataAccount;
import br.com.battlebits.commons.backend.model.ModelAccount;
import com.google.gson.*;
import lombok.NonNull;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RedisStorageDataAccount implements DataAccount {

    private static final String PLAYER_KEY = "battleAccount";

    @NonNull
    private final Jedis jedis;
    private final JsonParser jsonParser;
    private final Gson gson;

    public RedisStorageDataAccount(RedisDatabase redisDatabase) {
        this.jedis = redisDatabase.getJedisPool().getResource();
        this.jsonParser = new JsonParser();
        this.gson = new GsonBuilder().create();
    }

    @Override
    public ModelAccount getAccount(UUID uuid) {
        ModelAccount player;
        if (!jedis.exists(PLAYER_KEY + uuid.toString())) {
            return null;
        }
        Map<String, String> fields = jedis.hgetAll(PLAYER_KEY + uuid.toString());
        if (fields == null || fields.isEmpty() || fields.size() < 40) {
            return null;
        }
        JsonObject obj = new JsonObject();
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            obj.add(entry.getKey(), this.jsonParser.parse(entry.getValue()));
        }
        player = this.gson.fromJson(obj.toString(), ModelAccount.class);
        return player;
    }

    @Override
    public void saveAccount(BattleAccount account) {
        JsonObject jsonObject = this.jsonParser.parse(this.gson.toJson(account)).getAsJsonObject();
        Map<String, String> playerElements = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            playerElements.put(entry.getKey(), this.gson.toJson(entry.getValue()));
        }
        jedis.hmset(PLAYER_KEY + account.getUniqueId().toString(), playerElements);
    }

    @Override
    public void saveAccount(BattleAccount account, String field) {
        // TODO redisSave
    }

    @Override
    public void saveConfiguration(BattleAccount account, String field) {
        // TODO redisSaveConfig
    }
}
