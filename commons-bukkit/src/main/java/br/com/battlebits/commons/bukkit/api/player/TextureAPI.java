package br.com.battlebits.commons.bukkit.api.player;

import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.TimeUnit;

public class TextureAPI {
    public static final LoadingCache<WrappedGameProfile, WrappedSignedProperty> Textures = CacheBuilder.newBuilder()
            .expireAfterWrite(30L, TimeUnit.MINUTES)
            .build(new CacheLoader<WrappedGameProfile, WrappedSignedProperty>() {
                @Override
                public WrappedSignedProperty load(WrappedGameProfile key) throws Exception {
                    return loadTextures(key);
                }
            });

    private static final WrappedSignedProperty loadTextures(WrappedGameProfile profile) {
        // TODO Textures
        return null;
    }
}
