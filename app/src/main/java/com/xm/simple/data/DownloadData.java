package com.xm.simple.data;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;
import com.xm.xdownload.net.buffer.DownInfo;
import com.xm.xdownload.net.buffer.DownState;

/**
 * @author: 小民
 * @date: 2017-06-07
 * @time: 09:44
 * @开源地址: https://github.com/2745329043/NewSource
 * @说明:
 */
public class DownloadData extends BaseObservable {
    private String logo_url;
    private String name;
    private String version_name;
    private double size_fixed;
    private DownInfo downInfo;

    public DownloadData(String logo_url, String name, String version_name, double size_fixed, DownInfo downInfo) {
        this.logo_url = logo_url;
        this.name = name;
        this.version_name = version_name;
        this.size_fixed = size_fixed;
        this.downInfo = downInfo;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public double getSize_fixed() {
        return size_fixed;
    }

    public void setSize_fixed(double size_fixed) {
        this.size_fixed = size_fixed;
    }

    public DownInfo getDownInfo() {
        return downInfo;
    }

    public void setDownInfo(DownInfo downInfo) {
        this.downInfo = downInfo;
    }

    /* 是否显示进度条 */
    @Bindable
    public boolean isShowProgress(){
        DownState state = downInfo.getState();
        if(state == DownState.NORMAL){
            return false;
        }else{
            return true;
        }
    }

    /* 总进度 */
    @Bindable
    public int getProgress(){
        return (int) (downInfo.getReadLength() * 1.0 / downInfo.getCountLength() * 100);
    }

    /* 状态文本 */
    @Bindable
    public String getStateText(){
        return downInfo.getStateText();
    }

    public void updateProgress(DownState downState){
        downInfo.setDownState(downState.getState());
        notifyPropertyChanged(BR.showProgress);
        notifyPropertyChanged(BR.progress);
        notifyPropertyChanged(BR.stateText);
    }

}
