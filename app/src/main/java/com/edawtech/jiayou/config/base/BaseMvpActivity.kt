package com.edawtech.jiayou.config.base

import android.app.Activity
import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.os.Bundle
import android.view.WindowManager
import androidx.lifecycle.Lifecycle
import com.edawtech.jiayou.mvp.contract.PublicContract
import com.edawtech.jiayou.utils.permission.PermissionApplyManager
import com.edawtech.jiayou.utils.tool.ToastUtil
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.AutoDisposeConverter
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

/**
 * @author azheng
 * @date 2018/4/24.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：
 */
abstract class BaseMvpActivity : BaseActivity(), BaseView, PublicContract.View {
    private var mContextMVP: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContextMVP = context
        // 开启广播
        registerBroadcastReceivers(application)
        // 注册电量广播监听。
        registerReceiverLevel()
        /**
         * 小米政企服务系统权限控制。
         */
        //systemPermissionControl();
    }

    override fun getContext(): Context {
        return this@BaseMvpActivity
    }

    override fun showToast(msg: String) {
        ToastUtil.showMsg(msg)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        // 注销广播。
        // 蓝牙广播
        application.unregisterReceiver(mBluetoothStateBroadcastReceiver)
        // 定位广播
        if (PermissionApplyManager.isMarshmallowOrAbove()) {
            application.unregisterReceiver(mLocationProviderChangedReceiver)
        }
        // 注销电量广播监听。
        unregisterReceiverLevel()
    }

    /**
     * 绑定生命周期 防止MVP内存泄漏
     * @param <T>
     * @return
    </T> */
    override fun <T> bindAutoDispose(): AutoDisposeConverter<T> {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                .from(this, Lifecycle.Event.ON_DESTROY)) // OnDestory时自动解绑
    }

    /**
     * Register for required broadcast receivers.
     */
    private fun registerBroadcastReceivers(application: Application) {
        application.registerReceiver(mBluetoothStateBroadcastReceiver, IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED))
        if (PermissionApplyManager.isMarshmallowOrAbove()) {
            application.registerReceiver(mLocationProviderChangedReceiver, IntentFilter(LocationManager.MODE_CHANGED_ACTION))
        }
    }

    /**
     * Broadcast receiver to monitor the changes in the location provider.
     */
    private val mLocationProviderChangedReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val enabled = PermissionApplyManager.checkGPSIsOpen(context)
            // 定位状态回调
            onSetLocationEnabled_MVP(enabled)
        }
    }

    /**
     * Broadcast receiver to monitor the changes in the bluetooth adapter.
     */
    private val mBluetoothStateBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.STATE_OFF)
            val previousState = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_STATE, BluetoothAdapter.STATE_OFF)
            when (state) {
                BluetoothAdapter.STATE_TURNING_ON, BluetoothAdapter.STATE_ON ->                     // 蓝牙开启
                    onSetBluetoothEnabled_MVP(true)
                BluetoothAdapter.STATE_TURNING_OFF, BluetoothAdapter.STATE_OFF -> if (previousState != BluetoothAdapter.STATE_TURNING_OFF && previousState != BluetoothAdapter.STATE_OFF) {
                    // 蓝牙关闭
                    onSetBluetoothEnabled_MVP(false)
                }
            }
        }
    }

    // 定位状态回调
    fun onSetLocationEnabled_MVP(enabled: Boolean) {}

    // 蓝牙状态回调
    fun onSetBluetoothEnabled_MVP(enabled: Boolean) {}

    // 注册电量广播监听。
    private fun registerReceiverLevel() {
        registerReceiver(mReceiverLevel, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }

    // 注销电量广播监听。
    private fun unregisterReceiverLevel() {
        unregisterReceiver(mReceiverLevel)
    }

    // 电量低警告
    private val mReceiverLevel: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            //获取当前电量,范围是 0～100
            val level = intent.getIntExtra("level", 0)
            // 电量低警告回调
            onLowPowerWarning_MVP(level)
        }
    }

    // 电量低警告回调
    fun onLowPowerWarning_MVP(level: Int) {}

    /**
     * 使屏幕常亮 继承该Activity的页面也将常亮
     * Keep screen light Interface which inherits this activity also will keep light
     */
    fun keepScreenLongLight_MVP(activity: Activity) {
        val isOpenLight = true
        try {
            val window = activity.window
            if (isOpenLight) {
                window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 小米政企服务系统权限控制。
     */
    private fun systemPermissionControl() {
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