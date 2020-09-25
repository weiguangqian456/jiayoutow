package com.edawtech.jiayou.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.edawtech.jiayou.R;
import com.edawtech.jiayou.config.base.BaseMvpActivity;
import com.edawtech.jiayou.config.base.MyApplication;
import com.edawtech.jiayou.config.bean.LoginInfo;
import com.edawtech.jiayou.mvp.presenter.PublicPresenter;
import com.edawtech.jiayou.utils.ActivityCollector;
import com.edawtech.jiayou.utils.CommonParam;
import com.edawtech.jiayou.utils.FitStateUtils;
import com.edawtech.jiayou.utils.SharePreferencesHelper;
import com.edawtech.jiayou.utils.tool.ToastUtil;


import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 登录
 */
public class LoginActivity extends BaseMvpActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.iv_look)
    ImageView ivLook;

    //是否可见
    private boolean isLook = false;
    //数据绑定
    private PublicPresenter publicPresenter;
    //是否修改密码
    private boolean isChangePassword = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_vs_login;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

        Intent it = getIntent();
        //修改密码成功后，需要将之前Activity实例清除
        boolean isClearOtherActivity = it.getBooleanExtra("isClearOtherActivity", false);
        if (isClearOtherActivity) {
            //清除其他Activity实例
            ActivityCollector collector = ActivityCollector.getInstance();
            collector.finishOtherActivity(LoginActivity.class);
            isChangePassword = true;
        }

        FitStateUtils.setImmersionStateMode(this, R.color.activity_title_color);
        tvTitle.setText("登录");
        publicPresenter = new PublicPresenter(this, true, "登录中...");
        publicPresenter.attachView(this);
        initListener();
    }

    @Override
    protected void onDestroy() {
        if (publicPresenter != null) {
            publicPresenter.detachView();
        }
        super.onDestroy();
    }

    private void initListener() {
        //监听变化
        etAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //输入变化
                if (s.length() > 0) {
                    ivClose.setVisibility(View.VISIBLE);
                } else {
                    ivClose.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @OnClick({R.id.iv_back, R.id.iv_close, R.id.iv_look, R.id.tv_forget_password, R.id.tv_register, R.id.bt_login})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.iv_back:  //返回
                finish();
                break;
            case R.id.iv_close: //清除账号
                etAccount.setText("");
                break;
            case R.id.iv_look:  //密码可见
                if (isLook) {
                    ivLook.setImageResource(R.drawable.vs_checked_yes);
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etPassword.setSelection(etPassword.getText().toString().length());
                    isLook = false;
                } else {
                    ivLook.setImageResource(R.drawable.vs_checked_no);
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etPassword.setSelection(etPassword.getText().toString().length());
                    isLook = true;
                }
                break;
            case R.id.tv_forget_password:// 忘记密码
                String phone = etAccount.getText().toString().trim();
                Intent intent = new Intent(mContext, ResetPasswordActivity.class);
                if (phone != null && !"".equals(phone)) {
                    intent.putExtra("phone", phone);
                }
                // 进入重置密码界面
                startActivity(intent);
                break;
            case R.id.tv_register:// 免费注册
                Intent intent2 = new Intent(mContext, RegisterActivity.class);
                startActivity(intent2);
                break;
            case R.id.bt_login:// 登录
                String account = etAccount.getText().toString();
                String pwd = etPassword.getText().toString();
                if (account != null && !"null".equalsIgnoreCase(account) && !"".equalsIgnoreCase(account)) {
                    if (pwd != null && !"".equals(pwd) && pwd.length() >= 6) {
                        // 登录
                        login(account, pwd);
                    } else if (pwd == null || "".equals(pwd)) {
                        ToastUtil.showMsg(getResources().getString(R.string.vs_pwd_isnull_str));
                    } else if (pwd.length() < 6) {
                        ToastUtil.showMsg(getResources().getString(R.string.vs_pwd_xy6_str));
                    }
                } else {
                    ToastUtil.showMsg(getResources().getString(R.string.vs_phone_erro_isnull));
                }
                break;
            default:
                break;
        }
    }


    /**
     * 登录
     *
     * @param account
     * @param pwd
     */
    private void login(String account, String pwd) {
        Map<String, Object> map = new HashMap<>();
        map.put("appId", CommonParam.APP_ID);
        map.put("account", account);
        map.put("password", pwd);
        publicPresenter.netWorkRequestPost(CommonParam.LOGIN, map);
    }

    @Override
    public void onSuccess(String data) {
        Log.e("fxx", "登录成功data=" + data);
        LoginInfo info = JSONObject.parseObject(data, LoginInfo.class);
        ToastUtil.showMsg("登录成功");
        //保存全局消息
        MyApplication.isLogin = true;
        MyApplication.UID = info.getData().getUid();
        MyApplication.MOBILE = info.getData().getPhone();
        //保存至本地
        SharePreferencesHelper sp = new SharePreferencesHelper(mContext, CommonParam.SP_NAME);
        sp.put("uid", info.getData().getUid());
        sp.put("mobile", info.getData().getPhone());
        sp.put("isLogin", true);
        sp.put("level", info.getData().getLevel());
        sp.put("levelName", info.getData().getLevelName());
        if (isChangePassword) {
            startActivity(new Intent(this,MainActivity.class));
        } else {
            finish();
        }
        //EventBus通知我的页面更新用户数据
        EventBus.getDefault().postSticky(info.getData());
    }

    @Override
    public void onFailure(Throwable e, int code, String msg, boolean isNetWorkError) {
        Log.e("fxx", "登录失败 code=" + code + "     msg=" + msg + "         isNetWorkError=" + isNetWorkError);
        etPassword.setText("");
        ToastUtil.showMsg(msg);
    }
}
