package com.edawtech.jiayou.ui.fragment

import android.os.Bundle
import android.view.View
import com.edawtech.jiayou.R
import com.edawtech.jiayou.config.base.BaseMvpFragment
 //我的
class MyFragment : BaseMvpFragment() {
    override fun onSuccess(data: String?) {

    }

    override fun getLayoutId(): Int {
        return R.layout.my_frament
    }

    override fun onFailure(e: Throwable?, code: Int, msg: String?, isNetWorkError: Boolean) {

    }

    override fun initView(view: View?, savedInstanceState: Bundle?) {

    }
}