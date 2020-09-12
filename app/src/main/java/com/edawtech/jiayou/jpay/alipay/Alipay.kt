package com.edawtech.jiayou.jpay.alipay

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.TextUtils
import com.alipay.sdk.app.PayTask
import com.edawtech.jiayou.jpay.JPay.JPayListener
import com.edawtech.jiayou.jpay.weixin.WeiXinPay

/**
 * Created by Javen on 2016/11/21.
 */
class Alipay private constructor(private val mContext: Activity) {
    private var mJPayListener: JPayListener? = null

    /**
     * 调用支付宝SDK
     */
    fun startAliPay(orderInfo: String?, listener: JPayListener?) {
        mJPayListener = listener
        val payRunnable = Runnable {
            val alipay = PayTask(mContext)
            val result = alipay.payV2(orderInfo, true)
            val msg = Message()
            msg.obj = result
            mHandler.sendMessage(msg)
        }
        val payThread = Thread(payRunnable)
        payThread.start()
    }

    private val mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            val payResult = PayResult(msg.obj as Map<String?, String?>)
            val resultStatus = payResult.resultStatus
            if (mJPayListener == null) {
                return
            }
            // https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.xN1NnL&treeId=204&articleId=105302&docType=1
            if (payResult == null) {
                mJPayListener!!.onPayError(RESULT_ERROR, "结果解析错误")
                return
            }
            if (TextUtils.equals(resultStatus, "9000")) {
                //支付成功
                mJPayListener!!.onPaySuccess(resultStatus)
            } else if (TextUtils.equals(resultStatus, "8000")) {
                //正在处理中，支付结果未知（有可能已经支付成功），请查询商户中订单的支付状态
                mJPayListener!!.onPayError(PAY_DEALING, "正在处理结果中")
            } else if (TextUtils.equals(resultStatus, "6001")) {
                //支付取消
                mJPayListener!!.onPayCancel()
            } else if (TextUtils.equals(resultStatus, "6002")) {
                //网络连接出错
                mJPayListener!!.onPayError(PAY_NETWORK_ERROR, "网络连接出错")
            } else if (TextUtils.equals(resultStatus, "4000")) {
                //支付错误
                mJPayListener!!.onPayError(PAY_ERROR, "订单支付失败")
            } else {
                mJPayListener!!.onPayError(PAY_OTHER_ERROR, resultStatus)
            }
        }
    }

    companion object {
        private var mAliPay: Alipay? = null

        //支付失败
        const val PAY_ERROR = 0x001

        //支付网络连接出错
        const val PAY_NETWORK_ERROR = 0x002

        //支付结果解析错误
        const val RESULT_ERROR = 0x003

        //正在处理中
        const val PAY_DEALING = 0x004

        //其它支付错误
        const val PAY_OTHER_ERROR = 0x006

        //支付参数异常
        const val PAY_PARAMETERS_ERROE = 0x007
        @JvmStatic
        fun getInstance(context: Activity): Alipay? {
            if (mAliPay == null) {
                synchronized(WeiXinPay::class.java) {
                    if (mAliPay == null) {
                        mAliPay = Alipay(context)
                    }
                }
            }
            return mAliPay
        }
    }
}