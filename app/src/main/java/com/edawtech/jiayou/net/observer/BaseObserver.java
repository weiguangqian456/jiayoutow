package com.edawtech.jiayou.net.observer;

import android.accounts.NetworkErrorException;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 请求数据封装
 * 自定义Observer
 */

public class BaseObserver<T> implements Observer<T> {

    private ObserverOnNextListener listener;

    /**
     * 构造函数
     */
    public BaseObserver(ObserverOnNextListener listener) {
        this.listener = listener;
    }

    // 开始  onSubscribe等同于onStart
    @Override
    public void onSubscribe(Disposable d) {
        listener.onSubscribe(d);
    }

    // 获取数据
    @Override
    public void onNext(T t) {
        try {
            listener.onSuccees(t);
        } catch (Exception e) {
            e.printStackTrace();
            listener.onFailure(e);
        }
        onComplete();
    }

    // 失败
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException) {
                listener.onError(e, true);// 网络错误
            } else {
                listener.onError(e, false);// 其他错误
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            listener.onError(e1, false);// 其他错误
        }
        onComplete();
    }

    // 结束
    @Override
    public void onComplete() {
        listener.onComplete();// 请求结束
    }

}
