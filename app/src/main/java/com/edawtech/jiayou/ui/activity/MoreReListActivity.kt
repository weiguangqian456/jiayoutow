package com.edawtech.jiayou.ui.activity

import android.content.Context
import android.os.Bundle
import com.edawtech.jiayou.R
import com.edawtech.jiayou.config.base.BaseMvpActivity
import com.edawtech.jiayou.utils.tool.ArmsUtils
import kotlinx.android.synthetic.main.activity_more_re_list.*

class MoreReListActivity : BaseMvpActivity() {


    override val layoutId: Int
        get() = R.layout.activity_more_re_list


    override fun initView(savedInstanceState: Bundle?) {
        title_main_tv.leftBackImageTv.setOnClickListener { finish() }


    }



    override fun onSuccess(data: String?) {

    }

    override fun onFailure(e: Throwable?, code: Int, msg: String?, isNetWorkError: Boolean) {

    }


}