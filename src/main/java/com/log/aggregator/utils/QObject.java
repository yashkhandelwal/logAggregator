package com.log.aggregator.utils;

import java.io.Serializable;

/**
 * Created by yashkhandelwal
 */
public class QObject<T> implements Serializable {

    private T data;

    public QObject(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

}
