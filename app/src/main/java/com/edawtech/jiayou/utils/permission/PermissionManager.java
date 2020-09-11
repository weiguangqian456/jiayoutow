package com.edawtech.jiayou.utils.permission;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import com.edawtech.jiayou.utils.tool.ToastUtil;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by ${xinGen} on 2017/11/1.
 * blog: http://blog.csdn.net/hexingen
 * <p>
 * 权限工具类
 */
public class PermissionManager {

    /**
     * 权限检查
     *
     * @param context return true: 已经获取权限
     *                return false: 未获取权限，主动请求权限
     */
    // @AfterPermissionGranted(LOCATION_PERMISSION_CODE) 是可选的（在调用此方法的地方可以写上此注解）
    public static boolean checkPermission(Activity context, String[] perms) {
        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 请求权限
     *
     * @param context
     */
    public static void requestPermission(Activity context, String tip, int requestCode, String[] perms) {
        EasyPermissions.requestPermissions(context, tip, requestCode, perms);
    }

    /**
     * 常量：
     * 权限的请求code, 提示语，和权限码
     */
    // 读写权限
    // public final static String WRITE_PERMISSION_TIP = "为了正常使用，请允许读写权限!";
    public final static int CODE_WRITE = 110;
    public final static String[] PERMS_WRITE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    // 定位权限
    public final static int CODE_LOCATION = 111;
    public final static String[] PERMS_LOCATION = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    // 蓝牙权限，需要额外开启定位权限。
    public final static int CODE_BLUETOOTH = 112;
    public final static String[] PERMS_BLUETOOTH = {
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    // 相机权限
    public final static int CODE_CAMERA = 113;
    public final static String[] PERMS_CAMERA = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    // 获取通讯录权限
    public final static int CODE_CONTACTS = 114;
    public final static String[] PERMS_CONTACTS = {
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_PHONE_STATE};

    // 获取 获取手机号和ICCID 权限
    public final static int CODE_PhoneSms = 115;
    public final static String[] PERMS_PhoneSms = {
            Manifest.permission.READ_SMS,
            Manifest.permission.READ_PHONE_NUMBERS,
            Manifest.permission.READ_PHONE_STATE};

    // 获取所有需要的权限
    public final static int CODE_ALLPerms = 1122;
    public final static String[] PERMS_ALLPerms = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ANSWER_PHONE_CALLS,
            Manifest.permission.READ_CALL_LOG,
       //     Manifest.permission.READ_PHONE_STATE
    };

    public static void dealwithPermiss(final Activity context, String[] perms) {
        try {
            if (context != null && !checkPermission(context, perms)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("操作提示")
                        .setMessage("注意：当前缺少必要权限！\n请点击“设置”-“权限”-打开所需权限\n最后点击两次后退按钮，即可返回")
                        .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", context.getApplicationContext().getPackageName(), null);
                                intent.setData(uri);
                                context.startActivity(intent);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ToastUtil.showMsg("取消操作");
                            }
                        }).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
