package com.edawtech.jiayou.utils.process;

import java.util.ArrayList;

/**
 * 图片集合的压缩返回监听
 */
public interface CompressImage {

    // 开始压缩
    void compress();

    // 图片集合的压缩结果返回
    interface CompressListener {

        // 成功
        void onCompressSuccess(ArrayList<PhotoWy> images);

        // 失败
        void onCompressFailed(ArrayList<PhotoWy> images, String error);
    }
}
