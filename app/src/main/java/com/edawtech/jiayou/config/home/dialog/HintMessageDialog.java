package com.edawtech.jiayou.config.home.dialog;

import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.edawtech.jiayou.R;

import butterknife.BindView;

/**
 * ClassName:      HintMessageDialog
 * <p>
 * Author:
 * <p>
 * CreateDate:      2020/9/15 13:45
 * <p>
 * Description:     提示弹框
 */
public class HintMessageDialog extends  BaseDefaultDialog {

    @BindView(R.id.tv_massage)
    TextView tv_massage;
    @BindView(R.id.tv_confirm)
    TextView tv_confirm;
    @BindView(R.id.fl_close)
    FrameLayout fl_close;

    private boolean isShowCancel = true;
    private String mMessage = "提示";
    private String mConfirmText;
    private OnDialogClickListener mConfirmlistener;

    public static HintMessageDialog getInstance(){
        return new HintMessageDialog();
    }

    @Override
    protected int getGravity() {
        return Gravity.CENTER;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_hint;
    }

    @Override
    protected void initView() {
        tv_massage.setText(mMessage);
        tv_confirm.setText(mConfirmText);
        fl_close.setVisibility(isShowCancel ? View.VISIBLE : View.GONE);
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mConfirmlistener != null){
                    mConfirmlistener.onDialogClick(HintMessageDialog.this);
                }else{
                    dismiss();
                }
            }
        });
        fl_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public interface OnDialogClickListener{
        void onDialogClick(HintMessageDialog dialog);
    }

    @Override
    protected Boolean isLoadAnimation() {
        return false;
    }

    public HintMessageDialog setMessage(String text){
        mMessage = text;
        return this;
    }
}
