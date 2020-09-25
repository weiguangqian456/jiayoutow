package com.edawtech.jiayou.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.edawtech.jiayou.R
import com.edawtech.jiayou.config.base.BaseMvpActivity
import com.edawtech.jiayou.config.base.MyApplication
import com.edawtech.jiayou.config.bean.OrderRefurlBean
import com.edawtech.jiayou.mvp.presenter.PublicPresenter
import com.edawtech.jiayou.net.http.HttpRequest
import com.edawtech.jiayou.net.http.HttpURL
import com.edawtech.jiayou.ui.adapter.BaseRecyclerAdapter
import com.edawtech.jiayou.ui.adapter.BaseRecyclerHolder
import com.edawtech.jiayou.ui.adapter.RefuelOrderAdapter
import com.edawtech.jiayou.ui.dialog.PromptDialog
import com.edawtech.jiayou.utils.tool.GsonUtils
import com.edawtech.jiayou.utils.tool.JsonHelper
import com.edawtech.jiayou.utils.tool.ViewSetUtils
import com.flyco.roundview.RoundTextView
import kotlinx.android.synthetic.main.activity_more_re_list.*
import kotlinx.android.synthetic.main.activity_order_refurl.*
import kotlinx.android.synthetic.main.activity_order_refurl.rv_load
import kotlinx.android.synthetic.main.activity_order_refurl.srl_load

/**
 *
 * 加油订单*/
@Suppress("UNCHECKED_CAST")
class OrderRefurlActivity : BaseMvpActivity() {
    // 请求数据
    private var Inform_Target: PublicPresenter? = null

    var baseRecyclerAdapter: BaseRecyclerAdapter<OrderRefurlBean.OrderRefurlOrderList>? = null

    private var mAdapter: RefuelOrderAdapter? = null

    var mTvAccumulateConsumption: TextView? = null

    var mTvTvAccumulateRefuel: TextView? = null
    
    private var mHttpPage = 1

    var mtallText: TextView? = null
    var mTtextpaid: TextView? = null
    var mTreimburset: TextView? = null
    var name : String  =""

    override val layoutId: Int
        get() = R.layout.activity_order_refurl


    override fun initView(savedInstanceState: Bundle?) {
        Inform_Target = PublicPresenter(this, false, "")
        Inform_Target?.attachView(this)

        tv_title.text = "加油订单"
        val headerView = LayoutInflater.from(this).inflate(R.layout.header_refuel_order, null, false)
        mTvAccumulateConsumption = headerView.findViewById(R.id.tv_accumulate_consumption)
        mTvTvAccumulateRefuel = headerView.findViewById(R.id.tv_tv_accumulate_refuel)

        mAdapter = RefuelOrderAdapter(this@OrderRefurlActivity)

        tv_invoice.setOnClickListener { Dialgon() }
        fl_back.setOnClickListener { finish() }

        baseRecyclerAdapter = object : BaseRecyclerAdapter<OrderRefurlBean.OrderRefurlOrderList>(context, null, R.layout.item_refuel_order) {
            @SuppressLint("SetTextI18n")
            override fun convert(holder: BaseRecyclerHolder?, data: OrderRefurlBean.OrderRefurlOrderList?, position: Int, isScrolling: Boolean, selectedPosition: Int) {
                holder?.getView<TextView>(R.id.tv_order_title)?.text = data?.litre + "升 " + data?.oilNo + " " + data?.gasName
                holder?.getView<TextView>(R.id.tv_order_price)?.text = "￥" + data?.amountPay
                holder?.getView<TextView>(R.id.tv_order_time)?.text = data?.payTime

                holder?.getView<RoundTextView>(R.id.rtv_order_status)?.delegate?.backgroundPressColor = mContext!!.resources.getColor(if ("已支付" == data?.orderStatusName) R.color.public_color_3096F8 else R.color.public_color_EC6941)
                val mTvPayStatus = holder?.getView<TextView>(R.id.tv_pay_status)
                mTvPayStatus?.setTextColor(mContext!!.resources.getColor(if ("已支付" == data?.orderStatusName) R.color.public_color_3096F8 else R.color.public_color_EC6941))
                mTvPayStatus?.text = data?.orderStatusName
                holder?.getView<LinearLayout>(R.id.oder_linear)?.setOnClickListener {
                    ViewSetUtils.ButtonClickZoomInAnimation(it, 0.85f)
                    startActivity(Intent(context, OrderDetailActivity().javaClass).putExtra("OrderDetail", JsonHelper.newtoJson(data)))
                }


            }
        }
        rv_load.adapter = baseRecyclerAdapter
        rv_load.addHeaderView(headerView)
        newData("")
        mtallText = headerView.findViewById(R.id.allText)
        mTtextpaid = headerView.findViewById(R.id.textpaid)
        mTreimburset = headerView.findViewById(R.id.reimbursetext)

        mtallText?.setOnClickListener(onClickListener)
        mTtextpaid?.setOnClickListener(onClickListener)
        mTreimburset?.setOnClickListener(onClickListener)
        srl_load.setOnRefreshListener {
            mHttpPage = 1
            newData(name)
        }
        rv_load.useDefaultLoadMore()
        rv_load.loadMoreFinish(false, true)
        rv_load.setLoadMoreListener {  mHttpPage++
            newData(name)
        }
    }

    override fun onDestroy() {
        if (Inform_Target != null) {
            Inform_Target?.detachView()
        }
        super.onDestroy()
    }

    @SuppressLint("LogNotTimber")
    override fun onSuccess(data: String?) {
        srl_load.isRefreshing =false

        var ordeRefurl: OrderRefurlBean = GsonUtils.getGson().fromJson(data, OrderRefurlBean().javaClass)
           if (mHttpPage==1){
               baseRecyclerAdapter?.clear()
           }
        baseRecyclerAdapter?.setData(ordeRefurl.data?.orderList)
        mTvAccumulateConsumption?.text = ordeRefurl.data?.amountPaySum
        mTvTvAccumulateRefuel?.text = ordeRefurl.data?.litreSum

    }

    override fun onFailure(e: Throwable?, code: Int, msg: String?, isNetWorkError: Boolean) {

    }

    fun Dialgon() {
        PromptDialog(this@OrderRefurlActivity).widthScale(0.8f)
                .setTitle("开具发票")
                .setContent("请直接拨打客服电话：400-0365-388  来开具发票。")
                .show()
    }


    private fun IDStatView() {
        mtallText?.setBackgroundResource(R.drawable.textback_bg_le1)
        mTtextpaid?.setBackgroundColor(Color.parseColor("#EEEEEE"))
        mTreimburset?.setBackgroundResource(R.drawable.textback_bg_re2)
        mtallText?.setTextColor(Color.parseColor("#000000"))
        mTtextpaid?.setTextColor(Color.parseColor("#000000"))
        mTreimburset?.setTextColor(Color.parseColor("#000000"))
    }

    var onClickListener = View.OnClickListener { v ->
        IDStatView()
        when (v.id) {
            R.id.allText -> {
                name =""
                mtallText?.setBackgroundResource(R.drawable.textback_bg_le)
                mtallText?.setTextColor(Color.parseColor("#ffffff"))
                newData(name)

            }
            R.id.textpaid -> {
                name =""
                mTtextpaid?.setBackgroundColor(Color.parseColor("#EC6941"))
                mTtextpaid?.setTextColor(Color.parseColor("#ffffff"))
            }
            R.id.reimbursetext -> {
                mTreimburset?.setBackgroundResource(R.drawable.textback_bg_re)
                mTreimburset?.setTextColor(Color.parseColor("#ffffff"))
            }
        }
    }

    fun newData(name : String) {
        Inform_Target?.netWorkRequestGet(HttpURL.OrderZhuBangquery, HttpRequest.OrderRefurlist(MyApplication.MOBILE, "10", mHttpPage.toString(), "8.3.11",name))

    }
}