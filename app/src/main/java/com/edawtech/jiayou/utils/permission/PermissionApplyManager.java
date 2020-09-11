package com.edawtech.jiayou.utils.permission;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.core.content.ContextCompat;

import com.edawtech.jiayou.R;
import com.edawtech.jiayou.config.base.MyApplication;
import com.edawtech.jiayou.utils.tool.ToastUtil;


/**
 * @Name：MeiLan
 * @Description：描述信息
 * @Author：Administrator
 * @Date：2019/6/5 14:46
 * 修改人：Administrator
 * 修改时间：2019/6/5 14:46
 * 修改备注：
 *
 * 蓝牙连接前的判断。
 */
public class PermissionApplyManager {

    public static final int REQUEST_CODE_BLUETOOTH = 0x01;// 开启蓝牙返回code
    public static final int REQUEST_CODE_LOCATION = 0x02;// 开启定位返回code

    /**
     * 检查设备是否支持蓝牙BLE功能。
     */
    public static boolean isBLESupported() {
        if (MyApplication.getInstance().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            return true;
        }
        return false;
    }

    /**
     * 检查蓝牙是否开启
     * Checks whether Bluetooth is enabled.
     * @return true if Bluetooth is enabled, false otherwise.
     */
    public static boolean isBleEnabled() {
        final BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        return adapter != null && adapter.isEnabled();
    }

    /**
     * 检查蓝牙功能是否可用。
     */
    public static boolean checkBluetooth(Context context) {
        if (checkBluetoothState() == 2) {
            if (isLocationGranted(context)) {
                // 判断下当前设备是否是6.0及以上
                if (checkGPSIsOpen(context)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 检查蓝牙状态
     */
    public static int checkBluetoothState() {
        int bluetoothState;// 蓝牙状态： 0：不支持BLE；  1：off；  2：on
        /**
         * 判断当前Android设备是否支持BLE。
         * Android 4.3以后系统中加入了蓝牙BLE的功能。
         */
        if (isBLESupported()) {
            // 判断当前Android设备的蓝牙是否已经打开。
            if (isBleEnabled()) {
                bluetoothState = 2;// 0：不支持BLE；  1：off；  2：on
            } else {
                bluetoothState = 1;// 0：不支持BLE；  1：off；  2：on
            }
        } else {
            bluetoothState = 0;// 0：不支持BLE；  1：off；  2：on
        }
        return bluetoothState;
    }

    /**
     * 检查定位权限是否开启
     * Checks for required permissions.
     * @return True if permissions are already granted, false otherwise.
     */
    public static boolean isLocationGranted(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 检查定位是否打开
     * Location service if enable
     * @return location is enable if return true, otherwise disable.
     */
    public static boolean checkGPSIsOpen(Context context) {
        // Android 6.0以前不用检查位置权限。
        if (isMarshmallowOrAbove()) {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            if (locationManager == null) {
                return false;
            } else {
                boolean networkProvider = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                boolean gpsProvider = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (networkProvider || gpsProvider) return true;
                return false;
            }
        }
        return true;
    }

    public static boolean isMarshmallowOrAbove() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 开启蓝牙。
     */
    public static void openBluetooth(Activity activity) {
        // 方法1：通过蓝牙适配器直接打开蓝牙。
        // 方法2：通过startActivityForResult引导界面引导用户打开蓝牙，会有一个弹窗。
        Intent bluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(bluetoothIntent, REQUEST_CODE_BLUETOOTH);
    }

    /**
     * 打开定位授权页面。
     */
    public static void setLocationService(Activity activity) {
        Intent locationIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        activity.startActivityForResult(locationIntent, REQUEST_CODE_LOCATION);
    }

    // 权限设置页面。
    public static void onPermissionSettingsClicked(Activity activity) {
        final Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        activity.startActivity(intent);
    }
//
//    /**
//     * 蓝牙打开之后，进行扫描之前，需要判断下当前设备是否是6.0及以上，如果是，需要动态获取之前在Manifest中声明的位置权限。
//     */
//    public static void isLocationEnable(final Activity activity, boolean toOpen, final CheckBluetoothCallback callback) {
//        // 判断下当前设备是否是6.0及以上
//        if (!checkGPSIsOpen(activity)) {
//            if (toOpen) {
//                new AlertDialog.Builder(activity)
//                        .setTitle(R.string.notifyTitle)
//                        .setMessage(R.string.ForNormalUsePleaseAllowAccess3)
//                        .setNegativeButton(R.string.cancel,
//                                new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                        // 定位打开失败。
//                                        ToastUtil.showMsg(activity.getResources().getString(R.string.OpenGPSFailure));
//                                        if (callback != null) {
//                                            /** 权限开启失败。 **/
//                                            callback.openAccessFailure(false);
//                                        }
//                                    }
//                                })
//                        .setPositiveButton(R.string.setting,
//                                new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                        if (callback != null) {
//                                            /** 打开定位授权页面。 **/
//                                            setLocationService(activity);
//                                        }
//                                    }
//                                })
//                        .setCancelable(false)
//                        .show();
//            } else {
//                // 定位打开失败。
//                ToastUtil.showMsg(activity.getResources().getString(R.string.OpenGPSFailure));
//                if (callback != null) {
//                    /** 权限开启失败。 **/
//                    callback.openAccessFailure(false);
//                }
//            }
//        } else {
//            if (callback != null) {
//                /** 权限开启成功。 **/
//                callback.openAccessSuccessful(true);
//            }
//        }
//    }

}
