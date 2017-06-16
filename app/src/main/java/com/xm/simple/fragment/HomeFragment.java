package com.xm.simple.fragment;


import android.os.Bundle;
import android.view.View;

import com.xm.simple.base.BaseFragment;
import com.xm.simple.update.UpdateDialog;
import com.xm.simple.R;
import com.xm.simple.databinding.FragmentHomeBinding;
import com.xm.xdownload.net.buffer.DownInfo;
import com.xm.xdownload.net.buffer.DownState;
import com.xm.xdownload.net.download.DownResultListenner;
import com.xm.xdownload.net.download.RetrofitDownloadManager;
import com.xm.xdownload.utils.NetworkUtil;

/**
 * @author: 小民
 * @date: 2017-06-03
 * @time: 16:30
 * @开源地址: https://github.com/2745329043/XDownloadDemo
 * @说明:
 */
public class HomeFragment extends BaseFragment<FragmentHomeBinding> {
    private UpdateDialog mUpdateDialog;
    //下载对象
    private DownInfo mDownInfo;
    //下载管理 - 记得 ondestory
    private RetrofitDownloadManager mRetrofitDownloadManager;


    @Override
    public int getLayoutResID() {
        return R.layout.fragment_home;
    }

    @Override
    public void initBinding(FragmentHomeBinding binding) {
        binding.setPresenter(new Presenter());
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //下载管理者，因为我们这里只有一个更新版面，就不用判断 downinfo 属于哪个对象了
        mRetrofitDownloadManager = new RetrofitDownloadManager(new DownResultListenner() {
            @Override
            public void updateProgress(DownInfo info, int progress) {
                //放心年轻人。进度只有改变的时候，才会刷新
                mUpdateDialog.updateProgress(progress);
            }

            @Override
            public void updateState(DownInfo info, DownState state) {
                //刷新Dialog上面按钮状态
                mUpdateDialog.updateButtonState(info);
                if (state == DownState.FINISH) {
                    //下载完成，自动打开app安装
                    String path = info.getSavePath();
                    NetworkUtil.getInstance().installApk(path);
                }
            }
        });
        //更新Dialog
        mUpdateDialog = new UpdateDialog(
                getContext(),
                R.drawable.update_circle,
                "1.2",
                "1.优化部分问题\n2.新增反馈",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击按钮，开始更新
                        mRetrofitDownloadManager.down(mDownInfo);
                    }
                });
        //下载对象 - 这种方式。一点要用这个
        mDownInfo = mRetrofitDownloadManager.createDownInfo("http://gdown.baidu.com/data/wisegame/41e4d8d8127bb502/baidushoujizhushou_16793302.apk");
        //不是新建,查看刷新 进度条。
        if(mDownInfo.getState() != DownState.NORMAL){
            //已经下载过了。那么就刷新上面的进度条吧
            mUpdateDialog.updateProgress(mDownInfo.getProgress());
            //进度按钮
            mUpdateDialog.updateButtonState(mDownInfo);
        }
    }

    /**
     * 登录
     */
    public class Presenter {
        public void update(View v) {
            mUpdateDialog.show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRetrofitDownloadManager.destory();
    }
}
