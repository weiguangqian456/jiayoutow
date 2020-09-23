package com.edawtech.jiayou.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import com.alibaba.fastjson.JSON
import com.bumptech.glide.Glide
import com.edawtech.jiayou.R
import com.edawtech.jiayou.config.base.BaseMvpActivity
import com.edawtech.jiayou.config.base.MyApplication
import com.edawtech.jiayou.config.bean.MoreReListBean
import com.edawtech.jiayou.config.bean.RefuelDetailBean
import com.edawtech.jiayou.config.bean.ResultEntity
import com.edawtech.jiayou.mvp.presenter.PublicPresenter
import com.edawtech.jiayou.net.http.HttpRequest
import com.edawtech.jiayou.net.http.HttpURL
import com.edawtech.jiayou.net.http.HttpURL.queryPriceByPhone
import com.edawtech.jiayou.net.observer.TaskCallback
import com.edawtech.jiayou.ui.adapter.GunNoAdapter
import com.edawtech.jiayou.ui.adapter.OilNoAdapter
import com.edawtech.jiayou.ui.custom.CommonPopupWindow
import com.edawtech.jiayou.ui.statusbar.StatusBarUtil
import com.edawtech.jiayou.utils.glide.JudgeImageUrlUtils
import com.edawtech.jiayou.utils.tool.*
import kotlinx.android.synthetic.main.activity_refuel_detail.*
import kotlin.math.abs

//加油站详情
class RefuelDetailKTActivity : BaseMvpActivity() {
    // 请求数据
    private var Inform_Target: PublicPresenter? = null
    private var MoreReLis: MoreReListBean.MoreReListRecords? = null

    private var mOilStationId: String? = null
    private var mSource: String? = null
    private var mOilStationName: String? = null
    private var mOilName: String? = null
    private var mGunNo: String? = null
    private var mOilNo: String? = null

    private var mOilNoAdapter: OilNoAdapter? = null
    private var mGunNoAdapter: GunNoAdapter? = null
    private val mAlphaHeight = 300
    private var mGoLatitude = 0.0
    private var mGoLongitude = 0.0


    private var mapPop: CommonPopupWindow? = null
    override val layoutId: Int
        get() = R.layout.activity_refuel_detail

    override fun initView(savedInstanceState: Bundle?) {

        StatusBarUtil.setRootViewFitsSystemWindows(this, false)
        SoftHideKeyBoardUtil.assistActivity(this)

        Inform_Target = PublicPresenter(context, false, "")
        Inform_Target?.attachView(this)
        tablayout.addTab(tablayout.newTab().setText("我要加油"))
        tv_prompt.text = Html.fromHtml(resources.getString(R.string.refuel_detail_prompt))
        MoreReLis = GsonUtils.getGson().fromJson(intent.getStringExtra("MoreReListRecords"), MoreReListBean.MoreReListRecords().javaClass)

        mOilStationId = MoreReLis?.gasId
        mOilStationName = MoreReLis?.gasName
        mGoLatitude = MoreReLis?.gasAddressLatitude!!
        mGoLongitude = MoreReLis?.gasAddressLongitude!!
        Glide.with(mContext!!).load(JudgeImageUrlUtils.isAvailable(MoreReLis?.gasLogoBig)).into(iv_oil_station_image)

        tv_oil_station_name.text = MoreReLis?.gasName
        tv_oil_station_address.text = MoreReLis?.gasAddress
        tv_navigation.text = MoreReLis?.distance.toString() + "km"

        SetsOilNoAdapter()
        setsGunNoAdapter()
        nestedScrollView.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            val absVerticalOffset = abs(scrollY).toFloat()
            val alpha = if (absVerticalOffset > 150) 0.5f else absVerticalOffset / mAlphaHeight
            tv_title_background.alpha = alpha
        }
        Inform_Target?.netWorkRequestPost(queryPriceByPhone, HttpRequest.queryPrice(MoreReLis?.gasId,"13822438649"), object : TaskCallback {
            @SuppressLint("SetTextI18n")
            override fun onSuccess(data: String?) {
                var refuelDetail: RefuelDetailBean = JSON.parseObject(data, RefuelDetailBean().javaClass)
                mOilStationId = refuelDetail.data.gasId
                mOilStationName = refuelDetail.data.gasName
                mSource = refuelDetail.data.source
                mOilNo = refuelDetail.data.oilPriceList[0].oilNo

                val oilPriceZero: RefuelDetailBean.RefuelDetaiOilPriceList = refuelDetail.data.oilPriceList[0]
                val gunNosZero: RefuelDetailBean.RefuelDetaiOilGunNos = oilPriceZero.gunNos[0]

                tv_oil_price.text = oilPriceZero.priceYfq.toString() + ""
                tv_international_price.text = "比国标价降" + ArmsUtils.formatting(if (oilPriceZero.priceOfficial > oilPriceZero.priceYfq) oilPriceZero.priceOfficial - oilPriceZero.priceYfq else 0.00).toString() + "元"
                tv_depreciate.text = "比本站降" + ArmsUtils.formatting(if (oilPriceZero.priceGun > oilPriceZero.priceYfq) oilPriceZero.priceGun - oilPriceZero.priceYfq else 0.00).toString() + "元"

                oilPriceZero.check = true
                mOilName = oilPriceZero.oilName
                mOilNoAdapter!!.list = refuelDetail.data.oilPriceList
                mOilNoAdapter!!.notifyDataSetChanged()

                gunNosZero.check = true
                mGunNo = gunNosZero.gunNo
                mGunNoAdapter!!.list = oilPriceZero.gunNos
                mGunNoAdapter!!.notifyDataSetChanged()


            }

            override fun onFailure(e: Throwable?, code: Int, msg: String?, isNetWorkError: Boolean) {

            }

        })
        fl_back.setOnClickListener { finish() }
        rtv_confirm.setOnClickListener {
         if (MyApplication.isLogin)  isRefuelbalance() else startActivity(Intent(context,LoginActivity().javaClass))
        }
        iv_navigation.setOnClickListener { showMapPop(MoreReLis!!) }

    }

    override fun onDestroy() {
        if (Inform_Target != null) {
            Inform_Target!!.detachView()
        }
        super.onDestroy()
    }

    override fun onSuccess(data: String?) {

    }

    override fun onFailure(e: Throwable?, code: Int, msg: String?, isNetWorkError: Boolean) {

    }

    @SuppressLint("SetTextI18n")
    fun SetsOilNoAdapter() {
        mOilNoAdapter = OilNoAdapter(this)
        oil_no_recyclerView.adapter = mOilNoAdapter
        mOilNoAdapter?.setRecycleClickListener {

            val oilPriceList: RefuelDetailBean.RefuelDetaiOilPriceList = mOilNoAdapter!!.list[it]
            val gunNos: RefuelDetailBean.RefuelDetaiOilGunNos = oilPriceList.gunNos[0]
            mGunNoAdapter!!.cancelCheck()
            gunNos.check = true

            tv_oil_price.text = oilPriceList.priceYfq.toString() + ""
            tv_international_price.text = "比国标价降" + ArmsUtils.formatting(if (oilPriceList.priceOfficial > oilPriceList.priceYfq) oilPriceList.priceOfficial - oilPriceList.priceYfq else 0.00).toString() + "元"
            tv_depreciate.text = "比本站降" + ArmsUtils.formatting(if (oilPriceList.priceGun > oilPriceList.priceYfq) oilPriceList.priceGun - oilPriceList.priceYfq else 0.00).toString() + "元"


            mOilName = oilPriceList.oilName
            mGunNo = gunNos.gunNo
            mOilNo = oilPriceList.oilNo
            mGunNoAdapter!!.list = oilPriceList.gunNos
            mGunNoAdapter!!.notifyDataSetChanged()

        }

    }

    fun setsGunNoAdapter() {
        mGunNoAdapter = GunNoAdapter(this)
        gun_no_recyclerView.adapter = mGunNoAdapter
        mGunNoAdapter?.setRecycleClickListener {
            val gunNos: RefuelDetailBean.RefuelDetaiOilGunNos = mGunNoAdapter!!.list[it]
            mGunNo = gunNos.gunNo
        }

}

    private fun isRefuelbalance() {
        Inform_Target?.netWorkRequestGet(HttpURL.isRefuelbalance, HttpRequest.Refuelbalance(MyApplication.MOBILE), object : TaskCallback {
            override fun onSuccess(data: String?) {
                val resultData = GsonUtils.getGson().fromJson(data, ResultEntity::class.java)
                if (TextUtils.isEmpty(resultData.msg)) {
                    ToastUtil.showMsg(resultData.msg)
                }
                if (null != mSource && mSource == "1001")
                    RefuelCouponMoneyActivity.start(this@RefuelDetailKTActivity, MoreReLis?.gasId, mOilNo, mGunNo)
                else WebPageNavigationActivity.start(this@RefuelDetailKTActivity, mOilStationId, mOilStationName, mOilName, mGunNo)

            }

            override fun onFailure(e: Throwable?, code: Int, msg: String?, isNetWorkError: Boolean) {

            }

        })
    }

    //导航弹窗
    private fun showMapPop(data: MoreReListBean.MoreReListRecords) {
        mapPop = CommonPopupWindow.Builder(this).setView(R.layout.pop_map).setWidthAndHeight(resources.getDimension(R.dimen.w_277_dip).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
                .setBackGroundLevel(0.5f).setViewOnclickListener { view, _ ->
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
        mapPop?.showAtLocation(window.decorView.rootView, Gravity.CENTER, 0, 0)
    }

    private fun navWithBaidu() {
        val bdGps =RxLocationUtils.GCJ02ToBD09(mGoLongitude, mGoLatitude)
        val stringBuffer = StringBuffer("baidumap://map/navi?location=").append(bdGps?.latitude).append(",").append(bdGps?.longitude).append("&type=TIME")
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(stringBuffer.toString()))
        startActivity(intent)
    }

    private fun navWithAmap() {
        val intent = Intent("android.intent.action.VIEW", Uri.parse("androidamap://route?sourceApplication=appName&slat=&slon=&sname=我的位置&dlat=" + mGoLatitude +
                "&dlon=" + mGoLongitude + "&dname=目的地&dev=0&t=2"))
        startActivity(intent)
    }



}