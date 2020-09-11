package com.edawtech.jiayou.config.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author azheng
 * @date 2018/4/24.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：
 */
public class BasePresenter<V extends BaseView> {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    /**
     * 绑定的view
     */
    protected V mvpView;

    /**
     * 绑定view，一般在初始化中调用该方法
     * @param mvpView view
     */
    public void attachView(V mvpView) {
        this.mvpView = mvpView;
    }

    /**
     * 解除绑定view，一般在onDestroy中调用
     */
    public void detachView() {
        if (compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
        this.mvpView = null;
    }

    protected void addDisposable(Disposable d) {
        compositeDisposable.add(d);
    }

    /**
     * View是否绑定
     * 每次调用业务请求的时候都要出先调用方法检查是否与View建立连接
     */
    public boolean isViewAttached() {
        return mvpView != null;
    }

    /**
     * 获取连接的view
     */
    public V getView(){
        return mvpView;
    }

}
