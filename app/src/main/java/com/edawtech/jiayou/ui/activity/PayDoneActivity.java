package com.edawtech.jiayou.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.edawtech.jiayou.R;
import com.edawtech.jiayou.config.base.VsBaseActivity;
import com.edawtech.jiayou.utils.FitStateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 订单支付成功
 */
public class PayDoneActivity extends VsBaseActivity {
    @BindView(R.id.sys_title_txt)
    TextView titleTv;
    @BindView(R.id.tv_pay_money)
    TextView payMoneyTv;
    @BindView(R.id.btn_check_order)
    TextView checkOrderBtn;
    @BindView(R.id.btn_back_mall)
    TextView backMallBtn;

    private String discountAmount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_done);
        FitStateUtils.setImmersionStateMode(this,R.color.public_color_EC6941);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleTv.setText("付款成功");
        Intent intent = getIntent();
        discountAmount = intent.getStringExtra("discountAmount");
        if (discountAmount != null) {
            payMoneyTv.setText(discountAmount);
        }
    }

    @OnClick({R.id.btn_check_order})
    void gotoOrderDetailActivity(View view) {
        skipPageAndFinish(MainActivity.class, "indicator", 1);
    }

    @OnClick({R.id.btn_back_mall})
    void gotoSaleMallFragment(View view) {
        skipPageAndFinish(MainActivity.class, "indicator", 0);
    }
}
