package br.com.battlebits.commons.backend.mongodb;

import br.com.battlebits.commons.backend.Database;
import com.mongodb.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@RequiredArgsConstructor
public class MongoDatabase implements Database {

    @Getter
    private MongoClient client;
    @Getter
    private com.mongodb.client.MongoDatabase db;
    @NonNull
    private final String hostname, database, username, password;
    private final int port;

    public MongoDatabase() {
        this("localhost", "commons", "commons", "", 27017);
    }

    @Override
    public void connect() {

        MongoCredential credential = MongoCredential.createCredential(username, database, password.toCharArray());

        ServerAddress address = new ServerAddress(hostname, port);
        MongoClientOptions options = MongoClientOptions.builder().sslEnabled(false).build();
        client = new MongoClient(address, credential, options);

        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        db = client.getDatabase(database).withCodecRegistry(pojoCodecRegistry);
    }

    @Override
    public void disconnect() {
        client.close();
    }

    @Override
    public boolean isConnected() throws Exception {
        return client != null;
    }
}
