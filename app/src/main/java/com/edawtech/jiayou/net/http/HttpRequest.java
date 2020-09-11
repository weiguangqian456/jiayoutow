package com.edawtech.jiayou.net.http;

import com.edawtech.jiayou.utils.tool.IntentJumpUtils;

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


}
