package com.edawtech.jiayou.utils.tool;


import com.edawtech.jiayou.config.base.MyApplication;
import com.edawtech.jiayou.config.constant.VsUserConfig;

/**
 * @author Created by EDZ on 2018/8/8.
 *         检查登录状态
 */

public class CheckLoginStatusUtils {
    public static boolean isLogin() {
        boolean isLogin = false;
        String uid = VsUserConfig.getDataString(MyApplication.getContext(), VsUserConfig.JKey_KcId);
        if (uid != null && !uid.isEmpty()) {
            isLogin = true;
        }
        return isLogin;
    }
}
