package com.log.aggregator.producer;

import com.google.gson.Gson;
import com.log.aggregator.utils.LogProperty;
import com.log.aggregator.utils.LogThreadState;
import com.log.aggregator.utils.QDestination;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yashkhandelwal
 */
public class FileReaderProducer extends AbstractProducingThread {

    public FileReaderProducer(LogProducer logProducer) {
        super(logProducer);
    }

    @Override
    protected void doRun() {
        QDestination qDestination = QDestination.getDestinationOrNull(getLogProducer().getName());
        String filePath = null;
        if (qDestination != null && qDestination.getKeyAddress() != null) {
            filePath = qDestination.getKeyAddress();
        }
        if (filePath != null && !filePath.isEmpty()) {
            do {
                try (FileInputStream fstream = new FileInputStream(LogProperty.getLogProperties().getString(filePath));
                     BufferedReader br = new BufferedReader(new InputStreamReader(fstream))) {
                    List<String> dataToSend = new ArrayList<>();
                    Gson gson = new Gson();
                    while (true) {
                        String line = br.readLine();
                        if (line == null) {
                            Thread.sleep(500);
                        } else if (dataToSend.size() >= LogProperty.getLogProperties().getInt("produce.batch.size", 5)) {
                            String data = gson.toJson(dataToSend);
                            System.out.println("FileReaderProducer: Sending Message To Producer");
                            getLogProducer().process(data);
                            dataToSend = new ArrayList<>();
                        } else {
                            dataToSend.add(line);
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } while (getProducerState() == LogThreadState.RUNNING.getValue());
        }
    }
}
