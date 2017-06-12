package com.xm.frame.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.gyf.barlibrary.ImmersionBar;
import com.xm.frame.interfac.IBase;
import com.xm.xdownload.interfac.NetBase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * 功能:
 * 作者：小民
 * 创建时间：2017/5/26
 */
public abstract class BaseActivity<T extends ViewDataBinding> extends SwipeBackActivity implements IBase<T>,NetBase{
    public T mBinding;
    /** 垃圾回收队列 */
    private List<Disposable> mRequestQueueList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).init();
        mBinding = DataBindingUtil.setContentView(this, getLayoutResID());
        initBinding(mBinding);
        initView(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
        //网络请求回收
        netGc();
        //资源回收
        mBinding.unbind();
        mBinding = null;
    }
    /** 网络请求 */
    @Override
    public boolean addRequestQueue(Disposable disposable) {
        if(mRequestQueueList == null){
            return false;
        }
        mRequestQueueList.add(disposable);
        return true;
    }
    @Override
    public void removeRequestQueue(Disposable disposable) {
        mRequestQueueList.remove(disposable);
    }
    @Override
    public void netGc() {
        //取消队列
        for (int i = 0; i < mRequestQueueList.size(); i++) {
            Disposable disposable = mRequestQueueList.get(i);
            if(!disposable.isDisposed()){
                disposable.dispose();
            }
        }
        mRequestQueueList.clear();
        mRequestQueueList = null;
    }
}
