package com.edawtech.jiayou.config.base;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.camera.camera2.Camera2Config;
import androidx.camera.core.CameraXConfig;
import androidx.multidex.MultiDex;

import com.blankj.utilcode.util.Utils;
import com.edawtech.jiayou.R;
import com.edawtech.jiayou.net.manager.RxHttpManager;
import com.luck.picture.lib.app.IApp;
import com.luck.picture.lib.app.PictureAppMaster;
import com.luck.picture.lib.crash.PictureSelectorCrashUtils;
import com.luck.picture.lib.engine.PictureSelectorEngine;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

public class MyApplication extends Application implements IApp, CameraXConfig.Provider {
    private static Context mContext;
    private static MyApplication mInstance;
    // 当前线程id
    public static int mainThreadId;
    public static Handler handler;

    public int payType = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mContext = getApplicationContext();
        mainThreadId = android.os.Process.myTid();
        handler = new Handler();


        /**
         * 工具类库  初始化
         * init it in the function of onCreate in ur Application
         */
        Utils.init(mContext);

        /**
         * RxHttp初始化
         */
        RxHttpManager.init(this);

        /**
         * 图片选择器 PictureSelector 2.0：日志管理配制开始
         * 需要class implements IApp, CameraXConfig.Provider
         */
        // PictureSelector 绑定监听用户获取全局上下文或其他...
        PictureAppMaster.getInstance().setApp(this);
        // PictureSelector Crash日志监听
        PictureSelectorCrashUtils.init((t, e) -> {
            // Crash之后的一些操作可再此处理，没有就忽略...
        });

        //64k方法限制
        MultiDex.install(this);

        initOkGo();
    }

    /**
     * 初始化OkGo
     */
    private void initOkGo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //构建Builder
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                //配置log
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor("OkGo");
                //log打印级别，决定了log显示的详细程度
                interceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
                //log颜色级别，决定了log在控制台显示的颜色
                interceptor.setColorLevel(Level.INFO);
                builder.addInterceptor(interceptor);

                //配置超时时间    默认60秒   也可自行设置
                //全局的读取超时时间
                builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
                //全局的写入超时时间
                builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
                //全局的连接超时时间
                builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);

                //使用sp保持cookie，如果cookie不过期，则一直有效
                builder.cookieJar(new CookieJarImpl(new SPCookieStore(getApplicationContext())));
                //使用数据库保持cookie，如果cookie不过期，则一直有效
//        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));
                //使用内存保持cookie，app退出后，cookie消失
//        builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));

                //https配置
                //方法一：信任所有证书,不安全有风险
                HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
                //方法二：自定义信任规则，校验服务端证书
//        HttpsUtils.SSLParams sslParams2 = HttpsUtils.getSslSocketFactory(new SafeTrustManager());
                //方法三：使用预埋证书，校验服务端证书（自签名证书）
//        HttpsUtils.SSLParams sslParams3 = HttpsUtils.getSslSocketFactory(getAssets().open("srca.cer"));
                //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
//        HttpsUtils.SSLParams sslParams4 = HttpsUtils.getSslSocketFactory(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"));
                builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
                //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
//        builder.hostnameVerifier(new SafeHostnameVerifier());

                //OkGo配置
                OkGo.getInstance().init(MyApplication.this)                       //必须调用初始化
                        .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置将使用默认的
                        //请求网络失败后读取缓存   网络连接失败后，会自动读取缓存
                        .setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                        .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                        .setRetryCount(3);                              //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
//                .addCommonHeaders(headers)                      //全局公共头
//                .addCommonParams(params);                       //全局公共参数

            }
        }).start();
    }


    public static Context getContext() {
        return mContext;
    }


    public static MyApplication getInstance() {
        return mInstance;
    }

    /**
     * 重写attachBaseContext()方法：解决方法数 65536 (65k) 限制
     * 同时解决友盟分析问题Could not find class 'com.umeng.analytics.d'
     *
     * @param base
     */
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 调用
        MultiDex.install(this);
    }

    /**
     * 图片选择器 PictureSelector 2.0：日志管理配制开始
     */
    @NonNull
    @Override
    public CameraXConfig getCameraXConfig() {
        return Camera2Config.defaultConfig();
    }

    /**
     * 图片选择器 PictureSelector 2.0：日志管理配制开始
     */
    @Override
    public Context getAppContext() {
        return this;
    }

    @Override
    public PictureSelectorEngine getPictureSelectorEngine() {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // 通知渠道的id
            String id = "1";
            // 用户可以看到的通知渠道的名字.
            CharSequence name = "notification channel";
            // 用户可以看到的通知渠道的描述
            String description = "notification description";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            // 配置通知渠道的属性
            mChannel.setDescription(description);
            // 设置通知出现时的闪灯（如果 android 设备支持的话）
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            // 设置通知出现时的震动（如果 android 设备支持的话）
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            //最后在notificationmanager中创建该通知渠道
            mNotificationManager.createNotificationChannel(mChannel);
        }
    }

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.white, R.color.colorpage);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    public static String getVersionCode() {
        try {
            PackageManager manager = MyApplication.getContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(MyApplication.getContext().getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return MyApplication.getContext().getString(R.string.version_unkown);
        }
    }
}
