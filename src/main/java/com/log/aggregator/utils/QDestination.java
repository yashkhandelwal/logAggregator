package com.log.aggregator.utils;

/**
 * Created by yashkhandelwal
 */
public enum QDestination {

    APP1_QUEUE("app1_queue", "producer1.log.path"),
    APP2_QUEUE("app2_queue", "producer2.log.path"),
    APP3_QUEUE("app3_queue", "producer3.log.path"),
    APP4_QUEUE("app4_queue", "producer4.log.path"),
    APP5_QUEUE("app5_queue", "producer5.log.path");


    private String destinationName;
    private String keyAddress;

    QDestination(String destinationName, String keyAddress) {
        this.destinationName = destinationName;
        this.keyAddress = keyAddress;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public String getKeyAddress() {
        return keyAddress;
    }

    public static QDestination getDestinationOrNull(String name) {
        for (QDestination destination : QDestination.values()) {
            if (destination.getDestinationName().equalsIgnoreCase(name)) {
                return destination;
            }
        }
        return null;
    }
}
