package com.xm.simple.interfac;

import com.xm.simple.bean.BriefListBean;
import com.xm.simple.bean.DownListBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author: 小民
 * @date: 2017-06-05
 * @time: 16:22
 * @开源地址: https://github.com/2745329043/XDownloadDemo
 * @说明: 网络请求，用了360的接口。如有侵权联系本人删除
 */
public interface HttpService {

    /* 获取下载列表 post */
    @POST(IConstantPool.DOWNLOAD_URL)
    Observable<DownListBean> getDownloadList(
            @Query("page") int page,
            @Query("type") int type
    );

    /* 普通请求 Get -> 获取 String 版本*/
    @GET(IConstantPool.REQUEST_LIST_URL)
    Observable<ResponseBody> requestList();

    /* 普通请求 Get -> 获取 GSON版本 版本 -> 这奇葩结构   是List<XXX> 的这种*/
    @GET(IConstantPool.REQUEST_LIST_URL)
    Observable<List<BriefListBean>> requestList_GSON();
}
