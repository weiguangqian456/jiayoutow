package com.edawtech.jiayou.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * ClassName:      AutoCodeUtil
 * <p>
 * Author:
 * <p>
 * CreateDate:      2020/9/17 10:58
 * <p>
 * Description:     验证码倒计时工具
 */
public class AutoCodeUtil {

    private static final int count = 60000;  //总时间
    private static final int time = 1000;   //间隔时间
    private static CountDownTimer timer;


    //开始倒计时
    public static void start(final TextView tv) {
        timer = new CountDownTimer(count, time) {
            @Override
            public void onTick(long millisUntilFinished) {
                String text = (millisUntilFinished / time) + "s";
                tv.setEnabled(false);
                tv.setText("重新发送 (" + text + ")");
                tv.setTextColor(Color.parseColor("#969696"));
                tv.setBackgroundColor(Color.parseColor("#DCDCDC"));
            }

            @Override
            public void onFinish() {
                tv.setEnabled(true);
                tv.setText("重新获取");
                tv.setTextColor(Color.parseColor("#ffffff"));
                tv.setBackgroundColor(Color.parseColor("#2F95F8"));
            }
        };
        timer.start();
    }


    //开始倒计时
    public static void open(final TextView tv, final int tickColor, final int finishColor) {
        timer = new CountDownTimer(count, time) {
            @Override
            public void onTick(long millisUntilFinished) {
                String text = (millisUntilFinished / time) + "s";
                tv.setEnabled(false);
                tv.setText("重新发送 (" + text + ")");
                tv.setTextColor(tickColor);
                tv.setBackgroundColor(Color.parseColor("#DCDCDC"));
            }

            @Override
            public void onFinish() {
                tv.setEnabled(true);
                tv.setText("重新获取");
                tv.setTextColor(finishColor);
                tv.setBackgroundColor(Color.parseColor("#2F95F8"));
            }
        };
        timer.start();
    }

    //开始倒计时
    public static void start(final TextView tv, final int tickColor, final int finishColor) {
        timer = new CountDownTimer(count, time) {
            @Override
            public void onTick(long millisUntilFinished) {
                String text = (millisUntilFinished / time) + "s";
                tv.setEnabled(false);
                tv.setText("重新发送 (" + text + ")");
                tv.setTextColor(tickColor);
                tv.setBackgroundColor(Color.parseColor("#DCDCDC"));
            }

            @Override
            public void onFinish() {
                tv.setEnabled(true);
                tv.setText("点击获取");
                tv.setTextColor(finishColor);
                tv.setBackgroundColor(Color.parseColor("#2F95F8"));
            }
        };
        timer.start();
    }

    //关闭
    public static void cancel() {
        if (timer != null) {
            timer.cancel();
        }
    }
}
