package com.log.aggregator.utils;

/**
 * Created by yashkhandelwal
 */
public enum LogThreadState {

    STOPPED(0),
    STARTING(1),
    RUNNING(2),
    STOPPING(3);

    private int value;

    LogThreadState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
