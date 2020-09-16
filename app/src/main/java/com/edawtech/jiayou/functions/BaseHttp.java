package com.edawtech.jiayou.functions;


import com.edawtech.jiayou.utils.tool.VsMd5;

/**
 * Created by Jiangxuewu on 2015/2/3.
 * <p>
 * 服务器请求处理基类 2处联网请求走这里，第一：获取token 第二：拉取企业信息号
 * </p>
 */
public abstract class BaseHttp {

	private static final String TAG = BaseHttp.class.getSimpleName();


	private String getSign(String content) {
		return VsMd5.md5(content);
	}
}
