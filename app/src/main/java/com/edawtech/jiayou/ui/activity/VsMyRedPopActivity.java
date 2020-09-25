package com.edawtech.jiayou.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.edawtech.jiayou.R;
import com.edawtech.jiayou.config.base.MyApplication;
import com.edawtech.jiayou.utils.CommonParam;
import com.edawtech.jiayou.utils.StringUtils;
import com.edawtech.jiayou.utils.tool.LogUtils;
import com.edawtech.jiayou.utils.tool.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 提现
 */
public class VsMyRedPopActivity extends Activity {

    @BindView(R.id.weixin)
    TextView weixin;
    @BindView(R.id.money_get_edit)
    EditText moneyGetEdit;
    //
    private String money;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.my_red_popwindow);
        ButterKnife.bind(this);
        money = getIntent().getStringExtra("money");
    }

    @OnClick({R.id.money_img, R.id.money_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.money_img:
                finish();
                break;
            case R.id.money_btn:    //提现
                String withdrawMoney = moneyGetEdit.getText().toString();
                withdraw(withdrawMoney);
                break;
        }
    }

    /**
     * 提现
     */
    private void withdraw(String count) {
        if (StringUtils.isEmpty(count)) {
            ToastUtil.showMsg("提现金额不能为空");
            return;
        }
        double withDrawCount = Double.parseDouble(count);
        if (withDrawCount < 1) {
            ToastUtil.showMsg("提现金额不能小于1");
            return;
        }
        if (withDrawCount > Double.parseDouble(money)) {
            ToastUtil.showMsg("金额错误");
            return;
        }
        OkGo.<String>post(CommonParam.WITHDRAW_URL)
                .params("uid", MyApplication.UID)
                .params("amount", count)
                .params("appId", CommonParam.APP_ID)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        try {
                            JSONObject json = new JSONObject(body);
                            int code = json.getInt("code");
                            String msg = json.getString("msg");
                            if (code == 0){
                                //提现成功，通知其他页面刷新金额
                                EventBus.getDefault().post("withdrawSuccess");
                            }
                            ToastUtil.showMsg(msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        LogUtils.e("fxx", "提现错误=" + response.message());
                        ToastUtil.showMsg("网络加载错误！");
                    }
                });
    }
}