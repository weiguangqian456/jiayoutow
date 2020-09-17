package com.edawtech.jiayou.net.http;

import com.edawtech.jiayou.BuildConfig;

import rxhttp.wrapper.annotation.DefaultDomain;
import rxhttp.wrapper.annotation.Domain;

/**
 * 请求接口。
 */
public class HttpURL {


    //################################ 固定地址 ################################//
    // 正式环境API接口地址：--- 不经过单例模式网络框架的总接口地址。
    public final static String BASE_Url = BuildConfig.BASE_Url;// "http://192.168.1.120:8080";
    // 动态路径
    public final static String URL_dynamic = BuildConfig.URL_dynamic;// "/api";

    // 商户id标识
    public final static String APPID = BuildConfig.APPID;// "888888";

    /**
     * RxHttp提供了@DefaultDomain()、@Domain()这两个注解来标明默认域名和非默认域名。
     * 通过@Domain()注解标注非默认域名，就会在RxHttp类中生成setDomainToXxxIfAbsent()方法，其中Xxx就是注解中取的别名。
     * 使用了注解，此时(需要Rebuild一下项目)就会在RxHttp类中生成相应的setDomainToXxxIfAbsent()方法。
     * RxHttp共有3种指定域名的方式，按优先级排名分别是：手动输入域名 > 指定非默认域名 > 使用默认域名。
     * 动态域名切换的需求，只需要对BaseUrl重新赋值即可，如下：
     * HttpURL.baseUrl = "https://www.qq.com";（动态更改默认域名，改完立即生效，非默认域名同理）。
     */
    @DefaultDomain // 设置为默认域名（唯一）
    public static String baseUrl = BuildConfig.BASE_Url;// "https://www.wanandroid.com/";

    @Domain(name = "BaseUrlBaidu") // 非默认域名，并取别名为BaseUrlBaidu ---（非默认域名 示例）。
    public static String baidu = "https://www.baidu.com/";

    // 用户协议地址(无用)
    public final static String Req_UserAgreementAddress_CN = "https://api.meilancycling.com/html/cn/cntreaty.html";
    public final static String Req_UserAgreementAddress_EN = "https://api.meilancycling.com/html/en/entreaty.html";
    // 流量卡的 baseUrl
    public final static String Base_flowCardIp = "http://99.liumall.co/inter/";

    //加油站列表
    public final static String CheZhuBangControll = "/benefit/web/CheZhuBangController/queryGasStationInfoList";
    //获取油站详情
    public final static String queryPriceByPhone = "/benefit/web/CheZhuBangController/queryPriceByPhone";


    //我邀请的收益
    public final static String inviteIncome = "/route/benefit/api/userInfo/getWallet";
    //获取我要邀请的人数
    public final static String getUserLevel = "/benefit/api/userInfo/childAgentUserList";

    //加油金额
    public final static String refuelMoney = "/benefit/web/OrderController//selectGasStation";

    public final static String  OrderZhuBangquery ="benefit/web/CheZhuBangController/queryOrderInfo";

    // 验证是否有加油余额
    public final static String isRefuelbalance = "route/benefit/web/OrderController/selectOrder";
    //更新加油订单
    public final static String updateRefuelOrder = "/route/benefit/web/OrderController/updateOrder";
    //    记录加油用户信息
    public final static String recordUserInfo = "/route/benefit/tGasStationReconciliation";

    // 根据加油金额获取界面金额信息
    public final static String queryPriceByGas = "benefit/web/OrderController/queryPriceByGas";



}
