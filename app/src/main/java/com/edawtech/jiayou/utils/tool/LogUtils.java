package com.edawtech.jiayou.utils.tool;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;


import com.edawtech.jiayou.BuildConfig;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * 日志打印工具类
 *
 * @author Aaron
 */
public class LogUtils {

    // 打印日志开关,测试环境开启(true)，正式环境关闭(false)
    private static boolean IS_DEBUG = BuildConfig.DEBUG_TOGGLE;
    // DEBUG_LOG 是否打印系统异常信息
    private static boolean DEBUG_LOG = BuildConfig.DEBUG_TOGGLE;
    // 是否输出日志到手机sd卡上的一个文件,测试环境开启(true)，正式环境关闭(false)
    private static boolean isWriteToFile = BuildConfig.DEBUG_TOGGLE;

    // 输出日志文件变量。
    private static File mLogFile;
    private static String PATH = "";
    private static final String LOG_FILENAME = "LXJ_net_log";
    private static final String LOG_FILEEXT = ".txt";
    private static final long LOGFILE_LIMIT = 1000000L;// 日志文件限制

    public static void openDebutLog(boolean enable) {
        IS_DEBUG = enable;
        DEBUG_LOG = enable;
    }

    /**
     * 打印所有的 System.out.println 信息
     * @param tag
     * @param msg
     */
    public static void p(String tag, String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        // 检查日志文件是否创建。
        checkLog();
        if (IS_DEBUG) {
            System.out.println("Print---" + tag + "：=============>>：" + msg);
        }
        // 将log信息写入文件中
        writeLogFile("Print", tag, msg);
    }

    /**
     * 打印所有的信息
     * @param tag
     * @param msg
     */
    public static void v(String tag, String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        // 检查日志文件是否创建。
        checkLog();
        if (IS_DEBUG) {
            Log.v("Verb---" + tag, "------------->>：" + msg);
        }
        // 将log信息写入文件中
        writeLogFile("Verb", tag, msg);
    }

    /**
     * 打印 info 信息
     * @param tag
     * @param msg
     */
    public static void i(String tag, String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        // 检查日志文件是否创建。
        checkLog();
        if (IS_DEBUG) {
            Log.i("Info---" + tag, "------------->>：" + msg);
        }
        // 将log信息写入文件中
        writeLogFile("Info", tag, msg);
    }

    /**
     * 打印 debug 信息
     * @param tag
     * @param msg
     */
    public static void d(String tag, String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        // 检查日志文件是否创建。
        checkLog();
        if (IS_DEBUG) {
            Log.d("Debug---" + tag, "------------->>：" + msg);
        }
        // 将log信息写入文件中
        writeLogFile("Debug", tag, msg);
    }

    /**
     * 打印警告信息
     * @param tag
     * @param msg
     */
    public static void w(String tag, String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        // 检查日志文件是否创建。
        checkLog();
        if (IS_DEBUG) {
            Log.w("Warn---" + tag, "------------->>：" + msg);
        }
        // 将log信息写入文件中
        writeLogFile("Warn", tag, msg);
    }

    /**
     * 打印错误信息
     * @param tag
     * @param msg
     */
    public static void e(String tag, String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        // 检查日志文件是否创建。
        checkLog();
        if (IS_DEBUG) {
            Log.e("Error---" + tag, "------------->>：" + msg);
        }
        // 将log信息写入文件中
        writeLogFile("Error", tag, msg);
    }

    /**
     * 打印系统异常信息
     * @param e
     */
    public static void exception(Exception e) {
        // 检查日志文件是否创建。
        checkLog();
        if (DEBUG_LOG) {
            e.printStackTrace();
        }
        // 将log信息写入文件中
        writeLogFile("Exception", "Error", e.getMessage());
    }

    // 检查日志文件是否创建。
    private static void checkLog() {
        createLogFile();
    }

    // 创建日志文件。
    private static void createLogFile() {
        if (isWriteToFile) {
            synchronized (LOG_FILENAME) {
                if (mLogFile == null) {
                    try {
                        // SD卡是否存在
                        boolean isSDCardExist = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
                        boolean isRootDirExist = Environment.getExternalStorageDirectory().exists();
                        if (isSDCardExist && isRootDirExist) {
                            // 创建文件。
                            PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
                            mLogFile = new File(PATH + LOG_FILENAME + LOG_FILEEXT);
                            if (!mLogFile.exists()) {
                                mLogFile.createNewFile();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        if (mLogFile.isFile()) {
                            if (mLogFile.length() > LOGFILE_LIMIT) {
                                StringBuffer sb = new StringBuffer(PATH);
                                // sb.append(LOG_FILEPATH);
                                sb.append(LOG_FILENAME);
                                sb.append(new Date().toLocaleString());
                                sb.append(LOG_FILEEXT);
                                mLogFile.renameTo(new File(sb.toString()));
                                sb = null;
                                sb = new StringBuffer(PATH);
                                // sb.append(LOG_FILEPATH);
                                sb.append(LOG_FILENAME);
                                sb.append(LOG_FILEEXT);
                                mLogFile = new File(sb.toString());
                                sb = null;
                                if (!mLogFile.exists()) {
                                    try {
                                        mLogFile.createNewFile();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // 将log信息写入文件中
    private static void writeLogFile(String level, String tag, String msg) {
        try {
            if (isWriteToFile) {
                synchronized (LOG_FILENAME) {
                    if (mLogFile != null) {
                        StringBuffer sb = new StringBuffer();
                        sb.append(new Date().toLocaleString());
                        sb.append(": ");
                        sb.append(level);
                        sb.append(": ");
                        sb.append(tag);
                        sb.append(": ");
                        sb.append(msg);
                        sb.append("\n");
                        RandomAccessFile raf = null;
                        try {
                            raf = new RandomAccessFile(mLogFile, "rw");
                            raf.seek(mLogFile.length());
                            raf.write(sb.toString().getBytes("UTF-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            sb = null;
                            if (raf != null) {
                                raf.close();
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
