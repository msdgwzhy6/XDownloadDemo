package com.xm.simple.fragment;


import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xm.simple.base.BaseFragment;
import com.xm.xdownload.net.common.SimpleNetResponseListener;
import com.xm.xdownload.utils.ToastUtils;
import com.xm.simple.R;
import com.xm.simple.bean.BriefListBean;
import com.xm.simple.databinding.FragmentRequestBinding;
import com.xm.simple.interfac.HttpService;
import com.xm.simple.interfac.IConstantPool;
import com.xm.xdownload.net.NetBufferConfig;
import com.xm.xdownload.net.NetDialogConfig;
import com.xm.xdownload.net.buffer.BufferDbUtil;
import com.xm.xdownload.net.common.ApplySchedulers;
import com.xm.xdownload.net.common.NetProgressSubscriber;
import com.xm.xdownload.net.common.RetrofitClient;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;

/**
 * 普通网路请求
 */
public class RequestFragment extends BaseFragment<FragmentRequestBinding> {

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_request;
    }

    @Override
    public void initBinding(FragmentRequestBinding binding) {
        binding.setPresenter(new Presenter());
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    /** 监听事件 */
    public class Presenter {

        /** 普通请求，返回String 自己解析的这种 */
        public void request(View v){
            RetrofitClient.getService(HttpService.class)
                    .requestList()
                    .compose(new ApplySchedulers<ResponseBody>())
                    .subscribe(new NetProgressSubscriber<>(RequestFragment.this, NetDialogConfig.NORMAL_LOADING, new SimpleNetResponseListener<ResponseBody>() {
                        @Override
                        public void onSucceed(ResponseBody body, String s) {
                            try {
                                ToastUtils.getInstance().toast(body.string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }));
        }

        /** 普通请求，返回Gson  */
        public void request_gson(View v){
            RetrofitClient.getService(HttpService.class)
                    .requestList_GSON()
                    .compose(new ApplySchedulers<List<BriefListBean>>())
                    .subscribe(new NetProgressSubscriber<>(RequestFragment.this, NetDialogConfig.NORMAL_LOADING, new SimpleNetResponseListener<List<BriefListBean>>() {
                        @Override
                        public void onSucceed(List<BriefListBean> briefListBeen, String s) {
                            ToastUtils.getInstance().toast("拿到好多数据："  + briefListBeen.size());
                        }

                    }));
        }

        /** 普通请求，返回Gson 加载数据过程中，不可取消  */
        public void request_gson_dialog(View v){
            RetrofitClient.getService(HttpService.class)
                    .requestList_GSON()
                    .compose(new ApplySchedulers<List<BriefListBean>>())
                    .subscribe(new NetProgressSubscriber<>(RequestFragment.this, NetDialogConfig.FORBID_LOADING, new SimpleNetResponseListener<List<BriefListBean>>() {
                        @Override
                        public void onSucceed(List<BriefListBean> briefListBeen, String s) {
                            ToastUtils.getInstance().toast("拿到好多数据："  + briefListBeen.size());
                        }

                    }));
        }

        /** 普通请求，返回Gson 加载数据过程中，不显示Dialog  */
        public void request_gson_un_dialog(View v){
            RetrofitClient.getService(HttpService.class)
                    .requestList_GSON()
                    .compose(new ApplySchedulers<List<BriefListBean>>())
                    .subscribe(new NetProgressSubscriber<>(RequestFragment.this, NetDialogConfig.UN_LOADING, new SimpleNetResponseListener<List<BriefListBean>>() {
                        @Override
                        public void onSucceed(List<BriefListBean> briefListBeen, String s) {
                            ToastUtils.getInstance().toast("拿到好多数据："  + briefListBeen.size());
                        }

                    }));
        }

        /** 普通请求，返回Gson 加载数据过程中，显示Dialog ->  并缓存，appliction中 初始化的时候可自定义，默认6秒 */
        public void request_gson_buffer(View v){
            RetrofitClient.getService(HttpService.class)
                    .requestList_GSON()
                    .compose(new ApplySchedulers<List<BriefListBean>>())
                    //多了一个  请求地址的标识 IConstantPool.REQUEST_LIST_URL。因为数据是根据 接口来存，确保唯一性
                    .subscribe(new NetProgressSubscriber<>(RequestFragment.this, IConstantPool.REQUEST_LIST_URL, NetDialogConfig.NORMAL_LOADING, NetBufferConfig.NORMAL_BUFFER, new SimpleNetResponseListener<List<BriefListBean>>() {
                        @Override
                        public void onSucceed(List<BriefListBean> briefListBeen, String s) {
                            ToastUtils.getInstance().toast("拿到好多数据："  + briefListBeen.size());
                        }

                        //这个是从本地取的
                        @Override
                        public void onCookieSucceed(String result, String mothead) {
                            //这里需要这样解析转换后，返回 给 onSucceed
                            TypeToken type = new TypeToken<List<BriefListBean>>() {};
                            List<BriefListBean> list = new Gson().fromJson(result, type.getType());
                            ToastUtils.getInstance().toast("缓存区拿的："  + list.size());
//                            onSucceed(list,mothead);  一般都是公用一个方法
                        }
                    }));
        }

        /** 普通请求，返回Gson 加载数据过程中，显示Dialog ->  并缓存，appliction中 初始化的时候可自定义，默认6秒 */
        public void request_gson_buffer_custom(View v){
            RetrofitClient.getService(HttpService.class)
                    .requestList_GSON()
                    .compose(new ApplySchedulers<List<BriefListBean>>())
                    //多了一个  请求地址的标识 IConstantPool.REQUEST_LIST_URL。因为数据是根据 接口来存，确保唯一性
                    //250 自定义缓存时间，也就是250秒内的请求，直接从 数据库中拿,不进行网络请求
                    .subscribe(new NetProgressSubscriber<>(RequestFragment.this, IConstantPool.REQUEST_LIST_URL, NetDialogConfig.NORMAL_LOADING, NetBufferConfig.NORMAL_BUFFER,250, new SimpleNetResponseListener<List<BriefListBean>>() {
                        @Override
                        public void onSucceed(List<BriefListBean> briefListBeen, String s) {
                            ToastUtils.getInstance().toast("拿到好多数据："  + briefListBeen.size());
                        }

                        //这个是从本地取的
                        @Override
                        public void onCookieSucceed(String result, String mothead) {
                            //这里需要这样解析转换后，返回 给 onSucceed
                            TypeToken type = new TypeToken<List<BriefListBean>>() {};
                            List<BriefListBean> list = new Gson().fromJson(result, type.getType());
                            ToastUtils.getInstance().toast("缓存区拿的："  + list.size());
//                            onSucceed(list,mothead);  一般都是公用一个方法
                        }
                    }));
        }

        public void request_all(View v){
            RetrofitClient.getService(HttpService.class)
                    .requestList()
                    .compose(new ApplySchedulers<ResponseBody>())
                    //多了一个  请求地址的标识 IConstantPool.REQUEST_LIST_URL。因为数据是根据 接口来存，确保唯一性     为什么多个 string .. 因为上面demo也是这个接口  不多个标识，不同类，肯定蹦
                    //250 自定义缓存时间，也就是250秒内的请求，直接从 数据库中拿,不进行网络请求
                    .subscribe(new NetProgressSubscriber<>(RequestFragment.this, IConstantPool.REQUEST_LIST_URL + ":string", NetDialogConfig.NORMAL_LOADING, NetBufferConfig.NORMAL_BUFFER,250, new SimpleNetResponseListener<ResponseBody>() {
                        @Override
                        public void onSucceed(ResponseBody body, String s) {
                            //这里需要这样解析转换后，返回 给 onSucceed
                            try {
                                String result = body.string();  //取到json
                                ToastUtils.getInstance().toast("网络拿的："  + result);
                                /**
                                 * 切记 切记，因为  ResponseBody的特殊行，只能自己在回调里面存
                                 */
                                BufferDbUtil.getInstance().updateResulteBy(IConstantPool.REQUEST_LIST_URL + ":string",result);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        //这个是从本地取的
                        @Override
                        public void onCookieSucceed(String result, String mothead) {
                            ToastUtils.getInstance().toast("数据库获取的："  + result);
                        }
                    }));
        }



    }
}
