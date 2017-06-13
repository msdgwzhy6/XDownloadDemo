package com.xm.xdownload.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * @author: 小民
 * @date: 2017-05-27
 * @time: 11:09
 * @说明:
 */
public class ToastUtils {
    //单例
    private Context mContext;
    private static ToastUtils mToastUtils;
    /* Toast */
    private Toast mToast;

    private ToastUtils(Context context){
        mContext = context;
    }

    public static ToastUtils init(Context context){
        if(mToastUtils == null){
            mToastUtils = new ToastUtils(context);
        }
        return mToastUtils;
    }

    public static ToastUtils getInstance(){
        return mToastUtils;
    }


    /** Toast */
    public void toast(@StringRes int message){
        String string = mContext.getResources().getString(message);
        toast(string);
    }

    /** Toast */
    public void toast(String message){
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
        mToast.show();
    }
}
