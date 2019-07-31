package br.com.battlebits.commons.backend.redis;

import br.com.battlebits.commons.backend.Database;

public class RedisDatabase implements Database {
    @Override
    public void connect() throws Exception {

    }

    @Override
    public void disconnect() throws Exception {

    }

    @Override
    public boolean isConnected() throws Exception {
        return false;
    }
}
