package com.edawtech.jiayou.ui.activity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edawtech.jiayou.R;
import com.edawtech.jiayou.config.base.BaseMvpActivity;
import com.edawtech.jiayou.config.base.MyApplication;
import com.edawtech.jiayou.config.base.VsBaseActivity;
import com.edawtech.jiayou.config.constant.DfineAction;
import com.edawtech.jiayou.config.constant.GlobalVariables;
import com.edawtech.jiayou.config.constant.VsUserConfig;
import com.edawtech.jiayou.config.service.VsCoreService;
import com.edawtech.jiayou.json.me.JSONObject;
import com.edawtech.jiayou.mvp.presenter.PublicPresenter;
import com.edawtech.jiayou.net.observer.TaskCallback;
import com.edawtech.jiayou.ui.dialog.CustomDialog;
import com.edawtech.jiayou.utils.AutoCodeUtil;
import com.edawtech.jiayou.utils.CommonParam;
import com.edawtech.jiayou.utils.FitStateUtils;
import com.edawtech.jiayou.utils.LogUtils;
import com.edawtech.jiayou.utils.StringUtils;
import com.edawtech.jiayou.utils.sp.SharePreferencesHelper;
import com.edawtech.jiayou.utils.tool.CoreBusiness;
import com.edawtech.jiayou.utils.tool.ToastUtil;
import com.edawtech.jiayou.utils.tool.VsNetWorkTools;
import com.edawtech.jiayou.utils.tool.VsRc4;
import com.edawtech.jiayou.utils.tool.VsUtil;
import com.flyco.roundview.RoundTextView;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 重置密码
 */
public class VsSetPhoneActivity extends BaseMvpActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.iv_look)
    ImageView ivLook;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;

    //是否可见
    private boolean isLook = false;
    //数据绑定
    private PublicPresenter publicPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_vs_set_phone;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        FitStateUtils.setImmersionStateMode(this, R.color.public_color_EC6941);
        if (MyApplication.isLogin) {
            tvTitle.setText(R.string.vs_set_phone_title_hint1);
        } else {
            tvTitle.setText(R.string.for_get_password);
        }
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

    @OnClick({R.id.iv_back, R.id.iv_close, R.id.tv_get_code, R.id.iv_look, R.id.bt_sure})
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
                    etNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etNewPassword.setSelection(etNewPassword.getText().toString().length());
                    isLook = false;
                } else {
                    ivLook.setImageResource(R.drawable.vs_checked_no);
                    etNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etNewPassword.setSelection(etNewPassword.getText().toString().length());
                    isLook = true;
                }
                break;
            case R.id.bt_sure:      //重置密码
                mobile = etMobile.getText().toString();
                String code = etCode.getText().toString();
                String newPassword = etNewPassword.getText().toString();
                resetPassword(mobile, code, newPassword);
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
        //type = 2 修改/忘记密码
        map.put("type",2);
        //按钮计时
        AutoCodeUtil.start(tvGetCode);
        publicPresenter.netWorkRequestGet(CommonParam.TEST_BASE_URL + "/sms/code", map);
    }

    /**
     * 重置密码
     *
     * @param
     */
    private void resetPassword(String mobile, String code, String newPassword) {
        if (StringUtils.isEmpty(mobile)) {
            ToastUtil.showMsg("请输入手机号码");
            return;
        }
        if (mobile.length() != 11) {
            ToastUtil.showMsg("手机号码不正确");
            return;
        }
        if (StringUtils.isEmpty(code)) {
            ToastUtil.showMsg("验证码不能为空");
            return;
        }
        if (StringUtils.isEmpty(newPassword)) {
            ToastUtil.showMsg("新密码不能为空");
            return;
        }
        if (newPassword.length() < 8 || newPassword.length() > 14) {
            ToastUtil.showMsg("请输入8-14位新密码");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("phone",mobile);
        map.put("newPassword",newPassword);
        map.put("verifyCode",code);
        publicPresenter.netWorkRequestPost(CommonParam.TEST_BASE_URL + "/user/retrievePaw", map, new TaskCallback() {
            @Override
            public void onSuccess(String data) {
                LogUtils.e("fxx", "修改成功   data=" + data);
                //是否登录过   用于判断是从哪个入口进入重置密码页面    true 修改密码进入   false  忘记密码进入
//                boolean isLogin = MyApplication.isLogin;
//                if (isLogin) {
//                    //清除本地保存信息
//                    SharePreferencesHelper sp = new SharePreferencesHelper(VsSetPhoneActivity.this, CommonParam.SP_NAME);
//                    sp.clear();
//                    //清除全局信息
//                    MyApplication.isLogin = false;
//                    MyApplication.UID = "";
//                    MyApplication.MOBILE = "";
//
//                    //修改密码进入
//                    Intent intent = new Intent(VsSetPhoneActivity.this, VsLoginActivity.class);
//                    intent.putExtra("isClearOtherActivity", true);
//                    startActivity(intent);
//                }
//                finish();
            }

            @Override
            public void onFailure(Throwable e, int code, String msg, boolean isNetWorkError) {
                LogUtils.e("fxx", "修改失败   data=" + msg + "       code=" + code + "       isNetWorkError=" + isNetWorkError);
                ToastUtil.showMsg(msg);
                etNewPassword.setText("");
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
        com.edawtech.jiayou.utils.LogUtils.e("fxx", "获取验证码成功   data=" + data);
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
