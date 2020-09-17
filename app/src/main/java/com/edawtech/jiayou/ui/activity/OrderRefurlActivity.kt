package com.edawtech.jiayou.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.edawtech.jiayou.R
import com.edawtech.jiayou.config.base.BaseMvpActivity
import com.edawtech.jiayou.ui.adapter.RefuelOrderAdapter
import kotlinx.android.synthetic.main.activity_order_refurl.*

/**
 *
 * 加油订单*/
class OrderRefurlActivity : BaseMvpActivity(){

    private val mAdapter: RefuelOrderAdapter? = null

    override val layoutId: Int
        get() = R.layout.activity_order_refurl



    override fun initView(savedInstanceState: Bundle?) {
        tv_title.text = "加油订单"
        val headerView = LayoutInflater.from(this).inflate(R.layout.header_refuel_order, null, false)



    }

    override fun onSuccess(data: String?) {

    }

    override fun onFailure(e: Throwable?, code: Int, msg: String?, isNetWorkError: Boolean) {

    }
}