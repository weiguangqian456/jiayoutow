package com.edawtech.jiayou.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.edawtech.jiayou.R
import com.edawtech.jiayou.config.base.BaseMvpActivity
import com.edawtech.jiayou.ui.dialog.PromptDialog
import com.edawtech.jiayou.ui.fragment.AllOrderFragment
import com.edawtech.jiayou.ui.fragment.PayOrderFragment
import com.edawtech.jiayou.ui.fragment.ReimbOrderFragment
import kotlinx.android.synthetic.main.activity_order_refurl.*
import kotlinx.android.synthetic.main.activity_refurl_order.*
import org.linphone.mediastream.Log
import java.util.*

class RefurlOrderActivity : BaseMvpActivity() {
    //添加Fragment
    private var pageViews: ArrayList<Fragment>? = null

    override val layoutId: Int
        get() = R.layout.activity_refurl_order

    override fun initView(savedInstanceState: Bundle?) {

        id_stickynavlayout_indicator?.setupWithViewPager(id_stickynavlayout_viewpager)
        //添加Fragment
        pageViews = ArrayList<Fragment>()
        pageViews?.add(AllOrderFragment())
        pageViews?.add(PayOrderFragment())
        pageViews?.add(ReimbOrderFragment())
        Log.d("RefurlOrderActivity", pageViews?.size)

        id_stickynavlayout_viewpager.adapter = GuidePageAdapter(supportFragmentManager, pageViews!!)
        id_stickynavlayout_viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            @SuppressLint("MissingSuperCall")
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        findViewById<FrameLayout>(R.id.fl_back).setOnClickListener { finish() }
        findViewById<TextView>(R.id.tv_invoice).setOnClickListener {  nuePromptDialog() }

    }

    override fun onSuccess(data: String?) {

    }

    override fun onFailure(e: Throwable?, code: Int, msg: String?, isNetWorkError: Boolean) {

    }

    // 指引页面数据适配器
    internal class GuidePageAdapter : FragmentPagerAdapter {
        var list: ArrayList<Fragment>
        val str = arrayOf("全部", "已支付", "已退款")
        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            //super.destroyItem(container, position, object);
        }

        constructor(fm: FragmentManager, list: ArrayList<Fragment>) : super(fm) {
            this.list = list
        }

        constructor(fm: FragmentManager, behavior: Int, list: ArrayList<Fragment>) : super(fm, behavior) {
            this.list = list
        }

        override fun getItem(position: Int): Fragment {
            return list[position]
        }

        override fun getCount(): Int {
            return list.size
        }

        override fun getPageTitle(position: Int): CharSequence? { //添加标题Tab
            return str[position]
        }
    }

    fun nuePromptDialog() {
        PromptDialog(this)
                .widthScale(0.8f)
                .setTitle("开具发票")
                .setContent("请直接拨打客服电话：400-0365-388  来开具发票。")
                .show();

    }


}