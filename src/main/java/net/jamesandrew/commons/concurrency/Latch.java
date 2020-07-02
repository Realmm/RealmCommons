package net.jamesandrew.commons.concurrency;

import java.util.concurrent.CountDownLatch;

public class Latch extends CountDownLatch {

    public Latch() {
        super(1);
    }

    @Override
    public void await() {
        try {
            super.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
