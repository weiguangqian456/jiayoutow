package com.edawtech.jiayou.utils.download;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.PathUtils;
import com.edawtech.jiayou.R;

import com.edawtech.jiayou.config.base.MyApplication;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener1;
import com.liulishuo.okdownload.core.listener.assist.Listener1Assist;

// 下载和安装APP
public class UpDateUtil {

    //private static final String TAG = "UpDateUtil";
    private static final String UPDATE_CHANNEL = "UpaDate";
    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;
    private DownloadTask task;
    private DownloadListener1 downloadListener1;
    private UpDateListener upDateListener;

    public UpDateUtil(final Context context, String url) {

        //创建通知渠道
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(UPDATE_CHANNEL,
                    context.getResources().getString(R.string.AppUpdates), NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(context.getResources().getString(R.string.ApplyNewDownloadNotification));
            channel.enableLights(false);
            channel.enableVibration(false);
            channel.setSound(null, null);
            notificationManager.createNotificationChannel(channel);
        }
        //初始化通知
        builder = new NotificationCompat.Builder(context, UPDATE_CHANNEL);

        //初始化下载
        task = new DownloadTask.Builder(url, PathUtils.getExternalAppDownloadPath(), "KeBaoBao_NewApp.apk")
                .setMinIntervalMillisCallbackProcess(500)//下载进度回调的间隔时间（毫秒）
                .setPassIfAlreadyCompleted(false)//任务过去已完成是否要重新下载
                //.setWifiRequired(true)//是否只能在WiFi环境下下载
                .build();
        downloadListener1 = new DownloadListener1() {
            @Override
            public void taskStart(DownloadTask task, Listener1Assist.Listener1Model model) {
                //Log.d(TAG, "taskStart: ");
                if (upDateListener != null) {
                    upDateListener.onStartUpDate();
                }
                if (task.getTag() == null) {
                    builder.setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(context.getResources().getString(R.string.DownloadTheInstallationPackage))
                            .setContentText(context.getResources().getString(R.string.ReadyDownload));
                    notificationManager.notify(1, builder.build());
                    task.setTag(0);
                }
            }

            @Override
            public void retry(DownloadTask task, ResumeFailedCause cause) {
                //Log.d(TAG, "retry: ");
            }

            @Override
            public void connected(DownloadTask task, int blockCount, long currentOffset, long totalLength) {
                //Log.d(TAG, "connected: ");
                task.setTag(0);
            }

            @Override
            public void progress(DownloadTask task, long currentOffset, long totalLength) {
                //Log.d(TAG, "progress: ");
                if (upDateListener != null) {
                    upDateListener.onUpDateProgress(currentOffset, totalLength);
                }
                int progress = (int) (((double) currentOffset) / totalLength * 100);
                builder.setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(context.getResources().getString(R.string.DownloadingInstallationPackage))
                        .setContentText(progress + "%")
                        //.setOngoing(true)
                        .setProgress(100, progress, false);
                notificationManager.notify(1, builder.build());
            }

            @Override
            public void taskEnd(final DownloadTask task, EndCause cause, Exception realCause,
                                Listener1Assist.Listener1Model model) {

                if (cause == EndCause.COMPLETED) {//下载完成
                    if (upDateListener != null) {
                        upDateListener.onUpDateSucceed(task);
                    }
                    task.removeTag();
                    notificationManager.cancel(1);
                    // 静默安装判断
                    if (ActivityCompat.checkSelfPermission(MyApplication.getContext(),
                            Manifest.permission.INSTALL_PACKAGES) == PackageManager.PERMISSION_GRANTED) {
                        // 有权限
                        if (!AppUtils.installAppSilent(task.getFile())) {//悄无声息地安装应用程序。
                            AppUtils.installApp(task.getFile());//安装
                        }
                    } else {
                        // 无权限
                        AppUtils.installApp(task.getFile());//安装
                    }
                } else if (cause == EndCause.ERROR) {//下载错误进行重试
                    if ((int) task.getTag() == 5) {//重试超过5次 下载失败
                        if (upDateListener != null) {
                            upDateListener.onUpDateFailure();
                        }
                        task.removeTag();
                        builder.setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle(context.getResources().getString(R.string.DownloadTheInstallationPackage))
                                .setContentText(context.getResources().getString(R.string.DownloadFailed))
                                .setProgress(0, 0, false);
                        notificationManager.notify(1, builder.build());
                    } else {//等待3秒后再次尝试下载
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                task.setTag((int) task.getTag() + 1);
                                //Log.d(TAG, "run: 重试"+task.getTag());
                                task.enqueue(downloadListener1);
                            }
                        }, 3000);
                    }
                } else {
                    if (cause != EndCause.SAME_TASK_BUSY) {//不为任务繁忙(任务正在执行)的其他情况都为下载失败
                        if (upDateListener != null) {
                            upDateListener.onUpDateFailure();
                        }
                        task.removeTag();
                        builder.setSmallIcon(R.drawable.ic_launcher_background)
                                .setContentTitle(context.getResources().getString(R.string.DownloadTheInstallationPackage))
                                .setContentText(context.getResources().getString(R.string.DownloadFailed))
                                .setProgress(0, 0, false);
                        notificationManager.notify(1, builder.build());
                    }
                }
            }
        };
    }

    public void startUpDate() {
        // 异步执行任务
        task.enqueue(downloadListener1);
        // 同步执行任务
        // task.execute(listener);
    }

    public void setUpDateListener(UpDateListener upDateListener) {
        this.upDateListener = upDateListener;
    }

    public interface UpDateListener {
        void onStartUpDate();

        void onUpDateProgress(long currentOffset, long totalLength);

        void onUpDateFailure();

        void onUpDateSucceed(DownloadTask task);
    }
}
