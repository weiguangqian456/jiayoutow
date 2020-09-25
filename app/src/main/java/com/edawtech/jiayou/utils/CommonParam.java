package com.edawtech.jiayou.utils;


/**
 * ClassName:      CommonParam
 * <p>
 * Author:
 * <p>
 * CreateDate:      2020/9/16 18:03
 * <p>
 * Description:     公共参数
 */
public class CommonParam {

    /**
     * sp储存名
     */
    public static final String SP_NAME = "JIA_YOU_USER_INFO";

    /**
     * 全局APP_ID
     */
    public static final String APP_ID = "JIA_YOU";

    /**
     * 测试地址
     */
//    public static final String BASE_URL = "http://192.168.0.39:9999/account/api";
    private static final String BASE_URL = "http://cyjxh.natapp1.cc";
    /**
     * 上传文件返回拼接地址头
     */
    public static final String UPLOAD_HEAD_URL = "http://qiniu.edawtech.com/";

    /**
     * 登录
     */
    public static final String LOGIN = BASE_URL + "/account/api/user/login";

    /**
     * 获取验证码
     */
    public static final String GET_CODE = BASE_URL + "/account/api/sms/code";

    /**
     * 注册
     */
    public static final String REGISTER = BASE_URL + "/account/api/register";

    /**
     * 修改/忘记密码
     */
    public static final String RESET_PASSWORD = BASE_URL + "/account/api/user/retrievePaw";

    /**
     * 获取用户信息
     */
    public static final String GET_USER_INFO = BASE_URL + "/account/api/user/findUidByPhone";

    /**
     * 获取用户成长金
     */
    public static final String GET_USER_GROWTH = BASE_URL + "/benefit/api/userInfo/getWallet";

    /**
     * 修改用户信息
     */
    public static final String CHANGE_USER_INFO = BASE_URL + "/account/api/user/changeUserInfo";

    /**
     * 上传图片
     */
    public static final String UPLOAD_IMAGE = BASE_URL + "/shop/web/homePagePush/uploadIconImg";

    /**
     * 获取二维码
     */
    public static final String GET_QR_CODE = BASE_URL + "/shop/api/share/shareImg";

    /**
     * 获取用户分享内容
     */
    public static final String GET_USER_SHARE_MSG = BASE_URL + "/shop/api/share/shareLink";

    /**
     * 获取用户成长金提现列表
     */
    public static final String GET_USER_RED_LIST = BASE_URL + "/benefit/api/userWallet/getTuserDraw";

    /**
     * 获取用户邀请人数
     */
    public static final String GET_INVITE_LIST = BASE_URL + "/benefit/api/userInfo/apiChildAgentUserList";

    /**
     * 获取邀请用户加油明细列表
     */
    public static final String GET_INVITE_USER_CHEER_DETAILS = BASE_URL + "/benefit/web/OrderController//selectGasStation";

    /**
     * 检测用户是否绑定微信
     */
    public static final String CHECK_USER_IS_BIND_WX = BASE_URL + "/benefit/api/userBank/selectUserInfoBank";

    /**
     * 提现
     */
    public static final String WITHDRAW_URL = BASE_URL +"/benefit/api/userWallet/userInfoWithdrawal";

}
