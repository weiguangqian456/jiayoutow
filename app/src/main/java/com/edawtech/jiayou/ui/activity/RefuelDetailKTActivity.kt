package com.edawtech.jiayou.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.TextUtils
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
import com.edawtech.jiayou.ui.statusbar.StatusBarUtil
import com.edawtech.jiayou.utils.glide.JudgeImageUrlUtils
import com.edawtech.jiayou.utils.tool.ArmsUtils
import com.edawtech.jiayou.utils.tool.GsonUtils
import com.edawtech.jiayou.utils.tool.SoftHideKeyBoardUtil
import com.edawtech.jiayou.utils.tool.ToastUtil
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
    private var mGoLatitude: Double? = null
    private var mGoLongitude: Double? = null

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
        mGoLatitude = MoreReLis?.gasAddressLatitude
        mGoLongitude = MoreReLis?.gasAddressLongitude
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
        Inform_Target?.netWorkRequestPost(queryPriceByPhone, HttpRequest.queryPrice(MoreReLis?.gasId, MyApplication.MOBILE), object : TaskCallback {
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
            isRefuelbalance()
          //  if (MyApplication.isLogin)  isRefuelbalance() else startActivity(Intent(context,VsLoginActivity().javaClass))

        }

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
        mOilNoAdapter = OilNoAdapter(context)
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
        mGunNoAdapter = GunNoAdapter(context)
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


}