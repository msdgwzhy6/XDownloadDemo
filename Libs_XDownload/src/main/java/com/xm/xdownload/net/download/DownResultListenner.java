package com.xm.xdownload.net.download;

import com.xm.xdownload.net.buffer.DownInfo;
import com.xm.xdownload.net.buffer.DownState;

/**
 * @author: 小民
 * @date: 2017-06-08
 * @time: 13:24
 * @开源地址: https://github.com/2745329043/NewSource
 * @说明: 下载监听回调
 */
public interface DownResultListenner {
    //刷新进度
    void updateProgress(DownInfo info,int progress);
    //刷新状态
    void updateState(DownInfo info, DownState state);
}
