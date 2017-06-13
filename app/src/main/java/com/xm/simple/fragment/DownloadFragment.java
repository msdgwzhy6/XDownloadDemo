package com.xm.simple.fragment;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.xm.simple.BR;
import com.xm.simple.R;
import com.xm.simple.adapter.ViewHolder;
import com.xm.simple.base.BaseFragment;
import com.xm.simple.bean.DownListBean;
import com.xm.simple.data.DownloadData;
import com.xm.simple.databinding.FragmentDownloadBinding;
import com.xm.simple.interfac.HttpService;
import com.xm.simple.interfac.IConstantPool;
import com.xm.xdownload.net.NetBufferConfig;
import com.xm.xdownload.net.NetDialogConfig;
import com.xm.xdownload.net.buffer.DownInfo;
import com.xm.xdownload.net.buffer.DownState;
import com.xm.xdownload.net.common.ApplySchedulers;
import com.xm.xdownload.net.common.NetProgressSubscriber;
import com.xm.xdownload.net.common.RetrofitClient;
import com.xm.xdownload.net.common.SimpleNetResponseListener;
import com.xm.xdownload.net.download.DownResultListenner;
import com.xm.xdownload.net.download.RetrofitDownloadManager;
import com.xm.xdownload.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 下载功能
 */
public class DownloadFragment extends BaseFragment<FragmentDownloadBinding> implements DownResultListenner {
    private ContentAdapter mCommonAdapter;
    //下载管理
    private RetrofitDownloadManager mRetrofitDownloadManager;
    @Override
    public int getLayoutResID() {
        return R.layout.fragment_download;
    }

    @Override
    public void initBinding(FragmentDownloadBinding binding) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mCommonAdapter = new ContentAdapter(getContext(), R.layout.fragment_download_list_item);
        mBinding.recyclerView.setAdapter(mCommonAdapter);
        if(savedInstanceState == null){
            mRetrofitDownloadManager = new RetrofitDownloadManager(this);
        }
        //请求数据
        RetrofitClient.getService(HttpService.class)
                .getDownloadList(1, 1)
                .compose(new ApplySchedulers<DownListBean>())
                .subscribe(new NetProgressSubscriber<>(this, IConstantPool.DOWNLOAD_URL, NetDialogConfig.NORMAL_LOADING, NetBufferConfig.NORMAL_BUFFER,56000, new SimpleNetResponseListener<DownListBean>() {
                    @Override
                    public void onSucceed(DownListBean data, String mothead) {
                        //本地数据库缓存
                        List<DownloadData> list = localList(data.getList());
                        mCommonAdapter.clearAddAll(list);
                        //如果你需要，让默认等待的队列，开始下载，你就执行这句
                        mRetrofitDownloadManager.downloadAll();
                    }

                    //从缓冲区获得
                    @Override
                    public void onCookieSucceed(String result, String mothead) {
                        DownListBean data = new Gson().fromJson(result, DownListBean.class);
                        onSucceed(data, mothead);
                    }
                }));

    }

    @Override
    public void updateProgress(DownInfo info, int progress) {
        DownloadData downloadData = queryDownloadData(info);
        if(downloadData != null){
            downloadData.updateProgress(DownState.DOWN);
        }
    }

    @Override
    public void updateState(DownInfo info, DownState state) {
        DownloadData downloadData = queryDownloadData(info);
        if(downloadData != null){
            downloadData.updateProgress(state);
        }
    }

    /** 查找 下载的对象对应List 列表的哪个数据，并刷新 */
    private DownloadData queryDownloadData(DownInfo info){
        List<DownloadData> data = mCommonAdapter.data;
        for (int i = 0; i < data.size(); i++) {
            DownInfo downInfo = data.get(i).getDownInfo();
            if(downInfo == info){
                return data.get(i);
            }
        }
        return null;
    }

    /**
     * 自定义适配器
     */
    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        /* 打气筒 */
        private LayoutInflater mInflater;
        /* 绑定布局 */
        protected int layoutId;
        /* Data */
        public List<DownloadData> data = new ArrayList<>();
        /* 监听事件 */
        private final DownPersenter mPresenter;

        public ContentAdapter(Context context, int layoutId) {
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.layoutId = layoutId;
            mPresenter = new DownPersenter();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewDataBinding binding = DataBindingUtil.inflate(mInflater, layoutId, parent, false);
            return new ViewHolder<>(binding);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            DownloadData t = data.get(position);
            holder.getmBinding().setVariable(BR.item, t);
            holder.getmBinding().setVariable(BR.downPersenter, mPresenter);
            holder.getmBinding().executePendingBindings();
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        /* 新增全部数据 */
        public void clearAddAll(List<DownloadData> list) {
            data.clear();
            data.addAll(list);
            notifyDataSetChanged();
        }

        /**
         * 监听
         */
        public class DownPersenter {
            /** 开始下载 */
            public void download(DownloadData data) {
                DownInfo downInfo = data.getDownInfo();
                mRetrofitDownloadManager.down(downInfo);
            }
        }
    }

    /** 本地有缓存，那么直接从本地取，不重新下载
     * @param list*/
    public List<DownloadData> localList(List<DownListBean.ListBean> list){
        //保存路径
        String savePath = NetworkUtil.getInstance().getDownloadDirectory().getAbsolutePath();
        //过滤，如果本地有下载过同URL。直接本地拿数据
        List<DownloadData> resultList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            DownListBean.ListBean bean = list.get(i);
            String downUrl = bean.getDown_url();
            //创建下载类，有判断缓存
            DownInfo downInfo = mRetrofitDownloadManager.createDownInfo(downUrl,savePath);
            //转换类，进行下载
            DownloadData downloadData = new DownloadData(bean.getLogo_url(), bean.getName(), bean.getVersion_name(), bean.getSize_fixed(),downInfo);
            resultList.add(downloadData);
        }
        return resultList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //切记要销毁，以免队列资源不回手
        mRetrofitDownloadManager.destory();
    }
}
