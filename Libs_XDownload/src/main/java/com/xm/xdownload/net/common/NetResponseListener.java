package com.xm.xdownload.net.common;



/**
 * 网络请求接口进行回调。
 * by：小民
 */

public interface NetResponseListener<T> {
    /**
     * 成功后回调方法
     * @param data
     * @param mothead
     */
    void onSucceed(T data, String mothead);
    /**
     * 缓冲区数据回调方法
     * @param result
     * @param mothead
     */
    void onCookieSucceed(String result, String mothead);
    /**
     * 失败
     * 失败或者错误方法
     * 自定义异常处理
     * @param e
     */
    void onError(Throwable e);
    /**
     * 观察者结束,不管 错误与否，都会被调用
     */
    void onComplete();
}
