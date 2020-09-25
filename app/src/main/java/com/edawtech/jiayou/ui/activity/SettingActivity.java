package com.edawtech.jiayou.ui.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.edawtech.jiayou.R;
import com.edawtech.jiayou.config.base.BaseMvpActivity;
import com.edawtech.jiayou.config.base.MyApplication;
import com.edawtech.jiayou.utils.CommonParam;
import com.edawtech.jiayou.utils.DialogUtils;
import com.edawtech.jiayou.utils.FitStateUtils;
import com.edawtech.jiayou.utils.SharePreferencesHelper;
import com.edawtech.jiayou.utils.tool.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置
 */
public class SettingActivity extends BaseMvpActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FitStateUtils.setImmersionStateMode(this, R.color.activity_title_color);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        tvTitle.setText(R.string.seting_title);
    }

    @OnClick({R.id.iv_back,  R.id.change_password, R.id.about_us, R.id.exit_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.change_password:  //修改密码
                if (MyApplication.isLogin) {
                    startActivity(new Intent( mContext, ResetPasswordActivity.class));
                } else {
                    startActivity(new Intent( mContext, LoginActivity.class));
                    ToastUtil.showMsg(mContext.getResources().getString(R.string.nologin_auto_hint));
                }
                break;
            case R.id.about_us:         //关于我们
                startActivity(new Intent( mContext, KcQcodeActivity.class));
                break;
            case R.id.exit_login:       //退出登录
                logout();
                break;
        }
    }

    /**
     * 退出登录
     */
    private void logout() {
        if (MyApplication.isLogin) {
            DialogUtils. showYesNoDialog(this, null, getResources().getString(R.string.vs_setiong_exit_dialog_hint), getResources().getString(R.string.ok), getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //刷新我的页面数据信息
                    EventBus.getDefault().post(true);
                    MyApplication.isLogin = false;
                    MyApplication.UID = "%22%22";
                    //清空保存的数据
                    SharePreferencesHelper sp = new SharePreferencesHelper(mContext, CommonParam.SP_NAME);
                    sp.clear();
                    // 注销
                    startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                    finish();
                }
            }, null, null);
        } else {
            ToastUtil.showMsg("您目前尚未登录，请先登录");
        }
    }

    @Override
    public void onSuccess(String data) {

    }

    @Override
    public void onFailure(Throwable e, int code, String msg, boolean isNetWorkError) {

    }
}
