package com.edawtech.jiayou.ui.fragment

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.ArrayMap
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.alibaba.fastjson.JSON
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.bigkoo.convenientbanner.ConvenientBanner
import com.edawtech.jiayou.R
import com.edawtech.jiayou.config.base.BaseMvpFragment
import com.edawtech.jiayou.config.bean.BannerDataEntity
import com.edawtech.jiayou.config.bean.ResultEntity
import com.edawtech.jiayou.net.test.RetrofitCallback
import com.edawtech.jiayou.retrofit.RetrofitClient
import com.edawtech.jiayou.ui.activity.MoreRefuelActivity
import com.edawtech.jiayou.ui.dialog.CustomProgressDialog
import com.edawtech.jiayou.utils.tool.ViewSetUtils
import com.edawtech.jiayou.utils.tool.VsUtil
import com.service.helper.BDLBSMapHelper
import com.service.listener.OnAddressCallback
import kotlinx.android.synthetic.main.home_fragment.*


//加油首页
class HomeFragment : BaseMvpFragment() {
    private var mBdlbsMapHelper: BDLBSMapHelper? = null
    lateinit var banner: BannerDataEntity
    private var mParams: Map<String, String> = ArrayMap()
    private var mIsLocationSuccess = false
    private lateinit var loadingDialog: CustomProgressDialog

    private var mLatitude = 0.0
    private var mLongitude = 0.0
    private var mGoLatitude = 0.0
    private var mGoLongitude = 0.0

    private var locationClient: AMapLocationClient? = null
    private var locationOption: AMapLocationClientOption? = null

    var mLocationClient: LocationClient? = null


    override val layoutId: Int
        get() = R.layout.home_fragment

    override fun initView(view: View?, savedInstanceState: Bundle?) {
        mLocationClient = LocationClient(context)
        loadingDialog = CustomProgressDialog(context, "正在加载中...", R.drawable.loading_frame)
        loadingDialog.window!!.setBackgroundDrawable(ColorDrawable())
        loadingDialog.setLoadingDialogShow()

        getBanner()
      //更多油站
        view?.findViewById<LinearLayout>(R.id.llerefuel)?.setOnClickListener {
            ViewSetUtils.ButtonClickZoomInAnimation(it, 0.85f)
            MoreRefuelActivity.start(context, mIsLocationSuccess, mLatitude, mLongitude)
        }
        view?.findViewById<LinearLayout>(R.id.ll_refuel_order)?.setOnClickListener {
            ViewSetUtils.ButtonClickZoomInAnimation(it, 0.85f)

        }

        view?.findViewById<LinearLayout>(R.id.ll_refuel_money)?.setOnClickListener {
            ViewSetUtils.ButtonClickZoomInAnimation(it, 0.85f)
        }

        //附近加油站
        view?.findViewById<LinearLayout>(R.id.ll_error)?.setOnClickListener {
            ViewSetUtils.ButtonClickZoomInAnimation(it, 0.85f)


        }


    }

    //图片轮播图
    private fun getBanner() {
        RetrofitClient.getInstance(context).Api().banners.enqueue(object : RetrofitCallback<ResultEntity>() {
            @SuppressLint("LogNotTimber")
            override fun onNext(result: ResultEntity?) {
                loadingDialog.setLoadingDialogDismiss()
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

    private fun getRefuelData() {


    }


    override fun onStop() {
        super.onStop()

    }

    override fun onFailure(e: Throwable?, code: Int, msg: String?, isNetWorkError: Boolean) {

    }

    override fun onSuccess(data: String?) {

    }




}



