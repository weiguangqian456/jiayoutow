package com.edawtech.jiayou.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.edawtech.jiayou.R;
import com.edawtech.jiayou.config.base.BaseRecycleActivity;
import com.edawtech.jiayou.config.base.BaseRecycleAdapter;
import com.edawtech.jiayou.config.bean.RefuelOrder;
import com.edawtech.jiayou.config.constant.VsUserConfig;
import com.edawtech.jiayou.ui.adapter.RecycleItemClick;
import com.edawtech.jiayou.ui.adapter.RefuelOrderAdapter;
import com.edawtech.jiayou.ui.dialog.PromptDialog;
import com.edawtech.jiayou.utils.tool.unit.DensityUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RefuelOrderActivity extends BaseRecycleActivity {

    @BindView(R.id.tv_title_background)
    TextView mTvTitleBackground;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;

    private RefuelOrderAdapter mAdapter;
    private HolderView mHeaderView;
    private float mScrolledHeight;
    private int mAlphaHeight = 200;
    private PromptDialog mPromptDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_refuel_order;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    protected boolean isShowError() {
        return true;
    }

    @Override
    protected BaseRecycleAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    protected String getPath() {
        return "benefit/web/CheZhuBangController/queryOrderInfo";
    }

    @Override
    protected String getRequestType() {
        return RECYCLE_TYPE_REFUEL_ORDER;
    }

    @Override
    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put("phone", "13380391613");
        return params;
    }

    @Override
    protected void initView() {

        mTvTitle.setText("加油订单");
      //  ImmersionBar.with(this).statusBarDarkFont(false).titleBar(mLlTitle).keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN).init();

        View headerView = LayoutInflater.from(this).inflate(R.layout.header_refuel_order, null, false);
        mHeaderView = new HolderView(headerView);

        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mErrorView.getLayoutParams();
        layoutParams.topMargin = DensityUtils.dp2px(this, 287);
        mErrorView.setLayoutParams(layoutParams);
        mErrorView.setShowView();

        mPromptDialog = new PromptDialog(this);
        mPromptDialog.widthScale(0.8f);
        mPromptDialog.setTitle("开具发票")
                .setContent("请直接拨打客服电话：400-0365-388  来开具发票。");

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                mScrolledHeight += dy;
                float alpha = (mScrolledHeight > mAlphaHeight ? 1 : mScrolledHeight / mAlphaHeight);
                mTvTitleBackground.setAlpha(alpha);

            }
        });

        mAdapter = new RefuelOrderAdapter(this);
        mAdapter.addHeadView(headerView);

        mAdapter.setmRecycleItemClick(new RecycleItemClick() {
            @Override
            public void onItemClick(int position) {
                RefuelOrder refuelOrder = JSON.parseObject(mAdapter.getItem(position).toString(), RefuelOrder.class);
                RefuelOrderDetailActivity.start(RefuelOrderActivity.this, refuelOrder);
            }
        });

        super.initView();
    }

    @Override
    public void initRefresh() {
        super.initRefresh();
    }

    @Override
    public void onHttpFinish() {
        super.onHttpFinish();
        if (getRefuelOrderList() != null) {
            DecimalFormat df = new DecimalFormat("#.00");//保留两位小数

            mHeaderView.mTvAccumulateConsumption.setText(df.format(new BigDecimal(getRefuelOrderList().amountPaySum)));
            mHeaderView.mTvTvAccumulateRefuel.setText(df.format(new BigDecimal(getRefuelOrderList().litreSum)));
        }
    }

    @OnClick({R.id.fl_back, R.id.tv_invoice})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_back:
                finish();
                break;
            case R.id.tv_invoice:
                mPromptDialog.show();
                break;
        }
    }

    class HolderView {

        @BindView(R.id.tv_accumulate_consumption)
        TextView mTvAccumulateConsumption;
        @BindView(R.id.tv_tv_accumulate_refuel)
        TextView mTvTvAccumulateRefuel;

        HolderView(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }

}
