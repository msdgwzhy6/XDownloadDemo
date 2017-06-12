package com.xm.xdownload.net.common;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;

import com.google.gson.GsonBuilder;
import com.xm.xdownload.interfac.NetBase;
import com.xm.xdownload.net.NetBufferConfig;
import com.xm.xdownload.net.NetDialogConfig;
import com.xm.xdownload.net.buffer.BufferDbUtil;
import com.xm.xdownload.net.buffer.BufferResulte;
import com.xm.xdownload.utils.NetworkUtil;
import com.xm.xdownload.widget.CustomProgress;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * @author: 小民
 * @date: 2017-06-05
 * @time: 18:26
 * @开源地址: https://github.com/2745329043/NewSource
 * @说明: 带进度条的 subscriber
 */
public class NetProgressSubscriber<T> implements Observer<T> {
    //基类，需要基层 BaseFragment/BaseActivity   因为里面有做资源回收 -> 比如Activity退出时，取消队列
    private NetBase mIBase;
    //缓存功能 -> 默认不开启
    private int mNetBufferTime;
    private NetBufferConfig mNetBufferConfig = NetBufferConfig.UN_BUFFER;
    //用于标识 -> 多个接口同时回调时 以及 缓存
    private String mTag;
    //开关
    public Disposable mDisposable;
    //进度条Dialog
    private CustomProgress mCustomProgress;
    //回调接口
    private NetResponseListener<T> mSubscriberOnNextListener;



    /**
     * 新增一个带缓存功能的 Observer 默认缓存时间
     * @param bufferConfig                   缓存配置  详情 NetBufferConfig 里看
     */
    public NetProgressSubscriber(NetBase iBase, String tag, NetDialogConfig config, NetBufferConfig bufferConfig , NetResponseListener<T> subscriberOnNextListener) {
        this(iBase, tag, config, subscriberOnNextListener);
        mNetBufferConfig = bufferConfig;
    }

    /**
     * 新增一个带缓存功能的 Observer 默认缓存时间
     * @param bufferConfig                   缓存配置  详情 NetBufferConfig 里看
     * @param netBufferTime                  自定义 缓存时间 -> 【秒】
     */
    public NetProgressSubscriber(NetBase iBase, String tag, NetDialogConfig config, NetBufferConfig bufferConfig , int netBufferTime, NetResponseListener<T> subscriberOnNextListener) {
        this(iBase, tag, config, subscriberOnNextListener);
        mNetBufferConfig = bufferConfig;
        mNetBufferTime = netBufferTime;
    }

    /**
     * 新增一个不带缓存功能的 Observer
     * @param iBase                          基础类，用于Activity Fragment onDestory 时回收
     * @param tag                            用于多接口同时使用一个回调时，标识
     * @param config                         Dialog配置  详情 DialogConfig 里看
     * @param subscriberOnNextListener       回调
     */
    public NetProgressSubscriber(NetBase iBase, String tag, NetDialogConfig config, NetResponseListener<T> subscriberOnNextListener) {
        this(iBase, config, subscriberOnNextListener);
        mTag = tag;
    }

    /**
     * 新增一个不带缓存功能的 Observer
     * @param iBase                          基础类，用于Activity Fragment onDestory 时回收
     * @param config                         Dialog配置  详情 DialogConfig 里看
     * @param subscriberOnNextListener       回调
     */
    public NetProgressSubscriber(NetBase iBase, NetDialogConfig config, NetResponseListener<T> subscriberOnNextListener) {
        mIBase = iBase;
        mSubscriberOnNextListener = subscriberOnNextListener;
        //默认缓存时间
        mNetBufferTime = RetrofitClient.client().getNetBufferTime();
        //显示进度条(如果需要)
        showProgressDialog(config);
    }

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
        //如果需要缓存处理
        if(mNetBufferConfig != NetBufferConfig.UN_BUFFER){
            //有网络有缓存  不进行网络请求，直接从本地取
            String resulteBy = queryResulteBy(mTag);
            if(resulteBy != null && resulteBy.length() > 0){
                d.dispose();
                mSubscriberOnNextListener.onCookieSucceed(resulteBy,mTag);
                return;
            }
        }
        //增加对应的队列中，方便回收
        if(!mIBase.addRequestQueue(d)){
            d.dispose();
        }
        //显示进度条
        showProgressDialog();
    }

    @Override
    public void onNext(T t) {
        //回调
        mSubscriberOnNextListener.onSucceed(t,mTag);
        //如果需要缓存处理 ResponseBody 需要自己去存储
        if(mNetBufferConfig != NetBufferConfig.UN_BUFFER && !(t instanceof ResponseBody)){
            //保存到数据库
            updateResulteBy(mTag,t);
        }

    }

    @Override
    public void onError(Throwable e) {
        mSubscriberOnNextListener.onError(e);
        dismissProgressDialog();
        gc();
    }

    @Override
    public void onComplete() {
        /* 回调 */
        mSubscriberOnNextListener.onComplete();
        dismissProgressDialog();
        gc();
    }

    /** 线程异常，或者成功后，调用回收 */
    private void gc(){
        /* 资源回收 */
        mIBase.removeRequestQueue(mDisposable);
        mCustomProgress = null;
        mDisposable = null;
        mSubscriberOnNextListener = null;
    }
    /** 显示进度条 */
    private void showProgressDialog(NetDialogConfig config){
        if(config != NetDialogConfig.UN_LOADING){
            boolean canCancel = false;
            //可取消
            if(config == NetDialogConfig.NORMAL_LOADING){
                canCancel = true;
            }
            Context context = null;
            if(mIBase instanceof Fragment){
                context = ((Fragment)mIBase).getContext();
            }else if (mIBase instanceof Activity){
                context = ((Activity)mIBase);
            }
            if(context == null){
                RetrofitClient.client().log("你没有在继承NetBase,无法显示进度条");
                return;
            }
            mCustomProgress = new CustomProgress(context,canCancel);
            if(canCancel){
                mCustomProgress.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        onCancelProgress();
                    }
                });
            }
        }
    }
    /** 显示加载Dialog */
    private void showProgressDialog(){
        if (mCustomProgress != null){
            mCustomProgress.show();
        }
    }
    /** 销毁加载Dialog */
    private void dismissProgressDialog() {
        if (mCustomProgress != null && mCustomProgress.isShowing()){
            mCustomProgress.dismiss();
        }
    }
    /** 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求 */
    private void onCancelProgress() {
        if(!mDisposable.isDisposed()){
            mDisposable.dispose();
        }
        gc();
    }

    /**
     * 查询缓存数据
     * @param tag          缓存时候的标识，一般是接口名/自定义
     * @return  有就返回String 超时或者无缓存返回 null
     */
    private String queryResulteBy(String tag){
        int timerOut = mNetBufferTime;
        if(!NetworkUtil.getInstance().isNetworkAvailable()){
            //无网络的时候,取无网络 超时时间
            timerOut = RetrofitClient.client().getNoNetBufferTime();
        }
        //读取数据库
        BufferResulte resulte = BufferDbUtil.getInstance().query(tag);
        if (resulte != null) {
            long time = (System.currentTimeMillis() - resulte.getTime()) / 1000;
            if (time < timerOut) {
                return resulte.getResulte();
            }else{
                BufferDbUtil.getInstance().delete(resulte);
            }
        }
        return null;
    }

    /**
     * 更新缓存数据
     * @param t      需要缓存的类
     */
    private void updateResulteBy(String tag,T t){
        String json = new GsonBuilder().create().toJson(t);
        BufferDbUtil.getInstance().updateResulteBy(tag,json);
    }

}
