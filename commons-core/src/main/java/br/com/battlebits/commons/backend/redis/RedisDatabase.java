package br.com.battlebits.commons.backend.redis;

import br.com.battlebits.commons.backend.Database;
import lombok.Getter;
import lombok.NonNull;
import redis.clients.jedis.JedisPool;

public class RedisDatabase implements Database {

    @Getter
    private JedisPool jedisPool;
    @NonNull
    private final String hostName;
    private final int port;

    public RedisDatabase(@NonNull String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
    }

    @Override
    public void connect() {
        this.jedisPool = new JedisPool(this.hostName, this.port);
    }

    @Override
    public void disconnect() {
        this.jedisPool.close();
    }

    @Override
    public boolean isConnected() {
        return !this.jedisPool.isClosed();
    }

}
