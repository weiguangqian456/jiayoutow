package com.edawtech.jiayou.config.constant;

import java.text.DecimalFormat;

public class Constant {

    public static final String LOGGER = "logger";
    public static final String NETLOG_TAG = "netlog-";
    public static final String VIEW_TAG = "viewlog-";

    // 上传图片的类型
    public static final String[] imageType = new String[]{
            "image/png", "image/PNG",
            "image/jpeg", "image/JPEG",
            "image/jpg", "image/JPG",
            "image/bmp", "image/BMP",
            "image/ico", "image/ICO",
            "image/tga", "image/TGA",
            "image/tif", "image/TIF",
            "image/webp", "image/WEBP",
            "image/gif", "image/GIF"};
    // 上传视频的类型
    public static final String[] videoType = new String[]{
            "video/mp4", "video/MP4",
            "video/3gp", "video/3GP",
            "video/avi", "video/AVI",
            "video/mpg", "video/MPG",
            "video/flv", "video/FLV",
            "video/Ogg", "video/OGG",
            "video/gif", "video/GIF",
            "video/vob", "video/VOB",
            "video/mkv", "video/MKV",
            "video/webm", "video/WEBM",
            "video/mov", "video/MOV",
            "video/wmv", "video/WMV"};

    // 文档
    public static final String[] wordType = new String[]{"docx", "doc"};
    // excel
    public static final String[] excelType = new String[]{"xlsx", "xls"};

//    public static final String VERSIONCODE = ActivityUtils.getVersionCode();// 当前版本号
//    public static final String VERSIONNAME = ActivityUtils.getVersionName();// 当前版本名称
//    public static final int    UID         = ActivityUtils.getAppMetaDataint("uid"); // 用户ID

    // 获取缓存的字体主题标示。
    public static final String FontSizeTheme = "fontSizeTheme";
    // 获取缓存的引导页标示（以版本号为标示）。
    public static final String GuidePage = "GuidePage";
    // 缓存登录获取的token
    public static final String SP_TOKEN = "token";
    // 缓存用户所有的基本信息。
    public static final String UserInfor_Data = "UserInfor_Data";
    // 缓存头像
    public static final String SP_AVATAR = "avatar";
    // PreferenceHelper中，登录手机号key值
    public static final String LOGIN_PHONE = "account_phone";
    // PreferenceHelper中，登录密码key值
    public static final String LOGIN_PWD = "account_pwd";
    // PreferenceHelper中，iccid
    public static final String ICCID = "iccid";

    // 缓存APP下载地址
    public static final String APPURL = "APPURL";
    // 来自哪里的键
    public static final String FROM = "from";
    // 来自欢迎页面
    public static final int FROM_welcom = 8888;

    // 构造方法的字符格式这里如果小数不足2位,会以0补足.
    public static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    // paytype--------------- 1,2 支付方式 0-待定 1-支付宝  2-微信
    public static final String PayWay = "1";  // 1-支付宝
    public static final String PayWay2 = "2";  // 2-微信

    // 网络请求类型
    public static final int requestType_post = 1000;   // 1000:post 请求
    public static final int requestType_get = 2000;   // 2000:get 请求
    public static final int requestType_Upload = 3000;   // 3000:文件上传
    public static final int requestType_Download = 4000;   // 4000:文件下载

    // 数据类型: X-WWW-FORM-URLENCODED
    public static final String contentType_form = "application/x-www-form-urlencoded;charset=UTF-8";
    public static final String contentType_json = "application/json;charset=utf-8";

    // 请求接口每次获取的数据条数。
    public static final int PageSize_MAX = 999999;
    public static final int PageSize = 10;
    public static final int PageSize2 = 20;

    // 请求数据时填写的默认int值为：-1。
    public static final int Default_INT = -1;
    // 请求数据时填写的默认String值为：""。
    public static final String Default_STR = "";
    // 默认分隔符。
    public static final String Default_Separator = ",";

    // regType (string, optional): 接收类型 注册 1:手机，2邮箱，3微信，4QQ。
    public static final String receiveType_1 = "1";
    public static final String receiveType_2 = "2";
    public static final String receiveType_3 = "3";
    public static final String receiveType_4 = "4";

    // mesType (int): 发送信息的类型 1.手机注册 2.验证码登录 3.手机绑定。
    public static final int mesType_1 = 1;
    public static final int mesType_2 = 2;
    public static final int mesType_3 = 3;



    // 返回码说明
    public static final int CODE_Success = 200;   // 请求成功
    public static final int CODE_Failed = 500;   // 请求失败
    public static final int CODE_Failed_20201 = 1401;  // 请求失败（用户未登录）
    public static final int CODE_Failed_20202 = 1403;  // 请求失败（登录超时,请重新登录）
    public static final int CODE_Failed_20203 = 1404;  // 请求失败（账号被冻结）

    // 设置全局字体大小theme标识 -->
    public static final int Theme_FontSize_Small = 0x000001;// 小号字体
    public static final int Theme_FontSize_Default = 0x000002;// 标准字体（默认）
    public static final int Theme_FontSize_Medium = 0x000003;// 大号字体（中间号）
    public static final int Theme_FontSize_Large = 0x000004;// 超大字体
    public static final int Theme_FontSize_Big = 0x000005;// 特大字体

    // 自定义带有刻度的圆形进度条
    public static final int DEFAULT_SIZE = 150;
    public static final int DEFAULT_START_ANGLE = 270;
    public static final int DEFAULT_SWEEP_ANGLE = 360;
    public static final int DEFAULT_ANIM_TIME = 1000;
    public static final int DEFAULT_MAX_VALUE = 100;
    public static final int DEFAULT_VALUE = 50;
    public static final int DEFAULT_HINT_SIZE = 15;
    public static final int DEFAULT_UNIT_SIZE = 30;
    public static final int DEFAULT_VALUE_SIZE = 15;
    public static final int DEFAULT_ARC_WIDTH = 15;
    public static final int DEFAULT_WAVE_HEIGHT = 40;




}
