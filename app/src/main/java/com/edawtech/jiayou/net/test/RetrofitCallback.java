package com.edawtech.jiayou.net.test;

import android.text.TextUtils;


import com.edawtech.jiayou.config.base.Const;
import com.edawtech.jiayou.config.bean.ResultEntity;
import com.edawtech.jiayou.utils.tool.ToastUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : hc
 * @date : 2019/4/9.
 * @description:
 */

public abstract class RetrofitCallback<T extends ResultEntity> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        String msg = "数据或网络异常";
        T body = response.body();
        if(body != null){
            if(body.getCode() == Const.REQUEST_CODE){
                if(body.getData() != null){
                    onNext(response.body());
                }
                onFinish();
                return;
            }else{
                msg = TextUtils.isEmpty(body.getMsg()) ? msg : body.getMsg();
            }
        }
        ToastUtil.showMsg(msg);
        onFinish();
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onFinish();
    }

    protected abstract void onNext(T result);

    protected void onFinish(){

    }
}
