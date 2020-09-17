package com.edawtech.jiayou.utils;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * ClassName:      ActivityCollector
 * <p>
 * Author:
 * <p>
 * CreateDate:      2020/9/15 14:37
 * <p>
 * Description:     Activity实例管理器
 */
public class ActivityCollector {

    private static volatile ActivityCollector mSingleton = null;

    private ActivityCollector() {
    }

    public static ActivityCollector getInstance() {
        if (mSingleton == null) {
            synchronized (ActivityCollector.class) {
                if (mSingleton == null) {
                    mSingleton = new ActivityCollector();
                }
            }
        }
        return mSingleton;
    }

    /**
     * Activity容器
     */
    private ArrayList<WeakReference<Activity>> activityList = new ArrayList<>();

    /**
     * 获取容器数量
     *
     * @return 数量
     */
    public int size() {
        return activityList.size();
    }

    /**
     * 添加Activity实例
     *
     * @param weakRefActivity Activity弱引用实例
     */
    public void add(WeakReference<Activity> weakRefActivity) {
        activityList.add(weakRefActivity);
    }

    /**
     * 移除Activity实例
     *
     * @param weakRefActivity Activity弱引用实例
     */
    public void remove(WeakReference<Activity> weakRefActivity) {
        boolean result = activityList.remove(weakRefActivity);
    }

    /**
     * 结束所有Activity实例，可关闭进程
     */
    public void finishAll() {
        if (activityList.size() > 0) {
            for (int i = 0; i < activityList.size(); i++) {
                Activity activity = activityList.get(i).get();
                if (activity != null && !activity.isFinishing()) {
                    activity.finish();
                }
            }
            activityList.clear();
        }
    }

    /**
     * 结束其他Activity
     *
     * @param clazz
     */
    public void finishOtherActivity(Class<? extends Activity> clazz) {
        Activity activity;
        for (int i = 0; i < activityList.size(); i++) {
            activity = activityList.get(i).get();
            if (activity != null) {
                if (activity.getClass() != clazz) {
                    activity.finish();
                }
            }
        }
    }
}
