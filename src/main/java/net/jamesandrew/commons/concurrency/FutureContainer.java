package net.jamesandrew.commons.concurrency;

import java.util.concurrent.CompletableFuture;

public class FutureContainer<T, U> {

    private CompletableFuture<T> future;
    private U identifier;

    public FutureContainer(CompletableFuture<T> future, U identifier) {
        this.future = future;
        this.identifier = identifier;
    }

    public U getIdentifier() {
        return identifier;
    }

    public CompletableFuture<T> getFuture() {
        return future;
    }
}
