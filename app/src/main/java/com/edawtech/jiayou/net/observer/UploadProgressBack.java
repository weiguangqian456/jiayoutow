package com.edawtech.jiayou.net.observer;

import rxhttp.wrapper.entity.Progress;

// 文件上传下载进度回调。
public interface UploadProgressBack {

    /**
     * 数据请求成功
     *
     * @param progress 进度对象
     * @param currentProgress 当前进度 0-100
     * @param currentSize 当前已上传的字节大小
     * @param totalSize 要上传的总字节大小
     */
    void onProgress(Progress progress, int currentProgress, long currentSize, long totalSize);
}
