package com.edawtech.jiayou.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.alibaba.fastjson.JSON
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.androidkun.xtablayout.XTabLayout
import com.edawtech.jiayou.R
import com.edawtech.jiayou.config.base.BaseMvpActivity
import com.edawtech.jiayou.config.base.MyApplication
import com.edawtech.jiayou.config.bean.MoreReListBean
import com.edawtech.jiayou.mvp.presenter.PublicPresenter
import com.edawtech.jiayou.net.http.HttpRequest
import com.edawtech.jiayou.net.http.HttpURL
import com.edawtech.jiayou.ui.adapter.BaseRecyclerAdapter
import com.edawtech.jiayou.ui.adapter.BaseRecyclerHolder
import com.edawtech.jiayou.ui.custom.CommonPopupWindow
import com.edawtech.jiayou.ui.custom.CustomErrorView
import com.edawtech.jiayou.utils.tool.*
import com.edawtech.jiayou.utils.tool.unit.DensityUtils
import com.flyco.roundview.RoundTextView
import kotlinx.android.synthetic.main.activity_otil_search.*

/**
 * 加油站搜索
 *
 * */
class RefuelKotilActivity : BaseMvpActivity() {

    private var mSearchType = "1001"
    private var mSearchText = ""

    private var Inform_Target: PublicPresenter? = null

    var locationClient: AMapLocationClient? = null
    var option = AMapLocationClientOption()

    lateinit var layoutParams: ViewGroup.MarginLayoutParams

    private var mLatitude = 0.00
    private var mLongitude = 0.00

    private var mGoLatitude = 0.0
    private var mGoLongitude = 0.0

    private var mHttpPage = 1

    var baseRecyclerAdapter: BaseRecyclerAdapter<MoreReListBean.MoreReListRecords>? = null
    private var mapPop: CommonPopupWindow? = null

    override val layoutId: Int
        get() = R.layout.activity_otil_search

    override fun initView(savedInstanceState: Bundle?) {
        Inform_Target = PublicPresenter(this, true, "加载中")
        Inform_Target?.attachView(this)
        locationClient = AMapLocationClient(this)
        option.locationPurpose = AMapLocationClientOption.AMapLocationPurpose.SignIn
        locationClient!!.setLocationOption(option)

        locationClient?.setLocationListener {
            when (it.errorCode) {
                AMapLocation.LOCATION_SUCCESS -> {
                    mLatitude = it.latitude
                    mLongitude = it.longitude

                    refresh()?.let { it1 ->
                        newHttpData("", "1002", mLongitude, mLatitude,
                                it1, "10", mHttpPage.toString(), "8.3.11", MyApplication.UID)
                    }

                }
            }
        }



        fl_back.setOnClickListener { finish() }
        et_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mSearchText = et_search.text.toString().trim { it <= ' ' }
                iv_search_close.visibility = if (mSearchText.isNotEmpty()) View.VISIBLE else View.GONE
                tv_serach.visibility = if (mSearchText.isNotEmpty()) View.VISIBLE else View.GONE
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        tablayout.addTab(tablayout.newTab().setText("目的地"))
        tablayout.addTab(tablayout.newTab().setText("加油站"))
        tablayout.setOnTabSelectedListener(object : XTabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: XTabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> {
                        mSearchType = "1001"
                        et_search.hint = "请输入要搜索的目的地"
                        if (et_search.text.toString().isNotEmpty()) {
                            mHttpPage = 1
                            locationClient?.startLocation()
                        }
                    }
                    1 -> {
                        mSearchType = "1002"
                        et_search.hint = "请输入要搜索的加油站"
                        if (et_search.text.toString().isNotEmpty()) {
                            mHttpPage = 1
                            locationClient?.startLocation()
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: XTabLayout.Tab?) {

            }

            override fun onTabReselected(tab: XTabLayout.Tab?) {

            }

        })

        iv_search_close.setOnClickListener { et_search.setText("") }
        tv_serach.setOnClickListener {
            mHttpPage = 1
            locationClient?.startLocation()
        }


        layoutParams = cev_view.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.topMargin = DensityUtils.dp2px(this, 287f)

        cev_view.layoutParams = layoutParams
        cev_view.setShowView()

        baseRecyclerAdapter = object : BaseRecyclerAdapter<MoreReListBean.MoreReListRecords>(this, null, R.layout.item_more_refuel) {
            @SuppressLint("SetTextI18n")
            override fun convert(holder: BaseRecyclerHolder?, data: MoreReListBean.MoreReListRecords?, position: Int, isScrolling: Boolean, selectedPosition: Int) {
                holder?.getView<TextView>(R.id.tv_oil_station_name)?.text = data?.gasName
                holder?.getView<TextView>(R.id.tv_oil_station_address)?.text = data?.gasAddress
                holder?.getView<RoundTextView>(R.id.tv_depreciate)?.text = data?.distance
                holder?.getView<TextView>(R.id.tv_international_price)?.text = "国标价￥" + data?.priceOfficial
                holder?.getView<TextView>(R.id.tv_depreciate)?.text = "已降￥" + ArmsUtils.formatting(if (data?.priceOfficial!! > data?.priceYfq!!) data?.priceOfficial - data?.priceYfq!! else 0.00)
                holder?.getView<TextView>(R.id.tv_navigation)?.text = data?.distance + "km"
                holder?.getView<TextView>(R.id.tv_oil_price)?.text = data?.priceYfq.toString()
                holder?.getView<LinearLayout>(R.id.Lv_navigation)?.setOnClickListener {
                    mGoLatitude = data.gasAddressLatitude
                    mGoLongitude = data.gasAddressLongitude
                    showMapPop(data)


                }
                holder?.getView<LinearLayout>(R.id.Lin_rll_item)?.setOnClickListener {
                    startActivity(Intent(context, RefuelDetailKTActivity().javaClass)?.putExtra("MoreReListRecords", JsonHelper.newtoJson(data)))
                }

            }
        }
        rv_load.adapter = baseRecyclerAdapter

        //下拉刷新
        srl_load.setOnRefreshListener {
            if (et_search.text.toString().isNotEmpty()) {
                mHttpPage = 1
                Log.e("setLoadMoreListener", "---" + et_search.text.toString() + "--et_search.text.toString()-" + et_search.text.toString().length)
                //签到只需调用startLocation即可
                locationClient!!.startLocation()
            } else {
                ToastUtil.showMsg("请输入搜索内容！")
                srl_load.isRefreshing = false
            }


        }
        rv_load.useDefaultLoadMore()
        rv_load.loadMoreFinish(false, true)
        rv_load.setLoadMoreListener {
            mHttpPage++
            Log.e("setLoadMoreListener", mHttpPage.toString())
            refresh()?.let { it1 ->
                Log.e("setLoadMoreListener", it1)
                newHttpData("", "", mLongitude, mLatitude,
                        it1, "10", mHttpPage.toString(), "8.3.11", "%22%22")
            }
        }

    }

    override fun onDestroy() {
        if (Inform_Target != null) {
            Inform_Target?.detachView()
        }
        super.onDestroy()
    }

    override fun onSuccess(data: String?) {
        srl_load.isRefreshing = false
        rv_load.loadMoreFinish(false, true)
        if (mHttpPage == 1) {
            baseRecyclerAdapter?.clear()
        }
        var entity: MoreReListBean = JSON.parseObject(data, MoreReListBean().javaClass)
        if (entity.data.records.size > 0 || entity.data.records != null) {
            baseRecyclerAdapter?.setData(entity.data.records as MutableList<MoreReListBean.MoreReListRecords>?)
        } else {
            rv_load.loadMoreFinish(true, false)
            if (mHttpPage == 1) {
                cev_view?.initState(CustomErrorView.ERROR_VIEW_EMPTY)
            } else {
                cev_view?.initState(CustomErrorView.ERROR_NOT)
            }
        }

    }

    override fun onFailure(e: Throwable?, code: Int, msg: String?, isNetWorkError: Boolean) {

        srl_load.isRefreshing = false
        rv_load.loadMoreFinish(false, true)
        if (mHttpPage == 1) {
            cev_view?.initState(CustomErrorView.ERROR_VIEW_EMPTY)
        } else {
            cev_view?.initState(CustomErrorView.ERROR_NOT)
        }
        //   ToastUtil.showMsg(msg)
    }

    private fun newHttpData(
            phone: String, searchType: String, userAddressLongitude: Double,
            userAddressLatitude: Double, searchContent: String, pageSize: String,
            pageNum: String, version: String, uid: String
    ) {
        Inform_Target?.netWorkRequestGet(HttpURL.CheZhuBangControll, HttpRequest.CheZhuBang(phone, searchType, userAddressLongitude, userAddressLatitude, searchContent, pageSize, pageNum, version, uid))
    }


    private fun refresh(): String? {
        mSearchText = et_search.text.toString()
        var searchContent = "$mSearchType@$mSearchText"
        Log.e("setLoadMoreListener", searchContent)
        return Base64.encodeToString(searchContent.toByteArray(), Base64.NO_WRAP)
    }

    //导航弹窗
    private fun showMapPop(data: MoreReListBean.MoreReListRecords) {
        mapPop = CommonPopupWindow.Builder(this).setView(R.layout.pop_map).setWidthAndHeight(resources.getDimension(R.dimen.w_277_dip).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
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
        mapPop?.showAtLocation(window.decorView.rootView, Gravity.CENTER, 0, 0)
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