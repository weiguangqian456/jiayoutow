package com.edawtech.jiayou.utils.tool.unit;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.blankj.utilcode.util.StringUtils;
import com.edawtech.jiayou.config.constant.Constant;


import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

/**
 * 像素之间的 转换
 *
 * @author tp
 */
public class DensityUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelOffset(resId);
        }
        return result;
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取屏幕宽度(px)
     */
    public static int getScreenWidth(Context context) {

        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(dm);
        return dm.widthPixels;
        // 方法二：
        // return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度(px)
     */
    public static int getScreenHeigth(Context context) {

        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(dm);
        return dm.heightPixels;
        // 方法二：
        // return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取指定文件大小(单位：字节)
     *
     * @param filePath
     * @return
     * @throws Exception
     */
    public static long getFilePathSize(String filePath) {
        if (filePath != null && !filePath.equals("")) {
            long size = 0;
            // 创建File
            File mFile = new File(filePath);
            try {
                // 取得文件大小
                size = getFileSize(mFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return size;
        } else {
            return 0;
        }
    }

    /**
     * 获取指定文件大小(单位：字节)
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static long getFileSize(File file) throws Exception {
        if (file == null) {
            return 0;
        }
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            if (fis != null) {
                size = fis.available();
            }
        }
        return size;
    }

    /**
     * Object 转 String 再转 double 再转 int ：即 String ---> int
     */
    public static int StringToint(int defaultValue, String str) {
        int str_int = defaultValue;
        try {
            if (!TextUtils.isEmpty(str)) {
                double value = Double.valueOf(str);
                // 进行四舍五入操作：
                str_int = Integer.parseInt(new DecimalFormat("0").format(value));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str_int;
    }

    /**
     * Object 转 String 再转 double ：即 String ---> double  ： 保留两位小数点。
     */
    public static double StringToDouble(double defaultValue, String str) {
        try {
            if (!TextUtils.isEmpty(str)) {
                double value = Double.valueOf(str);
                // 保留两位小数点。
                value = Double.valueOf(Constant.decimalFormat.format(value));
                return value;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    /**
     * double 转 String  ： 保留两位小数点。
     */
    public static String DoubleToString(double value) {
        String strDouble = Constant.decimalFormat.format(value);
        return DoubleToString(strDouble);
    }

    public static String DoubleToString(String strDouble) {
        try {
            if (!StringUtils.isEmpty(strDouble)) {
                double value = Double.valueOf(strDouble);
                strDouble = Constant.decimalFormat.format(value);
                // 小数点后两位判断。
                String[] splitStr = strDouble.split("\\.");
                if (splitStr[1].length() == 1) {
                    strDouble = strDouble  + "0";
                } else if (splitStr[1].length() > 2) {
                    int endIndex = strDouble.length() - splitStr[1].length() + 2;
                    strDouble = strDouble.substring(0, endIndex);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDouble;
    }

//    public static void main(String[] args) {
//        double d = 0.60 + 98.18 + 0.04;
//
//        String dataLen = DoubleToString(d);
//        // String dataLen = DoubleToString(String.valueOf(d));
//
//        System.out.println("dataLen:============" + dataLen);
//    }



}
