package com.edawtech.jiayou.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.edawtech.jiayou.R
import com.edawtech.jiayou.config.base.BaseMvpFragment
import com.edawtech.jiayou.config.base.MyApplication
import com.edawtech.jiayou.config.bean.OrderRefurlBean
import com.edawtech.jiayou.mvp.presenter.PublicPresenter
import com.edawtech.jiayou.net.http.HttpRequest
import com.edawtech.jiayou.net.http.HttpURL
import com.edawtech.jiayou.ui.activity.OrderDetailActivity
import com.edawtech.jiayou.ui.adapter.BaseRecyclerAdapter
import com.edawtech.jiayou.ui.adapter.BaseRecyclerHolder
import com.edawtech.jiayou.ui.custom.CustomErrorView
import com.edawtech.jiayou.utils.tool.GsonUtils
import com.edawtech.jiayou.utils.tool.JsonHelper
import com.edawtech.jiayou.utils.tool.ViewSetUtils
import com.edawtech.jiayou.utils.tool.unit.DensityUtils
import com.flyco.roundview.RoundTextView
import com.yanzhenjie.recyclerview.SwipeRecyclerView


/**
 *
 * ClassName:      PayOrderFragment
 * <p>
 * Author:
 * <p>
 * CreateDate:      2020/9/24 11:13
 * <p>
 * Description:
 */

class PayOrderFragment : BaseMvpFragment() {
    // 请求数据
    private var Inform_Target: PublicPresenter? = null

    var baseRecyclerAdapter: BaseRecyclerAdapter<OrderRefurlBean.OrderRefurlOrderList>? = null

    private var mHttpPage = 1
    var mErrorView : CustomErrorView?=null


    override val layoutId: Int
        get() = R.layout.order_pay_fragment

    override fun initView(view: View?, savedInstanceState: Bundle?) {
        Inform_Target = PublicPresenter(context, false, "")
        Inform_Target?.attachView(this)
        var rvodeer = view?.findViewById<SwipeRecyclerView>(R.id.rv_load)

        mErrorView =view?.findViewById(R.id.cev_view)

        val layoutParams = mErrorView!!.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.topMargin = DensityUtils.dp2px(context, 0f)
        mErrorView!!.layoutParams = layoutParams
        mErrorView!!.setShowView()

        baseRecyclerAdapter = object : BaseRecyclerAdapter<OrderRefurlBean.OrderRefurlOrderList>(context, null, R.layout.item_refuel_order) {
            override fun convert(holder: BaseRecyclerHolder?, data: OrderRefurlBean.OrderRefurlOrderList?, position: Int, isScrolling: Boolean, selectedPosition: Int) {
                holder?.getView<TextView>(R.id.tv_order_title)?.text = data?.litre + "升 " + data?.oilNo + " " + data?.gasName
                holder?.getView<TextView>(R.id.tv_order_price)?.text = "￥" + data?.amountPay
                holder?.getView<TextView>(R.id.tv_order_time)?.text = data?.payTime
                val mTvPayStatus = holder?.getView<TextView>(R.id.tv_pay_status)
                val orderstatus = holder?.getView<RoundTextView>(R.id.rtv_order_status)
                when (data?.orderStatusName) {
                    "已支付" -> {
                        orderstatus?.delegate?.backgroundPressColor = R.color.public_color_3096F8
                        mTvPayStatus?.setTextColor(mContext!!.resources.getColor(R.color.public_color_3096F8))
                    }
                    "未付款" -> {
                        orderstatus?.delegate?.backgroundPressColor = R.color.public_color_FF5842
                        mTvPayStatus?.setTextColor(mContext!!.resources.getColor(R.color.public_color_FF5842))
                    }
                    "已退款" -> {
                        orderstatus?.delegate?.backgroundPressColor = R.color.public_color_00BE52
                        mTvPayStatus?.setTextColor(mContext!!.resources.getColor(R.color.public_color_00BE52))
                    }
                }
                mTvPayStatus?.text = data?.orderStatusName

                holder?.getView<LinearLayout>(R.id.oder_linear)?.setOnClickListener {
                    ViewSetUtils.ButtonClickZoomInAnimation(it, 0.85f)
                    startActivity(Intent(context, OrderDetailActivity().javaClass).putExtra("OrderDetail", JsonHelper.newtoJson(data)))
                }


            }
        }
        rvodeer?.adapter = baseRecyclerAdapter
        newData()
        rvodeer?.useDefaultLoadMore()
        rvodeer?.loadMoreFinish(false, true)
        rvodeer?.setLoadMoreListener {
            mHttpPage++
            newData()
        }
    }

    override fun onDestroyView() {
        Inform_Target?.detachView()
        super.onDestroyView()
    }

    override fun onSuccess(data: String?) {
        var ordeRefurl: OrderRefurlBean = GsonUtils.getGson().fromJson(data, OrderRefurlBean().javaClass)
        if (mHttpPage== 1 && ordeRefurl.data?.orderList?.size==null){
            mErrorView?.initState(CustomErrorView.ERROR_VIEW_EMPTY)
        }
        baseRecyclerAdapter?.setData(ordeRefurl.data?.orderList)
    }

    override fun onFailure(e: Throwable?, code: Int, msg: String?, isNetWorkError: Boolean) {
        if (mHttpPage== 1){
            mErrorView?.initState(CustomErrorView.ERROR_VIEW_EMPTY)
        }
    }

    fun newData() {
       Inform_Target?.netWorkRequestGet(HttpURL.OrderZhuBangquery, HttpRequest.OrderRefurlist(MyApplication.MOBILE, "10", mHttpPage.toString(), "8.3.11", "已支付"))

    }
}