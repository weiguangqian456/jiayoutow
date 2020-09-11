package com.edawtech.jiayou.ui.fragment

import android.os.Bundle
import android.view.View
import com.edawtech.jiayou.R
import com.edawtech.jiayou.config.base.BaseMvpFragment


//加油首页
class HomeFragment : BaseMvpFragment() {


    override fun getLayoutId(): Int {
        return R.layout.home_fragment
    }

    override fun initView(view: View?, savedInstanceState: Bundle?) {

    }


    override fun onFailure(e: Throwable?, code: Int, msg: String?, isNetWorkError: Boolean) {

    }

    override fun onSuccess(data: String?) {

    }


}