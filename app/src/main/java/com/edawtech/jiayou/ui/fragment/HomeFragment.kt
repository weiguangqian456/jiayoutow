package com.edawtech.jiayou.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.ArrayMap
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.alibaba.fastjson.JSON
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.baidu.location.LocationClient
import com.bigkoo.convenientbanner.ConvenientBanner
import com.edawtech.jiayou.R
import com.edawtech.jiayou.config.base.BaseMvpFragment
import com.edawtech.jiayou.config.base.MyApplication
import com.edawtech.jiayou.config.bean.BannerDataEntity
import com.edawtech.jiayou.config.bean.MoreReListBean
import com.edawtech.jiayou.config.bean.ResultEntity
import com.edawtech.jiayou.config.constant.Constant
import com.edawtech.jiayou.mvp.presenter.PublicPresenter
import com.edawtech.jiayou.net.http.HttpRequest
import com.edawtech.jiayou.net.http.HttpURL
import com.edawtech.jiayou.net.observer.TaskCallback
import com.edawtech.jiayou.net.test.RetrofitCallback
import com.edawtech.jiayou.retrofit.RetrofitClient
import com.edawtech.jiayou.ui.activity.*
import com.edawtech.jiayou.ui.custom.CommonPopupWindow
import com.edawtech.jiayou.ui.dialog.CustomProgressDialog
import com.edawtech.jiayou.ui.statusbar.StatusBarUtil
import com.edawtech.jiayou.utils.tool.*
import com.service.helper.BDLBSMapHelper
import kotlinx.android.synthetic.main.activity_more_re_list.*
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

    // 请求数据
    private var Inform_Target: PublicPresenter? = null

    var mLocationClient: LocationClient? = null

    var option = AMapLocationClientOption()

    private var mOilStationId: String? = null
    private var mSource: String? = null
    private var mOilStationName: String? = null
    private val mOilName: String? = null
    private val mGunNo: String? = null
    private var mOilNo: String? = null
    private var morerel: MoreReListBean.MoreReListRecords? = null

    private var mapPop: CommonPopupWindow? = null
    var intent: Intent? = null

    override val layoutId: Int
        get() = R.layout.home_fragment

    override fun initView(view: View?, savedInstanceState: Bundle?) {


        Inform_Target = PublicPresenter(context, false, "")
        Inform_Target?.attachView(this)

        mLocationClient = LocationClient(context)
        loadingDialog = CustomProgressDialog(context, "正在加载中...", R.drawable.loading_frame)
        loadingDialog.window!!.setBackgroundDrawable(ColorDrawable())
        loadingDialog.setLoadingDialogShow()

        option.locationPurpose = AMapLocationClientOption.AMapLocationPurpose.SignIn
        locationClient = AMapLocationClient(context)
        locationClient!!.setLocationOption(option)

        locationClient?.setLocationListener {
            when (it.errorCode) {
                AMapLocation.LOCATION_SUCCESS -> {
                    newData(it.longitude, it.latitude)
                }
            }
        }
        locationClient!!.startLocation()


        getBanner()
        //更多油站列表
        view?.findViewById<LinearLayout>(R.id.llerefuel)?.setOnClickListener {
            ViewSetUtils.ButtonClickZoomInAnimation(it, 0.85f)
            startActivity(Intent(context, MoreReListActivity::class.java))

        }
   //我的订单
        view?.findViewById<LinearLayout>(R.id.ll_refuel_order)?.setOnClickListener {
            ViewSetUtils.ButtonClickZoomInAnimation(it, 0.85f)

            startActivity(Intent(context, OrderRefurlActivity().javaClass))
//            if (MyApplication.isLogin){
//                startActivity(Intent(context, OrderRefurlActivity().javaClass))
//            }else{
//                startActivity(Intent(context,VsLoginActivity::class.java))
//            }

        }
        //我要赚钱
        view?.findViewById<LinearLayout>(R.id.ll_refuel_money)?.setOnClickListener {
            ViewSetUtils.ButtonClickZoomInAnimation(it, 0.85f)
            startActivity(Intent(context, MakeMoneyActivity().javaClass))
//            if (MyApplication.isLogin){
//                startActivity(Intent(context, MakeMoneyActivity().javaClass))
//            }else{
//                startActivity(Intent(context,VsLoginActivity::class.java))
//            }

        }

        //附近加油站
        view?.findViewById<LinearLayout>(R.id.ll_error)?.setOnClickListener {
            ViewSetUtils.ButtonClickZoomInAnimation(it, 0.85f)
            locationClient!!.startLocation()

        }
        //加油站详情
        view?.findViewById<LinearLayout>(R.id.rll_refuel)?.setOnClickListener {
            ViewSetUtils.ButtonClickZoomInAnimation(it, 0.85f)
            startActivity(Intent(context, RefuelDetailKTActivity().javaClass)?.putExtra("MoreReListRecords", JsonHelper.newtoJson(morerel)))
        }
        //导航
        view?.findViewById<RelativeLayout>(R.id.Relat_navigation)?.setOnClickListener {
            ViewSetUtils.ButtonClickZoomInAnimation(it, 0.85f)
            showMapPop(morerel)
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

    override fun onDestroy() {
        if (Inform_Target != null) {
            Inform_Target?.detachView()
        }
        super.onDestroy()
    }


    override fun onStop() {
        super.onStop()

    }

    override fun onFailure(e: Throwable?, code: Int, msg: String?, isNetWorkError: Boolean) {

    }

    override fun onSuccess(data: String?) {

    }

    //刷新数据
    private fun newData(userAddressLongitude: Double, userAddressLatitude: Double) {
        Inform_Target?.netWorkRequestGet(HttpURL.CheZhuBangControll, HttpRequest.CheZhuBang(
                "", "",
                userAddressLongitude, userAddressLatitude, HttpRequest.GetsearchContent(Constant.mFuelOilTypeList()[0].filtrateValue, Constant.mFiltrateList()[0].filtrateValue, Constant.mBrandList()), "1", "1", "8.3.11", "%22%22"), object : TaskCallback {
            @SuppressLint("SetTextI18n")
            override fun onSuccess(data: String?) {
                var entity: MoreReListBean = JSON.parseObject(data, MoreReListBean().javaClass)
                morerel = entity.data.records[0]

                ll_error.visibility = View.GONE
                rll_refuel.visibility = View.VISIBLE

                tv_oil_station_name.text = entity.data.records[0].gasName
                tv_oil_station_address.text = entity.data.records[0].gasAddress
                tv_oil_price.text = entity.data.records[0].priceYfq.toString() + ""
                tv_international_price.text = "国标价￥" + entity.data.records[0].priceOfficial
                tv_depreciate.text = "已降￥" + ArmsUtils.formatting(if (entity.data.records[0].priceOfficial > entity.data.records[0].priceYfq) entity.data.records[0].priceOfficial - entity.data.records[0].priceYfq else 0.00)
                tv_navigation.text = entity.data.records[0].distance.toString() + "km"
                mGoLatitude = entity.data.records[0].gasAddressLatitude
                mGoLongitude = entity.data.records[0].gasAddressLongitude
            }

            override fun onFailure(e: Throwable?, code: Int, msg: String?, isNetWorkError: Boolean) {
                rv_load.loadMoreFinish(false, true)
                ToastUtil.showMsg(msg)


            }
        })

    }

    //导航弹窗
    private fun showMapPop(data: MoreReListBean.MoreReListRecords?) {
        mapPop = CommonPopupWindow.Builder(context).setView(R.layout.pop_map).setWidthAndHeight(resources.getDimension(R.dimen.w_277_dip).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
                .setBackGroundLevel(0.5f).setViewOnclickListener { view, layoutResId ->
                    val amapCb = view.findViewById<View>(R.id.cb_amap) as CheckBox
                    var baidumapCb = view.findViewById<View>(R.id.cb_baidumap) as CheckBox
                    val rememberCb = view.findViewById<View>(R.id.cb_remember) as CheckBox
                    val ensureBtn = view.findViewById<View>(R.id.btn_ensure) as TextView

                    amapCb.setOnCheckedChangeListener { buttonView, isChecked ->
                        if (isChecked) {
                            baidumapCb.isChecked = false
                        }
                    }
                    baidumapCb.setOnCheckedChangeListener { buttonView, isChecked ->
                        if (isChecked) {
                            amapCb.isChecked = false
                        }
                    }
                    ensureBtn.setOnClickListener {
                        if (!baidumapCb.isChecked && !amapCb.isChecked) ToastUtil.showMsg("请选择一种地图")
                        if (baidumapCb.isChecked) {
                            if (StoreDetailActivity.mapisAvailable(context, "com.baidu.BaiduMap")) {
                                navWithBaidu()
                            } else {
                                ToastUtil.showMsg("您尚未安装百度地图")
                                val uri = Uri.parse("market://details?id=com.baidu.BaiduMap")
                                intent = Intent(Intent.ACTION_VIEW, uri)
                                startActivity(intent)
                            }
                        }
                        if (amapCb.isChecked) {
                            if (StoreDetailActivity.mapisAvailable(context, "com.autonavi.minimap")) {
                                navWithAmap()
                            } else {
                                Toast.makeText(context, "您尚未安装高德地图", Toast.LENGTH_LONG).show()
                                val uri = Uri.parse("market://details?id=com.autonavi.minimap")
                                intent = Intent(Intent.ACTION_VIEW, uri)
                                startActivity(intent)
                            }
                        }
                        if (rememberCb.isChecked && amapCb.isChecked) {
                            SpUtils.putIntValue(context, "selectMapFlag", 0)
                        } else if (rememberCb.isChecked && amapCb.isChecked) {
                            SpUtils.putIntValue(context, "selectMapFlag", 1)
                        }
                        mapPop!!.dismiss()
                    }


                }.setOutsideTouchable(true).create();
        mapPop?.showAtLocation(activity?.window?.decorView?.rootView, Gravity.CENTER, 0, 0)
    }

    private fun navWithBaidu() {
        val bdGps = RxLocationUtils.GCJ02ToBD09(mGoLongitude, mGoLatitude)
        val stringBuffer = StringBuffer("baidumap://map/navi?location=").append(bdGps.latitude).append(",").append(bdGps.longitude).append("&type=TIME")
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(stringBuffer.toString()))
        startActivity(intent)
    }

    private fun navWithAmap() {
        val intent = Intent("android.intent.action.VIEW", Uri.parse("androidamap://route?sourceApplication=appName&slat=&slon=&sname=我的位置&dlat=" + mGoLatitude +
                "&dlon=" + mGoLongitude + "&dname=目的地&dev=0&t=2"))
        startActivity(intent)
    }


}



