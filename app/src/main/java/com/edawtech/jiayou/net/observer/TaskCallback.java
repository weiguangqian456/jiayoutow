package com.edawtech.jiayou.net.observer;

public interface TaskCallback {

    /**
     * 数据请求成功
     *
     * @param data 请求到的数据
     */
    void onSuccess(String data);

    // 数据获取失败
    void onFailure(Throwable e, int code, String msg, boolean isNetWorkError);
}
