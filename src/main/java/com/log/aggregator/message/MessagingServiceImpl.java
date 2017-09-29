package com.log.aggregator.message;

import com.log.aggregator.utils.LogProperty;
import com.log.aggregator.utils.QDestination;
import com.log.aggregator.utils.QObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yashkhandelwal
 */
public class MessagingServiceImpl implements MessagingService {

    private ConcurrentHashMap<QDestination, BlockingQueue<QObject>> map = new ConcurrentHashMap<>();

    private MessagingServiceImpl() {
    }

    private static class MessagingServiceHolder {
        private static final MessagingServiceImpl INSTANCE = new MessagingServiceImpl();
    }

    public static MessagingServiceImpl getInstance() {
        return MessagingServiceHolder.INSTANCE;
    }

    @Override
    public void sendMessage(QDestination qDestination, QObject qObject) {
        try {
            BlockingQueue<QObject> queue = map.get(qDestination);
            if (queue == null) {
                System.out.println("MessageService : Creating New Queue for Destination " + qDestination.getDestinationName());
                queue = new ArrayBlockingQueue<>(LogProperty.getLogProperties().getInt("queue.capacity", 100));
                map.put(qDestination, queue);
            }
            System.out.println("MessageService: Adding Message in the Queue " + qDestination.getDestinationName());
            queue.add(qObject);
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public QObject getMessage(QDestination qDestination) {
        BlockingQueue<QObject> queue = map.get(qDestination);
        if (queue != null) {
            try {
                System.out.println("MessageService: Reading Message from Queue " + qDestination.getDestinationName());
                QObject qObject = queue.take();
                Thread.sleep(10);
                return qObject;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(("Queue Does Not Exist " + qDestination.getDestinationName()));
        return null;
    }

    @Override
    public List<QObject> getMessages(QDestination qDestination) {
        BlockingQueue<QObject> queue = map.get(qDestination);
        if (queue != null) {
            List<QObject> qObjects = new ArrayList<>();
            try {
                while (true) {
                    QObject qObject = queue.take();
                    if (qObject == null || qObjects.size() >= LogProperty.getLogProperties().getInt("queue.consume.batch.size", 4)) {
                        return qObjects;
                    } else {
                        qObjects.add(qObject);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        throw new IllegalArgumentException("Queue Does Not Exist");
    }
}
