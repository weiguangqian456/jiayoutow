package com.edawtech.jiayou.config.home.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.edawtech.jiayou.R;

/**
 * ClassName:      CustomProgressDialog
 * <p>
 * Author:
 * <p>
 * CreateDate:      2020/9/14 14:02
 * <p>
 * Description:     统一封装进度加载框
 */
public class CustomProgressDialog extends ProgressDialog {

    private AnimationDrawable mAnimation;
    private Context mContext;
    private ImageView mImageView;
    private String mLoadingTip;
    private TextView mLoadingTv;
    private int count = 0;
    private String oldLoadingTip;
    private int mResid;
    private int theme;

    public CustomProgressDialog(Context context, String content, int id) {
        super(context);
        this.mContext = context;
        this.mLoadingTip = content;
        this.mResid = id;
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        mImageView.setBackgroundResource(mResid);
        // 通过ImageView对象拿到背景显示的AnimationDrawable
        mAnimation = (AnimationDrawable) mImageView.getBackground();
        // 为了防止在onCreate方法中只显示第一帧的解决方案之一
        mImageView.post(new Runnable() {
            @Override
            public void run() {
                mAnimation.start();
            }
        });
        mLoadingTv.setText(mLoadingTip);
    }

    public void setContent(String str) {
        mLoadingTv.setText(str);
    }

    private void initView() {
        setContentView(R.layout.progress_dialog);
        mLoadingTv = (TextView) findViewById(R.id.loadingTv);
        mImageView = (ImageView) findViewById(R.id.loadingIv);
//        WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        getWindow().setAttributes(params);
    }

	/*@Override
    public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		mAnimation.start();
		super.onWindowFocusChanged(hasFocus);
	}*/

    /**
     * 取消加载动画弹框
     */
    public void setLoadingDialogShow() {
        if (this != null) {
            this.show();
        }
    }


    /**
     * 取消加载动画弹框
     */
    public void setLoadingDialogDismiss() {
        if (this != null && this.isShowing()) {
            this.dismiss();
        }
    }
}