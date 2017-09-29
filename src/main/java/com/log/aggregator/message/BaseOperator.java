package com.log.aggregator.message;

import com.google.gson.reflect.TypeToken;
import com.log.aggregator.utils.LogProperty;
import com.log.aggregator.utils.LogThreadState;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by yashkhandelwal
 */
public abstract class BaseOperator {

    protected static volatile ExecutorService executorService;
    protected volatile Thread thread = null;
    private final long maxBytesAllowed;
    private final AtomicLong currentBytesCount = new AtomicLong();
    private final String name;
    protected volatile int state = LogThreadState.STOPPED.getValue();
    private volatile List<String> grabbedDataList = new ArrayList<>();
    public static final Charset CHARSET_UTF_8 = Charset.forName("UTF-8");
    public static final Type STRING_LIST_TYPE = new TypeToken<List<String>>() {
    }.getType();

    protected BaseOperator(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot Be Empty");
        }
        this.name = name;
        maxBytesAllowed = LogProperty.getLogProperties().getInt("max.bytes.allowed", 50);
    }

    public void doTask() {
        startThreadIfNot();
        flushIfRequired(false);
    }

    private void flushIfRequired(boolean async) {
        if (isFlushNeeded()) {
            flushNow(async);
        }
    }

    private void flushNow(boolean async) {
        List<String> dataToSave = swapBuffer();
        if (dataToSave.isEmpty()) {
            return;
        }
        if (async) {
            getExecutorService().submit(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    doFlush(dataToSave);
                    return null;
                }
            });
        } else {
            doFlush(dataToSave);
        }
    }

    private synchronized List<String> swapBuffer() {
        List<String> swappedList = grabbedDataList;
        grabbedDataList = new ArrayList<>();
        currentBytesCount.set(0);
        return swappedList;
    }

    protected abstract void doFlush(List<String> dataToSave);

    protected ExecutorService getExecutorService() {
        if (executorService == null) {
            synchronized (this) {
                if (executorService == null) {
                    executorService = Executors.newFixedThreadPool(5);
                }
            }
        }
        return executorService;
    }

    private boolean isFlushNeeded() {
        return currentBytesCount.intValue() >= maxBytesAllowed;
    }

    protected abstract void startThreadIfNot();

    public void process(String data) {
        if (data == null || data.isEmpty()) {
            return;
        }
        final int dataBytesCount = data.getBytes(CHARSET_UTF_8).length;
        synchronized (this) {
            if (currentBytesCount.get() + dataBytesCount >= maxBytesAllowed) {
                flushNow(false);// change it to true for async
            }
            grabbedDataList.add(data);
            currentBytesCount.addAndGet(dataBytesCount);
        }
    }

    public int getState() {
        return state;
    }

    public void stop() {
        synchronized (this) {
            if (thread != null) {
                state = LogThreadState.STOPPING.getValue();
                thread.interrupt();
                thread = null;
                state = LogThreadState.STOPPED.getValue();
            }
        }
        flushNow(false);
    }

    public String getName() {
        return name;
    }
}

