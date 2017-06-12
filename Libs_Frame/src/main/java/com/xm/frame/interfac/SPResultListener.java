package com.xm.frame.interfac;

/**
 * 读取成功后的回调接口:
 */

public interface SPResultListener<T> {
    void onResult(T value);
}
