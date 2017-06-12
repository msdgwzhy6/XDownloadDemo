package com.xm.frame.utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.xm.frame.bean.KeyValue;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.xm.frame.interfac.IConfig.SHARED_PREFERENCES_FILE_NAME;

/**
 * SharedPreferences 工具类
 *
 */

public class SharedPreferencesUtils {
    private static Context mContext;

    /* 初始化 */
    public static void init(Context context){
        mContext = context;
    }

    public static void setBoolean(String key, boolean value){
        KeyValue keyValue = new KeyValue(key, value);
        Observable.just(keyValue)
                .map(new Function<KeyValue, KeyValue>() {
                    @Override
                    public KeyValue apply(KeyValue keyValue) throws Exception {
                        SharedPreferences sp = mContext.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean(keyValue.getKey(), (Boolean) keyValue.getValue());
                        editor.apply();
                        return keyValue;
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public static boolean getBoolean(String key){
        SharedPreferences sp = mContext.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }
}
