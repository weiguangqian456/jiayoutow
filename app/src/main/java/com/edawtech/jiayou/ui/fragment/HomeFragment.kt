package com.edawtech.jiayou.ui.fragment

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import com.edawtech.jiayou.R
import com.edawtech.jiayou.config.base.BaseMvpFragment
import kotlinx.android.synthetic.main.home_fragment.*


//加油首页
class HomeFragment : BaseMvpFragment() {




    override val layoutId: Int
        get() = R.layout.home_fragment

    override fun initView(view: View?, savedInstanceState: Bundle?) {


    }


    override fun onFailure(e: Throwable?, code: Int, msg: String?, isNetWorkError: Boolean) {

    }

    override fun onSuccess(data: String?) {

    }


}