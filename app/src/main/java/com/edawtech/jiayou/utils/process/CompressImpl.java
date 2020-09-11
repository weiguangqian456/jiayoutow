package com.edawtech.jiayou.utils.process;

import android.app.ProgressDialog;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CompressImpl {

    private static CompressConfig compressConfig;// 压缩配置
    private static AppCompatActivity context;
    private static String compressPath;
    private static ProgressDialog dialog;
    private static volatile CompressImpl mSingleton;

    public void preCompress(String photoPath, AppCompatActivity activity) {
        initData();
        context = activity;
        ArrayList<PhotoWy> photos = new ArrayList<>();
        photos.add(new PhotoWy(photoPath));
        if (!photos.isEmpty()) compress(photos);
    }

    private void initData() {
        compressConfig = CompressConfig.builder()
                .setUnCompressMinPixel(1000) // 最小像素不压缩，默认值：1000
                .setUnCompressNormalPixel(2000) // 标准像素不压缩，默认值：2000
                .setMaxPixel(1000) // 长或宽不超过的最大像素 (单位px)，默认值：1200
                .setMaxSize(100 * 1024) // 压缩到的最大大小 (单位B)，默认值：200 * 1024 = 200KB
                .enablePixelCompress(true) // 是否启用像素压缩，默认值：true
                .enableQualityCompress(true) // 是否启用质量压缩，默认值：true
                .enableReserveRaw(true) // 是否保留源文件，默认值：true
                .setCacheDir("") // 压缩后缓存图片路径，默认值：Constants.COMPRESS_CACHE
                .setShowCompressDialog(true) // 是否显示压缩进度条，默认值：false
                .create();
    }

    // 开始压缩
    private  void compress(ArrayList<PhotoWy> photos) {
        if (compressConfig.isShowCompressDialog()) {
            Log.e("netease >>> ", "开启了加载框");
            dialog = CommonUtils.showProgressDialog(context, "压缩中……");
        }
        CompressImageManager.build(context, compressConfig, photos, new CompressImage.CompressListener() {
            @Override
            public void onCompressSuccess(ArrayList<PhotoWy> images) {
                Log.e("netease >>> ", "压缩成功");
                compressPath = images.get(0).getCompressPath();
                String originalPath = images.get(0).getOriginalPath();

                if (compressPath != null) {
                    //请求接口，上传图片到服务器
                    File file = new File(compressPath);
                    RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part MultipartFile =
                            MultipartBody.Part.createFormData("file", file.getName(), imageBody);
                    // 联网请求
//            mPresenter.uploadPic(MultipartFile);
//                    uploadPicNet(file);
                } else if (originalPath != null) {
                }

                if (dialog != null && !context.isFinishing()) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onCompressFailed(ArrayList<PhotoWy> images, String error) {
                Log.e("netease >>> ", error);
                if (dialog != null && !context.isFinishing()) {
                    dialog.dismiss();
                }
            }
        }).compress();
    }


   /* private  void uploadPicNet(File file) {
        String token = Usng.Reading_Data(context, "data", "token");
        String url = UlieAunx.httpIP + UlieAunx.http_upload_pic;
//        String url = "http://192.168.100.66:8080" + UlieAunx.http_upload_pic;
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", token);
        uploadNet(file, url, map);
    }

    private  void uploadNet(File file, String url, Map<String, String> map) {
        OkhttpUtil.okHttpUploadFile(url, file, "file", "image", new HashMap<>(), map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Log.e("feedbackA", e + "");
            }

            @Override
            public void onResponse(String response) throws JSONException {
                Log.e("feedbackA", response);
                JSONObject jsonObject = new JSONObject(response);
                String code = jsonObject.getString("code");
                String message = jsonObject.getString("message");
                if (code.equals("200")) {
                    String picUrl = jsonObject.getString("data");
                    LiveDataBus.get().with("compre_single").postValue(picUrl);
                } else ToastUtils.showShort(message);
            }
        });
    }*/

    public static CompressImpl getInstance() {
        if (mSingleton == null) {
            synchronized (CompressImpl.class) {
                if (mSingleton == null) {
                    mSingleton= new CompressImpl();
                }
            }
        }
        return mSingleton;
    }
}
