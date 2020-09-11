package com.edawtech.jiayou.utils.download;

import android.os.Handler;

import com.blankj.utilcode.util.PathUtils;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener1;
import com.liulishuo.okdownload.core.listener.assist.Listener1Assist;

/**
 * @Name：MeiLan
 * @Description：描述信息
 * @Author：Administrator
 * @Date：2019/6/27 10:46
 * 修改人：Administrator
 * 修改时间：2019/6/27 10:46
 * 修改备注：
 *
 * 文件下载。
 */
public class DownloadUtil {

    private DownloadTask task;
    private DownloadListener1 downloadListener;
    private MyDownloadListener myListener;

    public DownloadUtil(String url, String filename) {
        // 初始化下载
        task = new DownloadTask.Builder(url, PathUtils.getExternalAppDownloadPath(), filename)
                .setMinIntervalMillisCallbackProcess(500)// 下载进度回调的间隔时间（毫秒）
                .setPassIfAlreadyCompleted(false)// 任务过去已完成是否要重新下载
                //.setWifiRequired(true)// 是否只能在WiFi环境下下载
                .build();

        // 下载监听。
        downloadListener = new DownloadListener1() {
            @Override
            public void taskStart(DownloadTask task, Listener1Assist.Listener1Model model) {
                // 准备下载
                if (myListener != null) {
                    myListener.onStartUpDate(task);
                }
                if (task.getTag() == null) {
                    task.setTag(0);
                }
            }

            @Override
            public void retry(DownloadTask task, ResumeFailedCause cause) {

            }

            @Override
            public void connected(DownloadTask task, int blockCount, long currentOffset, long totalLength) {
                task.setTag(0);
            }

            @Override
            public void progress(DownloadTask task, long currentOffset, long totalLength) {
                // 正在下载
                if (myListener != null) {
                    myListener.onUpDateProgress(currentOffset, totalLength);
                }
                // int progress = (int) (((double) currentOffset) / totalLength * 100);
            }

            @Override
            public void taskEnd(final DownloadTask task, EndCause cause, Exception realCause,
                                Listener1Assist.Listener1Model model) {
                if (cause == EndCause.COMPLETED) {// 下载完成
                    if (myListener != null) {
                        myListener.onUpDateSucceed(task);
                    }
                    task.removeTag();
                    // 安装
                    // AppUtils.installApp(task.getFile());
                } else if (cause == EndCause.ERROR) {// 下载错误进行重试
                    if ((int) task.getTag() == 5) {// 重试超过5次 下载失败
                        // 下载失败
                        if (myListener != null) {
                            myListener.onUpDateFailure(task);
                        }
                        task.removeTag();
                    } else {// 等待3秒后再次尝试下载
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                task.setTag((int) task.getTag() + 1);
                                //Log.d(TAG, "run: 重试"+task.getTag());
                                task.enqueue(downloadListener);
                            }
                        }, 3000);
                    }
                } else {
                    // 不为任务繁忙(任务正在执行)的其他情况都为下载失败
                    if (cause != EndCause.SAME_TASK_BUSY) {
                        // 下载失败
                        if (myListener != null) {
                            myListener.onUpDateFailure(task);
                        }
                        task.removeTag();
                    }
                }
            }
        };
    }

    // 开始下载。
    public void startDownload() {
        // 异步执行任务
        task.enqueue(downloadListener);
        // 同步执行任务
        // task.execute(listener);
    }

    // 设置外部回调。
    public void setMyDownloadListener(MyDownloadListener myListener) {
        this.myListener = myListener;
    }

    public interface MyDownloadListener {
        void onStartUpDate(DownloadTask task);

        void onUpDateProgress(long currentOffset, long totalLength);

        void onUpDateFailure(DownloadTask task);

        void onUpDateSucceed(DownloadTask task);
    }

}
