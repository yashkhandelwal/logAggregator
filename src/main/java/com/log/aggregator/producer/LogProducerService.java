package com.log.aggregator.producer;

/**
 * Created by yashkhandelwal
 */
public class LogProducerService {

    private volatile LogProducer logProducer;

    private LogProducer initializeProducer(String destination) {
        if (logProducer == null) {
            synchronized (this) {
                if (logProducer == null) {
                    logProducer = new LogProducer(destination);
                }
            }
        }
        return logProducer;
    }

    public void startLogProducer(String destination) {
        initializeProducer(destination).doTask();
    }

    public void stopProducer() {
        if (logProducer != null) {
            logProducer.stop();
            logProducer = null;
        }
    }
}
