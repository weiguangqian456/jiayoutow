package com.edawtech.jiayou.config.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import butterknife.Unbinder

/**
 * @author azheng
 * @date 2018/4/24.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：
 */
abstract class BaseFragment : Fragment() {
    private var unBinder: Unbinder? = null
    protected var mContext: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(layoutId, container, false)
        // 绑定事件ButterKnife.bind(this)必须在setContentView();之后。
        unBinder = ButterKnife.bind(this, view)
        mContext = activity
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 初始化视图
        initView(view, savedInstanceState)
    }

    /**
     * 设置布局
     */
    protected abstract val layoutId: Int

    /**
     * 初始化视图
     */
    protected abstract fun initView(view: View?, savedInstanceState: Bundle?)
    override fun onDestroyView() {
        super.onDestroyView()
        unBinder!!.unbind()
    }
}