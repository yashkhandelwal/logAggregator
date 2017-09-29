package com.log.aggregator.consumer;

import com.google.gson.Gson;
import com.log.aggregator.utils.LogThreadState;
import com.log.aggregator.utils.QDestination;
import com.log.aggregator.utils.QObject;

/**
 * Created by yashkhandelwal
 */
public class FileWriterConsumer extends AbstractConsumingThread {
    public FileWriterConsumer(LogConsumer logConsumer) {
        super(logConsumer);
    }

    @Override
    protected void doRun() {
        Gson gson = new Gson();
        do {
            QDestination qDestination = QDestination.getDestinationOrNull(logConsumer.getName());
            System.out.println("FileWriteConsumer: Reading Message From Queue");
            QObject qObject = logConsumer.getMessage(qDestination);
            if (qObject != null) {
                String data = gson.toJson(qObject.getData());
                System.out.println("FileWriteConsumer: Sending Message to LogConsumer");
                logConsumer.process(data);
            }
        } while (logConsumer.getState() == LogThreadState.RUNNING.getValue());
    }
}
