package com.edawtech.jiayou.ui.activity;


import android.os.Bundle;

import com.edawtech.jiayou.R;
import com.edawtech.jiayou.config.base.VsBaseActivity;
import com.edawtech.jiayou.utils.FitStateUtils;

/**
 *  绑定微信号步骤
 */
public class VsredinfoActivity extends VsBaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vsredinfo);
        FitStateUtils.setImmersionStateMode(this,R.color.public_color_EC6941);
        initTitleNavBar();
        showLeftNavaBtn(R.drawable.icon_back);
        mTitleTextView.setText("绑定微信步骤");


//        VsApplication.getInstance().addActivity(this);// 保存所有添加的activity。倒是后退出的时候全部关闭
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }
}
