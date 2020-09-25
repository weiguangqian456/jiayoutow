package com.edawtech.jiayou.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSONObject;
import com.androidkun.xtablayout.XTabLayout;
import com.edawtech.jiayou.R;
import com.edawtech.jiayou.config.base.BaseMvpActivity;
import com.edawtech.jiayou.config.base.MyApplication;
import com.edawtech.jiayou.config.bean.UserGrowthBean;
import com.edawtech.jiayou.config.constant.VsUserConfig;
import com.edawtech.jiayou.mvp.presenter.PublicPresenter;
import com.edawtech.jiayou.ui.adapter.MyPagerAdapter;
import com.edawtech.jiayou.ui.fragment.InviteFragment;
import com.edawtech.jiayou.ui.fragment.RedListFragment;
import com.edawtech.jiayou.utils.CommonParam;
import com.edawtech.jiayou.utils.FitStateUtils;
import com.edawtech.jiayou.utils.StringUtils;
import com.edawtech.jiayou.utils.tool.LogUtils;
import com.edawtech.jiayou.utils.tool.ToastUtil;
import com.edawtech.jiayou.widgets.MyNestedScrollView;
import com.edawtech.jiayou.widgets.MyViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;


import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 我的成长金
 */
public class GrowMoneyActivity extends BaseMvpActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tablayout)
    XTabLayout tabLayout;
    @BindView(R.id.tv_redbag)
    TextView tvRedBag;
    @BindView(R.id.nestedScrollView)
    MyNestedScrollView scrollView;
    @BindView(R.id.vp_content)
    MyViewPager mPager;

    private PublicPresenter mPresenter;
    private MyPagerAdapter adapter;
    private Fragment[] fs;
    //tabLayout显示的下标
    private int position = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_grow_money;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        FitStateUtils.setImmersionStateMode(this, R.color.public_color_DE5A3C);
        mPresenter = new PublicPresenter(this, false, "");
        mPresenter.attachView(this);
        tvTitle.setText("我的成长金");
        addFragments();
        initListener();
        //获取用户成长金
        getUserGrowth();
    }

    /**
     * 添加Fragment
     */
    private void addFragments() {
        String[] titles = new String[]{"成长金明细", "我的邀请"};
        fs = new Fragment[]{new RedListFragment(), new InviteFragment()};
        adapter = new MyPagerAdapter(getSupportFragmentManager(), titles, fs);
        mPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mPager);
    }

    private void initListener() {
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //判断是否滑到的底部
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    LogUtils.e("fxx", "nestedScrollView已经滑到底了");
                    switch (position){
                        case 0:         //成长金明细
                            //成长金明细加载更多
                            EventBus.getDefault().postSticky("redListLoadMore");
                            break;
                        case 1:         //邀请列表
                            //邀请列表加载更多
                            EventBus.getDefault().postSticky("inviteLoadMore");
                            break;
                    }
                }
            }
        });

        tabLayout.addOnTabSelectedListener(new XTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(XTabLayout.Tab tab) {
                position = tab.getPosition();
                LogUtils.e("fxx", "addOnTabSelectedListener    当前下标=" + position);
            }

            @Override
            public void onTabUnselected(XTabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(XTabLayout.Tab tab) {

            }
        });

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mPager.resetHeight(0);
                } else if (position == 1) {
                    mPager.resetHeight(1);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 获取用户成长金
     */
    private void getUserGrowth() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", MyApplication.UID);
        map.put("appId", CommonParam.APP_ID);
        mPresenter.netWorkRequestGet(CommonParam.GET_USER_GROWTH, map);
    }

    @OnClick({R.id.iv_back, R.id.rtv_go_withdraw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rtv_go_withdraw:
                Intent intent_balance = new Intent(this, VsMyRedPagActivity.class);
                String urlTo = VsUserConfig.getDataString(this, VsUserConfig.JKey_RED_PAGE);
                String[] aboutBusiness = new String[]{"我的红包", "", urlTo};
                intent_balance.putExtra("AboutBusiness", aboutBusiness);
                intent_balance.putExtra("uiFlag", "redbag");
                startActivity(intent_balance);
                break;
        }
    }

    @Override
    public void onSuccess(String data) {
        LogUtils.e("fxx", "获取用户成长金成功       data=" + data);
        UserGrowthBean bean = JSONObject.parseObject(data, UserGrowthBean.class);
        String amount = bean.getData().getAmount();
        if (!StringUtils.isEmpty(amount)) {
            //显示成长金
            tvRedBag.setText(amount);
        } else {
            tvRedBag.setText("0.00");
        }
    }

    @Override
    public void onFailure(Throwable e, int code, String msg, boolean isNetWorkError) {
        LogUtils.e("fxx", "获取用户成长金失败  code=" + code + "   msg=" + msg + "     isNetWorkError=" + isNetWorkError);
        ToastUtil.showMsg(msg);
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 刷新成长金
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshRed(String msg) {

    }
}

