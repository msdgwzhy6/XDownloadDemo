package com.xm.simple.interfac;

import com.xm.simple.bean.DownListBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author: 小民
 * @date: 2017-06-05
 * @time: 16:22
 * @开源地址: https://github.com/2745329043/NewSource
 * @说明: 网络请求，用了360的接口。如有侵权联系本人删除
 */
public interface HttpService {

    /* 获取下载列表 */
    @GET(IConstantPool.DOWNLOAD_URL)
    Observable<DownListBean> getDownloadList(
            @Query("page") int page,
            @Query("type") int type
    );



}
