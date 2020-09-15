package com.edawtech.jiayou.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import com.alibaba.fastjson.JSON
import com.bumptech.glide.Glide
import com.edawtech.jiayou.R
import com.edawtech.jiayou.config.base.BaseMvpActivity
import com.edawtech.jiayou.config.bean.MoreReListBean
import com.edawtech.jiayou.config.bean.RefuelDetail
import com.edawtech.jiayou.mvp.presenter.PublicPresenter
import com.edawtech.jiayou.net.http.HttpRequest
import com.edawtech.jiayou.net.http.HttpURL.queryPriceByPhone
import com.edawtech.jiayou.net.observer.TaskCallback
import com.edawtech.jiayou.ui.adapter.GunNoAdapter
import com.edawtech.jiayou.ui.adapter.OilNoAdapter
import com.edawtech.jiayou.utils.glide.JudgeImageUrlUtils
import com.edawtech.jiayou.utils.tool.ArmsUtils
import com.edawtech.jiayou.utils.tool.GsonUtils
import kotlinx.android.synthetic.main.activity_refuel_detail.*

class RefuelDetailKTActivity : BaseMvpActivity() {
    // 请求数据
    private var Inform_Target: PublicPresenter? = null
    private var MoreReLis: MoreReListBean.MoreReListRecords? = null

    private var mOilStationId: String? = null
    private var mSource: String? = null
    private var mOilStationName: String? = null
    private var mOilName: String? = null
    private val mGunNo: String? = null
    private var mOilNo: String? = null

    private var mOilNoAdapter: OilNoAdapter? = null
    private var mGunNoAdapter: GunNoAdapter? = null


    override val layoutId: Int
        get() = R.layout.activity_refuel_detail

    override fun initView(savedInstanceState: Bundle?) {
        Inform_Target = PublicPresenter(context, false, "")
        Inform_Target?.attachView(this)

        SetsOilNoAdapter()
        setsGunNoAdapter()

        tablayout.addTab(tablayout.newTab().setText("我要加油"))
        tv_prompt.text = Html.fromHtml(resources.getString(R.string.refuel_detail_prompt))
        MoreReLis = GsonUtils.getGson().fromJson(intent.getStringExtra("MoreReListRecords"), MoreReListBean.MoreReListRecords().javaClass)
        Glide.with(mContext!!).load(JudgeImageUrlUtils.isAvailable(MoreReLis?.gasLogoBig)).into(iv_oil_station_image)

        Inform_Target?.netWorkRequestPost(queryPriceByPhone, HttpRequest.queryPrice(MoreReLis?.gasId, "13822438649"), object : TaskCallback {
            @SuppressLint("SetTextI18n")
            override fun onSuccess(result: String?) {
                val data: RefuelDetail = JSON.parseObject(result, RefuelDetail::class.java)

                mOilStationId = data.gasId
                mOilStationName = data.gasName
                mSource = data.source
                mOilNo = data.oilPriceList[0].oilNo

                val oilPriceZero: RefuelDetail.OilPriceList = data.oilPriceList[0]
                val gunNosZero: RefuelDetail.OilPriceList.GunNos = oilPriceZero.gunNos[0]

                tv_oil_price.text = oilPriceZero?.priceYfq.toString()
                tv_international_price.text = "比国标价降" + ArmsUtils.formatting(if (oilPriceZero.priceOfficial > oilPriceZero.priceYfq) oilPriceZero.priceOfficial - oilPriceZero.priceYfq else 0.00).toString() + "元"
                tv_depreciate.text = "比本站降" + ArmsUtils.formatting(if (oilPriceZero.priceGun > oilPriceZero.priceYfq) oilPriceZero.priceGun - oilPriceZero.priceYfq else 0.00).toString() + "元"

                oilPriceZero.check = true
                mOilName = oilPriceZero.oilName


            }

            override fun onFailure(e: Throwable?, code: Int, msg: String?, isNetWorkError: Boolean) {

            }

        })
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

    fun SetsOilNoAdapter() {
        mOilNoAdapter = OilNoAdapter(this)
        oil_no_recyclerView.adapter = mOilNoAdapter
        mOilNoAdapter?.setRecycleClickListener {

            val oilPriceList: RefuelDetail.OilPriceList = mOilNoAdapter!!.list[it]
            val gunNos: RefuelDetail.OilPriceList.GunNos = oilPriceList.gunNos[0]


        }

    }

    fun setsGunNoAdapter() {
        mGunNoAdapter = GunNoAdapter(this)
        gun_no_recyclerView.adapter = mGunNoAdapter

    }

}