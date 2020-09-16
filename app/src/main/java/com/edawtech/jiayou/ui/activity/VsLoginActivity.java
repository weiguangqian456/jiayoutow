package com.edawtech.jiayou.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
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

import com.edawtech.jiayou.BuildConfig;
import com.edawtech.jiayou.R;
import com.edawtech.jiayou.config.base.BaseMvpActivity;
import com.edawtech.jiayou.config.constant.DfineAction;
import com.edawtech.jiayou.config.constant.GlobalVariables;
import com.edawtech.jiayou.config.constant.VsUserConfig;
import com.edawtech.jiayou.config.service.VsCoreService;
import com.edawtech.jiayou.mvp.presenter.PublicPresenter;
import com.edawtech.jiayou.utils.FitStateUtils;
import com.edawtech.jiayou.utils.tool.CoreBusiness;
import com.edawtech.jiayou.utils.tool.Rc;
import com.edawtech.jiayou.utils.tool.ToastUtil;
import com.edawtech.jiayou.utils.tool.VsMd5;
import com.edawtech.jiayou.utils.tool.VsNetWorkTools;
import com.edawtech.jiayou.utils.tool.VsRc4;
import com.edawtech.jiayou.utils.tool.VsUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.jetbrains.annotations.Nullable;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 登录
 */
public class VsLoginActivity extends BaseMvpActivity {

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
    private PublicPresenter publicPresenter;

    //是否可见
    private boolean isLook = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_vs_login;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

        Log.e("fxx","VsLoginActivity  登录");

        FitStateUtils.setImmersionStateMode(this, R.color.public_color_EC6941);
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
                Intent intent = new Intent(mContext, VsSetPhoneActivity.class);
                if (phone != null && !"".equals(phone)) {
                    intent.putExtra("phone", phone);
                }
                // 进入重置密码界面
                startActivity(intent);
                break;
            case R.id.tv_register:// 免费注册
                Intent intent2 = new Intent(mContext, VsRegisterActivity.class);
                startActivity(intent2);
                break;
            case R.id.bt_login:// 登录
                String mPhoneNumber = etAccount.getText().toString();
                String pwd = etPassword.getText().toString();
                if (mPhoneNumber != null && !"null".equalsIgnoreCase(mPhoneNumber) && !"".equalsIgnoreCase(mPhoneNumber)) {
                    if (pwd != null && !"".equals(pwd) && pwd.length() >= 6) {
                        // 登录
                            login(mPhoneNumber, pwd);
//                        login2(mPhoneNumber, pwd);
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

    private void login2(String account, String pwd) {

        Map<String, Object> map = new HashMap<>();
        String deciceid = VsUtil.getMacAddress(mContext);
        Long curTime = System.currentTimeMillis() / 1000;
        String netType = VsUtil.getNetTypeString();
        map.put("account", account);
        map.put("password", VsMd5.md5(pwd));
        map.put("device_id", deciceid.toUpperCase());
        map.put("net_mode", netType);
        map.put("device_type", Build.MODEL);

        String sign = "";
        sign = getSign(map);
        OkGo.<String>post("http://paas.edawtech.com/8.0.1/dudu/account/login")
//        OkGo.<String>post("https://route.edawtech.com/8.0.1/dudu/account/login")
                .params("account", account)
                .params("password", VsRc4.encry_RC4_string(pwd, DfineAction.passwad_key))
                .params("agent_id", "")
                .params("app_id", "dudu")
                .params("device_id", deciceid.toUpperCase())
                .params("device_type", Build.MODEL)
                .params("net_mode", netType)
                .params("pv", "android")
                .params("sign", sign)
                .params("token", "Y.XRWYO6DN5X7CV7GGV1OP7C55XY75TOONPUU1QRF3N8F_WU8OLNXMJFPH9MGPA9_RJNP3JFPE2UO054T7IJ4J4FWE.5MB64UODSEEKBJOF5KY3B6Z9MQM23ESZJC8MR")
                .params("ts", curTime.toString())
                .params("v", "8.3.11")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        Log.e("fxx", "登录成功：" + body);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Log.e("fxx", "登录失败：" + response.body());
                    }
                });
    }

    private String getSign(Map<String, Object> map) {
        TreeMap<String, Object> treemapsign = new TreeMap<String, Object>();
        treemapsign.putAll(map);
        treemapsign.put("key", "($*key*$)");
        StringBuffer src = null;
        Iterator iter = treemapsign.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            if (src == null) {
                src = new StringBuffer();
                src.append(key).append("=").append(URLEncoder.encode(val.toString()));
            } else {
                src.append("&").append(key).append("=").append(val.toString());
            }
        }
        String sign = null;
        try {
            // sign = VsMd5.md5(src.toString());
            sign = Rc.getSign(src.toString(), 1);
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
        return sign;
    }

    /**
     * 登录
     *
     * @param account
     * @param pwd
     */
    private void login(String account, String pwd) {

        Map<String, Object> map = new HashMap<>();
        String deciceid = VsUtil.getMacAddress(mContext);
        Long curTime = System.currentTimeMillis() / 1000;
        String netType = VsUtil.getNetTypeString();
        map.put("account", account);
        map.put("password", VsMd5.md5(pwd));
        map.put("device_id", deciceid.toUpperCase());
        map.put("net_mode", netType);
        map.put("device_type", Build.MODEL);

        String sign = getSign(map);

        map.put("agent_id", "");
        map.put("app_id", "dudu");
        map.put("pv", "android");
        map.put("sign", sign);
        map.put("token", "Y.XRWYO6DN5X7CV7GGV1OP7C55XY75TOONPUU1QRF3N8F_WU8OLNXMJFPH9MGPA9_RJNP3JFPE2UO054T7IJ4J4FWE.5MB64UODSEEKBJOF5KY3B6Z9MQM23ESZJC8MR");
        map.put("ts", curTime.toString());
        map.put("v", "8.3.11");

        publicPresenter.netWorkRequestGet("http://paas.edawtech.com/8.0.1/dudu/account/login", map);
    }

    @Override
    public void onSuccess(String data) {
        Log.e("fxx", "登录成功data=" + data);
    }

    @Override
    public void onFailure(Throwable e, int code, String msg, boolean isNetWorkError) {
        Log.e("fxx", "登录失败 code=" + code + "     msg=" + msg + "         isNetWorkError=" + isNetWorkError);

    }
}
