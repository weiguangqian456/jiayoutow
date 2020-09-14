package com.edawtech.jiayou.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.alibaba.fastjson.JSON
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
import com.edawtech.jiayou.ui.dialog.RefuelFiltrateDialog
import com.edawtech.jiayou.utils.tool.ArmsUtils
import com.edawtech.jiayou.utils.tool.ToastUtil
import com.flyco.roundview.RoundTextView
import kotlinx.android.synthetic.main.activity_more_re_list.*
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

    private var mFuelOilTypeDialog: RefuelFiltrateDialog? = null
    private var mFiltrateDialog: RefuelFiltrateDialog? = null
    private var mBrandDialog: RefuelFiltrateDialog? = null

    private var mFuelOilType: String? = null
    private var mFiltrate: String? = null
    private var brandBuilder: StringBuilder? = null
    var mBrandLisd: List<RefuelFiltrate> = ArrayList()

    override val layoutId: Int
        get() = R.layout.activity_more_re_list

    override fun initView(savedInstanceState: Bundle?) {
        title_main_tv.leftBackImageTv.setOnClickListener { finish() }
        Inform_Target = PublicPresenter(context, true, "加载中...")
        Inform_Target?.attachView(this)
        mFuelOil()
        mFiltrate()
        mBrand()
        mFuelOilType = mFuelOilTypeList()[0].filtrateValue
        mFiltrate = mFiltrateList()[0].filtrateName

        tv_fuel_oil_type.text = mFuelOilType
        tv_filtrate.text = mFiltrate
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

            }
        }
        rv_load.adapter = baseRecyclerAdapter


        //下拉刷新
        srl_load.setOnRefreshListener {
            mHttpPage = 1
            baseRecyclerAdapter?.clear()
            newData("", "", 114.11445, 22.554025,
                    GetsearchContent(mFuelOilTypeList()[0].filtrateValue, mFiltrateList()[0].filtrateValue, mBrandList()), "10", mHttpPage.toString(), "8.3.11", "%22%22")
        }
        rv_load.useDefaultLoadMore()
        rv_load.loadMoreFinish(false, true)
        rv_load.setLoadMoreListener {
            mHttpPage++
            Log.e("setLoadMoreListener", mHttpPage.toString())
            newData("", "", 114.11445, 22.554025,
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

        newData("", "", 114.11445, 22.554025,
                GetsearchContent(mFuelOilType, mFiltrate, mBrandLisd), "10", "1", "8.3.11", "%22%22")


    }
    override fun onDestroy() {
        if (Inform_Target != null) {
            Inform_Target!!.detachView()
        }
        super.onDestroy()
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
            onResume()
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
            onResume()
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
                    onResume()
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
                114.11445, 22.554025, GetsearchContent(mFuelOilTypeList()[0].filtrateValue, mFiltrateList()[0].filtrateValue, mBrandList()), "10", mHttpPage.toString(), "8.3.11", "%22%22"), object : TaskCallback {
            override fun onSuccess(data: String?) {
                srl_load.isRefreshing = false
                rv_load.loadMoreFinish(false, true)

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


}