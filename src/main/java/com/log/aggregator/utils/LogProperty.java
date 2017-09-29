package com.log.aggregator.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by yashkhandelwal
 */
public class LogProperty {

    private static final String FILE_PATH = "/mnt1/config/log.properties";
    private static Properties props;
    private final Properties properties;

    private LogProperty(Properties properties) {
        this.properties = properties;
    }

    public static LogProperty getLogProperties() {
        return LogPropertyHolder.INSTANCE;
    }

    public static Properties getInstance() {
        if (props == null) {
            loadProperties();
        }
        return props;
    }

    private static synchronized void loadProperties() {
        InputStream stream = null;
        try {
            stream = new FileInputStream(FILE_PATH);
            props = new Properties();
            props.load(stream);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public Integer getInt(String key, Integer defaultValue) {
        String value = getProperty(key);
        if (value.isEmpty()) {
            return defaultValue;
        }
        return Integer.valueOf(value);
    }

    public String getString(String key) {
        return getProperty(key);
    }

    private static class LogPropertyHolder {
        private static final LogProperty INSTANCE = new LogProperty(LogProperty.getInstance());
    }
}
