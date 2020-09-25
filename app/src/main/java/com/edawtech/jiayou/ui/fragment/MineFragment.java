package com.edawtech.jiayou.ui.fragment;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.edawtech.jiayou.R;
import com.edawtech.jiayou.config.base.BaseMvpFragment;
import com.edawtech.jiayou.config.base.MyApplication;
import com.edawtech.jiayou.config.bean.LoginInfo;
import com.edawtech.jiayou.config.bean.UserGrowthBean;
import com.edawtech.jiayou.config.bean.UserInfoBean;
import com.edawtech.jiayou.mvp.presenter.PublicPresenter;
import com.edawtech.jiayou.net.observer.TaskCallback;
import com.edawtech.jiayou.ui.activity.GrowMoneyActivity;
import com.edawtech.jiayou.ui.activity.KcMyQcodeActivity;
import com.edawtech.jiayou.ui.activity.LoginActivity;
import com.edawtech.jiayou.ui.activity.UserInfoActivity;
import com.edawtech.jiayou.ui.activity.SettingActivity;
import com.edawtech.jiayou.ui.activity.WeiboShareWebViewActivity;
import com.edawtech.jiayou.ui.adapter.MyFragmentListAdapter;
import com.edawtech.jiayou.ui.view.CircleImageView;
import com.edawtech.jiayou.utils.AppIconHelper;
import com.edawtech.jiayou.utils.CommonParam;
import com.edawtech.jiayou.utils.StringUtils;
import com.edawtech.jiayou.utils.SharePreferencesHelper;
import com.edawtech.jiayou.utils.tool.LogUtils;
import com.edawtech.jiayou.utils.tool.ToastUtil;
import com.mob.MobSDK;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class MineFragment extends BaseMvpFragment {

    @BindView(R.id.iv_user_icon)
    CircleImageView ivUserIcon;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    @BindView(R.id.tv_user_type)
    TextView tvUserType;
    @BindView(R.id.tv_money)
    TextView czjMoney;
    @BindView(R.id.list)
    RecyclerView list;

    private Context mContext;
    private PublicPresenter mPresenter;
    private MyFragmentListAdapter adapter;
    private boolean isChangeInfo = false;     //是否修改了用户资料
    private UserInfoBean.DataBean.TUserInfoBean mUserInfo;  //用户信息
    private ArrayList<Integer> iconData = new ArrayList<>();
    private String[] titles = new String[4];
    //用户分享时需传递的链接
    private String shareUrl = "";

    @Override
    protected int getLayoutId() {
        return R.layout.my_frament;
    }

    @Override
    protected void initView(@Nullable View view, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        EventBus.getDefault().register(this);
        mPresenter = new PublicPresenter(getContext(), false, "");
        mPresenter.attachView(this);
        addIconRes();
        initListener();
        getUserInfo();
    }

    /**
     * 添加资源文件
     */
    private void addIconRes() {
        iconData.add(R.drawable.ic_share_icon);
        iconData.add(R.drawable.ic_qr_code);
        iconData.add(R.drawable.ic_help_center);
        iconData.add(R.drawable.ic_setting_icon);

        titles[0] = "分享好友";
        titles[1] = "我的二维码";
        titles[2] = "帮助中心";
        titles[3] = "设置";
    }


    /**
     * 适配器监听
     */
    private void initListener() {
        if (adapter == null) {
            adapter = new MyFragmentListAdapter(getContext(), iconData, titles);
        }
        list.setNestedScrollingEnabled(false);  //不准滑动
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(adapter);

        adapter.setItemClickListener(new MyFragmentListAdapter.ItemClickListener() {
            @Override
            public void onClickListener(View v, int position) {
                switch (position) {
                    case 0:     //分享好友
                        if (MyApplication.isLogin) {
                            if (!StringUtils.isEmpty(shareUrl)) {
                                share();
                            } else {
                                ToastUtil.showMsg("获取分享链接失败");
                            }
                        } else {
                            ToastUtil.showMsg(mContext.getResources().getString(R.string.nologin_auto_hint));
                        }
                        break;
                    case 1:     //我的二维码
                        if (MyApplication.isLogin) {
                            startActivity(new Intent(mContext, KcMyQcodeActivity.class));
                        } else {
                            startActivity(new Intent(mContext, LoginActivity.class));
                            ToastUtil.showMsg(mContext.getResources().getString(R.string.nologin_auto_hint));
                        }
                        break;
                    case 2:     //帮助中心
                        Intent helperIntent = new Intent();
                        String[] aboutBusiness = new String[]{mContext.getResources().getString(R.string.help), "", "http://paas.edawtech.com/help/dudu_help.html"};
                        helperIntent.putExtra("AboutBusiness", aboutBusiness);
                        helperIntent.setClass(mContext, WeiboShareWebViewActivity.class);
                        startActivity(helperIntent);
                        break;
                    case 3:     //设置
                        startActivity(new Intent(mContext, SettingActivity.class));
                        break;
                }
            }
        });
    }

    /**
     * 分享
     */
    private void share() {
        Bitmap bt = AppIconHelper.getAppIcon(getContext().getPackageManager(), "com.edawtech.jiayou");

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("佳油");
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl(shareUrl);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageData(bt);
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl(shareUrl);
        // comment是我对这条分享的评论，仅在人人网使用
//        oks.setComment("我是测试评论文本");
        // 启动分享GUI
        oks.show(MobSDK.getContext());
    }

    @OnClick({R.id.user_info_layout, R.id.czj_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_info_layout:     //头像
                if (MyApplication.isLogin) {
                    if (mUserInfo != null) {
                        EventBus.getDefault().postSticky(mUserInfo);
                    }

                    if (Build.VERSION.SDK_INT >= 21) {
                        startActivity(new Intent(mContext, UserInfoActivity.class), ActivityOptions.makeSceneTransitionAnimation(getActivity(), ivUserIcon, "icon").toBundle());
                    } else {
                        startActivity(new Intent(mContext, UserInfoActivity.class));
                    }
                } else {
                    startActivity(new Intent(mContext, LoginActivity.class));
                    ToastUtil.showMsg(mContext.getResources().getString(R.string.login_prompt4));
                }
                break;
            case R.id.czj_layout:       //成长金
                if (MyApplication.isLogin) {
                    if (Build.VERSION.SDK_INT >= 21) {
                        startActivity(new Intent(mContext, GrowMoneyActivity.class), ActivityOptions.makeSceneTransitionAnimation(getActivity(), czjMoney, "money").toBundle());
                    } else {
                        startActivity(new Intent(mContext, GrowMoneyActivity.class));
                    }
                } else {
                    startActivity(new Intent(mContext, LoginActivity.class));
                    ToastUtil.showMsg(mContext.getResources().getString(R.string.login_prompt2));
                }
                break;
        }
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        LogUtils.e("fxx", "是否登录=" + MyApplication.isLogin);
        if (MyApplication.isLogin) {
            Map<String, Object> map = new HashMap<>();
            map.put("phone", MyApplication.MOBILE);
            map.put("appId", CommonParam.APP_ID);
            map.put("uid", MyApplication.UID);
            mPresenter.netWorkRequestGet(CommonParam.GET_USER_INFO, map);
            if (!isChangeInfo) {
                //获取用户成长金
                getUserGrowth();
                //获取用户分享内容
                getShareContent();
            }
        } else {
            tvNickname.setText("未登陆");
            czjMoney.setText("0");
            tvMobile.setVisibility(View.GONE);
            tvUserType.setVisibility(View.GONE);
        }
    }

    /**
     * 获取用户成长金
     */
    private void getUserGrowth() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", MyApplication.UID);
        map.put("appId", CommonParam.APP_ID);
        mPresenter.netWorkRequestGet(CommonParam.GET_USER_GROWTH, map, new TaskCallback() {
            @Override
            public void onSuccess(String data) {
                LogUtils.e("fxx", "获取用户成长金成功       data=" + data);
                UserGrowthBean bean = JSONObject.parseObject(data, UserGrowthBean.class);
                String amount = bean.getData().getAmount();
                if (!StringUtils.isEmpty(amount)) {
                    //显示成长金
                    czjMoney.setText(amount);
                } else {
                    czjMoney.setText("0");
                }
            }

            @Override
            public void onFailure(Throwable e, int code, String msg, boolean isNetWorkError) {
                LogUtils.e("fxx", "获取用户成长金失败  code=" + code + "   msg=" + msg + "     isNetWorkError=" + isNetWorkError);
                ToastUtil.showMsg(msg);
            }
        });
    }

    /**
     * 获取分享内容
     *
     * @param
     */
    private void getShareContent() {
        Map<String, Object> map = new HashMap<>();
        map.put("appId", CommonParam.APP_ID);
        map.put("uid", MyApplication.UID);
        map.put("type", "ere");
        map.put("phone", MyApplication.MOBILE);
        mPresenter.netWorkRequestGet(CommonParam.GET_USER_SHARE_MSG, map, new TaskCallback() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("fxx", "获取用户分享内容成功   data=" + data);
                try {
                    org.json.JSONObject json = new org.json.JSONObject(data);
                    org.json.JSONObject dataJson = json.getJSONObject("data");
                    shareUrl = dataJson.getString("shareLink");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable e, int code, String msg, boolean isNetWorkError) {
                LogUtils.i("fxx", "获取用户分享内容失败   code=" + code + "    msg=" + msg + "    isNetWorkError=" + isNetWorkError);
            }
        });
    }


    @Override
    public void onSuccess(String data) {
        LogUtils.e("fxx", "获取用户信息成功      data=" + data);
        UserInfoBean info = JSONObject.parseObject(data, UserInfoBean.class);
        List<UserInfoBean.DataBean.TUserInfoBean> list = info.getData().getTUserInfo();
        int level = info.getData().getLevel();
        //保存用户level值
        SharePreferencesHelper sp = new SharePreferencesHelper(mContext, CommonParam.SP_NAME);
        sp.put("level", level);

        String levelName = info.getData().getLevelName();
        if (list.size() > 0) {
            //改变是否修改用户信息
            isChangeInfo = false;
            tvMobile.setVisibility(View.VISIBLE);
            tvUserType.setVisibility(View.VISIBLE);
            mUserInfo = list.get(0);
            String phone = mUserInfo.getPhone();
            String nickname = mUserInfo.getUserName();
            String icon = mUserInfo.getHeadLikeUrl();
            if (icon != null && icon.length() > 0) {
                Glide.with(mContext)
                        .load(icon)
                        .placeholder(R.drawable.mall_user_image_defult)
                        .into(ivUserIcon);
            }
            if (nickname != null && nickname.length() > 0) {
                tvNickname.setText(nickname);
            } else {
                tvNickname.setText("未设置");
            }
            tvMobile.setText(phone.substring(0, 3) + "****" + phone.substring(7));
            tvUserType.setText(levelName);
        } else {
            LogUtils.e("fxx", "数据获取成功，但是数组没有数据");
            tvMobile.setVisibility(View.GONE);
            tvUserType.setVisibility(View.GONE);
            tvNickname.setText("未登陆");
        }
    }

    @Override
    public void onFailure(Throwable e, int code, String msg, boolean isNetWorkError) {
        LogUtils.e("fxx", "获取用户信息失败  code=" + code + "   msg=" + msg + "     isNetWorkError=" + isNetWorkError);
        ToastUtil.showMsg(msg);
        tvMobile.setVisibility(View.GONE);
        tvUserType.setVisibility(View.GONE);
        tvNickname.setText("未登陆");
    }


    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getActivity().getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getActivity().getPackageName(), 0);
            String version = info.versionName;
            return "V" + version;
        } catch (Exception e) {
            e.printStackTrace();
            return this.getString(R.string.version_unkown);
        }
    }

    /**
     * 修改用户信息成功后  刷新用户信息    提现成功  刷新成长金金额
     *
     * @param msg
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeUserInfoSuccess(String msg) {
        if (msg.equals("userInfoChangeSuccess")) {
            LogUtils.i("fxx", "修改用户  刷新用户信息");
            //修改信息后回调
            isChangeInfo = true;
            getUserInfo();
        } else if (msg.equals("withdrawSuccess")){
            LogUtils.i("fxx", "提现成功  刷新成长金信息");
            //提现成功  刷新成长金
            getUserGrowth();
        }
    }

    /**
     * EventBus 回调，登录成功后  刷新用户信息
     *
     * @param info
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void refreshUserInfo(LoginInfo.DataBean info) {
        LogUtils.i("fxx", "登录成功  刷新用户信息");
        getUserInfo();
    }

    /**
     * 退出登录后   刷新用户信息
     *
     * @param isRefresh
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void logoutSuccess(Boolean isRefresh) {
        LogUtils.i("fxx", "退出登录成功  刷新用户信息     isLogin=" + MyApplication.isLogin);
        if (isRefresh) {
            Glide.with(mContext)
                    .load(R.drawable.mall_user_image_defult)
                    .into(ivUserIcon);
            tvNickname.setText("未登录");
            czjMoney.setText("0");
            tvMobile.setVisibility(View.GONE);
            if (tvUserType.getVisibility() == View.VISIBLE) {
                tvUserType.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onDestroy() {
        //注销EventBus
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
