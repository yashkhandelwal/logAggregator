package com.log.aggregator.producer;

import com.google.common.base.Function;
import com.google.gson.Gson;
import com.log.aggregator.message.BaseOperator;
import com.log.aggregator.message.MessagingServiceImpl;
import com.log.aggregator.retry.RetryService;
import com.log.aggregator.utils.LogProperty;
import com.log.aggregator.utils.LogThreadState;
import com.log.aggregator.utils.QDestination;
import com.log.aggregator.utils.QObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by yashkhandelwal
 */
public class LogProducer extends BaseOperator {

    protected LogProducer(String name) {
        super(name);
    }

    @Override
    protected void doFlush(List<String> dataToSave) {
        Throwable exception = null;
        try {
            QDestination destination = QDestination.getDestinationOrNull(getName());
            if (destination != null) {
                System.out.println("LogProducer: Sending Message To Queue");
                QObject qObject = new QObject(dataToSave);
                getMessageService().sendMessage(destination, qObject);
            } else {
                System.out.println("InValid QDestination");
            }
        } catch (Throwable e) {
            exception = e;
        } finally {
            boolean success = exception == null;
            if (!success) {
                getRetryService().retry(dataToSave);
            }
        }
    }

    @Override
    protected void startThreadIfNot() {
        if (thread != null) {
            return;
        }
        synchronized (this) {
            if (thread == null) {
                thread = new FileReaderProducer(this);
                state = LogThreadState.RUNNING.getValue();
                thread.start();
            }
        }
    }

    private MessagingServiceImpl getMessageService() {
        return MessagingServiceImpl.getInstance();
    }

    private RetryService<String, Void> getRetryService() {
        return new RetryService<>(new Function<List<String>, Void>() {

            @Override
            public Void apply(List<String> dataToSave) {
                try (FileWriter fw = new FileWriter(LogProperty.getLogProperties().getString("consumer.log.path"), true);
                     BufferedWriter bw = new BufferedWriter(fw);
                     PrintWriter out = new PrintWriter(bw)) {
                    Gson gson = new Gson();
                    for (String data : dataToSave) {
                        List<String> lineData = gson.fromJson(data, STRING_LIST_TYPE);
                        for (String line : lineData) {
                            out.println(line);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

}
