package com.edawtech.jiayou.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edawtech.jiayou.R;


/**
 * 自定义对话框无标题
 */
public class CustomDialogNoTitle extends Dialog {

    public CustomDialogNoTitle(Context context) {
        super(context);
    }

    public CustomDialogNoTitle(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;
        private boolean mCancelable = true;
        private Dialog dialog_V;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param //title
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.mCancelable = cancelable;
            return this;
        }

        public CustomDialogNoTitle create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final CustomDialogNoTitle dialog = new CustomDialogNoTitle(context, R.style.Dialog);
            dialog.setCancelable(mCancelable);// 设置是否可撤销
            this.dialog_V = dialog;

            View layout = inflater.inflate(R.layout.dialog_notitle_normal_layout, null);
            TextView title_text = (TextView) layout.findViewById(R.id.title);
            TextView message_text = (TextView) layout.findViewById(R.id.message);
            Button positiveButton = (Button) layout.findViewById(R.id.positiveButton);
            Button negativeButton = (Button) layout.findViewById(R.id.negativeButton);

            // 初始化背景颜色
            //			ServiceDialog.setSDCardBitmap(negativeButton, "gg_an_1.png", context);
            //			ServiceDialog.setSDCardBitmap(positiveButton, "gg_an_2.png", context);

            dialog.addContentView(layout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            // set the confirm button
            if (positiveButtonText != null) {
                positiveButton.setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    positiveButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                        }
                    });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                positiveButton.setVisibility(View.GONE);
            }
            // set the cancel button
            if (negativeButtonText != null) {
                negativeButton.setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    negativeButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                        }
                    });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                negativeButton.setVisibility(View.GONE);
            }

            // set the content title
            if (title != null && !title.equals("")) {
                title_text.setVisibility(View.VISIBLE);
                title_text.setText(title);
                message_text.setTextColor(0xff666666);
                message_text.setTextSize(14);
                message_text.setGravity(Gravity.CENTER_VERTICAL);
            } else {
                title_text.setVisibility(View.GONE);
                message_text.setTextColor(0xff333333);
                message_text.setTextSize(16);
                message_text.setGravity(Gravity.CENTER_HORIZONTAL);
            }

            // set the content message
            if (message != null && !message.equals("")) {
                message_text.setVisibility(View.VISIBLE);
                message_text.setText(message);
            } else if (contentView != null) {
                // if no message set
                // add the contentView to the dialog body
                ((LinearLayout) layout.findViewById(R.id.content)).removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content)).addView(
                        contentView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            } else {
                message_text.setVisibility(View.GONE);
            }
            dialog.setContentView(layout);
            return dialog;
        }

        public Builder setDismiss() {
            if (dialog_V != null) {
                dialog_V.dismiss();
            }
            return this;
        }
    }
}
