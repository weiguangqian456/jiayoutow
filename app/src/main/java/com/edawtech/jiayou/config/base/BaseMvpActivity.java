package com.edawtech.jiayou.config.base;

import android.app.Activity;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.lifecycle.Lifecycle;


import com.edawtech.jiayou.mvp.contract.PublicContract;
import com.edawtech.jiayou.utils.permission.PermissionApplyManager;
import com.edawtech.jiayou.utils.tool.ToastUtil;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

/**
 * @author azheng
 * @date 2018/4/24.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：
 */
public abstract class BaseMvpActivity extends BaseActivity implements BaseView, PublicContract.View {

    private Context mContextMVP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContextMVP = getContext();
        // 开启广播
        registerBroadcastReceivers(getApplication());
        // 注册电量广播监听。
        registerReceiverLevel();

        /**
         * 小米政企服务系统权限控制。
         */
        //systemPermissionControl();
    }

    @Override
    public Context getContext() {
        return BaseMvpActivity.this;
    }

    @Override
    public void showToast(String msg) {
        ToastUtil.showMsg(msg);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销广播。
        // 蓝牙广播
        getApplication().unregisterReceiver(mBluetoothStateBroadcastReceiver);
        // 定位广播
        if (PermissionApplyManager.isMarshmallowOrAbove()) {
            getApplication().unregisterReceiver(mLocationProviderChangedReceiver);
        }
        // 注销电量广播监听。
        unregisterReceiverLevel();
    }

    /**
     * 绑定生命周期 防止MVP内存泄漏
     * @param <T>
     * @return
     */
    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                .from(this, Lifecycle.Event.ON_DESTROY));// OnDestory时自动解绑
    }

    /**
     * Register for required broadcast receivers.
     */
    private void registerBroadcastReceivers(final Application application) {
        application.registerReceiver(mBluetoothStateBroadcastReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
        if (PermissionApplyManager.isMarshmallowOrAbove()) {
            application.registerReceiver(mLocationProviderChangedReceiver, new IntentFilter(LocationManager.MODE_CHANGED_ACTION));
        }
    }

    /**
     * Broadcast receiver to monitor the changes in the location provider.
     */
    private final BroadcastReceiver mLocationProviderChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            final boolean enabled = PermissionApplyManager.checkGPSIsOpen(context);
            // 定位状态回调
            onSetLocationEnabled_MVP(enabled);
        }
    };

    /**
     * Broadcast receiver to monitor the changes in the bluetooth adapter.
     */
    private final BroadcastReceiver mBluetoothStateBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.STATE_OFF);
            final int previousState = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_STATE, BluetoothAdapter.STATE_OFF);

            switch (state) {
                case BluetoothAdapter.STATE_TURNING_ON:
                case BluetoothAdapter.STATE_ON:
                    // 蓝牙开启
                    onSetBluetoothEnabled_MVP(true);
                    break;
                case BluetoothAdapter.STATE_TURNING_OFF:
                case BluetoothAdapter.STATE_OFF:
                    if (previousState != BluetoothAdapter.STATE_TURNING_OFF && previousState != BluetoothAdapter.STATE_OFF) {
                        // 蓝牙关闭
                        onSetBluetoothEnabled_MVP(false);
                    }
                    break;
            }
        }
    };

    // 定位状态回调
    public void onSetLocationEnabled_MVP(boolean enabled) {
    }

    // 蓝牙状态回调
    public void onSetBluetoothEnabled_MVP(boolean enabled) {
    }

    // 注册电量广播监听。
    private void registerReceiverLevel() {
        registerReceiver(mReceiverLevel, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }
    // 注销电量广播监听。
    private void unregisterReceiverLevel() {
        unregisterReceiver(mReceiverLevel);
    }
    // 电量低警告
    private BroadcastReceiver mReceiverLevel = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //获取当前电量,范围是 0～100
            int level = intent.getIntExtra("level", 0);
            // 电量低警告回调
            onLowPowerWarning_MVP(level);
        }
    };
    // 电量低警告回调
    public void onLowPowerWarning_MVP(int level) {
    }

    /**
     * 使屏幕常亮 继承该Activity的页面也将常亮
     * Keep screen light Interface which inherits this activity also will keep light
     */
    public void keepScreenLongLight_MVP(Activity activity) {
        boolean isOpenLight = true;
        try {
            Window window = activity.getWindow();
            if (isOpenLight) {
                window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 小米政企服务系统权限控制。
     */
    private void systemPermissionControl() {
        // 小米政企服务系统权限控制。
   /*     ApplicationManager mApplicationManager;
        RestrictionsManager mRestrictionsManager;
        String packageName = "com.example.applicphone";

        // 设置应用权限配置
        mApplicationManager = ApplicationManager.getInstance();
        // 授予权限，保活与防卸载
        mApplicationManager.setApplicationSettings(packageName, mApplicationManager.FLAG_ALLOW_AUTOSTART);// 自启动权限标志位
        mApplicationManager.setApplicationSettings(packageName, mApplicationManager.FLAG_KEEP_ALIVE);// 应用保活标志位
        mApplicationManager.setApplicationSettings(packageName, mApplicationManager.FLAG_PREVENT_UNINSTALLATION);// 防卸载标志位
        mApplicationManager.setApplicationSettings(packageName, mApplicationManager.FLAG_GRANT_ALL_RUNTIME_PERMISSION);// 运行时权限授予

        // 管控系统功能
        mRestrictionsManager = RestrictionsManager.getInstance();
        // 管控系统功能 : 为true时功能为管控状态，无法使用
        mRestrictionsManager.setRestriction(RestrictionsManager.DISALLOW_SYSTEM_UPDATE, true);// 管控系统升级【定制 ROM】
        mRestrictionsManager.setRestriction(RestrictionsManager.DISALLOW_TETHER, false);// 数据热点功能管控
        mRestrictionsManager.setRestriction(RestrictionsManager.DISALLOW_CAMERA, false);// 相机功能管控
        mRestrictionsManager.setRestriction(RestrictionsManager.DISALLOW_MICROPHONE, false);// 录音功能管控
        mRestrictionsManager.setRestriction(RestrictionsManager.DISALLOW_IMEIREAD, false);// IMEI 读取管控
        // Wifi，蓝牙，GPS，NFC，飞行模式开关管控
        //mRestrictionsManager.setControlStatus(RestrictionsManager.BLUETOOTH_STATE, RestrictionsManager.FORCE_OPEN);// 蓝牙管控
        mRestrictionsManager.setControlStatus(RestrictionsManager.GPS_STATE, RestrictionsManager.FORCE_OPEN);// GPS 管控
*/
    }

}
