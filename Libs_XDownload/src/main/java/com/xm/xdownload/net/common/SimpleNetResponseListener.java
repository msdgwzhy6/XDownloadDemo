package com.xm.xdownload.net.common;


import com.xm.xdownload.utils.ToastUtils;

/**
 * @author: 小民
 * @date: 2017-06-05
 * @time: 23:19
 * @开源地址: https://github.com/2745329043/XDownloadDemo
 * @说明:
 */
public abstract class SimpleNetResponseListener<T> implements NetResponseListener<T> {

    @Override
    public void onCookieSucceed(String result, String mothead) {

    }

    @Override
    public void onError(Throwable e) {
        ToastUtils.getInstance().toast(e.getMessage());
    }

    @Override
    public void onComplete() {

    }
}
