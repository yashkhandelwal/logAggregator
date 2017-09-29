package com.log.aggregator.consumer;

/**
 * Created by yashkhandelwal
 */
public abstract class AbstractConsumingThread extends Thread {

    protected final LogConsumer logConsumer;

    public AbstractConsumingThread(LogConsumer logConsumer) {
        this.logConsumer = logConsumer;
    }

    public void run() {
        doRun();
    }

    protected abstract void doRun();

}
