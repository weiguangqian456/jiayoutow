package com.edawtech.jiayou.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder
import com.edawtech.jiayou.R
import com.edawtech.jiayou.config.base.BaseMvpActivity
import com.edawtech.jiayou.config.constant.VsUserConfig
import com.edawtech.jiayou.utils.tool.unit.DensityUtil
import kotlinx.android.synthetic.main.activity_kc_my_qcode.*

class MyQcodeActivity : BaseMvpActivity() {


    override val layoutId: Int
        get() = R.layout.activity_my_qcode

    override fun initView(savedInstanceState: Bundle?) {

        // 生成二维码
        try {
            val mBitmap = QRCodeEncoder.syncEncodeQRCode("https://www.163.com/", DensityUtil.dip2px(context, 200f),
                    Color.parseColor("#333333"))
            iv_result.setImageBitmap(mBitmap)
        } catch (e: Exception) {
        }
        fl_back.setOnClickListener { finish() }

    }

    override fun onSuccess(data: String?) {


    }

    override fun onFailure(e: Throwable?, code: Int, msg: String?, isNetWorkError: Boolean) {

    }
}