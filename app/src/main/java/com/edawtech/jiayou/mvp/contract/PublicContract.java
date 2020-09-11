package com.edawtech.jiayou.mvp.contract;



import com.edawtech.jiayou.config.base.BaseView;
import com.edawtech.jiayou.net.observer.TaskCallback;
import com.edawtech.jiayou.net.observer.UploadProgressBack;


import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author azheng
 * @date 2018/6/4.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：Contract 是一个契约，将Model、View、Presenter 进行约束管理，方便后期类的查找、维护。
 */
public interface PublicContract {

//    interface Model {
//        // Flowable<BaseObjectBean<LoginBean>> login(String username, String password);
//        /**
//         * post json请求
//         */
//        Observable<String> post(String url, String json);
//    }

    interface View extends BaseView {

        // 数据获取成功
        void onSuccess(String data);

        // 数据获取失败
        void onFailure(Throwable e, int code, String msg, boolean isNetWorkError);
    }

    interface Presenter {
        /**
         * 网络请求：POST请求
         * @param url
         * @param json
         */
        void netWorkRequestPost(String url, String contentType, boolean isAddHeaders, Map<String, Object> headers,
                                String json, Map<String, Object> params, TaskCallback task);

        /**
         * 网络请求：GET请求
         * @param url
         * @param json
         */
        void netWorkRequestGet(String url, boolean isAddHeaders, Map<String, Object> headers, String json,
                               Map<String, Object> params, TaskCallback task);

        /**
         * 网络请求：postForm请求：文件上传，带进度上传
         */
        void netWorkRequestPostForm(String url, boolean isAddHeaders, Map<String, Object> headers, Map<String, Object> params,
                                    String key, File file, List<File> fileList, UploadProgressBack progressBack, TaskCallback task);

        /**
         * 网络请求：get请求：文件下载，带进度下载
         */
        void netWorkRequestGetDownload(String url, boolean isAddHeaders, Map<String, Object> headers, Map<String, Object> params,
                                       String destPath, UploadProgressBack progressBack, TaskCallback task);
    }

}
