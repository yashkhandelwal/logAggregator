package com.log.aggregator.consumer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.log.aggregator.message.BaseOperator;
import com.log.aggregator.message.MessagingServiceImpl;
import com.log.aggregator.utils.LogProperty;
import com.log.aggregator.utils.LogThreadState;
import com.log.aggregator.utils.QDestination;
import com.log.aggregator.utils.QObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by yashkhandelwal
 */
public class LogConsumer extends BaseOperator {

    protected LogConsumer(String name) {
        super(name);
    }

    @Override
    protected void doFlush(List<String> dataToSave) {

        try (FileWriter fw = new FileWriter(LogProperty.getLogProperties().getString("consumer.log.path"), true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            Gson gson = new Gson();
            System.out.println("LogConsumer: Writing logs To Centralised File");
            for (String data : dataToSave) {
                List<String> lineData = gson.fromJson(data, STRING_LIST_TYPE);
                for (String line : lineData) {
                    List<String> logLines = gson.fromJson(line, STRING_LIST_TYPE);
                    for (String logLine : logLines) {
                        out.println(logLine);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void startThreadIfNot() {
        if (thread != null) {
            return;
        }
        synchronized (this) {
            if (thread == null) {
                thread = new FileWriterConsumer(this);
                state = LogThreadState.RUNNING.getValue();
                thread.start();
            }
        }
    }

    public QObject getMessage(QDestination qDestination) {
        return getMessageService().getMessage(qDestination);
    }

    private MessagingServiceImpl getMessageService() {
        return MessagingServiceImpl.getInstance();
    }
}
