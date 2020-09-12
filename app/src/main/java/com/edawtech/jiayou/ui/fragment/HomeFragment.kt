package com.edawtech.jiayou.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import com.alibaba.fastjson.JSON
import com.baidu.location.BDLocation
import com.bigkoo.convenientbanner.ConvenientBanner
import com.edawtech.jiayou.R
import com.edawtech.jiayou.config.base.BaseMvpFragment
import com.edawtech.jiayou.config.bean.BannerDataEntity
import com.edawtech.jiayou.config.bean.ResultEntity
import com.edawtech.jiayou.net.test.RetrofitCallback
import com.edawtech.jiayou.retrofit.RetrofitClient
import com.edawtech.jiayou.utils.tool.VsUtil
import com.service.helper.BDLBSMapHelper
import com.service.listener.OnAddressCallback
import kotlinx.android.synthetic.main.home_fragment.*
import java.util.*


//加油首页
class HomeFragment : BaseMvpFragment() , OnAddressCallback {
    private var mBdlbsMapHelper: BDLBSMapHelper? = null
    lateinit var banner: BannerDataEntity
    private var mParams: Map<String, String> = HashMap()
    override val layoutId: Int
        get() = R.layout.home_fragment

    override fun initView(view: View?, savedInstanceState: Bundle?) {
        getBanner()
        mBdlbsMapHelper = BDLBSMapHelper()
        mBdlbsMapHelper!!.getAddressDetail( context, this)

    }

    //图片轮播图
    private fun getBanner() {
        RetrofitClient.getInstance(context).Api().banners.enqueue(object : RetrofitCallback<ResultEntity>() {
            @SuppressLint("LogNotTimber")
            override fun onNext(result: ResultEntity?) {
                banner = JSON.parseObject(result!!.data.toString(), BannerDataEntity::class.java)
                Log.d("RetrofitClient", banner.oil.get(0).imageUrl)
                if (banner.oil == null || banner.oil.size === 0) {
                    iv_banner_default.visibility = View.VISIBLE
                    return
                }
                iv_banner_default.visibility = View.GONE
                cb_banner.setPageIndicator(intArrayOf(R.mipmap.xiang_huise, R.mipmap.xing_bai))
                cb_banner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                VsUtil.setPase(cb_banner, banner.oil as List<Any>?)
                cb_banner.startTurning(3000)
            }
        })
    }

    private fun getRefuelData(){


    }



    override fun onFailure(e: Throwable?, code: Int, msg: String?, isNetWorkError: Boolean) {

    }

    override fun onSuccess(data: String?) {

    }



    override fun onAddressStart() {
        TODO("Not yet implemented")
    }

    override fun onAddressFail() {
        TODO("Not yet implemented")
    }

    override fun onAddressSuccess(p0: BDLocation?) {
        TODO("Not yet implemented")
    }

    override fun onAddressFinish() {
        TODO("Not yet implemented")
    }


}



