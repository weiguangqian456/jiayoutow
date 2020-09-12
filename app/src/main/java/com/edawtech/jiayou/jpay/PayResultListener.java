package com.edawtech.jiayou.jpay;

/**
 * @author Created by EDZ on 2018/8/10.
 *         支付返回结果
 */

public interface PayResultListener {
    void onPaySuccess();

    void onPayError();

    void onPayCancel();
}
