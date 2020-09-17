package com.edawtech.jiayou.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.edawtech.jiayou.R
import com.edawtech.jiayou.config.base.BaseMvpActivity

class MakeMoneyActivity : BaseMvpActivity() {

    override val layoutId: Int
        get() = R.layout.activity_make_money

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun onSuccess(data: String?) {

    }

    override fun onFailure(e: Throwable?, code: Int, msg: String?, isNetWorkError: Boolean) {

    }
}