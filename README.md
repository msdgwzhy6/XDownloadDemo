XDownload介绍

本库封装基于Okhttp3,Retrofit2,RxJava2.0,Greendao3.2
ps : 当然当然，都封装好了，你也可以无视

----------

#GitHub地址
> 如果你觉得好用，对你有帮助，请给个star
> 接口使用360市场的如果有侵权，联系本人删除，谢谢

#效果图
![这里写图片描述](http://img.blog.csdn.net/20170617180411464?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXFfMzA4ODkzNzM=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
![这里写图片描述](http://img.blog.csdn.net/20170617180428230?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXFfMzA4ODkzNzM=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
![这里写图片描述](http://img.blog.csdn.net/20170617180440613?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXFfMzA4ODkzNzM=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)


#主要功能

- **自动更新，酷炫的水波纹 **
- **网络请求，支持自动ProgressDialog**
- **网络请求，自定义是否缓存，无论任何方式**
- **网络请求，自定义缓存时间，无网络缓存时间**
- **队列下载，可限制队列最大数。**
- **断点下载，APP强制关闭，依旧不影响**
- **网络请求缓存，可缓存String,Gson**
- **代码全注释，方便您的阅读**

-------------------

# 如何使用

在当前工程中引入
compile 'com.github.2745329043:XDownloadLibrary:1.0.6'

最好直接参考demo
https://github.com/2745329043/XDownloadDemo

Application初始化工作
``` python
//初始化网络请求
RetrofitClient.init(this)
	.setDebug(true)
	.setBaseUrl(IConstantPool.sCommonUrl)
	.setApplictionId(BuildConfig.APPLICATION_ID)      //7.0安装apk
     /** 以下都是按需设置 */
    .setDownloadsQueueCount(5)                 //下载最大数量
    .setDbName("net_buffer_db")                //数据库表名
    .setConnectionTimeout(6)                   //普通请求连接超时
    .setReadTimeout(6)                         //普通请求读取超时
    .setDownConnectionTime(6)                  //下载连接超时 6秒
    .setNetBufferTime(60)                      //有网络的情况下缓存 60    
    .setNoNetBufferTime(24 * 60 * 60 * 7)      //无网络的时候，缓存时间
    /** 设置完，记得Buid */
    .build();
```

## 先来效果图


## 网络请求

	> **定义 HttpService 接口**
``` python
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

```
###普通String请求
``` python
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
```
###普通Gson请求
``` python
RetrofitClient.getService(HttpService.class)
	.requestList_GSON()
	.compose(new ApplySchedulers<List<BriefListBean>>())
    .subscribe(new NetProgressSubscriber<>(RequestFragment.this, NetDialogConfig.NORMAL_LOADING, new SimpleNetResponseListener<List<BriefListBean>>() {
	 @Override
	public void onSucceed(List<BriefListBean> bean, String s) {
		ToastUtils.getInstance().toast("拿到好多数据："  + bean.size());
     }
 }));
```
###网络请求过程中，是否显示Dialog
NetDialogConfig.UN_LOADING| 不显示
NetDialogConfig.NORMAL_LOADING| 显示,点击Dialog外,销毁并关闭队列
NetDialogConfig.FORBID_LOADING| 显示,不可取消

###网络请求过程中，是否缓存数据
NetBufferConfig.NORMAL_BUFFER| 缓存(ps:以初始化app)
NetBufferConfig.UN_BUFFER| 不缓存

###缓存使用案例
![Demo中截图](http://img.blog.csdn.net/20170615173955341?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXFfMzA4ODkzNzM=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

###自定义缓存时间使用案例
![这里写图片描述](http://img.blog.csdn.net/20170615174207471?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXFfMzA4ODkzNzM=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

###<font color=red size=6>String缓存处理</font>
因为ResponseBody.string() 方法 机制问题。所以框架内。String缓存需要
![这里写图片描述](http://img.blog.csdn.net/20170617164009324?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXFfMzA4ODkzNzM=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

```
//需要自己手动存储
 BufferDbUtil.getInstance().updateResulteBy(IConstantPool.REQUEST_LIST_URL,result);
```

## 下载功能
``` python
//下载管理 - 记得 ondestory
private RetrofitDownloadManager mRetrofitDownloadManager;
//使用 RetrofitDownloadManager 创建下载对象,内部有断点功能
mDownInfo = mRetrofitDownloadManager.createDownInfo("http://xxx.apk");
//点击按钮，开始更新
mRetrofitDownloadManager.down(mDownInfo);

```
<font color=red size=5>就这么简单，要记得释放资源,当然你不做也可以</font>
``` python
@Override
public void onDestroy() {
   super.onDestroy();
   mRetrofitDownloadManager.destory();
}
```
##多下载参考demo
DownloadFragment
