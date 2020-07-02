package net.jamesandrew.commons.database.mongo.callback;

import java.util.concurrent.CountDownLatch;

public abstract class IDatabaseResultCallback<T> {

    private CountDownLatch waiter;

    public final void accept(T value) {
        try {
            onReceived(value);
        } finally {
            if (waiter != null) {
                waiter.countDown();
                waiter.countDown();
            }
        }
    }

    protected abstract void onReceived(T value);

    public final void await() {
        waiter = new CountDownLatch(1);
        try {
            waiter.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
