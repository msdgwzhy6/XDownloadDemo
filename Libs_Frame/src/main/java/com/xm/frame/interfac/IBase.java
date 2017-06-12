package com.xm.frame.interfac;

import android.os.Bundle;

/**
 * 功能: 针对Fragment 与 Activity Base类。公用
 * 作者：小民
 * 创建时间：2017/5/26
 */
public interface IBase<T> {
    /* 初始化 */
    int getLayoutResID();
    void initBinding(T binding);
    void initView(Bundle savedInstanceState);
}
