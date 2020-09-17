package com.edawtech.jiayou.config.home.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.edawtech.jiayou.R;

import butterknife.ButterKnife;

/**
 * @author : hc
 * @date : 2019/3/7.
 * @description:
 */

public abstract class BaseDefaultDialog extends DialogFragment {

    protected Context mContext;

    protected abstract int   getGravity ();
    protected abstract int   getLayoutId();
    protected abstract void  initView   ();

    protected Boolean isLoadAnimation(){
        return true;
    }

    protected int getDAnimation(){
        return R.style.DialogAnimation;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        View inflate = inflater.inflate(getLayoutId(), container);
        this.mContext = getActivity();
        ButterKnife.bind(this,inflate);
        initView();
        return inflate;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if(window == null){return;}
        //背景
        window.setBackgroundDrawableResource(android.R.color.transparent);
        //设置在底部 以及 填充全部宽度
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = getGravity();
        params.width = getResources().getDisplayMetrics().widthPixels;
        if(isLoadAnimation()){
            params.windowAnimations = getDAnimation();
        }
        window.setAttributes(params);
    }
}
