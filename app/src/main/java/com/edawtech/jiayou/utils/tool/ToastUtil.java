package com.edawtech.jiayou.utils.tool;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Looper;
import android.widget.Toast;

import com.edawtech.jiayou.config.base.BaseMvpActivity;
import com.edawtech.jiayou.config.base.MyApplication;
import com.edawtech.jiayou.ui.dialog.MessageDialog;


/**
 * Created by on 2017/11/20
 * Email:
 */
public class ToastUtil {

    public static Toast mToast;

    @SuppressLint("ShowToast")
    public static void showMsg(String text) {
        try {
            //            if (mToast != null) {
            //                mToast.setText(text);
            //            } else {
            //                mToast = Toast.makeText(MyApplication.getInstance(), text, Toast.LENGTH_SHORT);
            //            }
            //            mToast.show();

            Toast.makeText(MyApplication.getInstance(), text, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            // 解决在子线程中调用Toast的异常情况处理
            Looper.prepare();
            Toast.makeText(MyApplication.getInstance(), text, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }

    public static ToastUtil getInstanse() {
        return InnerUtil.INSTANCE;
    }
    private static class InnerUtil {
        public static final ToastUtil INSTANCE = new ToastUtil();
    }

    public void showUpdateDialog(BaseMvpActivity activity, String toastStr) {
        new MessageDialog.Builder(activity)
                .setMessage(toastStr)
                .setConfirm("确定")
                .setCancel("")
                .setCancel(null) // 设置 null 表示不显示取消按钮
                .setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                .setListener(new MessageDialog.OnListener() {
                    @Override
                    public void onConfirm(Dialog dialog) {
                        activity.finish();
                    }
                    @Override
                    public void onCancel(Dialog dialog) {
                    }
                })
                .setCancelable(false)
                .show();
    }
}
