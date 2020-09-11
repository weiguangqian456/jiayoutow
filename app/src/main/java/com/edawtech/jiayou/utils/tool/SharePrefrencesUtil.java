package com.edawtech.jiayou.utils.tool;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


import com.edawtech.jiayou.config.base.MyApplication;

import java.util.Set;

/**
 * 时间 ： 2017/11/20
 * 描述 :commit 同步直接写入磁盘，apply 是先保存到内存中然后异步写入磁盘。
 * 1：commit()方法是Added in API level 1的,也就是sdk1就已经存在了.
 * commit()有返回值,成功返回true,失败返回false.commit()方法是同步提交到硬件磁盘，
 * 因此，在多个并发的提交commit的时候，他们会等待正在处理的commit保存到磁盘后在操作，
 * 从而降低了效率。
 * 2：apply()方法是Added in API level 9的.
 * apply()没有返回值.apply()是将修改的数据提交到内存, 而后异步真正的提交到硬件磁盘.
 */
public class SharePrefrencesUtil {

    private static SharePrefrencesUtil mInstance;
    private static SharedPreferences sp;
    private static Editor editor;

    /**
     * 保存在手机里面的名字
     */
    private static final String SP_NAME = "setting_data";

    private SharePrefrencesUtil() {
        sp = MyApplication.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    private static SharePrefrencesUtil getInstance() {
        if (mInstance == null) {
            synchronized (SharePrefrencesUtil.class) {
                if (mInstance == null) {
                    mInstance = new SharePrefrencesUtil();
                }
            }
        }
        return mInstance;
    }

    /**
     * 保存数据的方法，拿到数据保存数据的基本类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public static void put(String key, Object object) {

        if (object instanceof String) {
            // editor.putString(key, (String) object);
            getInstance().putString(key, (String) object);

        } else if (object instanceof Integer) {
            getInstance().putInt(key, (Integer) object);

        } else if (object instanceof Long) {
            getInstance().putLong(key, (Long) object);

        } else if (object instanceof Float) {
            getInstance().putFloat(key, (Float) object);

        } else if (object instanceof Boolean) {
            getInstance().putBoolean(key, (Boolean) object);

        } else {
            getInstance().putString(key, object.toString());
        }
        // editor.apply();
    }

    /**
     * 向SP存入指定key对应的数据
     * 其中value可以是String、boolean、float、int、long等各种基本类型的值
     *
     * @param key
     * @param value
     */
    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    public void putLong(String key, long value) {
        editor.putLong(key, value);
        editor.apply();
    }

    public void putFloat(String key, float value) {
        editor.putFloat(key, value);
        editor.apply();
    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void putSet(String key, Set<String> value) {
        editor.putStringSet(key, value);
        editor.apply();
    }

    /**
     * 删除SP里指定key对应的数据项
     *
     * @param key
     */
    public void removeObject(String key) {
        editor.remove(key);
        editor.apply();
    }

    /**
     * 获取保存数据的方法，我们根据默认值的到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key           键的值
     * @param defaultObject 默认值
     * @return
     */
    public static Object get(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            // return sp.getString(key, (String) defaultObject);
            return getInstance().getString(key, (String) defaultObject);

        } else if (defaultObject instanceof Integer) {
            return getInstance().getInt(key, (Integer) defaultObject);

        } else if (defaultObject instanceof Long) {
            return getInstance().getLong(key, (Long) defaultObject);

        } else if (defaultObject instanceof Float) {
            return getInstance().getFloat(key, (Float) defaultObject);

        } else if (defaultObject instanceof Boolean) {
            return getInstance().getBoolean(key, (Boolean) defaultObject);

        } else {
            return getInstance().getString(key, null);
        }
    }

    /**
     * 获取SP数据里指定key对应的value。如果key不存在，则返回默认值defValue。
     *
     * @param key
     * @param defValue
     * @return
     */
    public static String getString(String key, String defValue) {
        try {
            return sp.getString(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    public static int getInt(String key, int defValue) {
        try {
            return sp.getInt(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    public static long getLong(String key, long defValue) {
        try {
            return sp.getLong(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    public static float getFloat(String key, float defValue) {
        try {
            return sp.getFloat(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    public static boolean getBoolean(String key, boolean defValue) {
        try {
            return sp.getBoolean(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    public static Set<String> getSet(String key, Set<String> defValue) {
        try {
            return sp.getStringSet(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    /**
     * 清空SP里所以数据
     */
    public void celars() {
        editor.clear();
        editor.apply();
    }

    /**
     * 判断SP是否包含特定key的数据
     *
     * @param key
     * @return
     */
    public boolean contains(String key) {
        return sp.contains(key);
    }

}
