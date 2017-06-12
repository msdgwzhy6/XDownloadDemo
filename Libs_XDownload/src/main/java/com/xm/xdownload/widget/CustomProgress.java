package com.xm.xdownload.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.xm.xdownload.R;


/**
 * 加载进度条
 * 作者：小民
 */

public class CustomProgress extends Dialog {

    /**
     * 创建自定义ProgressDialog
     * @param context     上下文
     * @param cancelable 是否按返回键取消
     */
    public CustomProgress(Context context, boolean cancelable) {
        super(context, R.style.loading_dialog_style);
        setContentView(R.layout.dialog_loading);
        //旋转动画
        startAnimotion();
        // 按返回键是否取消
        setCancelable(cancelable);
        // 设置居中
        getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        // 设置背景层透明度
        lp.dimAmount = 0.6f;
        getWindow().setAttributes(lp);
    }

    @Override
    public void show() {
        super.show();
        startAnimotion();
    }

    /** 旋转动画 */
    private void startAnimotion(){
        ImageView customLoading = (ImageView) findViewById(R.id.custom_iv_loading);
        Animation rotate = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        customLoading.startAnimation(rotate);
    }
}
