package com.log.aggregator.message;

import com.log.aggregator.utils.QDestination;
import com.log.aggregator.utils.QObject;

import java.util.List;

/**
 * Created by yashkhandelwal
 */
public interface MessagingService {

    void sendMessage(QDestination qDestination, QObject qObject);

    QObject getMessage(QDestination qDestination);

    List<QObject> getMessages(QDestination qDestination);

}
