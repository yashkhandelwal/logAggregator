package com.log.aggregator.producer;

/**
 * Created by yashkhandelwal
 */
public abstract class AbstractProducingThread extends Thread {
    protected final LogProducer logProducer;

    public AbstractProducingThread(LogProducer logProducer) {
        this.logProducer = logProducer;
    }

    public void run() {
        doRun();
    }

    protected abstract void doRun();
}
