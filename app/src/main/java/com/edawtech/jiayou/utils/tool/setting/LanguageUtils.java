package com.edawtech.jiayou.utils.tool.setting;

import java.util.Locale;

/**
 * Android 判断当前语言环境是否是中文环境 工具类
 */
public class LanguageUtils {

    // 声明LanguageUtils对象
    private static volatile LanguageUtils mInstance;

    // 声明Language对象
    private static String language;

    /**
     * 单例模式实例化。
     */
    public static LanguageUtils getInstance() {
        if (mInstance == null) {
            synchronized (LanguageUtils.class) {
                if (mInstance == null) {
                    mInstance = new LanguageUtils();
                }
            }
        }
        return mInstance;
    }

    private LanguageUtils() {
        language = getLanguageEnv();
    }

    public boolean isLunarSetting() {
        if (language != null
                && (language.trim().equalsIgnoreCase("zh-CN") || language.trim().equalsIgnoreCase("zh-TW")
                || language.trim().equalsIgnoreCase("zh-HK"))) {
            return true;
        } else {
            return false;
        }
    }

    public String getLanguage() {
        return language;
    }

    private String getLanguageEnv() {
        Locale locale = Locale.getDefault();
        String language = locale.getLanguage();
        String country = locale.getCountry().toLowerCase();
        if ("zh".equalsIgnoreCase(language)) {
            if ("CN".equalsIgnoreCase(country)) {
                language = "zh-CN";
            } else if ("TW".equalsIgnoreCase(country)) {
                language = "zh-TW";
            } else if ("HK".equalsIgnoreCase(country)) {
                language = "zh-HK";
            } else {
                language = "zh-CN";
            }
        } else if ("za".equalsIgnoreCase(language)) {
            if ("CN".equalsIgnoreCase(country)) {
                language = "zh-CN";
            }
        } else if ("ug".equalsIgnoreCase(language)) {
            if ("CN".equalsIgnoreCase(country)) {
                language = "zh-CN";
            }
        } else if ("en".equalsIgnoreCase(language)) {
            if ("UK".equalsIgnoreCase(country)) {
                language = "en-UK";
            } else if ("US".equalsIgnoreCase(country)) {
                language = "en-US";
            } else if ("GB".equalsIgnoreCase(country)) {
                language = "en-GB";
            } else {
                language = "en-US";
            }
        }
        return language;
    }

}
