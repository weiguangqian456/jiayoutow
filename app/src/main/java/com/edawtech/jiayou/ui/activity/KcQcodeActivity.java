package com.edawtech.jiayou.ui.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.edawtech.jiayou.R;
import com.edawtech.jiayou.config.base.BaseMvpActivity;
import com.edawtech.jiayou.config.base.MyApplication;
import com.edawtech.jiayou.config.base.common.VsUpdateAPK;
import com.edawtech.jiayou.config.constant.VsUserConfig;
import com.edawtech.jiayou.utils.FitStateUtils;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 关于我们
 */
public class KcQcodeActivity extends BaseMvpActivity  {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_wx_gzh)
    TextView tvWxGzh;
    @BindView(R.id.tv_QQ)
    TextView tvQQ;
    @BindView(R.id.tv_kf_mobile)
    TextView tvKfMobile;
    @BindView(R.id.tv_kf_time)
    TextView tvKfTime;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_bq_about)
    TextView tvBqAbout;
    @BindView(R.id.tv_ysxy)
    TextView tvYsxy;

    private String qq;

    @Override
    public int getLayoutId() {
        return R.layout.activity_kc_qcode;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        FitStateUtils.setImmersionStateMode(this, R.color.activity_title_color);
        tvTitle.setText(R.string.my_tv10);
        tvVersion.setText(getVersion());

    }

    @OnClick({R.id.iv_back, R.id.QQ_layout, R.id.kf_mobile_layout, R.id.version_layout, R.id.ysxy_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.QQ_layout:
                //客服QQ
                String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq;
                Intent intent_qq = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                if (intent_qq.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent_qq);
                } else {
                    Toast.makeText(KcQcodeActivity.this, "未安装QQ", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.kf_mobile_layout:

                break;
            case R.id.version_layout:
                //版本更新

                break;
            case R.id.ysxy_layout:
                final String urlTo = "file:///android_asset/service_terms.html";
                Intent intent_fw = new Intent();
                intent_fw.setClass(mContext, WeiboShareWebViewActivity.class);
                String[] aboutBusiness = new String[]{mContext.getString(R.string.welcome_main_clause), "service", urlTo};
                intent_fw.putExtra("AboutBusiness", aboutBusiness);
                startActivity(intent_fw);
                break;
        }
    }

    @Override
    public void onSuccess(String data) {

    }

    @Override
    public void onFailure(Throwable e, int code, String msg, boolean isNetWorkError) {

    }

    /**
     * 开始更新
     */
    private void startUpdateApk() {
        VsUpdateAPK mUpdateAPK = new VsUpdateAPK(mContext);
        // mUpdateAPK.DowndloadThread(KcUserConfig.getDataString(mContext, KcUserConfig.JKey_UpgradeUrl), true);
        mUpdateAPK.NotificationDowndload(VsUserConfig.getDataString(mContext, VsUserConfig.JKey_UpgradeUrl), true, null);
//        mUpdateAPK.NotificationDowndload(VsUserConfig.getDataString(mContext, VsUserConfig.JKey_new_version_pgyer_url, ""), true, null);
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion() {
        try {
            PackageManager manager = MyApplication.getContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(MyApplication.getContext().getPackageName(), 0);
            String version = info.versionName;
            return "V" + version;
        } catch (Exception e) {
            e.printStackTrace();
            return MyApplication.getContext().getString(R.string.version_unkown);
        }
    }

    public static String getVersionCode() {
        try {
            PackageManager manager = MyApplication.getContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(MyApplication.getContext().getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return MyApplication.getContext().getString(R.string.version_unkown);
        }
    }
}
