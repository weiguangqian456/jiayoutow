package com.edawtech.jiayou.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import com.edawtech.jiayou.R
import com.edawtech.jiayou.config.base.BaseMvpActivity
import com.edawtech.jiayou.config.bean.MoreReListBean
import com.edawtech.jiayou.config.bean.RefuelFiltrate
import com.edawtech.jiayou.config.constant.Constant.*
import com.edawtech.jiayou.mvp.presenter.PublicPresenter
import com.edawtech.jiayou.net.http.HttpRequest.CheZhuBang
import com.edawtech.jiayou.net.http.HttpRequest.GetsearchContent
import com.edawtech.jiayou.net.http.HttpURL.CheZhuBangControll
import com.edawtech.jiayou.net.observer.TaskCallback
import com.edawtech.jiayou.ui.adapter.BaseRecyclerAdapter
import com.edawtech.jiayou.ui.adapter.BaseRecyclerHolder
import com.edawtech.jiayou.ui.adapter.MoreRefuelAdapter
import com.edawtech.jiayou.ui.custom.CommonPopupWindow
import com.edawtech.jiayou.ui.dialog.RefuelFiltrateDialog
import com.edawtech.jiayou.utils.tool.*
import com.flyco.roundview.RoundTextView
import kotlinx.android.synthetic.main.activity_more_re_list.*
import java.io.Serializable
import java.util.*


class MoreReListActivity : BaseMvpActivity() {
    // 请求数据
    private var Inform_Target: PublicPresenter? = null
    private val mSearchType = "1001"
    private var mSearchText = ""
    private val mAdapter: MoreRefuelAdapter? = null

    private var mHttpPage = 1

    private var isNotMore = false
    private var isNowLoad = false

    var baseRecyclerAdapter: BaseRecyclerAdapter<MoreReListBean.MoreReListRecords>? = null
    var locationClient: AMapLocationClient? = null
    private var mFuelOilTypeDialog: RefuelFiltrateDialog? = null
    private var mFiltrateDialog: RefuelFiltrateDialog? = null
    private var mBrandDialog: RefuelFiltrateDialog? = null

    private var mFuelOilType: String? = null
    private var mFiltrate: String? = null
    private var brandBuilder: StringBuilder? = null
    var mBrandLisd: List<RefuelFiltrate> = ArrayList()
    private var mapPop: CommonPopupWindow? = null

    var option = AMapLocationClientOption()
    private var mLatitude = 0.00
    private var mLongitude = 0.00

    private var mGoLatitude = 0.0
    private var mGoLongitude = 0.0

    override val layoutId: Int
        get() = R.layout.activity_more_re_list

    override fun initView(savedInstanceState: Bundle?) {
        title_main_tv.leftBackImageTv.setOnClickListener { finish() }
        Inform_Target = PublicPresenter(context, false, "")
        Inform_Target?.attachView(this)
        locationClient = AMapLocationClient(this)
        /**
         * 设置签到场景，相当于设置为：
         * option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
         * option.setOnceLocation(true);
         * option.setOnceLocationLatest(true);
         * option.setMockEnable(false);
         * option.setWifiScan(true);
         * option.setGpsFirst(false);
         * 其他属性均为模式属性。
         * 如果要改变其中的属性，请在在设置定位场景之后进行
         */
        option.locationPurpose = AMapLocationClientOption.AMapLocationPurpose.SignIn
        locationClient!!.setLocationOption(option)
        locationClient?.setLocationListener {
            when (it.errorCode) {
                AMapLocation.LOCATION_SUCCESS -> {
                    mLatitude = it.latitude
                    mLongitude = it.longitude
                    newData("", "", mLongitude, mLatitude,
                            GetsearchContent(mFuelOilType, mFiltrate, mBrandLisd), "10", "1", "8.3.11", "%22%22")
                }

            }

        }

        locationClient!!.startLocation()
        mFuelOil()
        mFiltrate()
        mBrand()
        mFuelOilType = mFuelOilTypeList()[0].filtrateValue
        mFiltrate = mFiltrateList()[0].filtrateValue

        tv_fuel_oil_type.text = mFuelOilTypeList()[0].filtrateValue
        tv_filtrate.text = mFiltrateList()[0].filtrateName
        mBrandLisd = mBrandList()
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
                    mGoLongitude =data.gasAddressLongitude
                    showMapPop(data)


                }
                holder?.getView<LinearLayout>(R.id.Lin_rll_item)?.setOnClickListener {
                    startActivity(Intent(context,RefuelDetailKTActivity().javaClass)?.putExtra("MoreReListRecords", JsonHelper.newtoJson(data)))
                }

            }
        }
        rv_load.adapter = baseRecyclerAdapter

        srl_load.isRefreshing =true
        //下拉刷新
        srl_load.setOnRefreshListener {

            mHttpPage = 1
            //签到只需调用startLocation即可
            locationClient!!.startLocation()
        }
        rv_load.useDefaultLoadMore()
        rv_load.loadMoreFinish(false, true)
        rv_load.setLoadMoreListener {
            mHttpPage++
            Log.e("setLoadMoreListener", mHttpPage.toString())
          newData("", "", mLongitude, mLatitude,
                  GetsearchContent(mFuelOilTypeList()[0].filtrateValue, mFiltrateList()[0].filtrateValue, mBrandList()), "10", mHttpPage.toString(), "8.3.11", "%22%22")

        }

        ll_fuel_oil_type.setOnClickListener {
            if (mFuelOilTypeDialog!!.isShowing) {
                mFuelOilTypeDialog!!.dismiss()
            } else {
                mFuelOilTypeDialog!!.show()
            }
        }

        ll_filtrate.setOnClickListener {
            if (mFiltrateDialog!!.isShowing) {
                mFiltrateDialog!!.dismiss()
            } else {
                mFiltrateDialog!!.show()
            }
        }

        ll_brand.setOnClickListener {
            if (mBrandDialog!!.isShowing) {
                mBrandDialog!!.dismiss()
            } else {
                mBrandDialog!!.show()
            }

        }


    }

    override fun onResume() {
        super.onResume()

    }

    override fun onDestroy() {
        if (Inform_Target != null) {
            Inform_Target!!.detachView()
        }
        super.onDestroy()
        //销毁时，需要销毁定位client
        if (null != locationClient) {
            locationClient!!.onDestroy()
        }
    }

    override fun onSuccess(data: String?) {}

    override fun onFailure(e: Throwable?, code: Int, msg: String?, isNetWorkError: Boolean) {}

    //油号 下拉选择
    fun mFuelOil() {
        mFuelOilTypeDialog = RefuelFiltrateDialog(this, ll_refuel_filtrate)
        mFuelOilTypeDialog?.setData(mFuelOilTypeList())
        mFuelOilTypeDialog?.mAdapter?.setRecycleClickListener {
            var item = mFuelOilTypeDialog!!.mAdapter.list[it]
            mFuelOilType = item.filtrateValue
            tv_fuel_oil_type.text = item.filtrateName
            baseRecyclerAdapter?.clear()
            mHttpPage = 1
            locationClient!!.startLocation()
            mFuelOilTypeDialog!!.dismiss()

        }
    }

    //排序
    fun mFiltrate() {
        mFiltrateDialog = RefuelFiltrateDialog(this, ll_refuel_filtrate)
        mFiltrateDialog?.setData(mFiltrateList())
        mFiltrateDialog?.mAdapter?.setRecycleClickListener {
            val item = mFiltrateDialog!!.mAdapter.list[it]
            mFiltrate = item.filtrateValue
            tv_filtrate.text = item.filtrateName
            baseRecyclerAdapter?.clear()
            mHttpPage = 1
            srl_load.isRefreshing =true
            locationClient!!.startLocation()
            mFiltrateDialog!!.dismiss()
        }
    }

    //品牌
    fun mBrand() {
        mBrandDialog = RefuelFiltrateDialog(this, ll_refuel_filtrate)
        mBrandDialog?.setData(mBrandList())
        mBrandDialog!!.setIsAllChoose(true)
        mBrandDialog?.setOnClickListener {
            when (it.id) {
                R.id.rtv_cancel -> mBrandDialog!!.dismiss()
                R.id.rtv_confirm -> {
                    mBrandLisd = mBrandDialog!!.mAdapter.chooseList
                    tv_brand.text = (if (mBrandLisd.size == mBrandDialog!!.mAdapter.list.size) "全部品牌" else "部分品牌")
                    baseRecyclerAdapter?.clear()
                    mHttpPage = 1
                    srl_load.isRefreshing = true
                    locationClient!!.startLocation()
                    mBrandDialog!!.dismiss()
                }
            }

        }
    }


    //刷新数据
    fun newData(phone: String, searchType: String, userAddressLongitude: Double,
                userAddressLatitude: Double, searchContent: String, pageSize: String,
                pageNum: String, version: String, uid: String) {
        Inform_Target?.netWorkRequestGet(CheZhuBangControll, CheZhuBang(
                "", "",
                userAddressLongitude, userAddressLatitude, GetsearchContent(mFuelOilType, mFiltrate, mBrandLisd), "10", mHttpPage.toString(), "8.3.11", "%22%22"), object : TaskCallback {
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
                }
            }

            override fun onFailure(e: Throwable?, code: Int, msg: String?, isNetWorkError: Boolean) {
                rv_load.loadMoreFinish(false, true)
                ToastUtil.showMsg(msg)
            }
        })

    }

    //导航弹窗
    private fun showMapPop(data: MoreReListBean.MoreReListRecords) {
        mapPop = CommonPopupWindow.Builder(this).setView(R.layout.pop_map).setWidthAndHeight(resources.getDimension(R.dimen.w_277_dip).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
                .setBackGroundLevel(0.5f).setViewOnclickListener { view, layoutResId ->
                    val amapCb = view.findViewById<View>(R.id.cb_amap) as CheckBox
                    var baidumapCb = view.findViewById<View>(R.id.cb_baidumap) as CheckBox
                    val rememberCb = view.findViewById<View>(R.id.cb_remember) as CheckBox
                    val ensureBtn = view.findViewById<View>(R.id.btn_ensure) as TextView

                    amapCb.setOnCheckedChangeListener { buttonView, isChecked -> if (isChecked){
                          baidumapCb.isChecked = false
                      } }
                    baidumapCb.setOnCheckedChangeListener { buttonView, isChecked -> if (isChecked){
                        amapCb.isChecked = false } }

                    ensureBtn.setOnClickListener {
                        if (!baidumapCb.isChecked && !amapCb.isChecked)  ToastUtil.showMsg("请选择一种地图")
                        if (baidumapCb.isChecked) {
                            if(StoreDetailActivity.mapisAvailable(context, "com.baidu.BaiduMap")){
                                navWithBaidu()
                            }else{
                                ToastUtil.showMsg("您尚未安装百度地图")
                                val uri = Uri.parse("market://details?id=com.baidu.BaiduMap")
                                intent = Intent(Intent.ACTION_VIEW, uri)
                                startActivity(intent)
                            }
                        }
                        if (amapCb.isChecked){
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
                        mapPop!!.dismiss() }





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