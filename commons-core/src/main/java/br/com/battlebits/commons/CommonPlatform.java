package br.com.battlebits.commons;

import java.util.UUID;


public interface CommonPlatform {

    UUID getUUID(String name);

    String getName(UUID uuid);

    boolean isOnline(UUID uuid);

    boolean isOnline(String name);

    <T> T getPlayerExact(String name, Class<T> clazz);

    default Object getPlayerExact(String name) {
        return getPlayerExact(name, Object.class);
    }

    void runAsync(Runnable runnable);

}
