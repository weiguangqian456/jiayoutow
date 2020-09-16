package com.edawtech.jiayou.net.http;

import android.text.TextUtils;
import android.util.Base64;

import com.edawtech.jiayou.config.bean.RefuelFiltrate;
import com.edawtech.jiayou.utils.tool.IntentJumpUtils;
import com.edawtech.jiayou.utils.tool.JsonHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求参数。
 */
public class HttpRequest {
    // 全局请求头
    public static HashMap<String, Object> Headers(String contentType) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("accept", "*/*");// 数据接受类型
        if (!contentType.equals("") && contentType.length() != 0) {
            params.put("Content-Type", contentType);// 数据类型: X-WWW-FORM-URLENCODED
        }
        params.put("token", "IntentJumpUtils.getUsrToken()");// 公共参数，用户验证。
        return params;
    }

    //加油站列表
    public static HashMap<String, Object> CheZhuBang(String phone, String searchType, double userAddressLongitude,
                                                     double userAddressLatitude, String searchContent, String pageSize,
                                                     String pageNum, String version, String uid) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("phone", phone);
        params.put("searchType", searchType);
        params.put("userAddressLongitude", userAddressLongitude);
        params.put("userAddressLatitude", userAddressLatitude);
        params.put("searchContent", searchContent);
        params.put("pageSize", pageSize);
        params.put("pageNum", pageNum);
        params.put("version", version);
        params.put("uid", uid);
        params.put("appId", "dudu");
        return params;
    }

    public static String GetsearchContent(String mFuelOilType, String mFiltrate,List<RefuelFiltrate> mBrandList){
        String searchContent = "@" + mFuelOilType + "@" + mFiltrate + "@" + TextUtils.join("#",mBrandList);
        String base64Str = Base64.encodeToString(searchContent.getBytes(), Base64.NO_WRAP);
        return base64Str;
    }

    public static  String queryPrice(String gasId ,String phone ){
        HashMap<String, Object> params = new HashMap<>();
        params.put("gasId",gasId);
        params.put("phone", phone);
        return JsonHelper.newtoJson(params);
    }


}
