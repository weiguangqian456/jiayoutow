package com.edawtech.jiayou.utils.process;

import android.net.Uri;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

/**
 * Description:
 * Copyright  : Copyright (c) 2015
 * Author     : hzc
 * Date       : 2019/5/30 下午12:03
 */
public class WyCamePhoto {

    public static String cameraCachePath;    // 拍照源文件路径

    // 点击拍照
    public static void camera(AppCompatActivity activity) {
        // FileProvider
        Uri outputUri;
        File file = CachePathUtils.getCameraCacheFile();
        ;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            outputUri = UriParseUtils.getCameraOutPutUri(activity, file);
        } else {
            outputUri = Uri.fromFile(file);
        }
        cameraCachePath = file.getAbsolutePath();
        // 启动拍照
        CommonUtils.hasCamera(activity, CommonUtils.getCameraIntent(outputUri), Constants.CAMERA_CODE);
    }

    // 点击相册
    public static void album(AppCompatActivity activity) {
        CommonUtils.openAlbum(activity, Constants.ALBUM_CODE);
    }
}
