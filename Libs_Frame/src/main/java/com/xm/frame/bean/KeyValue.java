package com.xm.frame.bean;

/**
 * @author: 小民
 * @date: 2017-05-27
 * @time: 10:53
 * @说明:
 */
public class KeyValue<T> {
    private String key;
    private T value;

    public KeyValue(String key, T value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
