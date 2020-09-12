package com.edawtech.jiayou.ui.activity

import android.os.Bundle
import com.edawtech.jiayou.R
import com.edawtech.jiayou.config.base.BaseMvpActivity

class LoginActivity : BaseMvpActivity() {


    override val layoutId: Int
        get() =  R.layout.activity_login

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun onFailure(e: Throwable?, code: Int, msg: String?, isNetWorkError: Boolean) {

    }
    override fun onSuccess(data: String?) {

    }

}