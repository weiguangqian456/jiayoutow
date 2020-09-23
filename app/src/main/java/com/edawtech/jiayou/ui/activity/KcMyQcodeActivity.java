package com.edawtech.jiayou.ui.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.core.graphics.ColorUtils;

import com.bumptech.glide.Glide;
import com.edawtech.jiayou.R;
import com.edawtech.jiayou.config.base.BaseMvpActivity;
import com.edawtech.jiayou.config.base.MyApplication;
import com.edawtech.jiayou.mvp.presenter.PublicPresenter;
import com.edawtech.jiayou.utils.CommonParam;
import com.edawtech.jiayou.utils.ScreenAdaptiveUtils;
import com.edawtech.jiayou.utils.tool.LogUtils;
import com.edawtech.jiayou.utils.tool.ToastUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的二维码
 */
public class KcMyQcodeActivity extends BaseMvpActivity {

    @BindView(R.id.iv_result)
    ImageView ivResult;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private PublicPresenter mPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_kc_my_qcode;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        //今日头条适配屏幕
        ScreenAdaptiveUtils.initCustomDensity(this, getApplication());
        initStatusBar(Color.parseColor("#ED3328"));
        mPresenter = new PublicPresenter(this, true, "加载中...");
        mPresenter.attachView(this);
        tvTitle.setText("我的二维码");
        getQrCode();
    }

    /**
     * 获取二维码
     *
     * @param
     */
    private void getQrCode() {
        Map<String, Object> map = new HashMap<>();
        map.put("appId", CommonParam.APP_ID);
        map.put("uid", MyApplication.UID);
        map.put("type", "ere");
        map.put("phone", MyApplication.MOBILE);
        mPresenter.netWorkRequestGet(CommonParam.GET_QR_CODE, map);
    }

    private void initStatusBar(@ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 设置状态栏底色颜色
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(color);
            // 如果亮色，设置状态栏文字为黑色
            if (ColorUtils.calculateLuminance(color) >= 0.5) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }
    }

    /**
     * 生成二维码 要转换的地址或字符串,可以是中文
     *
     * @param url
     * @param width
     * @param height
     * @return
     */
    public Bitmap createQRImage(String url, final int width, final int height) {
        try {
            // 判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1) {
                return null;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = 0xff000000;
                    } else {
                        pixels[y * width + x] = 0xffffffff;
                    }
                }
            }
            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onSuccess(String data) {
        LogUtils.i("fxx", "二维码获取成功   data=" + data);
        try {
            JSONObject json = new JSONObject(data);
            JSONObject jsonData = json.getJSONObject("data");
            String path = jsonData.getString("imagePath");
            path = CommonParam.UPLOAD_HEAD_URL + path;
            Glide.with(this)
                    .load(path)
                    .centerCrop()
                    .into(ivResult);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Throwable e, int code, String msg, boolean isNetWorkError) {
        LogUtils.i("fxx", "二维码获取失败   code=" + code + "   msg=" + msg + "   isNetWorkError=" + isNetWorkError);
        ToastUtil.showMsg(msg);
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}
