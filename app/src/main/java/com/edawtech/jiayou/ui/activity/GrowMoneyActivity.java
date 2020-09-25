package com.edawtech.jiayou.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSONObject;
import com.androidkun.xtablayout.XTabLayout;
import com.edawtech.jiayou.R;
import com.edawtech.jiayou.config.base.BaseMvpActivity;
import com.edawtech.jiayou.config.base.MyApplication;
import com.edawtech.jiayou.config.bean.UserGrowthBean;
import com.edawtech.jiayou.mvp.presenter.PublicPresenter;
import com.edawtech.jiayou.ui.adapter.MyPagerAdapter;
import com.edawtech.jiayou.ui.fragment.InviteFragment;
import com.edawtech.jiayou.ui.fragment.RedListFragment;
import com.edawtech.jiayou.utils.CommonParam;
import com.edawtech.jiayou.utils.DialogUtils;
import com.edawtech.jiayou.utils.FitStateUtils;
import com.edawtech.jiayou.utils.StringUtils;
import com.edawtech.jiayou.utils.tool.LogUtils;
import com.edawtech.jiayou.utils.tool.ToastUtil;
import com.edawtech.jiayou.widgets.MyViewPager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;


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
    @BindView(R.id.vp_content)
    MyViewPager mPager;
    @BindView(R.id.bt_bind)
    Button btBind;

    private PublicPresenter mPresenter;
    private MyPagerAdapter adapter;
    private Fragment[] fs;
    //是否绑定微信
    private boolean isBindWx = false;
    //用户余额
    private String money;

    @Override
    public int getLayoutId() {
        return R.layout.activity_grow_money;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        FitStateUtils.setImmersionStateMode(this, R.color.activity_title_color);
        mPresenter = new PublicPresenter(this, false, "");
        mPresenter.attachView(this);
        tvTitle.setText("我的成长金");
        addFragments();
        initListener();
        //检测用户是否绑定
        checkUserIsBindWx();
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
        //适应高度
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

    /**
     * 检测用户是否绑定微信
     */
    private void checkUserIsBindWx() {
        OkGo.<String>post(CommonParam.CHECK_USER_IS_BIND_WX)
                .params("appId", CommonParam.APP_ID)
                .params("uid", MyApplication.UID)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        LogUtils.i("fxx", "检测用户绑定微信成功   data=" + body);
                        try {
                            org.json.JSONObject json = new org.json.JSONObject(body);
                            int code = json.getInt("code");
                            String msg = json.getString("msg");
                            if (code == 0) {
                                org.json.JSONObject data = json.getJSONObject("data");
                                int isBind = data.getInt("isBind");
                                if (isBind == 1) {
                                    isBindWx = true;
                                    btBind.setText("去提现");
                                } else {
                                    isBindWx = false;
                                    btBind.setText("未绑定");
                                }
                            } else {
                                isBindWx = false;
                                btBind.setText("未绑定");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        LogUtils.i("fxx", "检测用户绑定微信失败   data=" + response.body());
                        isBindWx = false;
                        btBind.setText("未绑定");
                    }
                });
    }


    @OnClick({R.id.iv_back, R.id.bt_bind, R.id.tv_bind_wx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                ActivityCompat.finishAfterTransition(this);
                break;
            case R.id.bt_bind:
                if (isBindWx) {
                    if (Double.parseDouble(money) > 0) {
                        Intent it = new Intent(this, VsMyRedPopActivity.class);
                        it.putExtra("money", money);
                        startActivity(it);
                    } else {
                        ToastUtil.showMsg("余额不足");
                    }
                } else {
                    DialogUtils. showYesNoDialog(this, null,"未绑定微信，无法提现\n请前往微信绑定", getResources().getString(R.string.ok), getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //刷新我的页面数据信息
                            startActivity(new Intent(GrowMoneyActivity. this, VsredinfoActivity.class));
                        }
                    }, null, null);
                }
                break;
            case R.id.tv_bind_wx:
                startActivity(new Intent(this, VsredinfoActivity.class));
                break;
        }
    }

    @Override
    public void onSuccess(String data) {
        LogUtils.e("fxx", "获取用户成长金成功       data=" + data);
        UserGrowthBean bean = JSONObject.parseObject(data, UserGrowthBean.class);
        money = bean.getData().getRed();
        if (!StringUtils.isEmpty(money)) {
            //显示成长金
            tvRedBag.setText(money);
        } else {
            tvRedBag.setText("0");
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
        if (msg.equals("withdrawSuccess")){
            LogUtils.i("fxx","提现成功   刷新成长金");
            getUserGrowth();
        }
    }
}

