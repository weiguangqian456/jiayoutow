/**
 * Copyright (c) 2015-2017, javen205  (javendev@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.edawtech.jiayou.jpay.weixin

import android.app.Activity
import android.content.Context
import android.util.Log
import com.edawtech.jiayou.jpay.JPay.JPayListener
import com.tencent.mm.opensdk.constants.Build
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory

/**
 *
 * Created by Javen on 2016/11/20.
 */
class WeiXinPay private constructor(context: Activity) {
    private val mContext: Context

    /**
     * 获取微信接口
     * @return
     */
    var wXApi: IWXAPI? = null
        private set
    private var mJPayListener: JPayListener? = null

    /**
     * 初始化微信支付接口
     * @param appId
     */
    fun init(appId: String?) {
        wXApi = WXAPIFactory.createWXAPI(mContext, null)
        val registerApp = this.wXApi?.registerApp(appId)
    }

    /**
     * 调起支付
     * @param appId
     * @param partnerId
     * @param prepayId
     * @param nonceStr
     * @param timeStamp
     * @param sign
     */
    fun startWXPay(appId: String?, partnerId: String?, prepayId: String?,
                   nonceStr: String?, timeStamp: String?, sign: String?, listener: JPayListener?) {
        mJPayListener = listener
        init(appId)
        if (!checkWx()) {
            listener?.onPayError(WEIXIN_VERSION_LOW, "未安装微信或者微信版本过低")
            return
        }
        val request = PayReq()
        request.appId = appId
        request.partnerId = partnerId
        request.prepayId = prepayId
        request.packageValue = "Sign=WXPay"
        request.nonceStr = nonceStr
        request.timeStamp = timeStamp
        request.sign = sign
        wXApi!!.sendReq(request)
    }

    /**
     * 响应支付回调
     * @param error_code
     * @param message
     */
    fun onResp(error_code: Int, message: String?) {
        Log.e("mJPayListener", "----$error_code")
        if (mJPayListener == null) {
            return
        }
        if (error_code == 0) {
            //支付成功
            Log.e("mJPayListener", "" + error_code)
            mJPayListener!!.onPaySuccess(message)
        } else if (error_code == -1) {
            //支付异常
            Log.e("mJPayListener", "" + error_code)
            mJPayListener!!.onPayError(PAY_ERROR, message)
        } else if (error_code == -2) {
            //支付取消
            Log.e("mJPayListener", "" + error_code)
            mJPayListener!!.onPayCancel()
        }
        mJPayListener = null
    }

    //  String sign = WXPayUtil.generateSignature(repData, ourWxPayConfig.getKey()); //签名
    //检测微信客户端是否支持微信支付
    private fun checkWx(): Boolean {
        return isWeixinAvilible && wXApi!!.isWXAppInstalled && wXApi!!.wxAppSupportAPI >= Build.PAY_SUPPORTED_SDK_INT
    }

    /**
     * 判断微信是否安装
     * @return
     */
    private val isWeixinAvilible: Boolean
        private get() = appIsAvilible("com.tencent.mm")

    /**
     * 判断app是否安装
     * @param packageName
     * @return
     */
    private fun appIsAvilible(packageName: String): Boolean {
        val packageManager = mContext.packageManager // 获取packagemanager
        val pinfo = packageManager.getInstalledPackages(0) // 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (i in pinfo.indices) {
                val pn = pinfo[i].packageName
                if (pn == packageName) {
                    return true
                }
            }
        }
        return false
    }

    companion object {
        private var mWeiXinPay: WeiXinPay? = null

        //未安装微信或微信版本过低
        const val WEIXIN_VERSION_LOW = 0x001

        //支付参数异常
        const val PAY_PARAMETERS_ERROE = 0x002

        //支付失败
        const val PAY_ERROR = 0x003
        @JvmStatic
        fun getInstance(context: Activity): WeiXinPay? {
            if (mWeiXinPay == null) {
                synchronized(WeiXinPay::class.java) {
                    if (mWeiXinPay == null) {
                        mWeiXinPay = WeiXinPay(context)
                    }
                }
            }
            return mWeiXinPay
        }
    }

    init {
        mContext = context
    }
}