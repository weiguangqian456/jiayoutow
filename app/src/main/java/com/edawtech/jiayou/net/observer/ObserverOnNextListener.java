package com.edawtech.jiayou.net.observer;

import io.reactivex.disposables.Disposable;

/**
 * Created by DeMon on 2017/9/6.
 */
public interface ObserverOnNextListener<T> {

    /**
     * 当rxJava发生订阅
     */
    void onSubscribe(Disposable d);

    /**
     * 数据请求成功
     * @param data 请求到的数据
     */
    void onSuccees(T data);
    // void onSuccees(BaseObjectBean<T> data) throws Exception;

    /**
     * 请求数据失败，指在请求网络API接口请求方式时，虽然已经请求成功但是由于{@code msg}的原因无法正常返回数据。
     */
    void onFailure(Throwable e);

    /**
     * 返回失败
     * 使用网络API接口请求方式时，出现无法联网、 缺少权限，内存泄露等原因导致无法连接到请求数据源。
     * @param isNetWorkError 是否是网络错误
     */
    void onError(Throwable e, boolean isNetWorkError);

    /**
     * 当请求数据结束时，无论请求结果是成功，失败或是抛出异常都会执行此方法给用户做处理，通常做网络
     * 请求时可以在此处隐藏“正在加载”的等待控件
     */
    void onComplete();

}
