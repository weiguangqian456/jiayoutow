package com.edawtech.jiayou.ui.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.edawtech.jiayou.R;
import com.edawtech.jiayou.config.base.BaseMvpActivity;
import com.edawtech.jiayou.config.base.MyApplication;
import com.edawtech.jiayou.config.bean.UserInfoBean;
import com.edawtech.jiayou.config.constant.VsUserConfig;
import com.edawtech.jiayou.mvp.presenter.PublicPresenter;
import com.edawtech.jiayou.net.observer.TaskCallback;
import com.edawtech.jiayou.ui.view.CircleImageView;
import com.edawtech.jiayou.utils.CommonParam;
import com.edawtech.jiayou.utils.FitStateUtils;
import com.edawtech.jiayou.utils.StringUtils;
import com.edawtech.jiayou.utils.tool.LogUtils;
import com.edawtech.jiayou.utils.tool.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人中心
 */
public class UserInfoActivity extends BaseMvpActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.user_icon)
    CircleImageView userIcon;
    @BindView(R.id.nickname)
    TextView nickname;
    @BindView(R.id.account)
    TextView account;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.birthday)
    TextView birthday;

    //类型   1 代表修改生日   2 代表修改头像
    private int type = 0;
    //选中的日期
    private String selectTime;
    //用户生日
    private Date mDate;
    //请求
    private PublicPresenter mPresenter;
    //相机拍摄的头像文件(存放在SD卡根目录下) 当前时间+.jpg方式
    private static String IMAGE_FILE_NAME = "image_file_name.jpg";
    //裁剪图片uri
    private Uri cropImageUri;
    //裁剪宽高大小
    private static final int output_X = 60;
    private static final int output_Y = 60;


    @Override
    public int getLayoutId() {
        return R.layout.activity_user_info_text;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        FitStateUtils.setImmersionStateMode(this, R.color.activity_title_color);
        mPresenter = new PublicPresenter(this, true, "修改中...");
        mPresenter.attachView(this);
        tvTitle.setText(getResources().getString(R.string.vs_myinfo_text_title));
    }

    @OnClick({R.id.iv_back, R.id.my_icon_layout, R.id.nickname_layout, R.id.qrcode_layout, R.id.email_layout, R.id.sex_layout, R.id.birthday_layout})
    public void onViewClicked(View view) {
        Intent intent = new Intent(UserInfoActivity.this, SettingDetailActivity.class);
        switch (view.getId()) {
            case R.id.iv_back:
                ActivityCompat.finishAfterTransition(this);
                break;
            case R.id.my_icon_layout:   //我的头像
                iconDialog();
                break;
            case R.id.nickname_layout:  //昵称
                intent.putExtra("flag", "nickname");
                String name = nickname.getText().toString();
                if (name.equals("未设置")) {
                    name = "";
                }
                intent.putExtra("oldName", name);
                startActivityForResult(intent, 1000);
                break;
            case R.id.qrcode_layout:    //我的二维码
                Intent intent_qrcode = new Intent(mContext, KcMyQcodeActivity.class);
                String mRecommendInfo = VsUserConfig.getDataString(mContext, VsUserConfig.JKey_FRIEND_INVITE);
                intent_qrcode.putExtra("code", mRecommendInfo);
                startActivity(intent_qrcode);
                break;
            case R.id.email_layout:    //邮箱
                intent.putExtra("flag", "email");
                String mailbox = email.getText().toString();
                if (mailbox.equals("未设置")) {
                    mailbox = "";
                }
                intent.putExtra("oldName", mailbox);
                startActivityForResult(intent, 5000);
                break;
            case R.id.sex_layout:     //性别
                intent.putExtra("flag", "sex");
                String sex = this.sex.getText().toString();
                if (sex.equals("未设置")) {
                    sex = "";
                }
                intent.putExtra("oldName", sex);
                startActivityForResult(intent, 2000);
                break;
            case R.id.birthday_layout: //生日
                setBirthday();
                break;
        }
    }

    /**
     * 头像选择
     */
    private void iconDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setCancelable(true);
        dialog.show();

        dialog.getWindow().setContentView(R.layout.dialog_image);
        TextView mPhotoAlbum = dialog.findViewById(R.id.tv_album);
        TextView mCamera = dialog.findViewById(R.id.tv_photo);
        TextView mCancel = dialog.findViewById(R.id.tv_cancel);

        //设置弹出框位置为底部
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(R.color.transparent);
        window.setGravity(Gravity.BOTTOM);
        //设置弹出框宽度铺满
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.DialogAnimation);


        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        mPhotoAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPicForLocal();
                dialog.dismiss();
            }
        });

        mCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPicForCamera();
                dialog.dismiss();
            }
        });
    }

    /**
     * 设置生日
     */
    private void setBirthday() {
        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                //选中日期
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                selectTime = sdf.format(date);
                LogUtils.i("fxx", "选中的日期=" + selectTime);
                if (!birthday.getText().equals(selectTime)) {
                    //设置类型
                    type = 1;
                    //上传用户生日
                    changeUserInfo("birthday", selectTime);
                }
            }
        }).setType(new boolean[]{true, true, true, false, false, false})
                .setCancelText("取消")
                .setCancelColor(Color.BLACK)
                .setSubmitText("确定")
                .setSubmitColor(Color.BLACK)
                .setContentSize(18)
                .setTitleText("请选择日期")
                .setTitleColor(getResources().getColor(R.color.public_color_EC6941))
                .setTitleSize(20)
                .setOutSideCancelable(true)
                .isCyclic(true)
                .setTitleBgColor(0xFFFFFFFF)//标题背景颜色 Night mode
                .setBgColor(0xFFFFFFFF)//滚轮背景颜色 Night mode
                .setDate(Calendar.getInstance())// 默认是系统时间*/
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.show();
    }

    /**
     * 加载本地相册
     */
    private void getPicForLocal() {
        Intent it = new Intent();
        //设置文件类型
        it.setType("image/*");
        it.setAction(Intent.ACTION_PICK);
        startActivityForResult(it, 9999);
    }

    /**
     * 拍照
     */
    private void getPicForCamera() {
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断sd卡是否可用,储存照片文件
        if (hasSdcard()) {
            it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.
                    getExternalStorageDirectory(), IMAGE_FILE_NAME)));
        }
        startActivityForResult(it, 8888);
    }


    /**
     * 检查SD卡是否可用
     */
    private static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        //有存储的sdcard
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 裁剪图片
     */
    private void cropRawPic(Uri uri) {
        File cropPic = new File(getExternalCacheDir(), "crop.jpg");
        try {
            if (cropPic.exists()) {
                cropPic.delete();
            }
            cropPic.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cropImageUri = Uri.fromFile(cropPic);
        Intent it = new Intent("com.android.camera.action.CROP");
        it.setDataAndType(uri, "image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        it.putExtra("crop", "true");
        it.putExtra("scale", true);
        // aspectX , aspectY :宽高的比例
        it.putExtra("aspectX", 1);
        it.putExtra("aspectY", 1);
        // outputX , outputY : 裁剪图片宽高
        it.putExtra("outputX", output_X);
        it.putExtra("outputY", output_Y);
        it.putExtra("return_data", false);
        it.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri);
        it.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        it.putExtra("noFaceDetection", true);
        startActivityForResult(it, 1011);
    }


    /**
     * 回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = null;
        if (requestCode == 1000 ||
                requestCode == 2000 ||
                requestCode == 5000 ||
                requestCode == 8888 ||
                requestCode == 9999 ||
                requestCode == 999 ||
                requestCode == 1011) {
            if (data != null) {
                bundle = data.getExtras();
            }
        }
        switch (requestCode) {
            case 1000:          //昵称
                if (bundle == null) {
                    break;
                }
                nickname.setText(bundle.getString("nickname"));
                break;
            case 2000:          //性别
                if (bundle == null) {
                    break;
                }
                sex.setText(bundle.getString("sex"));
                break;
            case 5000:          //邮箱
                if (bundle == null) {
                    break;
                }
                email.setText(bundle.getString("email"));
                break;
            case 8888:     //拍照
                LogUtils.i("fxx", "拍照");
                //检测手机是否有SD卡
                if (hasSdcard()) {
                    File tempFile = new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME);
                    //这个是相机拍摄图片的保存地址
                    String path = Environment.getExternalStorageDirectory().toString();
                    //拍摄图片的图片地址
                    String CAMERA_IMAGE_PATH = path + "/" + IMAGE_FILE_NAME;
                    LogUtils.i("fxx", "相机图片地址==" + CAMERA_IMAGE_PATH);
                    //头像URI
                    Uri phoneUri = Uri.fromFile(tempFile);
                    //裁剪图片
                    cropRawPic(phoneUri);
                } else {
                    ToastUtil.showMsg("检测到手机没有SD卡存在");
                }
                break;
            case 9999:      //本地
                if (data != null) {
                    LogUtils.i("fxx", "相册");
                    //头像Uri
                    Uri localUri = data.getData();
                    //裁剪图片
                    cropRawPic(localUri);
                }
                break;
            case 1011:      //裁剪
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        LogUtils.i("fxx", "裁剪成功   上传图片");
                        //设置类型
                        type = 2;
                        //执行上传图片操作
                        uploadImage(cropImageUri);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 上传图片
     */
    private void uploadImage(Uri uri) {
        Map<String, Object> map = new HashMap<>();
        map.put("appId", CommonParam.APP_ID);
        map.put("uid", MyApplication.UID);
        map.put("phone", MyApplication.MOBILE);

        mPresenter.netWorkRequestPostForm(CommonParam.UPLOAD_IMAGE, false, null, map,
                "file", new File(uri.getPath()), null, null, new TaskCallback() {
                    @Override
                    public void onSuccess(String data) {
                        LogUtils.i("fxx", "上传成功=" + data);
                        try {
                            JSONObject json = new JSONObject(data);
                            JSONObject jsonData = json.getJSONObject("data");
                            String path = jsonData.getString("imagePath");
                            //拼接用户头像地址
                            String url = CommonParam.UPLOAD_HEAD_URL + path;
                            //修改用户头像资料
                            changeUserInfo("headLikeUrl", url);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable e, int code, String msg, boolean isNetWorkError) {
                        LogUtils.i("fxx", "上传失败   code=" + code + "  msg=" + msg + "   isNetWorkError=" + isNetWorkError);
                        ToastUtil.showMsg(msg);
                    }
                });
    }

    /**
     * 修改用户信息
     *
     * @param key
     * @param value
     */
    private void changeUserInfo(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        map.put("uid", MyApplication.UID);
        map.put("phone", MyApplication.MOBILE);
        map.put("appId", CommonParam.APP_ID);
        mPresenter.netWorkRequestPost(CommonParam.CHANGE_USER_INFO, map);
    }


    @Override
    public void onSuccess(String data) {
        LogUtils.i("fxx", "修改成功 data=" + data);
        ToastUtil.showMsg("修改成功");
        switch (type) {
            case 1:     //设置生日
                LogUtils.i("fxx", "设置生日成功");
                birthday.setText(selectTime);
                break;
            case 2:     //设置头像
                LogUtils.i("fxx", "设置头像成功");
                Glide.with(this)
                        .load(cropImageUri)
                        .into(userIcon);
                break;
        }
        //EventBus 我的页面更新用户数据
        EventBus.getDefault().post("userInfoChangeSuccess");
    }

    @Override
    public void onFailure(Throwable e, int code, String msg, boolean isNetWorkError) {
        LogUtils.e("fxx", "修改失败      code=" + code + "    msg=" + msg + "    isNetWorkError=" + isNetWorkError);
        type = 0;
        ToastUtil.showMsg(msg);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void showUserInfo(UserInfoBean.DataBean.TUserInfoBean info) {
        if (info != null) {
            LogUtils.i("fxx", "用户详情接收到数据");
            String url = info.getHeadLikeUrl();
            String nick = info.getUserName();
            String mAccount = info.getPhone();
            String mEmail = info.getEmail();
            String mSex = info.getUserGender();
            String mBirthday = info.getBirthday();
            account.setText(mAccount);
            if (!StringUtils.isEmpty(url)) {
                Glide.with(mContext)
                        .load(url)
                        .placeholder(R.drawable.myself_head)
                        .into(userIcon);
            }
            if (!StringUtils.isEmpty(nick)) {
                nickname.setText(nick);
            } else {
                nickname.setText("未设置");
            }
            if (!StringUtils.isEmpty(mEmail)) {
                email.setText(mEmail);
            } else {
                email.setText("未设置");
            }
            if (!StringUtils.isEmpty(mSex)) {
                sex.setText(mSex);
            } else {
                sex.setText("未设置");
            }
            if (!StringUtils.isEmpty(mBirthday)) {
                long time = Long.parseLong(mBirthday);
                mDate = new Date(time);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                birthday.setText(sdf.format(mDate));
            } else {
                birthday.setText("未设置");
            }
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        mPresenter.detachView();
        super.onDestroy();
    }
}
