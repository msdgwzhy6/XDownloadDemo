package com.xm.simple.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xm.simple.R;
import com.xm.simple.base.BaseActivity;
import com.xm.simple.databinding.ActivityWelcomeBinding;
import com.xm.xdownload.utils.ToastUtils;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class WelcomeActivity extends BaseActivity<ActivityWelcomeBinding> {
    //权限适配
    private RxPermissions mRxPermissions;

    @Override
    public int getLayoutResID() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initBinding(ActivityWelcomeBinding binding) {}

    @Override
    public void initView(Bundle savedInstanceState) {
        startAnimotion();
        //RxPermissions权限申请
        mRxPermissions = new RxPermissions(this);
        Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                //模拟做了一些初始化耗时操作
                Thread.sleep(1500);
                return true;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean tag) throws Exception {
                        mRxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .subscribe(new Consumer<Boolean>() {
                                    @Override
                                    public void accept(Boolean value) throws Exception {
                                        if(!value){
                                            ToastUtils.getInstance().toast("用户拒绝缓存");
                                        }
                                        //清除动画
                                        mBinding.ivLoading.setVisibility(View.GONE);
                                        mBinding.ivLoading.clearAnimation();
                                        //是否guide
                                        startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                                        finish();
                                    }
                                });
                    }
                });

     }

    /** 旋转动画 */
    private void startAnimotion(){
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.guide_rotate);
        mBinding.ivLoading.startAnimation(rotate);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding = null;
    }

    /** Back键拦截 */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
