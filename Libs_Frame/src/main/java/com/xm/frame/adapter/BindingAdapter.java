package com.xm.frame.adapter;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xm.frame.transform.GlideCircleTransform;

/**
 * @author: 小民
 * @date: 2017-06-03
 * @time: 11:57
 * @开源地址: https://github.com/2745329043/NewSource
 * @说明: 自定义属性绑定 DataBinding   XML中使用
 */
public class BindingAdapter {
    /**
     * 加载图片，通过适配器
     * @param imageView
     * @param url       下载图片地址
     */
    @android.databinding.BindingAdapter({"app:imageUrl"})
    public static void
    loadImageFromUrl(ImageView imageView, String url){
        loadImageFromUrl(imageView, url,null);
    }

    /**
     * 加载图片，通过适配器
     * @param imageView
     * @param url       下载图片地址
     * @param drawable  设置资源加载过程中的占位Drawable。
     */
    @android.databinding.BindingAdapter({"app:imageUrl","app:placeholder"})
    public static void loadImageFromUrl(ImageView imageView, String url, Drawable drawable){
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(drawable)
                .into(imageView);
    }

    /**
     * 加载圆形图片，通过适配器
     * @param imageView
     * @param url       下载图片地址
     */
    @android.databinding.BindingAdapter({"app:imageCircleUrl"})
    public static void loadCircleImageFromUrl(ImageView imageView, String url){
        loadCircleImageFromUrl(imageView,url,null);
    }

    /**
     * 加载圆形图片，通过适配器
     * @param imageView
     * @param url       下载图片地址
     * @param drawable  设置资源加载过程中的占位Drawable。
     */
    @android.databinding.BindingAdapter({"app:imageCircleUrl","app:placeholder"})
    public static void loadCircleImageFromUrl(ImageView imageView, String url, Drawable drawable){
        Glide.with(imageView.getContext())
                .load(url)
                .transform(new GlideCircleTransform(imageView.getContext()))
                .placeholder(drawable)
                .into(imageView);
    }


}
