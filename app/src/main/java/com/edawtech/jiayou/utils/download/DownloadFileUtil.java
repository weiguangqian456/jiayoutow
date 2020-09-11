package com.edawtech.jiayou.utils.download;

import android.content.Context;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.edawtech.jiayou.utils.tool.LogUtils;
import com.edawtech.jiayou.utils.tool.ToastUtil;
import com.liulishuo.okdownload.DownloadListener;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author: LuoYangMian
 * @date: 2019/12/6
 * @describe: 文件下载
 */
public class DownloadFileUtil {

    private String downLoadUrl;
    private Context mContext;

    public DownloadFileUtil(Context context, String downLoadUrl, String folder) {

        this.downLoadUrl = downLoadUrl;
        this.mContext = context;

        downloadFile(downLoadUrl,folder);

    }


    //监听回调
    public interface DownloadFileListener {

        public void completed(String filepath, DownloadTask task);
    }

    private DownloadFileListener mDownloadFileListener;

    public void setOnDownloadFileListener(DownloadFileListener listener) {
        mDownloadFileListener = listener;
    }


    /**
     * @param downurl
     * @param folder  文件夹
     */
    private void downloadFile(String downurl, String folder) {

        //根据下载链接 file/attachment/txt/2019-12-05/cd55e4b573774b78a96fa83b63fa3fc3/1.txt 获取文件名和文件类型
        String downloadurl = downurl;

        String fileName;// 生成的文件名
        String substring = downloadurl.substring(downloadurl.lastIndexOf("/") + 1);//获取链接“/”最后一个字符
        String[] filenamestr = substring.split("\\.");//截取“.”，strs[0]就是文件名 。 strs[1]就是文件拓展名
        if (filenamestr.length >= 2) {

            fileName = filenamestr[filenamestr.length - 2] + "." + filenamestr[1];
        } else {
            ToastUtil.showMsg("未知文件类型");
            return;
        }

        // MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton(); 获取文件类型
        //String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(fileurl));//text/plain
        File file = new File(Environment.getExternalStorageDirectory() + "/Swing" + "/" + folder);//文件保存的路径 这方创建文件路径 app卸载不会销毁这些文件
        //File file = new File(FileUtils.getFilePath(mContext,"Chatfile"));

        DownloadTask task = new DownloadTask.Builder(downloadurl, file)
                .setFilename(fileName)
                // the minimal interval millisecond for callback progress
                .setMinIntervalMillisCallbackProcess(30)
                // do re-download even if the task has already been completed in the past.
                .setPassIfAlreadyCompleted(false)
                .build();

        task.enqueue(new DownloadListener() {
            @Override
            public void taskStart(@NonNull DownloadTask task) {

            }

            @Override
            public void connectTrialStart(@NonNull DownloadTask task, @NonNull Map<String, List<String>> requestHeaderFields) {

            }

            @Override
            public void connectTrialEnd(@NonNull DownloadTask task, int responseCode, @NonNull Map<String, List<String>> responseHeaderFields) {

            }

            @Override
            public void downloadFromBeginning(@NonNull DownloadTask task, @NonNull BreakpointInfo info, @NonNull ResumeFailedCause cause) {

            }

            @Override
            public void downloadFromBreakpoint(@NonNull DownloadTask task, @NonNull BreakpointInfo info) {

            }

            @Override
            public void connectStart(@NonNull DownloadTask task, int blockIndex, @NonNull Map<String, List<String>> requestHeaderFields) {

            }

            @Override
            public void connectEnd(@NonNull DownloadTask task, int blockIndex, int responseCode, @NonNull Map<String, List<String>> responseHeaderFields) {

            }

            @Override
            public void fetchStart(@NonNull DownloadTask task, int blockIndex, long contentLength) {

            }

            @Override
            public void fetchProgress(@NonNull DownloadTask task, int blockIndex, long increaseBytes) {

            }

            @Override
            public void fetchEnd(@NonNull DownloadTask task, int blockIndex, long contentLength) {

            }

            @Override
            public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause) {
                if (cause == EndCause.COMPLETED) {
                    LogUtils.e("task", task.getFile().getAbsolutePath());//下载后的本地文件路径
                    mDownloadFileListener.completed(task.getFile().getAbsolutePath(), task);//
                }
            }
        });
    }

}
