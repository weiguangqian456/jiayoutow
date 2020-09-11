package com.edawtech.jiayou.utils.tool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;

import com.blankj.utilcode.util.ActivityUtils;
import com.edawtech.jiayou.ui.activity.LoginActivity;


import org.apache.commons.lang3.StringUtils;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

/**
 * activity 页面跳转用。
 */
public class IntentJumpUtils {

    // 缓存 用户登录方式：微信登录为true（需要绑定手机号）； 手机号登录为false。
    public static void setLoginTypeBinderPhone(boolean mLoginType) {
        SharePrefrencesUtil.put("SP_LoginTypeBinderPhone", mLoginType);
    }
    // 获取 用户登录方式：微信登录为true（需要绑定手机号）； 手机号登录为false。
    public static boolean getLoginTypeBinderPhone() {
        return SharePrefrencesUtil.getBoolean("SP_LoginTypeBinderPhone", false);
    }

    /**
     * 获取绑定的蓝牙打印机Mac地址。
     */
    public static String getBleDeviceMacAddress() {
        return SharePrefrencesUtil.getString("BleDeviceMacAddress", "");
    }

    /**
     * 保存绑定的蓝牙打印机Mac地址。
     */
    public static void setBleDeviceMacAddress(String macAddress) {
        SharePrefrencesUtil.put("BleDeviceMacAddress", macAddress);
    }

    /**
     * 使用startActivityForResult的方式启动应用设置界面
     *
     * @param context
     */
    public static void startAppSetting(Context context) {
        Intent in = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        in.setData(uri);
        context.startActivity(in);
    }

    /**
     * 切换Activity
     *
     * @param class1
     */
    public static void startIntent(Context context, Class class1) {
        Intent intent = new Intent(context, class1);
        context.startActivity(intent);
    }


//    /**
//     * 切换Activity
//     * 登录判断切换。
//     *
//     * @param class1
//     */
//    public void startIntentJudge(Context context, Class class1) {
//        if (isLogin()) {
//            Intent intent = new Intent(context, class1);
//            context.startActivity(intent);
//        } else {
//        //    Intent intent = new Intent(context, LoginActivity.class);
//        //    context.startActivity(intent);
//        }
//    }
//
//    /**
//     * 判断是否登录。再跳转登录。
//     */
//    public static boolean sureLoginIntentJudge(Context context) {
//        if (isLogin()) {
//            return true;
//        } else {
//            Intent intent = new Intent(context, LoginActivity.class);
//            context.startActivity(intent);
//        }
//        return false;
//    }
//
//    /**
//     * 判断是否登录。
//
//    public static boolean isLogin() {
//        String data = getUserInforData();
//        if (StringUtils.isNotEmpty(getUsrToken()) && StringUtils.isNotEmpty(data)) {
//            try {
//                UserInforBean logRegister = GsonUtils.getGson().fromJson(data, UserInforBean.class);
//                if (logRegister != null && logRegister.getData() != null) {
//                    // 缓存登录数据。
//                    saveLoginData(data, logRegister);
//                    return true;
//                }
//            } catch (Exception e) {
//                // 数据解析失败 DataParsingFailed
//                e.printStackTrace();
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 获取 "deptId": 0, 部门id。
//     */
//    public static String getDeptId() {
//        return SharePrefrencesUtil.getString("SP_DeptId", Constant.Default_STR);
//    }
//
//    /**
//     * 保存 "deptId": 0, 部门id。
//     */
//    public static void setDeptId(String deptId) {
//        SharePrefrencesUtil.put("SP_DeptId", deptId);
//    }
//
//    /**
//     * 获取 "userId": 0, 员工ID。
//     */
//    public static String getUsrId() {
//        return SharePrefrencesUtil.getString("SP_userId", Constant.Default_STR);
//    }
//
//    /**
//     * 保存 "userId": 0, 员工ID。
//     */
//    public static void setUsrId(String userId) {
//        SharePrefrencesUtil.put("SP_userId", userId);
//    }
//
//    /**
//     * 获取usr_token。
//     */
//    public static String getUsrToken() {
//        return SharePrefrencesUtil.getString(Constant.SP_TOKEN, "");
//    }
//
//    /**
//     * 保存usr_token。
//     */
//    public static void setUsrToken(String token) {
//        SharePrefrencesUtil.put(Constant.SP_TOKEN, token);
//    }
//    /**
//     * 获取 iccid。
//     */
//    public static String getIccid() {
//        return SharePrefrencesUtil.getString(Constant.ICCID, "");
//    }
//
//    /**
//     * 保存 iccid。
//     */
//    public static void setIccid(String iccid) {
//        SharePrefrencesUtil.put(Constant.ICCID, iccid);
//    }
//
//    /**
//     * 获取用户所有的基本信息。
//     */
//    public static String getUserInforData() {
//        return SharePrefrencesUtil.getString(Constant.UserInfor_Data, "");
//    }
//
//    /**
//     * 缓存用户所有的基本信息。
//     */
//    public static void setUserInforData(String data) {
//        SharePrefrencesUtil.put(Constant.UserInfor_Data, data);
//    }
//
//    /**
//     * 获取用户名称。
//     */
//    public static String getUsrName() {
//        return SharePrefrencesUtil.getString("UsrName", "");
//    }
//
//    /**
//     * 保存用户名称。
//     */
//    public static void setUsrName(String name) {
//        SharePrefrencesUtil.put("UsrName", name);
//    }
//
//    /**
//     * 获取usr_Avatar 头像。
//     */
//    public static String getUserAvatar() {
//        return SharePrefrencesUtil.getString(Constant.SP_AVATAR, "");
//    }
//
//    /**
//     * 保存usr_Avatar 头像。
//     */
//    public static void setUserAvatar(String useravatar) {
//        SharePrefrencesUtil.put(Constant.SP_AVATAR, useravatar);
//    }
//
//    // 缓存 用户角色 类型 : 用户群体（1 内部员工 2 代理商  3终端客户(游客)）
//    public static void setUserType(int userRolesType) {
//        SharePrefrencesUtil.put("SP_User_Roles_Type", userRolesType);
//    }
//
//    // 获取 用户角色 类型 : 用户群体（1 内部员工 2 代理商  3终端客户(游客)）
//    public static int getUserType() {
//        return SharePrefrencesUtil.getInt("SP_User_Roles_Type", Constant.Default_INT);
//    }
//
//    // 缓存 用户类型：1-普通用户；2-企业用户。
//    public static void setTypes(int types) {
//        SharePrefrencesUtil.put("SP_Type_types", types);
//    }
//
//    // 获取 用户类型：1-普通用户；2-企业用户。
//    public static int getTypes() {
//        return SharePrefrencesUtil.getInt("SP_Type_types", Constant.Default_INT);
//    }
//
//    // 缓存 企业ID（个人用户为空）
//    public static void setEnterpriseId(String enterpriseId) {
//        SharePrefrencesUtil.put("SP_EnterpriseId", enterpriseId);
//    }
//
//    // 获取 企业ID（个人用户为空）
//    public static String getEnterpriseId() {
//        return SharePrefrencesUtil.getString("SP_EnterpriseId", "");
//    }
//
//    /**
//     * 判断是否是个人版本登录
//     */
//    public static boolean isPersonalVersion() {
//        if (getTypes() == 1 && StringUtils.isEmpty(getEnterpriseId())) {
//            // 个人用户 不显示企业相关信息
//            return true;
//        } else if((getTypes() == 2 && StringUtils.isNotEmpty(getEnterpriseId())) || getUserType() == 2) {
//            // 企业用户 需要显示企业相关信息
//            return false;
//        }
//        // 默认： 企业用户 需要显示企业相关信息
//        return false;
//    }
//
//    private static TaskCallback mTaskCallback;
//    /**
//     * 登录成功，参数处理，有回调。
//     */
//    public static void LoginSucceeded(Context mContext, String data, String Succeeded_str, String Failed_str, TaskCallback taskCallback) {
//        mTaskCallback = taskCallback;
//        LoginSucceeded(mContext, data, Succeeded_str, Failed_str);
//    }
//
//    /**
//     * 登录成功，参数处理。
//     */
//    public static void LoginSucceeded(Context mContext, String data, String Succeeded_str, String Failed_str) {
//        // 清除缓存的token
//        setUsrToken("");
//        try {
//            if (!data.isEmpty()) {
//                UserInforBean logRegister = GsonUtils.getGson().fromJson(data, UserInforBean.class);
//                if (logRegister != null && logRegister.getData() != null) {
//                    // 登录成功
//                    if (!TextUtils.isEmpty(Succeeded_str)) {
//                        ToastUtil.showMsg(Succeeded_str);
//                    }
//                    // 缓存登录数据。
//                    saveLoginData(data, logRegister);
//
//                    if (mTaskCallback != null) {
//                        mTaskCallback.onSuccess(data);
//                    } else {
//                        // 跳转到首页。
//                        MainActivity.toStartActivity(mContext);
//                        ((Activity) mContext).finish();
//                    }
//                } else {
//                    // 数据解析失败 DataParsingFailed
//                    if (!TextUtils.isEmpty(Failed_str)) {
//                        ToastUtil.showMsg(Failed_str);
//                    }
//                    if (mTaskCallback != null) {
//                        mTaskCallback.onFailure(null, 0, Failed_str, false);
//                    }
//                }
//            } else {
//                // 数据解析失败 DataParsingFailed
//                if (!TextUtils.isEmpty(Failed_str)) {
//                    ToastUtil.showMsg(Failed_str);
//                }
//                if (mTaskCallback != null) {
//                    mTaskCallback.onFailure(null, 0, Failed_str, false);
//                }
//            }
//        } catch (Exception e) {
//            // 数据解析失败 DataParsingFailed
//            if (!TextUtils.isEmpty(Failed_str)) {
//                ToastUtil.showMsg(Failed_str);
//            }
//            if (mTaskCallback != null) {
//                mTaskCallback.onFailure(null, 0, Failed_str, false);
//            }
//        }
//    }
//
//    // 缓存登录数据。
//    public static void saveLoginData(String data, UserInforBean logRegister) {
//        if (logRegister != null && logRegister.getData() != null) {
//            // 单例模式保存 用户登录的基本信息。
//            UserInforManage.getInstance().setUserInfor(logRegister);
//            // 缓存用户所有的基本信息。
//            setUserInforData(data);
//            String token = logRegister.getData().getToken();
//            // 缓存token
//            setUsrToken(token);
//
//            if (logRegister.getData().getInfo() != null) {
//                // 缓存 用户群体（1 内部员工 2 代理商  3终端客户(游客)）
//                if (logRegister.getData().getInfo().getUserType().equals("1")) {
//                    setUserType(Constant.userType_1);
//                } else if (logRegister.getData().getInfo().getUserType().equals("2")) {
//                    setUserType(Constant.userType_2);
//                } else {
//                    setUserType(Constant.userType_3);
//                }
//                if (StringUtils.isEmpty(token)) {
//                    // 缓存token
//                    setUsrToken(logRegister.getData().getInfo().getToken());
//                }
//                // 缓存 用户类型：1-普通用户；2-企业用户。
//                setTypes(logRegister.getData().getInfo().getTypes());
//                // 缓存 企业ID（个人用户为空）
//                setEnterpriseId(logRegister.getData().getInfo().getEnterpriseId());
//
//                /** 保存 "userId": 0, 员工ID。 */
//                setUsrId(logRegister.getData().getInfo().getId());
//
//                if (logRegister.getData().getInfo().getDeptId() == null) {
//                    /** 保存 "deptId": 0, 部门id。 */
//                    setDeptId(Constant.Default_STR);
//                } else {
//                    /** 保存 "deptId": 0, 部门id。 */
//                    setDeptId(logRegister.getData().getInfo().getDeptId());
//                }
//                /** 保存用户名称。 */
//                setUsrName(logRegister.getData().getInfo().getUsername());
//                // 保存usr_Avatar 头像。
//                if (TextUtils.isEmpty(getUserAvatar())) {
//                    setUserAvatar(logRegister.getData().getInfo().getAvatar());
//                }
//            }
//        }
//    }
//
//    /**
//     * 退出登录。
//     */
//    public static void Logout(Context mContext, boolean isToLogin) {
//        // 清除缓存的 单例模式保存 用户登录的基本信息。
//        UserInforManage.getInstance().setUserInfor(null);
//        // 清除缓存缓存用户所有的基本信息
//        setUserInforData("");
//        // 清除缓存的token
//        setUsrToken("");
//        // 清除缓存的 用户角色 类型
//        setUserType(Constant.Default_INT);
//        // 缓存 用户类型：1-普通用户；2-企业用户。
//        setTypes(Constant.Default_INT);
//        // 缓存 企业ID（个人用户为空）
//        setEnterpriseId(Constant.Default_STR);
//
//        /** 保存 "userId": 0, 员工ID。 */
//        setUsrId(Constant.Default_STR);
//        /** 保存 "deptId": 0, 部门id。 */
//        setDeptId(Constant.Default_STR);
//        /** 清除缓存的用户名称。 */
//        setUsrName("");
//        // 清除缓存的usr_Avatar 头像。
//        setUserAvatar("");
//
//        // 关闭所有activity
//        ActivityUtils.finishAllActivities();
//        if (isToLogin) {
//            // 清空所有Activity，并去登录界面
//            Intent intentlogin = new Intent(mContext, LoginActivity.class)
//                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intentlogin);
//        }
//    }

}
