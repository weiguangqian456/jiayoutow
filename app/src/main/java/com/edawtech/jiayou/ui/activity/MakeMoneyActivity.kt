package com.edawtech.jiayou.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import com.edawtech.jiayou.R
import com.edawtech.jiayou.config.base.BaseMvpActivity
import com.edawtech.jiayou.config.constant.DfineAction
import com.edawtech.jiayou.config.constant.VsUserConfig
import kotlinx.android.synthetic.main.activity_make_money.*

class MakeMoneyActivity : BaseMvpActivity() {

    override val layoutId: Int
        get() = R.layout.activity_make_money

    override fun initView(savedInstanceState: Bundle?) {
        title_main_tv.leftBackImageTv.setOnClickListener { finish() }


    }

    override fun onSuccess(data: String?) {

    }

    override fun onFailure(e: Throwable?, code: Int, msg: String?, isNetWorkError: Boolean) {

    }

    /**
     * 压缩图片
     * @param bitMap
     */
    private fun compressBitmap(bitMap: Bitmap): Bitmap? {
        val width = bitMap.width
        val height = bitMap.height
        // 设置想要的大小
        val newWidth = 99
        val newHeight = 99
        // 计算缩放比例
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        // 取得想要缩放的matrix参数
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        // 得到新的图片
        return Bitmap.createBitmap(bitMap, 0, 0, width, height, matrix, true)
    }

    /**
     * 设置分享内容
     *
     * @return
     */
    fun shareContent(): String? {
        // 推荐好友
        var mRecommendInfo: String = VsUserConfig.getDataString(mContext, VsUserConfig.JKey_GET_MY_SHARE)
        if (mRecommendInfo == null || "" == mRecommendInfo) {
            mRecommendInfo = DfineAction.InviteFriendInfo
        }
        return mRecommendInfo
    }

}