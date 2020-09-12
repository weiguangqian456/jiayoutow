package com.edawtech.jiayou.config.base

/**
 * @author hc
 * @date 2019/3/4.
 * @description: Fragment 懒加载 - 懒得写
 */
abstract class BaseLazyFragment : BaseFragment() {
    /**
     * 交给子类实现
     * @return 布局id
     */
    abstract override val layoutId: Int

    /**
     * 布局显示后调用
     */
    protected abstract fun loadLazyData()
    protected fun initView() {
        //懒加载实现initView调用isLazyLoad
        isLazyLoad
    }

    private val isLazyLoad: Unit
        private get() {
            loadLazyData()
        }
}