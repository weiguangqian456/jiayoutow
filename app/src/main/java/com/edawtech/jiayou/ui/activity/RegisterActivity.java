package com.edawtech.jiayou.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.edawtech.jiayou.R;
import com.edawtech.jiayou.config.base.BaseMvpActivity;
import com.edawtech.jiayou.mvp.presenter.PublicPresenter;
import com.edawtech.jiayou.net.observer.TaskCallback;
import com.edawtech.jiayou.utils.AutoCodeUtil;
import com.edawtech.jiayou.utils.CommonParam;
import com.edawtech.jiayou.utils.FitStateUtils;
import com.edawtech.jiayou.utils.LogUtils;
import com.edawtech.jiayou.utils.StringUtils;
import com.edawtech.jiayou.utils.tool.ToastUtil;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 注册
 */
public class RegisterActivity extends BaseMvpActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_invite)
    EditText etInvite;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.iv_look)
    ImageView ivLook;

    //是否可见
    private boolean isLook = false;
    //数据绑定
    private PublicPresenter publicPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        FitStateUtils.setImmersionStateMode(this, R.color.public_color_EC6941);
        tvTitle.setText("注册");
        publicPresenter = new PublicPresenter(this, true, "请求中...");
        publicPresenter.attachView(this);
        initListener();
    }

    private void initListener() {
        //监听变化
        etMobile.addTextChangedListener(new TextWatcher() {
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

    @Override
    protected void onStop() {
        //解决倒计时内存泄露问题
        AutoCodeUtil.cancel();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        //解除绑定
        publicPresenter.detachView();
        super.onDestroy();
    }

    @OnClick({R.id.iv_back, R.id.iv_close, R.id.tv_get_code, R.id.iv_look, R.id.bt_sure, R.id.tv_deal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_close:
                etMobile.setText("");
                break;
            case R.id.tv_get_code:      //获取验证码
                //用户注册的账号  手机号
                String mobile = etMobile.getText().toString();
                getCode(mobile);
                break;
            case R.id.iv_look:          //密码可见
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
            case R.id.bt_sure:          //注册
                mobile = etMobile.getText().toString();
                String code = etCode.getText().toString();
                String pwd = etPassword.getText().toString();
                String invite = etInvite.getText().toString();
                register(mobile, code, pwd, invite);
                break;
            case R.id.tv_deal:          //电子协议
                final String urlTo = "file:///android_asset/shop_service_terms.html";
                Intent intent = new Intent();
                intent.setClass(mContext, WeiboShareWebViewActivity.class);
                String[] aboutBusiness = new String[]{mContext.getString(R.string.welcome_main_elecdeal), "service", urlTo};
                intent.putExtra("AboutBusiness", aboutBusiness);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * 获取验证码
     *
     * @param mobile
     */
    private void getCode(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            ToastUtil.showMsg("请输入手机号码");
            return;
        }
        if (mobile.length() != 11) {
            ToastUtil.showMsg("手机号码不正确");
            return;
        }
        Map<String, Object> map = new HashMap<>();
//        map.put("mobile",mobile);
        map.put("mobile", "13312388345");
        //type  = 1 注册
        map.put("type",1);
        //按钮计时
        AutoCodeUtil.start(tvGetCode);
        publicPresenter.netWorkRequestGet(CommonParam.TEST_BASE_URL + "/sms/code", map);
    }

    /**
     * 注册
     */
    private void register(String phone, String code, String pwd, String invite) {
        if (StringUtils.isEmpty(phone)) {
            ToastUtil.showMsg("请输入手机号码");
            return;
        }
        if (phone.length() != 11) {
            ToastUtil.showMsg("手机号码不正确");
            return;
        }
        if (StringUtils.isEmpty(code)) {
            ToastUtil.showMsg("验证码不能为空");
            return;
        }
        if (StringUtils.isEmpty(pwd)) {
            ToastUtil.showMsg("密码不能为空");
            return;
        }
        if (pwd.length() < 8 || pwd.length() > 14) {
            ToastUtil.showMsg("请输入8-14位密码");
            return;
        }
        if (StringUtils.isEmpty(invite)) {
            ToastUtil.showMsg("邀请码不能为空");
            return;
        }

        long time = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>();
        map.put("agenId", "jiayou");
        map.put("appId", CommonParam.APP_ID);
        map.put("code", code);
        map.put("invitationCode", invite);
        map.put("password", pwd);
//        map.put("phone", phone);
        map.put("phone", "13312388345");
        map.put("pv", "android");
        map.put("v", "1.0");
        map.put("first_pay_time", String.valueOf(time));

        publicPresenter.netWorkRequestPost(CommonParam.TEST_BASE_URL + "/register", map, new TaskCallback() {
            @Override
            public void onSuccess(String data) {
                LogUtils.e("fxx", "注册成功   data=" + data);
                ToastUtil.showMsg("注册成功");
                finish();
            }

            @Override
            public void onFailure(Throwable e, int code, String msg, boolean isNetWorkError) {
                LogUtils.e("fxx", "注册失败   data=" + msg + "       code=" + code + "       isNetWorkError=" + isNetWorkError);
                ToastUtil.showMsg(msg);
                etPassword.setText("");
                //获取验证码按钮获取点击焦点
                AutoCodeUtil.cancel();
                tvGetCode.setEnabled(true);
                tvGetCode.setText("重新发送");
                tvGetCode.setTextColor(getResources().getColor(R.color.White));
            }
        });

    }


    @Override
    public void onSuccess(String data) {
        LogUtils.e("fxx", "获取验证码成功   data=" + data);
        ToastUtil.showMsg("发送成功，请注意查收！");
    }

    @Override
    public void onFailure(Throwable e, int code, String msg, boolean isNetWorkError) {
        LogUtils.e("fxx", "获取验证码失败   data=" + msg + "       code=" + code + "       isNetWorkError=" + isNetWorkError);
        //获取验证码按钮获取点击焦点
        AutoCodeUtil.cancel();
        tvGetCode.setEnabled(true);
        tvGetCode.setText("重新发送");
        tvGetCode.setTextColor(getResources().getColor(R.color.White));
    }
}