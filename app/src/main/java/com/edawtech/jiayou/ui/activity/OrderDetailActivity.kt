package com.edawtech.jiayou.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import com.edawtech.jiayou.R
import com.edawtech.jiayou.config.base.BaseMvpActivity
import com.edawtech.jiayou.config.bean.OrderRefurlBean
import com.edawtech.jiayou.utils.tool.GsonUtils
import kotlinx.android.synthetic.main.activity_order_detail.*

class OrderDetailActivity : BaseMvpActivity() {
    var mRefuelOrder: OrderRefurlBean.OrderRefurlOrderList? = null

    override val layoutId: Int
        get() = R.layout.activity_order_detail

    @SuppressLint("SetTextI18n")
    override fun initView(savedInstanceState: Bundle?) {
        title_order.leftBackImageTv.setOnClickListener { finish() }
        mRefuelOrder = GsonUtils.getGson().fromJson(intent.getStringExtra("OrderDetail"), OrderRefurlBean.OrderRefurlOrderList().javaClass)

        tv_order_id.text = mRefuelOrder?.orderId
        tv_oil_station_name.text = mRefuelOrder?.gasName
        tv_oil_station_address.text = mRefuelOrder?.province + mRefuelOrder?.city + mRefuelOrder?.county
        tv_oil_no.text = mRefuelOrder?.oilNo
        tv_gun_no.text = mRefuelOrder?.gunNo.toString() + "号枪"
        tv_refuel_sum.text = "￥" + mRefuelOrder?.amountGun
        tv_refuel_litre.text = mRefuelOrder?.litre.toString() + "升"
        tv_discounts.text = "￥" + mRefuelOrder?.amountDiscounts
        tv_pay_way.text = mRefuelOrder?.payType
        tv_pay_sum.text = "￥" + mRefuelOrder?.amountPay
        tv_pay_time.text = mRefuelOrder?.payTime
        tv_pay_money.text = "￥" + mRefuelOrder?.amountPay


    }

    override fun onSuccess(data: String?) {

    }

    override fun onFailure(e: Throwable?, code: Int, msg: String?, isNetWorkError: Boolean) {

    }
}