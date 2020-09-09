package net.jamesandrew.commons.database.mongo;

import com.mongodb.ServerAddress;
import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.*;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.connection.ClusterSettings;
import com.mongodb.connection.ConnectionPoolSettings;
import net.jamesandrew.commons.concurrency.Latch;
import net.jamesandrew.commons.database.mongo.callback.IDatabaseResultCallback;
import org.bson.Document;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class MongoDB {

//    private static final String PLAYER_DATA_COLLECTION = "player_data";

    private MongoClient client;

    private final String database;
    private final String host;
    private final int port;

    public MongoDB(String database, String host, int port) {
        this.database = database;
        this.host = host;
        this.port = port;
    }

    protected CompletableFuture<Void> initializeDatabase() {
        return CompletableFuture.runAsync(() -> {
            ClusterSettings clusterSettings = ClusterSettings.builder()
                    .hosts(Collections.singletonList(new ServerAddress(host, port)))
                    .build();

            ConnectionPoolSettings connectionPoolSettings = ConnectionPoolSettings.builder()
                    .maxSize(10)
                    .build();

            MongoClientSettings clientSettings = MongoClientSettings.builder()
                    .clusterSettings(clusterSettings)
                    .connectionPoolSettings(connectionPoolSettings)
                    .build();

            this.client = MongoClients.create(clientSettings);
        });
    }

    public void closeDatabase() {
        if (client != null) {
            client.close();
            client = null;
        }
    }

    protected void deleteCollection(String s, IDatabaseResultCallback<Void> callback) {
        getCollection(s).drop(new ErrorOnlyCallBack<Void>() {
            @Override
            public void onResult(Void result) {
                if (callback != null) callback.accept(result);
            }
        });
    }

    protected void createCollection(String s, IDatabaseResultCallback<Void> callback) {
        MongoDatabase database = getDatabase();
        Set<String> list = new HashSet<>();

        Latch latch = new Latch();

        database.listCollectionNames().forEach(list::add, (result, throwable) -> latch.countDown());

        latch.await();


        if (!list.contains(s)) {
            database.createCollection(s,
                    new CreateCollectionOptions().autoIndex(true), new ErrorOnlyCallBack<Void>() {
                        @Override
                        public void onResult(Void result) {
                            if (callback != null) callback.accept(result);
                        }
                    });
        } else {
            if (callback != null) callback.accept(null);
        }
    }

    protected void getDocument(MongoCollection<Document> documents, String key, String id, IDatabaseResultCallback<Document> callback) {
        documents.find(new Document(key, id)).first(new ErrorOnlyCallBack<Document>() {
            @Override
            public void onResult(Document document) {
                if (callback != null) callback.accept(document);
            }
        });
    }

    protected void insert(MongoCollection<Document> documents, Document document, IDatabaseResultCallback<Void> callback) {
        documents.insertOne(document, new ErrorOnlyCallBack<Void>() {
            @Override
            public void onResult(Void aVoid) {
                if (callback != null) callback.accept(aVoid);
            }
        });
    }

    protected void update(MongoCollection<Document> documents, Document toUpdate, Document updated, IDatabaseResultCallback<UpdateResult> callback) {
        Document d = new Document(UpdateBuilder.Operators.SET, updated);
        documents.updateOne(toUpdate, d, new UpdateOptions().upsert(true), new ErrorOnlyCallBack<UpdateResult>() {
            @Override
            public void onResult(UpdateResult result) {
                if (callback != null) callback.accept(result);
            }
        });
    }

    protected void delete(MongoCollection<Document> documents, Document document, IDatabaseResultCallback<DeleteResult> callback) {
        if (document == null) return;
        documents.deleteOne(document, new ErrorOnlyCallBack<DeleteResult>() {
            @Override
            public void onResult(DeleteResult result) {
                if (callback != null) callback.accept(result);
            }
        });
    }

    protected MongoCollection<Document> getCollection(String collection) {
        return getDatabase().getCollection(collection);
    }

    protected MongoDatabase getDatabase() {
        return client.getDatabase(this.database);
    }

    private abstract static class ErrorOnlyCallBack<T> implements SingleResultCallback<T> {
        @Override
        public void onResult(T t, Throwable throwable) {
            if (throwable != null) {
                throwable.printStackTrace();
            }
            onResult(t);
        }

        public abstract void onResult(T result);
    }

    private abstract static class NonErrorOnlyCallBack<T> implements SingleResultCallback<T> {
        @Override
        public final void onResult(T t, Throwable throwable) {
            if (throwable != null) {
                throwable.printStackTrace();
            } else {
                onResult(t);
            }
        }

        public abstract void onResult(T t);
    }

}
