package com.log.aggregator.consumer;

/**
 * Created by yashkhandelwal
 */
public class LogConsumerService {

    private volatile LogConsumer logConsumer;

    private LogConsumer initializeConsumer(String destination) {
        if (logConsumer == null) {
            synchronized (this) {
                if (logConsumer == null) {
                    logConsumer = new LogConsumer(destination);
                }
            }
        }
        return logConsumer;
    }

    public void startLogConsumer(String destination) {
        initializeConsumer(destination).doTask();
    }

    public void stopConsumer() {
        if (logConsumer != null) {
            logConsumer.stop();
            logConsumer = null;
        }
    }
}
