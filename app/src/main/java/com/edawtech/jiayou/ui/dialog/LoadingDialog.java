package com.edawtech.jiayou.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.edawtech.jiayou.R;


// 网络加载通用LOADING.
public class LoadingDialog extends Dialog {

    public LoadingDialog(@NonNull Context context) {
        // super(context, R.style.MyDialog);
        super(context, R.style.CustomProgressDialog);
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected LoadingDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
    }

    @Override
    public void show() {
        super.show();
        // 初始化UI。
        initView();
        // 关闭监听。
        setOnDismissListener(dialog -> {
        });
    }

    // 初始化UI。
    private void initView() {
        TextView loadingCont = findViewById(R.id.loadingCont);
        if (!TextUtils.isEmpty(content)) {
            loadingCont.setText(content);
            loadingCont.setVisibility(View.VISIBLE);
        } else {
            loadingCont.setText("");
            loadingCont.setVisibility(View.GONE);
        }
    }

    private String content;

    public void setContent(String content) {
        this.content = content;
    }

}
