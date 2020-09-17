package com.zbar.lib;

/**
 * ClassName:      ZbarManager
 * <p>
 * Author:
 * <p>
 * CreateDate:      2020/9/12 18:39
 * <p>
 * Description:     zbar调用类
 */
public class ZbarManager {


    static {
        System.loadLibrary("zbar");
    }

    public native String decode(byte[] data, int width, int height, boolean isCrop, int x, int y, int cwidth, int cheight);
}
