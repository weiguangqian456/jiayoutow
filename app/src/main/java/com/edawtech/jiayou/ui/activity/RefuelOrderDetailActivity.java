package com.edawtech.jiayou.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.edawtech.jiayou.R;
import com.edawtech.jiayou.config.base.BaseActivity;
import com.edawtech.jiayou.config.bean.RefuelOrder;
import com.edawtech.jiayou.utils.tool.ArmsUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class RefuelOrderDetailActivity extends BaseActivity {

    @BindView(R.id.fl_back)
    FrameLayout mFlBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_order_id)
    TextView mTvOrderId;
    @BindView(R.id.tv_oil_station_name)
    TextView mTvOilStationName;
    @BindView(R.id.tv_oil_station_address)
    TextView mTvOilStationAddress;
    @BindView(R.id.tv_oil_no)
    TextView mTvOilNo;
    @BindView(R.id.tv_gun_no)
    TextView mTvGunNo;
    @BindView(R.id.tv_refuel_sum)
    TextView mTvRefuelSum;
    @BindView(R.id.tv_refuel_litre)
    TextView mTvRefuelLitre;
    @BindView(R.id.tv_discounts)
    TextView mTvDiscounts;
    @BindView(R.id.tv_pay_way)
    TextView mTvPayWay;
    @BindView(R.id.tv_pay_sum)
    TextView mTvPaySum;
    @BindView(R.id.tv_pay_time)
    TextView mTvPayTime;
    @BindView(R.id.tv_pay_money)
    TextView mTvPayMoney;

    public static void start(Context context, RefuelOrder refuelOrder) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("REFUEL_ORDER", refuelOrder);
        ArmsUtils.startActivity(context, RefuelOrderDetailActivity.class, bundle);
    }

    private RefuelOrder mRefuelOrder;

    @Override
    public int getLayoutId() {
        return R.layout.activity_refuel_order_detail;
    }



    @Override
    public void initView(Bundle savedInstanceState) {
        mTvTitle.setText("订单详情");

        mRefuelOrder = (RefuelOrder) getIntent().getSerializableExtra("REFUEL_ORDER");
        if (mRefuelOrder != null) {
            mTvOrderId.setText(mRefuelOrder.orderId);
            mTvOilStationName.setText(mRefuelOrder.gasName);
            mTvOilStationAddress.setText(mRefuelOrder.province + mRefuelOrder.city + mRefuelOrder.county);
            mTvOilNo.setText(mRefuelOrder.oilNo);
            mTvGunNo.setText(mRefuelOrder.gunNo + "号枪");
            mTvRefuelSum.setText("￥" + mRefuelOrder.amountGun);
            mTvRefuelLitre.setText(mRefuelOrder.litre + "升");
            mTvDiscounts.setText("￥" + mRefuelOrder.amountDiscounts);
            mTvPayWay.setText(mRefuelOrder.payType);
            mTvPaySum.setText("￥" + mRefuelOrder.amountPay);
            mTvPayTime.setText(mRefuelOrder.payTime);
            mTvPayMoney.setText("￥" + mRefuelOrder.amountPay);
        }

    }

    @OnClick({R.id.fl_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_back:
                finish();
                break;
        }
    }
}
