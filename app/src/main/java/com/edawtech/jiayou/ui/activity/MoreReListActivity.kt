package com.edawtech.jiayou.ui.activity

import android.os.Bundle
import com.edawtech.jiayou.R
import com.edawtech.jiayou.config.base.BaseMvpActivity
import com.edawtech.jiayou.mvp.presenter.PublicPresenter
import kotlinx.android.synthetic.main.activity_more_re_list.*

class MoreReListActivity : BaseMvpActivity() {


    // 请求数据
    private var Inform_Target: PublicPresenter? = null

    override val layoutId: Int
        get() = R.layout.activity_more_re_list


    override fun initView(savedInstanceState: Bundle?) {
        title_main_tv.leftBackImageTv.setOnClickListener { finish() }
        Inform_Target = PublicPresenter(context, false, null)
        Inform_Target?.attachView(this)


    }

    override fun onDestroy() {
        if (Inform_Target != null) {
            Inform_Target!!.detachView()
        }
        super.onDestroy()
    }



    override fun onSuccess(data: String?) {

    }

    override fun onFailure(e: Throwable?, code: Int, msg: String?, isNetWorkError: Boolean) {

    }


}