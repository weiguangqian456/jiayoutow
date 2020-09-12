package com.edawtech.jiayou.config.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author azheng
 * @date 2018/4/24.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：
 */
open class BasePresenter<V : BaseView?> {
    private val compositeDisposable = CompositeDisposable()
    /**
     * 获取连接的view
     */
    /**
     * 绑定的view
     */
    var view: V? = null
        protected set

    /**
     * 绑定view，一般在初始化中调用该方法
     * @param mvpView view
     */
    fun attachView(mvpView: V) {
        view = mvpView
    }

    /**
     * 解除绑定view，一般在onDestroy中调用
     */
    fun detachView() {
        if (compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
        view = null
    }

    protected fun addDisposable(d: Disposable?) {
        compositeDisposable.add(d!!)
    }

    /**
     * View是否绑定
     * 每次调用业务请求的时候都要出先调用方法检查是否与View建立连接
     */
    val isViewAttached: Boolean
        get() = view != null
}