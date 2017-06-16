package com.xm.xdownload.net.download;

import com.xm.xdownload.net.buffer.DownInfo;
import com.xm.xdownload.net.buffer.DownState;
import com.xm.xdownload.net.common.RetrofitClient;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author: 小民
 * @date: 2017-06-08
 * @time: 09:52
 * @开源地址: https://github.com/2745329043/XDownloadDemo
 * @说明:
 */
public class DownloadSubscriber<T> implements Observer<T> {
    //当前绑定的下载地址 -> 因为不可能 有同一URL 在下载. 都被我用来当ID
    private DownInfo mDownInfo;
    //回调
    private DownResultListenner mDownResultListenner;
    //用于关闭上下流
    private Disposable mDisposable;

    public DownloadSubscriber(DownInfo downInfo,DownResultListenner downResultListenner) {
        this.mDownInfo = downInfo;
        this.mDownResultListenner = downResultListenner;
    }

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
        mDownResultListenner.updateState(mDownInfo,DownState.DOWN);
    }

    @Override
    public void onNext(T value) {
        RetrofitClient.log("DownloadSubscriber -> onNext");
    }

    @Override
    public void onError(Throwable e) {
        RetrofitClient.log("updateDownloadState -> onError");
        mDownResultListenner.updateState(mDownInfo,DownState.ERROR);
    }

    @Override
    public void onComplete() {
        RetrofitClient.log("onComplete -> onComplete");
        mDownResultListenner.updateState(mDownInfo,DownState.FINISH);
    }

    /** 关闭 */
    public void dispose(){
        if(mDisposable != null && !mDisposable.isDisposed()){
            mDisposable.dispose();
        }
    }
}
