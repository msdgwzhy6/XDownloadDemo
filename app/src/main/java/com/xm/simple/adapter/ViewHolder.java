package com.xm.simple.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * 功能:
 * 作者：小民
 * 创建时间：2017/5/25
 */
public class ViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    private T mBinding;

    public ViewHolder(T binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public T getmBinding() {
        return mBinding;
    }
}
