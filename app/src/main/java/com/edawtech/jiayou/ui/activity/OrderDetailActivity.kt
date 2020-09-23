package com.edawtech.jiayou.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.edawtech.jiayou.R
import com.edawtech.jiayou.config.base.BaseMvpActivity
import com.edawtech.jiayou.config.bean.OrderRefurlBean
import com.edawtech.jiayou.mvp.presenter.PublicPresenter
import com.edawtech.jiayou.net.http.HttpURL
import com.edawtech.jiayou.net.observer.TaskCallback
import com.edawtech.jiayou.utils.tool.GsonUtils
import com.edawtech.jiayou.utils.tool.ToastUtil
import kotlinx.android.synthetic.main.activity_order_detail.*

class OrderDetailActivity : BaseMvpActivity() {
    // 请求数据
    private var Inform_Target: PublicPresenter? = null
    var mRefuelOrder: OrderRefurlBean.OrderRefurlOrderList? = null

    override val layoutId: Int
        get() = R.layout.activity_order_detail

    @SuppressLint("SetTextI18n")
    override fun initView(savedInstanceState: Bundle?) {
        Inform_Target = PublicPresenter(this, true, "加载中...")
        Inform_Target?.attachView(this)
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

        textpay.text = "订单" + mRefuelOrder?.orderStatusName
        when (mRefuelOrder?.orderStatusName) {
            "已支付" -> textesc.visibility = View.GONE
            "已退款" -> {
                textesc.visibility = View.GONE
                textmes.text = "订单" + mRefuelOrder?.orderStatusName.toString() + "，可重新下单"
            }
            else -> {
                paytextec.text = "订单未支付"
                textmes.text = "订单" + mRefuelOrder?.orderStatusName.toString() + "，可重新下单"
            }
        }
        textesc.setOnClickListener {
            Inform_Target?.netWorkRequestPost(HttpURL.query_delete + mRefuelOrder?.orderId, "", object : TaskCallback {
                override fun onSuccess(data: String?) {
                    textesc.visibility = View.GONE
                }

                override fun onFailure(e: Throwable?, code: Int, msg: String?, isNetWorkError: Boolean) {
                    ToastUtil.showMsg(msg)
                }

            })
        }

    }

    override fun onDestroy() {
        if (Inform_Target != null) {
            Inform_Target?.detachView()
        }
        super.onDestroy()
    }

    override fun onSuccess(data: String?) {

    }

    override fun onFailure(e: Throwable?, code: Int, msg: String?, isNetWorkError: Boolean) {

    }
}