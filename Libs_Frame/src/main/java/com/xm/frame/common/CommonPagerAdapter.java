package com.xm.frame.common;

import android.databinding.ViewDataBinding;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * ViewPager动态创建
 */
public class CommonPagerAdapter extends PagerAdapter {
    private List<ViewDataBinding> mPagerData;
    private String[] mTitles;


    public CommonPagerAdapter(List<ViewDataBinding> pagerData) {
        mPagerData = pagerData;
    }

    public CommonPagerAdapter(List<ViewDataBinding> pagerData, String[] titles) {
        mPagerData = pagerData;
        mTitles = titles;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mPagerData.get(position).getRoot();
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mPagerData.get(position).getRoot());
    }

    @Override
    public int getCount() {
        return mPagerData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(mTitles == null){
            return super.getPageTitle(position);
        }
        return mTitles[position];
    }
}
