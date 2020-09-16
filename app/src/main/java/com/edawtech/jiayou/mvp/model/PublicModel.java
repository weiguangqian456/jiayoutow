package com.edawtech.jiayou.mvp.model;


import com.edawtech.jiayou.config.constant.Constant;
import com.edawtech.jiayou.net.observer.UploadProgressBack;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import rxhttp.wrapper.annotation.Converter;
import rxhttp.wrapper.callback.IConverter;
import rxhttp.wrapper.converter.FastJsonConverter;
import rxhttp.wrapper.converter.XmlConverter;
import rxhttp.wrapper.cookie.CookieStore;
import rxhttp.wrapper.param.Method;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.ssl.SSLSocketFactoryImpl;
import rxhttp.wrapper.ssl.X509TrustManagerImpl;
/**
 * @author azheng
 * @date 2018/6/4.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：
 */
public class PublicModel {

    //        RxHttp.get(String)              //get请求    参数拼接在url后面
    //        RxHttp.head(String)             //head请求   参数拼接在url后面
    //        RxHttp.postForm(String)         //post请求   参数以{application/x-www-form-urlencoded}形式提交
    //        RxHttp.postJson(String)         //post请求   参数以{application/json; charset=utf-8}形式提交，发送Json对象
    //        RxHttp.postJsonArray(String)    //post请求   参数以{application/json; charset=utf-8}形式提交，发送Json数组
    //        RxHttp.putForm(String)          //put请求    参数以{application/x-www-form-urlencoded}形式提交
        //        RxHttp.putJson(String)          //put请求    参数以{application/json; charset=utf-8}形式提交，发送Json对象
    //        RxHttp.putJsonArray(String)     //put请求    参数以{application/json; charset=utf-8}形式提交，发送Json数组
    //        RxHttp.patchForm(String)        //patch请求  参数以{application/x-www-form-urlencoded}形式提交
    //        RxHttp.patchJson(String)        //patch请求  参数以{application/json; charset=utf-8}形式提交，发送Json对象
    //        RxHttp.patchJsonArray(String)   //patch请求  参数以{application/json; charset=utf-8}形式提交，发送Json数组
    //        RxHttp.deleteForm(String)       //delete请求 参数以{application/x-www-form-urlencoded}形式提交
    //        RxHttp.deleteJson(String)       //delete请求 参数以{application/json; charset=utf-8}形式提交，发送Json对象
    //        RxHttp.deleteJsonArray(String)  //delete请求 参数以{application/json; charset=utf-8}形式提交，发送Json数组

    /**
     * get 无参数请求
     *
     * @param url 请求接口连接，可以不带域名（已经初始化默认域名），可以带域名的全连接（相当于手动输入域名）。
     *            RxHttp共有3种指定域名的方式，按优先级排名分别是：手动输入域名 > 指定非默认域名 > 使用默认域名。
     * @param isAddHeaders 是否添加请求头。
     * @return
     */
    public Observable<String> get(String url, boolean isAddHeaders) {
        return RxHttp.get(url).setAssemblyEnabled(isAddHeaders)//设置是否添加公共参数/头部，默认为true
                .asString();//返回字符串数据
    }

    /**
     * get key-value 请求
     */
    public Observable<String> get(String url, boolean isAddHeaders, Map<String, Object> params) {
        return RxHttp.get(url).addAll(params)
                .setAssemblyEnabled(isAddHeaders)//设置是否添加公共参数/头部，默认为true
                .asString();//返回字符串数据
    }

    /**
     * post key-value 请求
     */
    public Observable<String> post(String url, String contentType, boolean isAddHeaders, Map<String, Object> params) {
        if (contentType.equals(Constant.contentType_form)) {
            return RxHttp.postForm(url).addAll(params)
                    .setAssemblyEnabled(isAddHeaders)//设置是否添加公共参数/头部，默认为true
                    .asString();//返回字符串数据
        }
        return RxHttp.postJson(url).addAll(params)
                .setAssemblyEnabled(isAddHeaders)//设置是否添加公共参数/头部，默认为true
                .asString();//返回字符串数据
    }

    /**
     * post json请求
     */
    public Observable<String> post(String url, boolean isAddHeaders, String json) {
        return RxHttp.postJson(url).addAll(json)
                .setAssemblyEnabled(isAddHeaders)//设置是否添加公共参数/头部，默认为true
                .asString();//返回字符串数据
    }

    /**
     * post jsonArray请求
     */
    public Observable<String> postArray(String url, boolean isAddHeaders, String json) {
        return RxHttp.postJsonArray(url).addAll(json)
                .setAssemblyEnabled(isAddHeaders)//设置是否添加公共参数/头部，默认为true
                .asString();//返回字符串数据
    }

    /**
     * postForm 无参数请求：文件上传，带进度上传
     */
    public Observable<String> postForm(String url, boolean isAddHeaders, String key, File file, UploadProgressBack progressBack) {
        // 发送Form表单形式的Post请求
        return RxHttp.postForm(url).setMultiForm()// 设置提交方式为{multipart/form-data}
                //.setEncoded("Content-Type", "multipart/form-data")
                .setEncoded("content-type", "multipart/form-data")
                .addFile(key, file).setAssemblyEnabled(isAddHeaders)//设置是否添加公共参数/头部，默认为true
                .asUpload(progress -> {
                    //上传进度回调,0-100，仅在进度有更新时才会回调,最多回调101次，最后一次回调Http执行结果
                    int currentProgress = progress.getProgress(); //当前进度 0-100
                    long currentSize = progress.getCurrentSize(); //当前已上传的字节大小
                    long totalSize = progress.getTotalSize();     //要上传的总字节大小
                    if (progressBack != null) {
                        progressBack.onProgress(progress, currentProgress, currentSize, totalSize);
                    }
                }, AndroidSchedulers.mainThread());//指定主线程回调
    }

    /**
     * postForm 无参数请求：多文件上传，带进度上传
     */
    public Observable<String> postForm(String url, boolean isAddHeaders, String key, List<File> fileList, UploadProgressBack progressBack) {
        // 发送Form表单形式的Post请求
        return RxHttp.postForm(url).setMultiForm()// 设置提交方式为{multipart/form-data}
                //.setEncoded("Content-Type", "multipart/form-data")
                .setEncoded("content-type", "multipart/form-data")
                .addFile(key, fileList).setAssemblyEnabled(isAddHeaders)//设置是否添加公共参数/头部，默认为true
                .asUpload(progress -> {
                    //上传进度回调,0-100，仅在进度有更新时才会回调,最多回调101次，最后一次回调Http执行结果
                    int currentProgress = progress.getProgress(); //当前进度 0-100
                    long currentSize = progress.getCurrentSize(); //当前已上传的字节大小
                    long totalSize = progress.getTotalSize();     //要上传的总字节大小
                    if (progressBack != null) {
                        progressBack.onProgress(progress, currentProgress, currentSize, totalSize);
                    }
                }, AndroidSchedulers.mainThread());//指定主线程回调
    }

    /**
     * postForm key-value请求：文件上传，带进度上传
     */
    public Observable<String> postForm(String url, boolean isAddHeaders, Map<String, Object> params,
                                       String key, File file, UploadProgressBack progressBack) {
        // 发送Form表单形式的Post请求
        return RxHttp.postForm(url).setMultiForm()// 设置提交方式为{multipart/form-data}
                //.setEncoded("Content-Type", "multipart/form-data")
                .setEncoded("content-type", "multipart/form-data")
                .addAll(params)//添加参数，非必须
                .addFile(key, file)
                .setAssemblyEnabled(isAddHeaders)//设置是否添加公共参数/头部，默认为true
                .asUpload(progress -> {
                    //上传进度回调,0-100，仅在进度有更新时才会回调,最多回调101次，最后一次回调Http执行结果
                    int currentProgress = progress.getProgress(); //当前进度 0-100
                    long currentSize = progress.getCurrentSize(); //当前已上传的字节大小
                    long totalSize = progress.getTotalSize();     //要上传的总字节大小
                    if (progressBack != null) {
                        progressBack.onProgress(progress, currentProgress, currentSize, totalSize);
                    }
                }, AndroidSchedulers.mainThread());//指定主线程回调
    }

    /**
     * postForm key-value请求：多文件上传，带进度上传
     */
    public Observable<String> postForm(String url, boolean isAddHeaders, Map<String, Object> params,
                                       String key, List<File> fileList, UploadProgressBack progressBack) {
        // 发送Form表单形式的Post请求
        return RxHttp.postForm(url).setMultiForm()// 设置提交方式为{multipart/form-data}
                //.setEncoded("Content-Type", "multipart/form-data")
                .setEncoded("content-type", "multipart/form-data")
                .addAll(params)//添加参数，非必须
                .addFile(key, fileList)
                .setAssemblyEnabled(isAddHeaders)//设置是否添加公共参数/头部，默认为true
                .asUpload(progress -> {
                    //上传进度回调,0-100，仅在进度有更新时才会回调,最多回调101次，最后一次回调Http执行结果
                    int currentProgress = progress.getProgress(); //当前进度 0-100
                    long currentSize = progress.getCurrentSize(); //当前已上传的字节大小
                    long totalSize = progress.getTotalSize();     //要上传的总字节大小
                    if (progressBack != null) {
                        progressBack.onProgress(progress, currentProgress, currentSize, totalSize);
                    }
                }, AndroidSchedulers.mainThread());//指定主线程回调
    }

    /**
     * get 无参数请求：文件下载，带进度下载
     */
    public Observable<String> getDownload(String url, boolean isAddHeaders, String destPath, UploadProgressBack progressBack) {
        // 文件存储路径 String destPath = getExternalCacheDir() + "/" + System.currentTimeMillis() + ".apk";
        return RxHttp.get(url).setAssemblyEnabled(isAddHeaders)//设置是否添加公共参数/头部，默认为true
                .asDownload(destPath, progress -> {
                    //下载进度回调,0-100，仅在进度有更新时才会回调，最多回调101次，最后一次回调文件存储路径
                    int currentProgress = progress.getProgress(); //当前进度 0-100
                    long currentSize = progress.getCurrentSize(); //当前已下载的字节大小
                    long totalSize = progress.getTotalSize();     //要下载的总字节大小
                    if (progressBack != null) {
                        progressBack.onProgress(progress, currentProgress, currentSize, totalSize);
                    }
                }, AndroidSchedulers.mainThread()); //指定主线程回调
    }

    /**
     * get key-value请求：文件下载，带进度下载
     */
    public Observable<String> getDownload(String url, boolean isAddHeaders, Map<String, Object> params,
                                          String destPath, UploadProgressBack progressBack) {
        return RxHttp.get(url).addAll(params)
                .setAssemblyEnabled(isAddHeaders)//设置是否添加公共参数/头部，默认为true
                .asDownload(destPath, progress -> {
                    //下载进度回调,0-100，仅在进度有更新时才会回调，最多回调101次，最后一次回调文件存储路径
                    int currentProgress = progress.getProgress(); //当前进度 0-100
                    long currentSize = progress.getCurrentSize(); //当前已下载的字节大小
                    long totalSize = progress.getTotalSize();     //要下载的总字节大小
                    if (progressBack != null) {
                        progressBack.onProgress(progress, currentProgress, currentSize, totalSize);
                    }
                }, AndroidSchedulers.mainThread()); //指定主线程回调
    }


}
