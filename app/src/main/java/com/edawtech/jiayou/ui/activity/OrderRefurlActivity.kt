package com.edawtech.jiayou.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.edawtech.jiayou.R
import com.edawtech.jiayou.config.base.BaseMvpActivity
import com.edawtech.jiayou.config.bean.RefuelOrderList
import com.edawtech.jiayou.config.bean.ResultEntity
import com.edawtech.jiayou.mvp.presenter.PublicPresenter
import com.edawtech.jiayou.net.http.HttpRequest
import com.edawtech.jiayou.net.http.HttpURL
import com.edawtech.jiayou.ui.adapter.RefuelOrderAdapter
import com.edawtech.jiayou.ui.dialog.PromptDialog
import com.edawtech.jiayou.utils.tool.GsonUtils
import kotlinx.android.synthetic.main.activity_order_refurl.*

/**
 *
 * 加油订单*/
class OrderRefurlActivity : BaseMvpActivity() {
    // 请求数据
    private var Inform_Target: PublicPresenter? = null


    private val mAdapter: RefuelOrderAdapter? = null

    override val layoutId: Int
        get() = R.layout.activity_order_refurl


    override fun initView(savedInstanceState: Bundle?) {
        Inform_Target = PublicPresenter(this, false, "")
        Inform_Target?.attachView(this)

        tv_title.text = "加油订单"
        val headerView = LayoutInflater.from(this).inflate(R.layout.header_refuel_order, null, false)
        tv_invoice.setOnClickListener { Dialgon() }
        fl_back.setOnClickListener { finish() }
        Inform_Target?.netWorkRequestGet(HttpURL.OrderZhuBangquery, HttpRequest.OrderRefurlist("13612810115", "10", "1", "8.3.11"))

    }

    override fun onDestroy() {
        if (Inform_Target != null) {
            Inform_Target?.detachView()
        }
        super.onDestroy()
    }

    @SuppressLint("LogNotTimber")
    override fun onSuccess(data: String?) {
        var resultEntity: ResultEntity = GsonUtils.getGson().fromJson(data, ResultEntity().javaClass)
        if (resultEntity.data !=null){
            var   refuelOrderList = JSON.parseObject(resultEntity.data.toString(), RefuelOrderList::class.java)
            var array: JSONObject? = JSON.parseObject(refuelOrderList.orderList.toString())

            Log.e("OrderRefurlActivity",resultEntity.data.toString())
            Log.e("OrderRefurlActivity",refuelOrderList.orderList.toString())


        }


    }

    override fun onFailure(e: Throwable?, code: Int, msg: String?, isNetWorkError: Boolean) {

    }

    fun Dialgon() {
        PromptDialog(this@OrderRefurlActivity).widthScale(0.8f)
                .setTitle("开具发票")
                .setContent("请直接拨打客服电话：400-0365-388  来开具发票。")
                .show()
    }
}