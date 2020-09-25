package com.edawtech.jiayou.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.edawtech.jiayou.R;
import com.edawtech.jiayou.config.base.BaseMvpActivity;
import com.edawtech.jiayou.config.base.MyApplication;
import com.edawtech.jiayou.config.constant.DfineAction;
import com.edawtech.jiayou.config.constant.GlobalVariables;
import com.edawtech.jiayou.config.constant.VsUserConfig;
import com.edawtech.jiayou.mvp.presenter.PublicPresenter;
import com.edawtech.jiayou.utils.CommonParam;
import com.edawtech.jiayou.utils.FitStateUtils;
import com.edawtech.jiayou.utils.StringUtils;
import com.edawtech.jiayou.utils.tool.CoreBusiness;
import com.edawtech.jiayou.utils.tool.LogUtils;
import com.edawtech.jiayou.utils.tool.ToastUtil;
import com.edawtech.jiayou.utils.tool.VsNetWorkTools;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置详情
 */
public class VsMyTextDetailActivity extends BaseMvpActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.et_nickname)
    EditText etNickname;
    @BindView(R.id.sex_man)
    ImageView sexMan;
    @BindView(R.id.sex_woman)
    ImageView sexWoman;
    @BindView(R.id.sex_layout)
    LinearLayout sexLayout;
    @BindView(R.id.et_email)
    EditText etEmail;

    private String flag;        //个人中心传来的标识name
    private String name;         //用于区别性别
    private boolean isMan = true;      //性别是否男
    private boolean isNotSet = false;  //性别是否未设置
    private PublicPresenter mPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_vs_my_text_detail;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        FitStateUtils.setImmersionStateMode(this, R.color.public_color_EC6941);
        mPresenter = new PublicPresenter(this, true, "请求中...");
        mPresenter.attachView(this);
        showMsg();
    }

    /**
     * 显示信息
     */
    private void showMsg() {
        tvRight.setText("保存");
        tvRight.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        //个人中心传来的值
        flag = intent.getStringExtra("flag");
        name = intent.getStringExtra("oldName");
        switch (flag) {
            case "nickname":        //昵称
                tvTitle.setText(getResources().getString(R.string.vs_myinfotext_name));
                etNickname.setVisibility(View.VISIBLE);
                etNickname.setText(name);
                sexLayout.setVisibility(View.GONE);
                etEmail.setVisibility(View.GONE);
                break;
            case "sex":         //性别
                tvTitle.setText(getResources().getString(R.string.vs_myinfotext_sex));
                sexLayout.setVisibility(View.VISIBLE);
                if (name.equals("女")) {
                    isMan = false;
                    sexWoman.setVisibility(View.VISIBLE);
                    sexMan.setVisibility(View.INVISIBLE);
                } else if (name.equals("男")) {        //性别男   或者默认 未设置  也默认男
                    isMan = true;
                    sexWoman.setVisibility(View.INVISIBLE);
                    sexMan.setVisibility(View.VISIBLE);
                } else {       //当性别传递过来是未设置时
                    isNotSet = true;
                    sexWoman.setVisibility(View.INVISIBLE);
                    sexMan.setVisibility(View.INVISIBLE);
                }
                etNickname.setVisibility(View.GONE);
                etEmail.setVisibility(View.GONE);
                break;
            case "email":     //邮箱
                tvTitle.setText(getResources().getString(R.string.vs_myinfotext_mailbox));
                etEmail.setVisibility(View.VISIBLE);
                etEmail.setText(name);
                etNickname.setVisibility(View.GONE);
                sexLayout.setVisibility(View.GONE);
                break;
        }
    }


    @OnClick({R.id.iv_back, R.id.tv_right, R.id.sex_man_layout, R.id.sex_woman_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right:             //确认
                clickConfirm();
                break;
            case R.id.sex_man_layout:       //性别男
                isMan = true;
                isNotSet = false;   //已经选择设置性别
                sexWoman.setVisibility(View.INVISIBLE);
                sexMan.setVisibility(View.VISIBLE);
                break;
            case R.id.sex_woman_layout:     //性别女
                isMan = false;
                isNotSet = false;   //已经选择设置性别
                sexWoman.setVisibility(View.VISIBLE);
                sexMan.setVisibility(View.INVISIBLE);
                break;
        }
    }

    /**
     * 点击确定
     */
    private void clickConfirm() {
        // 点击保存
        if (!VsNetWorkTools.isNetworkAvailable(mContext)) {
            ToastUtil.showMsg("网络连接失败，请检查网络");
            return;
        }
        switch (flag) {
            case "nickname":        //昵称
                String nick = etNickname.getText().toString();
                if (StringUtils.isEmpty(nick)) {
                    ToastUtil.showMsg("昵称不能为空");
                    return;
                }
                if (name.equals(nick)) {
                    ToastUtil.showMsg("昵称未修改");
                    return;
                }
                changeUserInfo("username", nick);
                break;
            case "email":     //邮箱
                String Email = etEmail.getText().toString();
                if (StringUtils.isEmpty(Email)) {
                    ToastUtil.showMsg("邮箱不能为空");
                    return;
                }
                if (!Email.matches("^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$")) {
                ToastUtil.showMsg("邮箱格式错误");
                return;
            }
            if (name.equals(Email)) {
                ToastUtil.showMsg("邮箱未修改");
                return;
            }
            changeUserInfo("email", Email);
            break;
            case "sex":         //性别
                if (isNotSet) {
                    //未修改性别时，点击确定
                    ToastUtil.showMsg("请选择性别");
                } else {
                    if (isMan) {
                        //男
                        if (name.equals("男")) {
                            ToastUtil.showMsg("请修改性别");
                            return;
                        }
                    } else {
                        //女
                        if (name.equals("女")) {
                            ToastUtil.showMsg("请修改性别");
                            return;
                        }
                    }
                    changeUserInfo("userGender", isMan ? "男" : "女");
                }
                break;
        }
    }

    /**
     * 修改用户信息
     */
    private void changeUserInfo(String key, String value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        map.put("uid", MyApplication.UID);
        map.put("phone",MyApplication.MOBILE);
        map.put("appId",CommonParam.APP_ID);
        mPresenter.netWorkRequestPost(CommonParam.CHANGE_USER_INFO, map);
    }

    @Override
    public void onSuccess(String data) {
        LogUtils.i("fxx", "修改成功=" + data);
        //EventBus 我的页面更新用户数据
        EventBus.getDefault().post("userInfoChangeSuccess");
        ToastUtil.showMsg("修改成功");
        //返回信息给上个页面
        Intent intent = new Intent();
        switch (flag){
            case "nickname":
                intent.putExtra("nickname", etNickname.getText().toString());
                this.setResult(1000, intent);
                break;
            case "email":
                intent.putExtra("email", etEmail.getText().toString());
                this.setResult(5000, intent);
                break;
            case "sex":
                intent.putExtra("sex", isMan ? "男" : "女");
               this.setResult(2000, intent);
                break;
        }
        this.finish();
    }

    @Override
    public void onFailure(Throwable e, int code, String msg, boolean isNetWorkError) {
        LogUtils.e("fxx", "修改失败      code=" + code + "    msg=" + msg + "   isNetWorkError=" + isNetWorkError);
        ToastUtil.showMsg(msg);
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}
