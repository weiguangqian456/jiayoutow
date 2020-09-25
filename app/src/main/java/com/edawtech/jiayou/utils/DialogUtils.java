package com.edawtech.jiayou.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;

import com.edawtech.jiayou.R;
import com.edawtech.jiayou.ui.dialog.CustomDialog;

/**
 * ClassName:      DialogUtils
 * <p>
 * Author:
 * <p>
 * CreateDate:      2020/9/18 15:34
 * <p>
 * Description:     弹框复用
 */
public class DialogUtils {

    /**
     * 显示选择提示框
     *
     * @param titleResId
     * @param messageId
     * @param
     * @param okBtmListener
     * @param cancelBtnListener
     */
    public static void showYesNoDialog(Context mContext, int titleResId, String messageId, DialogInterface.OnClickListener okBtmListener, DialogInterface.OnClickListener cancelBtnListener) {
        CustomDialog.Builder builder = new CustomDialog.Builder(mContext);
        builder.setTitle(titleResId);
        builder.setMessage(messageId);
        builder.setPositiveButton(mContext.getResources().getString(R.string.ok), okBtmListener);
        builder.setNegativeButton(mContext.getResources().getString(R.string.cancel), cancelBtnListener);
        CustomDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * 显示选择提示框
     *
     * @param titleResId
     * @param messageId
     * @param
     * @param okBtmListener
     * @param cancelBtnListener
     */
    public static void showYesNoDialog(Context mContext,int titleResId, int messageId, DialogInterface.OnClickListener okBtmListener, DialogInterface.OnClickListener cancelBtnListener) {
        CustomDialog.Builder builder = new CustomDialog.Builder(mContext);
        builder.setTitle(titleResId);
        builder.setMessage(messageId);
        builder.setPositiveButton(mContext.getResources().getString(R.string.ok), okBtmListener);
        builder.setNegativeButton(mContext.getResources().getString(R.string.cancel), cancelBtnListener);
        CustomDialog dialog = builder.create();
        dialog.show();
    }

    public static void showYesNoDialog(Context mContext,String title, int messageId, DialogInterface.OnClickListener okBtmListener, DialogInterface.OnClickListener cancelBtnListener) {
        CustomDialog.Builder builder = new CustomDialog.Builder(mContext);
        builder.setTitle(title);
        builder.setMessage(messageId);
        builder.setPositiveButton(mContext.getResources().getString(R.string.ok), okBtmListener);
        builder.setNegativeButton(mContext.getResources().getString(R.string.cancel), cancelBtnListener);
        CustomDialog dialog = builder.create();
        dialog.show();
    }

    public static void showYesNoDialog(Context mContext,String title, String message, DialogInterface.OnClickListener okBtmListener, DialogInterface.OnClickListener cancelBtnListener,
                                   DialogInterface.OnCancelListener cancelListener) {
        CustomDialog.Builder builder = new CustomDialog.Builder(mContext);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(mContext.getResources().getString(R.string.ok), okBtmListener);
        builder.setNegativeButton(mContext.getResources().getString(R.string.cancel), cancelBtnListener);
        builder.setOnCancelListener(cancelListener);
        CustomDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * 显示选择提示
     *
     * @param title
     * @param message
     * @param PositiveButton
     * @param NegativeButton
     * @param okBtmListener
     * @param cancelBtnListener
     * @param cancelListener
     */
    public static void showYesNoDialog(Context mContext,String title, String message, String PositiveButton, String NegativeButton, DialogInterface.OnClickListener okBtmListener, DialogInterface
            .OnClickListener cancelBtnListener, DialogInterface.OnCancelListener cancelListener) {
        CustomDialog.Builder builder = new CustomDialog.Builder(mContext);
        builder.setTitle(title);
        builder.setMessage(Html.fromHtml(message) + "");
        builder.setPositiveButton(PositiveButton, okBtmListener);
        builder.setNegativeButton(NegativeButton, cancelBtnListener);
        builder.setOnCancelListener(cancelListener);
        CustomDialog dialog = builder.create();
        dialog.show();
    }
}
