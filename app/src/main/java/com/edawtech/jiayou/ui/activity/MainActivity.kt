package com.edawtech.jiayou.ui.activity

import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.edawtech.jiayou.R
import com.edawtech.jiayou.config.base.BaseMvpActivity
import com.edawtech.jiayou.config.event.MainFragmentEvent
import com.edawtech.jiayou.ui.fragment.HomeFragment
import com.edawtech.jiayou.ui.fragment.MineFragment
import com.edawtech.jiayou.ui.fragment.MyFragment
import com.edawtech.jiayou.ui.statusbar.StatusBarUtil
import com.edawtech.jiayou.utils.tool.SoftHideKeyBoardUtil
import com.edawtech.jiayou.utils.tool.ViewSetUtils
import com.permissionx.guolindev.PermissionX
import easypermission.davidinchina.com.easylibrary.util.EasyPermissionUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus

class MainActivity : BaseMvpActivity() {

    private val ExitTime: Long = 0 // 再按一次退出时间计算

    private var fragmentTransaction: FragmentTransaction? = null
    private var showFragmentPage = -1
    private lateinit var TabFragment1: Fragment
    private lateinit var TabFragment2: Fragment



    override val layoutId: Int
        get() = R.layout.activity_main


    override fun initView(savedInstanceState: Bundle?) {
        /**
         * 设置状态栏透明：侵入式透明status bar >> 顶部需要沉浸的是图片View.
         * 不要忘记了, 在当前activity onCreate中设置 取消padding,  因为这个padding 我们用代码实现了,不需要系统帮我
         */
        StatusBarUtil.setRootViewFitsSystemWindows(this, false)
        SoftHideKeyBoardUtil.assistActivity(this)
        initFragment()

        ly_Tab1.setOnClickListener {
            ViewSetUtils.ButtonClickZoomInAnimation(it, 0.9f)
            resetBtn()
            switchFragment(0)
        }
        ly_Tab4.setOnClickListener {
            ViewSetUtils.ButtonClickZoomInAnimation(it, 0.9f)
            resetBtn()
            switchFragment(1)
        }

    }

    override fun onFailure(e: Throwable?, code: Int, msg: String?, isNetWorkError: Boolean) {

    }

    override fun onSuccess(data: String?) {

    }

    /**
     * 初始化页面
     */
    private fun initFragment() {
        // 初始化Fragment。
        TabFragment1 = HomeFragment()
//        TabFragment2 = MyFragment()
        TabFragment2 = MineFragment()

        /**
         * getFragmentManager();注：如果使用Android3.0以下的版本，需要引入v4的包，
         * 然后Activity继承FragmentActivity，然后通过getSupportFragmentManager获得FragmentManager。
         */
        // 开启一个Fragment事务
        fragmentTransaction = supportFragmentManager.beginTransaction()
        // 如果MessageFragment为空，则创建一个并添加到界面上
        fragmentTransaction!!.add(R.id.id_content, TabFragment1)
        fragmentTransaction!!.add(R.id.id_content, TabFragment2)
        // 重置按钮
        resetBtn()
        // 加载Fragment
        fragmentTransaction!!.commit()

        // 页面判断（初始化加载页面）
        switchFragment(0)
    }

    /**
     * 清除掉所有的选中状态。
     */
    fun resetBtn() {

        // 初始化栏目图标
        iv_Tab1.setImageResource(R.mipmap.dudujiayou2x)
        tv_Tab1.setTextColor(resources.getColor(R.color.colorLineBox)) // 设置字体颜色

        iv_Tab2.setImageResource(R.mipmap.icon_home_mine_n)
        tv_Tab2.setTextColor(resources.getColor(R.color.colorLineBox)) // 设置字体颜色

        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        fragmentTransaction!!.hide(TabFragment1)
        fragmentTransaction!!.hide(TabFragment2)

    }

    fun switchFragment(page: Int) {
        if (page == showFragmentPage) {
            return
        }
        fragmentTransaction = supportFragmentManager.beginTransaction()
        // 重置按钮
        resetBtn()
        when (page) {
            0 -> {
                iv_Tab1.setImageResource(R.mipmap.dudujiayou2x)
                tv_Tab1.setTextColor(resources.getColor(R.color.TextYellow))
                // 设置要显示的Fragment
                fragmentTransaction!!.show(TabFragment1)
            }
            1 -> {
                iv_Tab2.setImageResource(R.mipmap.icon_home_mine_y)
                tv_Tab2.setTextColor(resources.getColor(R.color.TextYellow))
                // 设置要显示的Fragment
                fragmentTransaction!!.show(TabFragment2)
            }
        }

        // 发送到Fragment页面刷新数据
        EventBus.getDefault().post(MainFragmentEvent(page))
        // 加载Fragment
        fragmentTransaction!!.commit()
        showFragmentPage = page
    }

    override fun onStart() {
        super.onStart()
        /**
         * 检查所有需要的权限
         */
        setPermission()
    }

    //检查所有需要的权限
    fun setPermission() {
        PermissionX.init(this)
                .permissions(*EasyPermissionUtil.getPermissions(this))
                .onExplainRequestReason { scope, deniedList, beforeRequest -> scope.showRequestReasonDialog(deniedList, "为了保证程序正常工作，请您同意以下权限申请", "我已明白") }
                .onForwardToSettings { scope, deniedList -> scope.showForwardToSettingsDialog(deniedList, "您需要去应用程序设置当中手动开启权限", "我已明白") }
                .request { allGranted, grantedList, deniedList ->
                    //finish();
                    if (allGranted) {
                    //    Toast.makeText(this@MainActivity, "所有申请的权限都已通过", Toast.LENGTH_SHORT).show();
                    } else {
                    }
                }
    }

    /**
     * Fragment如何调用所在Activity的dispatchTouchEvent(MotionEvent ev)函数。
     */
    // 在父Activity中定义一个接口
    interface OnHideKeyboardListener {
        fun hideKeyboard(ev: MotionEvent?)
    }

    var onHideKeyboardListener: OnHideKeyboardListener? = null



    /**
     * 获取点击事件:
     * 通过dispatchTouchEvent每次ACTION_DOWN事件中动态判断非EditText本身区域的点击事件，然后在事件中进行屏蔽。
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            if (onHideKeyboardListener != null) {
                onHideKeyboardListener!!.hideKeyboard(ev)
            }
            return super.dispatchTouchEvent(ev)
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return if (window.superDispatchTouchEvent(ev)) {
            true
        } else onTouchEvent(ev)
    }



}