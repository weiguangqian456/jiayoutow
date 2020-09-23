package com.edawtech.jiayou.config.base;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;

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
import com.mob.MobSDK;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;



public class MyApplication extends Application implements IApp, CameraXConfig.Provider {
    private static Context mContext;
    private static MyApplication mInstance;
    // 当前线程id
    public static int mainThreadId;
    public static Handler handler;

    public int payType = 0;


    /**
     * 全局唯一UID
     */
    public static String UID  = "%22%22";

    /**
     * 用户手机号
     */
    public static String MOBILE = "";

    /**
     * 是否登录
     */
    public static boolean isLogin = false;

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

        //允许7.0手机打开手机下的文件路径  否则将进不去拍照功能
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder b = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(b.build());
        }

        //Mob分享初始化
        MobSDK.init(this);

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
