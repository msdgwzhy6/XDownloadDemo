package com.xm.frame.fragment;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xm.frame.interfac.IBase;
import com.xm.xdownload.interfac.NetBase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * 功能:
 * 作者：小民
 * 创建时间：2017/5/26
 */
public abstract class BaseFragment<T extends ViewDataBinding>  extends Fragment implements IBase<T>,NetBase {
    public T mBinding;
    /** 垃圾回收队列 */
    private List<Disposable> mRequestQueueList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mBinding == null){
            mBinding = DataBindingUtil.inflate(inflater, getLayoutResID(), container, false);
            initBinding(mBinding);
            initView(savedInstanceState);
        }
        return mBinding.getRoot();
    }

    /** 垃圾回收 */
    @Override
    public void onDestroy() {
        super.onDestroy();
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
