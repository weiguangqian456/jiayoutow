package com.edawtech.jiayou.mvp.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;


import com.edawtech.jiayou.R;
import com.edawtech.jiayou.config.bean.BaseResultData;
import com.edawtech.jiayou.config.bean.ResultEntity;
import com.edawtech.jiayou.config.constant.Constant;
import com.edawtech.jiayou.mvp.model.PublicModel;
import com.edawtech.jiayou.net.observer.BaseObserver;
import com.edawtech.jiayou.net.observer.ObserverOnNextListener;
import com.edawtech.jiayou.net.observer.TaskCallback;
import com.edawtech.jiayou.net.observer.UploadProgressBack;
import com.edawtech.jiayou.ui.dialog.CustomDialogNoTitle;
import com.edawtech.jiayou.ui.dialog.LoadingDialog;
import com.edawtech.jiayou.utils.tool.GsonUtils;
import com.edawtech.jiayou.utils.tool.IntentJumpUtils;
import com.edawtech.jiayou.utils.tool.LogUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @ProjectName: ApplicPhone
 * @Package: com.example.applicphone.mvp.presenter
 * @ClassName: PublicGetTask
 * @Author: LXJ
 * @CreateDate: 2020/7/25 18:30
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/25 18:30
 * @UpdateRemark: 更新说明
 * @Version: PublicGetTask
 * @Description: java类作用描述：不绑定MVP生命周期的网络请求类。
 */
public class PublicGetTask {

    private Context context;
    private PublicModel pModel;
    private TaskCallback taskCallBack;
    private boolean isHaveCode = true;// 接口返回没有code码。

    // 网络加载通用LOADING.
    private LoadingDialog mLoadingDialog;
    private boolean isShowLoadingDialog;// 是否显示。
    private String msg;
    private boolean isFromService;// 是否是后台服务类请求数据。
    private CustomDialogNoTitle.Builder mBuilderDialog;

    // 接口返回没有code码。
    public void setHaveCode(boolean haveCode) {
        this.isHaveCode = haveCode;
    }

    // 初始化。
    private void initLoading(Context context) {
        if (context != null) {
            if (mLoadingDialog == null) {
                mLoadingDialog = new LoadingDialog(context);
            }
            mLoadingDialog.setCancelable(true);
        }
    }

    // 显示。
    private void showLoading() {
        if (mLoadingDialog != null && isShowLoadingDialog) {
            mLoadingDialog.setContent(msg);
            mLoadingDialog.dismiss();
            mLoadingDialog.show();
        }
    }

    // 关闭。
    private void clearLoading() {
        try {
            if (mLoadingDialog != null) {
                mLoadingDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 退出登录弹出框提醒
    public void showAlertDialog(Context context, String msg) {
        try {
            if (context == null || TextUtils.isEmpty(msg)) {
                return;
            }
            if (mBuilderDialog != null) {
                mBuilderDialog.setDismiss();
                mBuilderDialog = null;
            }
            mBuilderDialog = new CustomDialogNoTitle.Builder(context);
            mBuilderDialog.setCancelable(false);
            // builder.setTitle(getResources().getString(R.string.ExitLogin));
            mBuilderDialog.setMessage(msg);// 确定要退出登录吗？
            mBuilderDialog.setPositiveButton(context.getResources().getString(R.string.sure), new DialogInterface.OnClickListener() {// 确定
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    // 设置你的操作事项
                    // 退出登录。
                //    IntentJumpUtils.Logout(context, true);
                }
            });
            //        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {// 取消
            //            public void onClick(DialogInterface dialog, int which) {
            //                dialog.dismiss();
            //            }
            //        });
            mBuilderDialog.create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //public PublicGetTask() { this.pModel = new PublicModel(); }

    public PublicGetTask(Context context, boolean isFromService) {
        this.context = context;
        this.pModel = new PublicModel();
        this.isFromService = isFromService;
    }

    public PublicGetTask(Context context, boolean isFromService, boolean isShowLoadingDialog, String msg) {
        this.context = context;
        this.pModel = new PublicModel();
        this.isFromService = isFromService;
        this.isShowLoadingDialog = isShowLoadingDialog;
        this.msg = msg;

        // 初始化加载框。
        initLoading(context);
    }

    // 是否显示网络加载。
    public void setShowLoadingDialog(boolean isShowLoadingDialog) {
        this.isShowLoadingDialog = isShowLoadingDialog;
    }
    // 网络加载框是否可以隐藏。
    public void setCancelableDialog(boolean cancelable) {
        try {
            if (mLoadingDialog != null) {
                mLoadingDialog.setCancelable(cancelable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 网络请求：POST请求
     */
    public void netWorkRequestPost(String url, String contentType, boolean isAddHeaders, Map<String, Object> headers,
                                   String json, Map<String, Object> params, TaskCallback task) {
        netWorkRequest(Constant.requestType_post, url, contentType, isAddHeaders, headers, json, params, task,
                "", null, "", null, false, null);
    }

    /**
     * 网络请求：GET请求
     */
    public void netWorkRequestGet(String url, boolean isAddHeaders, Map<String, Object> headers,
                                  String json, Map<String, Object> params, TaskCallback task) {
        netWorkRequest(Constant.requestType_get, url, Constant.contentType_json, isAddHeaders, headers, json, params, task,
                "", null, "", null, false, null);
    }

    /**
     * 网络请求：postForm请求：文件上传，带进度上传
     */
    public void netWorkRequestPostForm(String url, boolean isAddHeaders, Map<String, Object> headers, Map<String, Object> params,
                                       String key, File file, List<File> fileList, UploadProgressBack progressBack, TaskCallback task) {
        netWorkRequest(Constant.requestType_Upload, url, Constant.contentType_json, isAddHeaders, headers, "", params, task,
                key, file, "", progressBack, false, fileList);
    }

    /**
     * 网络请求：get请求：文件下载，带进度下载
     */
    public void netWorkRequestGetDownload(String url, boolean isAddHeaders, Map<String, Object> headers, Map<String, Object> params,
                                          String destPath, UploadProgressBack progressBack, TaskCallback task) {
        netWorkRequest(Constant.requestType_Download, url, Constant.contentType_json, isAddHeaders, headers, "", params, task,
                "", null, destPath, progressBack, false, null);
    }

    /**
     * 网络请求：POST jsonArray请求（单独处理）
     */
    public void netWorkRequestPostArray(String url, boolean isAddHeaders, String json, TaskCallback task) {
        netWorkRequest(Constant.requestType_post, url, Constant.contentType_json, isAddHeaders, null,
                json, null, task, "", null, "", null, true, null);
    }

    /**
     * 网络请求：
     */
    private void netWorkRequest(int requestType, String url, String contentType, boolean isAddHeaders, Map<String, Object> headers,
                                String json, Map<String, Object> params, TaskCallback task,
                                String key, File file, String destPath, UploadProgressBack progressBack,
                                boolean isPostJsonArray, List<File> fileList) {
        // View是否绑定 如果没有绑定，就不执行网络请求
        if (context == null || pModel == null || task == null) {
            return;
        }
        this.taskCallBack = task;
        /**
         * 获取Observable对象。
         */
        Observable<String> mObservable;
        /**  POST请求  **/
        if (requestType == Constant.requestType_post) {
            if (isPostJsonArray) {
                /**  jsonArray请求  **/
                mObservable = pModel.postArray(url, isAddHeaders, json);
            } else {
                if (params != null) {
                    /**  key-value请求  **/
                    mObservable = pModel.post(url, contentType, isAddHeaders, params);
                } else {
                    /**  json请求  **/
                    mObservable = pModel.post(url, isAddHeaders, json);
                }
            }
        } else if (requestType == Constant.requestType_Upload) {
            // postForm请求：文件上传，带进度上传
            // postForm请求：文件上传，带进度上传
            if (fileList != null && fileList.size() > 0) {
                if (params != null) {
                    /**  key-value请求  **/
                    mObservable = pModel.postForm(url, isAddHeaders, params, key, fileList, progressBack);
                } else {
                    mObservable = pModel.postForm(url, isAddHeaders, key, fileList, progressBack);
                }
            } else {
                if (params != null) {
                    /**  key-value请求  **/
                    mObservable = pModel.postForm(url, isAddHeaders, params, key, file, progressBack);
                } else {
                    mObservable = pModel.postForm(url, isAddHeaders, key, file, progressBack);
                }
            }
        } else if (requestType == Constant.requestType_Download) {
            // get请求：文件下载，带进度下载
            if (params != null) {
                /**  key-value请求  **/
                mObservable = pModel.getDownload(url, isAddHeaders, params, destPath, progressBack);
            } else {
                mObservable = pModel.getDownload(url, isAddHeaders, destPath, progressBack);
            }
        } else {
            /**  GET请求  **/
            if (params != null) {
                /**  key-value请求  **/
                mObservable = pModel.get(url, isAddHeaders, params);
            } else {
                /**  无参数 请求  **/
                mObservable = pModel.get(url, isAddHeaders);
            }
        }
        // 网络请求
        //        mObservable.as(RxLife.asOnMain((LifecycleOwner) context))//感知生命周期，并在主线程回调
        //                .subscribe(observer);
        mObservable
                /*http请求线程*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread())
                /*数据回调*/
                .subscribe(new BaseObserver<String>(new ObserverOnNextListener<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // 显示加载框
                        showLoading();
                    }

                    @Override
                    public void onSuccees(String data) {

                            try {
                            if (!isHaveCode) {
                                LogUtils.i("data",data);
                                taskCallBack.onSuccess(data);
                            } else {
//                                BaseResultData resultData = GsonUtils.getGson().fromJson(data, BaseResultData.class);
//                                if (resultData.getCode() == Constant.CODE_Success) {
//                                    LogUtils.i("data",data);
//                                    taskCallBack.onSuccess(data);
//
//                                } else if (resultData.getCode() == Constant.CODE_Failed_20201
//                                        || resultData.getCode() == Constant.CODE_Failed_20202
//                                        || resultData.getCode() == Constant.CODE_Failed_20203) {
//                                    String msg = context.getResources().getString(R.string.pleaseLoginAgain);
//                                    if (resultData.getCode() == Constant.CODE_Failed_20201) {
//                                        msg = context.getResources().getString(R.string.UserNotLoggedIn);
//                                    } else if (resultData.getCode() == Constant.CODE_Failed_20202) {
//                                        msg = context.getResources().getString(R.string.pleaseLoginAgain);
//                                    } else if (resultData.getCode() == Constant.CODE_Failed_20203) {
//                                        msg = context.getResources().getString(R.string.AccountHasBeenFrozen);
//                                    }
//                                    if (!isFromService) {
//                                        // 身份已过期,请重新登录
//                                        //ToastUtil.showMsg(msg);
//                                        // 退出登录弹出框提醒
//                                        showAlertDialog(context, msg);
//                                    }
//                                    // 回调。
//                                    taskCallBack.onFailure(null, resultData.getCode(), resultData.getMessage(), false);
//                                } else {
//                                    taskCallBack.onFailure(null, resultData.getCode(), resultData.getMessage(), false);
//                                }


                                ResultEntity  resultData = GsonUtils.getGson().fromJson(data, ResultEntity.class);
                                if (resultData.getCode()==Constant.CODE_Success){
                                    taskCallBack.onSuccess(data);
                                }

                         }
                        } catch (Exception e) {
                            // 数据解析失败 DataParsingFailed getView().getContext().getResources().getString(R.string.DataParsingFailed)
                            taskCallBack.onFailure(e, Constant.Default_INT, e.getMessage(), false);


                        }
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        taskCallBack.onFailure(e, Constant.Default_INT, e.getMessage(), false);
                    }

                    @Override
                    public void onError(Throwable e, boolean isNetWorkError) {
                        if (isNetWorkError) {
                            // 网络错误 NetworkError
                            taskCallBack.onFailure(e, Constant.Default_INT, context.getResources().getString(R.string.NetworkError), true);
                        } else {
                            taskCallBack.onFailure(e, Constant.Default_INT, e.getMessage(), false);
                        }
                    }

                    @Override
                    public void onComplete() {
                        // 隐藏加载框
                        clearLoading();
                    }
                }));//第三步, 订阅回调(此步骤同RxJava订阅观察者)
    }

    /** GET请求 */
//    // 网络请求：GET请求
//    public void netWorkRequestGet(String url) {
//        netWorkRequestGet(url, true, null, "", null, null);
//    }
//    // 网络请求：GET请求
//    public void netWorkRequestGet(String url, boolean isAddHeaders) {
//        netWorkRequestGet(url, isAddHeaders, null, "", null, null);
//    }
    // 网络请求：GET请求
    public void netWorkRequestGet(String url, TaskCallback task) {
        netWorkRequestGet(url, true, null, "", null, task);
    }
    // 网络请求：GET请求
    public void netWorkRequestGet(String url, boolean isAddHeaders, TaskCallback task) {
        netWorkRequestGet(url, isAddHeaders, null, "", null, task);
    }
//    // 网络请求：GET请求
//    public void netWorkRequestGet(String url, Map<String, Object> params) {
//        netWorkRequestGet(url, true, null, "", params, null);
//    }
//    // 网络请求：GET请求
//    public void netWorkRequestGet(String url, boolean isAddHeaders, Map<String, Object> params) {
//        netWorkRequestGet(url, isAddHeaders, null, "", params, null);
//    }
    // 网络请求：GET请求
    public void netWorkRequestGet(String url, Map<String, Object> params, TaskCallback task) {
        netWorkRequestGet(url, true, null, "", params, task);
    }


    // 网络请求：GET请求
    public void netWorkRequestGet(String url, boolean isAddHeaders, Map<String, Object> params, TaskCallback task) {
        netWorkRequestGet(url, isAddHeaders, null, "", params, task);
    }

    /** POST请求 */
//    // 网络请求：POST请求
//    public void netWorkRequestPost(String url, String contentType, String json) {
//        netWorkRequestPost(url, contentType, true, null, json, null, null);
//    }
//    // 网络请求：POST请求
//    public void netWorkRequestPost(String url, String contentType, Map<String, Object> params) {
//        netWorkRequestPost(url, contentType, true, null, "", params, null);
//    }
//    // 网络请求：POST请求
//    public void netWorkRequestPost(String url, String contentType, boolean isAddHeaders, String json) {
//        netWorkRequestPost(url, contentType, isAddHeaders, null, json, null, null);
//    }
//    // 网络请求：POST请求
//    public void netWorkRequestPost(String url, String contentType, boolean isAddHeaders, Map<String, Object> params) {
//        netWorkRequestPost(url, contentType, isAddHeaders, null, "", params, null);
//    }
    // 网络请求：POST请求
    public void netWorkRequestPost(String url, String contentType, String json, TaskCallback task) {
        netWorkRequestPost(url, contentType, true, null, json, null, task);
    }
    // 网络请求：POST请求
    public void netWorkRequestPost(String url, String contentType, Map<String, Object> params, TaskCallback task) {
        netWorkRequestPost(url, contentType, true, null, "", params, task);
    }
    // 网络请求：POST请求
    public void netWorkRequestPost(String url, String contentType, boolean isAddHeaders, String json, TaskCallback task) {
        netWorkRequestPost(url, contentType, isAddHeaders, null, json, null, task);
    }
    // 网络请求：POST请求
    public void netWorkRequestPost(String url, String contentType, boolean isAddHeaders, Map<String, Object> params, TaskCallback task) {
        netWorkRequestPost(url, contentType, isAddHeaders, null, "", params, task);
    }

//    // 网络请求：POST请求
//    public void netWorkRequestPost(String url, String json) {
//        netWorkRequestPost(url, Constant.contentType_json, true, null, json, null, null);
//    }
//    // 网络请求：POST请求
//    public void netWorkRequestPost(String url, Map<String, Object> params) {
//        netWorkRequestPost(url, Constant.contentType_json, true, null, "", params, null);
//    }
//    // 网络请求：POST请求
//    public void netWorkRequestPost(String url, boolean isAddHeaders, String json) {
//        netWorkRequestPost(url, Constant.contentType_json, isAddHeaders, null, json, null, null);
//    }
//    // 网络请求：POST请求
//    public void netWorkRequestPost(String url, boolean isAddHeaders, Map<String, Object> params) {
//        netWorkRequestPost(url, Constant.contentType_json, isAddHeaders, null, "", params, null);
//    }
    // 网络请求：POST请求
    public void netWorkRequestPost(String url, String json, TaskCallback task) {
        netWorkRequestPost(url, Constant.contentType_json, true, null, json, null, task);
    }
    // 网络请求：POST请求
    public void netWorkRequestPost(String url, Map<String, Object> params, TaskCallback task) {
        netWorkRequestPost(url, Constant.contentType_json, true, null, "", params, task);
    }
    // 网络请求：POST请求
    public void netWorkRequestPost(String url, boolean isAddHeaders, String json, TaskCallback task) {
        netWorkRequestPost(url, Constant.contentType_json, isAddHeaders, null, json, null, task);
    }
    // 网络请求：POST请求
    public void netWorkRequestPost(String url, boolean isAddHeaders, Map<String, Object> params, TaskCallback task) {
        netWorkRequestPost(url, Constant.contentType_json, isAddHeaders, null, "", params, task);
    }


    /** 文件上传 请求 */
//    public void netWorkRequestPostForm(String url, String key, File file, UploadProgressBack progressBack) {
//        netWorkRequestPostForm(url, true, null, null, key, file, progressBack, null);
//    }
//    public void netWorkRequestPostForm(String url, boolean isAddHeaders, String key, File file, UploadProgressBack progressBack) {
//        netWorkRequestPostForm(url, isAddHeaders, null, null, key, file, progressBack, null);
//    }
    public void netWorkRequestPostForm(String url, String key, File file, UploadProgressBack progressBack, TaskCallback task) {
        netWorkRequestPostForm(url, true, null, null, key, file, null, progressBack, task);
    }
    public void netWorkRequestPostForm(String url, boolean isAddHeaders, String key, File file, UploadProgressBack progressBack, TaskCallback task) {
        netWorkRequestPostForm(url, isAddHeaders, null, null, key, file, null, progressBack, task);
    }
//    public void netWorkRequestPostForm(String url, Map<String, Object> params, String key, File file, UploadProgressBack progressBack) {
//        netWorkRequestPostForm(url, true, null, params, key, file, progressBack, null);
//    }
//    public void netWorkRequestPostForm(String url, boolean isAddHeaders, Map<String, Object> params, String key, File file, UploadProgressBack progressBack) {
//        netWorkRequestPostForm(url, isAddHeaders, null, params, key, file, progressBack, null);
//    }
    public void netWorkRequestPostForm(String url, Map<String, Object> params, String key, File file, UploadProgressBack progressBack, TaskCallback task) {
        netWorkRequestPostForm(url, true, null, params, key, file, null, progressBack, task);
    }
    public void netWorkRequestPostForm(String url, boolean isAddHeaders, Map<String, Object> params, String key, File file,
                                       UploadProgressBack progressBack, TaskCallback task) {
        netWorkRequestPostForm(url, isAddHeaders, null, params, key, file, null, progressBack, task);
    }
    /** 多文件上传 请求 */
//    public void netWorkRequestPostForm(String url, String key, List<File> fileList, UploadProgressBack progressBack) {
//        netWorkRequestPostForm(url, true, null, null, key, null, fileList, progressBack, null);
//    }
//    public void netWorkRequestPostForm(String url, boolean isAddHeaders, String key, List<File> fileList, UploadProgressBack progressBack) {
//        netWorkRequestPostForm(url, isAddHeaders, null, null, key, null, fileList, progressBack, null);
//    }
    public void netWorkRequestPostForm(String url, String key, List<File> fileList, UploadProgressBack progressBack, TaskCallback task) {
        netWorkRequestPostForm(url, true, null, null, key, null, fileList, progressBack, task);
    }
    public void netWorkRequestPostForm(String url, boolean isAddHeaders, String key, List<File> fileList, UploadProgressBack progressBack, TaskCallback task) {
        netWorkRequestPostForm(url, isAddHeaders, null, null, key, null, fileList, progressBack, task);
    }
//    public void netWorkRequestPostForm(String url, Map<String, Object> params, String key, List<File> fileList, UploadProgressBack progressBack) {
//        netWorkRequestPostForm(url, true, null, params, key, null, fileList, progressBack, null);
//    }
//    public void netWorkRequestPostForm(String url, boolean isAddHeaders, Map<String, Object> params, String key, List<File> fileList, UploadProgressBack progressBack) {
//        netWorkRequestPostForm(url, isAddHeaders, null, params, key, null, fileList, progressBack, null);
//    }
    public void netWorkRequestPostForm(String url, Map<String, Object> params, String key, List<File> fileList, UploadProgressBack progressBack, TaskCallback task) {
        netWorkRequestPostForm(url, true, null, params, key, null, fileList, progressBack, task);
    }
    public void netWorkRequestPostForm(String url, boolean isAddHeaders, Map<String, Object> params, String key, List<File> fileList,
                                       UploadProgressBack progressBack, TaskCallback task) {
        netWorkRequestPostForm(url, isAddHeaders, null, params, key, null, fileList, progressBack, task);
    }


    /** 文件下载 请求 */
//    public void netWorkRequestGetDownload(String url, String destPath, UploadProgressBack progressBack) {
//        netWorkRequestGetDownload(url, true, null, null, destPath, progressBack, null);
//    }
//    public void netWorkRequestGetDownload(String url, boolean isAddHeaders, String destPath, UploadProgressBack progressBack) {
//        netWorkRequestGetDownload(url, isAddHeaders, null, null, destPath, progressBack, null);
//    }
    public void netWorkRequestGetDownload(String url, String destPath, UploadProgressBack progressBack, TaskCallback task) {
        netWorkRequestGetDownload(url, true, null, null, destPath, progressBack, task);
    }
    public void netWorkRequestGetDownload(String url, boolean isAddHeaders, String destPath, UploadProgressBack progressBack, TaskCallback task) {
        netWorkRequestGetDownload(url, isAddHeaders, null, null, destPath, progressBack, task);
    }
//    public void netWorkRequestGetDownload(String url, Map<String, Object> params, String destPath, UploadProgressBack progressBack) {
//        netWorkRequestGetDownload(url, true, null, params, destPath, progressBack, null);
//    }
//    public void netWorkRequestGetDownload(String url, boolean isAddHeaders, Map<String, Object> params, String destPath, UploadProgressBack progressBack) {
//        netWorkRequestGetDownload(url, isAddHeaders, null, params, destPath, progressBack, null);
//    }
    public void netWorkRequestGetDownload(String url, Map<String, Object> params, String destPath, UploadProgressBack progressBack, TaskCallback task) {
        netWorkRequestGetDownload(url, true, null, params, destPath, progressBack, task);
    }
    public void netWorkRequestGetDownload(String url, boolean isAddHeaders, Map<String, Object> params, String destPath,
                                          UploadProgressBack progressBack, TaskCallback task) {
        netWorkRequestGetDownload(url, isAddHeaders, null, params, destPath, progressBack, task);
    }

}
