package com.xm.simple;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.xm.simple.interfac.IConstantPool;
import com.xm.xdownload.net.common.RetrofitClient;

/**
 * 功能:
 * 作者：小民
 * 创建时间：2017/5/26ww
 */
public class App extends Application {
    // 入口函数
    private static App application;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        //初始化网络请求
        RetrofitClient.init(this)
                .setDebug(true)
                .setBaseUrl(IConstantPool.sCommonUrl)
                .setApplictionId(BuildConfig.APPLICATION_ID)
                /** 以下都是按需设置 */
                .setDownloadsQueueCount(1)                 //下载最大数量
                .setDbName("net_buffer_db")                //数据库表名
                .setConnectionTimeout(6)                   //普通请求连接超时
                .setReadTimeout(6)                         //普通请求读取超时
                .setDownConnectionTime(6)                  //下载连接超时   6秒
                .setNetBufferTime(60)                      //有网络的情况下缓存  60秒
                .setNoNetBufferTime(24 * 60 * 60 * 7)      //无网络的时候，缓存
                /** 设置完，记得Buid */
                .build();
    }

    /** 方便其他地方调用 */
    public static App getInstance(){
        return application;
    }


}
