package com.edawtech.jiayou.config.base

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.Unbinder
import com.edawtech.jiayou.ui.statusbar.StatusBarUtil
import com.edawtech.jiayou.utils.ActivityCollector
import com.edawtech.jiayou.utils.permission.PermissionManager
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks
import java.lang.ref.WeakReference
import java.util.*


/**
 * @author azheng
 * @date 2018/4/24.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com 1
 * Description：
 */
abstract class BaseActivity : AppCompatActivity(), PermissionCallbacks {
    private var unbinder: Unbinder? = null

    /**
     * 弱引用实例
     */
    private var weakRefActivity: WeakReference<Activity>? = null

    /**
     * 弹出框
     */
    protected var mProgressDialog: ProgressDialog? = null

    @JvmField
    protected var mContext: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //去掉title

        // 设置布局
        setContentView(layoutId)
        mContext = this
        /**
         * 设置状态栏透明：侵入式透明status bar
         *
         * 这里注意下 因为在评论区发现有网友调用setRootViewFitsSystemWindows 里面 winContent.getChildCount()=0 导致代码无法继续
         * 是因为你需要在setContentView之后才可以调用 setRootViewFitsSystemWindows
         */
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this, true)
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this)
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        //状态栏 黑白主题 切换： dark=>true:状态栏黑色图标; dark=>false:状态栏白色图标。
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this, 0x55000000)
        }

        // 绑定事件ButterKnife.bind(this)必须在setContentView();之后。
        unbinder = ButterKnife.bind(this)
        // 初始化视图
        initView(savedInstanceState)
        //初始化Activity实例
        weakRefActivity = WeakReference(this)
        //添加至Activity管理器
        val instance = ActivityCollector.getInstance()
        instance.add(weakRefActivity)
    }

    /**
     * 设置布局
     */
    abstract val layoutId: Int

    /**
     * 初始化视图
     */
    abstract fun initView(savedInstanceState: Bundle?)

    override fun onDestroy() {
        unbinder!!.unbind()
        super.onDestroy()
        //移除Activity实例
        val collector = ActivityCollector.getInstance()
        collector.remove(weakRefActivity)
    }
    // 使用EasyPermission权限库解决6.0权限问题，解决7.0 FileProvider问题，华为手机获取不到图库相片问题等等
    /**
     * 重写onRequestPermissionsResult，用于接受请求结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // EasyPermissions handles the request result. 将请求结果传递EasyPermission库处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    /**
     * 请求权限成功
     *
     * @param requestCode
     * @param perms
     */
    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size)
        onPermissionsRequestSuccessfulOrFailed(requestCode, true, perms)
    }

    /**
     * 请求权限失败
     *
     * @param requestCode
     * @param perms
     */
    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size)
        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        /**
         * 若是在权限弹窗中，用户勾选了'NEVER ASK AGAIN.'或者'不在提示'，且拒绝权限。
         * 这时候，需要跳转到设置界面去，让用户手动开启。
         */
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    // 接收请求返回码
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // 当从软件设置界面，返回当前程序时候
        // if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {   }
        // 重新检查权限是否开通。
        onPermissionsRequestSuccessfulOrFailed(requestCode, false, null)
    }

    /**
     * 权限请求成功 或 失败 回调。
     */
    fun onPermissionsRequestSuccessfulOrFailed(requestCode: Int, isSuccessful: Boolean, perms: List<String>?) {}

    /**
     * 检查读写权限
     */
    //@AfterPermissionGranted(PermissionManager.CODE_WRITE)
    fun checkWritePermission(tip: String?) {
        val result = PermissionManager.checkPermission(this, PermissionManager.PERMS_WRITE)
        if (result) {
            // Have permissions, do the thing!
            onPermissionsRequestSuccessfulOrFailed(PermissionManager.CODE_WRITE, true, Arrays.asList(*PermissionManager.PERMS_WRITE))
        } else {
            // Ask for both permissions
            PermissionManager.requestPermission(this, tip, PermissionManager.CODE_WRITE, PermissionManager.PERMS_WRITE)
        }
    }

    /**
     * 检查定位权限
     */
    //@AfterPermissionGranted(PermissionManager.CODE_LOCATION)
    fun checkLocationPermission(tip: String?) {
        val result = PermissionManager.checkPermission(this, PermissionManager.PERMS_LOCATION)
        if (result) {
            // Have permissions, do the thing!
            onPermissionsRequestSuccessfulOrFailed(PermissionManager.CODE_LOCATION, true, Arrays.asList(*PermissionManager.PERMS_LOCATION))
        } else {
            // Ask for both permissions
            PermissionManager.requestPermission(this, tip, PermissionManager.CODE_LOCATION, PermissionManager.PERMS_LOCATION)
        }
    }

    /**
     * 检查蓝牙权限
     */
    //@AfterPermissionGranted(PermissionManager.CODE_BLUETOOTH)
    fun checkBluetoothPermission(tip: String?) {
        val result = PermissionManager.checkPermission(this, PermissionManager.PERMS_BLUETOOTH)
        if (result) {
            // Have permissions, do the thing!
            onPermissionsRequestSuccessfulOrFailed(PermissionManager.CODE_BLUETOOTH, true, Arrays.asList(*PermissionManager.PERMS_BLUETOOTH))
        } else {
            // Ask for both permissions
            PermissionManager.requestPermission(this, tip, PermissionManager.CODE_BLUETOOTH, PermissionManager.PERMS_BLUETOOTH)
        }
    }

    /**
     * 检查相机权限
     */
    //@AfterPermissionGranted(PermissionManager.CODE_CAMERA)
    fun checkCameraPermission(tip: String?) {
        val result = PermissionManager.checkPermission(this, PermissionManager.PERMS_CAMERA)
        if (result) {
            // Have permissions, do the thing!
            onPermissionsRequestSuccessfulOrFailed(PermissionManager.CODE_CAMERA, true, Arrays.asList(*PermissionManager.PERMS_CAMERA))
        } else {
            // Ask for both permissions
            PermissionManager.requestPermission(this, tip, PermissionManager.CODE_CAMERA, PermissionManager.PERMS_CAMERA)
        }
    }

    /**
     * 检查所有需要的权限
     */
    //@AfterPermissionGranted(PermissionManager.CODE_CAMERA)
    fun checkAllPermission(tip: String?) {
        val result = PermissionManager.checkPermission(this, PermissionManager.PERMS_ALLPerms)
        if (result) {
            // Have permissions, do the thing!
            onPermissionsRequestSuccessfulOrFailed(PermissionManager.CODE_ALLPerms, true, Arrays.asList(*PermissionManager.PERMS_ALLPerms))
        } else {
            // Ask for both permissions
            PermissionManager.requestPermission(this, tip, PermissionManager.CODE_ALLPerms, PermissionManager.PERMS_ALLPerms)
        }
    }

    /**
     * 检查 获取手机号和ICCID 的权限。
     */
    fun checkPhoneSmsPermission(tip: String?) {
        val result = PermissionManager.checkPermission(this, PermissionManager.PERMS_PhoneSms)
        if (result) {
            // Have permissions, do the thing!
            onPermissionsRequestSuccessfulOrFailed(PermissionManager.CODE_PhoneSms, true, Arrays.asList(*PermissionManager.PERMS_PhoneSms))
        } else {
            // Ask for both permissions
            PermissionManager.requestPermission(this, tip, PermissionManager.CODE_PhoneSms, PermissionManager.PERMS_PhoneSms)
        }
    }

    companion object {
        private const val TAG = "BaseActivity"
    }

    /**
     * 加载等待界面
     *
     * @param message
     */
    protected open fun loadProgressDialog(message: String?) {
        if (mProgressDialog != null) {
            mProgressDialog!!.dismiss()
        }
        mProgressDialog = ProgressDialog(mContext)
        mProgressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        mProgressDialog!!.setMessage(message)
        mProgressDialog!!.show()
    }

    protected open fun loadProgressDialog(message: String?, Cancelable: Boolean) {
        if (mProgressDialog != null) {
            mProgressDialog!!.dismiss()
        }
        mProgressDialog = ProgressDialog(mContext)
        mProgressDialog!!.setCancelable(Cancelable) // 设置是否可以取消
        mProgressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        mProgressDialog!!.setMessage(message)
        mProgressDialog!!.show()
    }

    /**
     * 销毁等待界面
     *
     * @param
     */
    protected open fun dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.dismiss()
        }
        mProgressDialog = null
    }
}