package com.edawtech.jiayou.utils.tool;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * Gson解析
 */
public class JsonHelper {

	public static Gson getGson() {
		return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	}

	/**
	 * json字符串转为对应实体类的数据。
	 */
	public static <T> T fromJson(String json, Class<T> clazz) {
		try {
			if (!TextUtils.isEmpty(json)) {
				if (json.endsWith(";")) {
					json = json.substring(0, json.length() - 1);
				}
				Gson gson = new Gson();
				T t = gson.fromJson(json, clazz);
				clazz = null;
				gson = null;
				return t;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * json字符串转为对应格式的数据。
	 */
	public static <T> T fromJson(String json, TypeToken<T> typeToken) {
		try {
			if (!TextUtils.isEmpty(json)) {
				if (json.endsWith(";")) {
					json = json.substring(0, json.length() - 1);
				}
				Gson gson = getGson();
				Type type = typeToken.getType();
				T t = gson.fromJson(json, type);
				typeToken = null;
				type = null;
				gson = null;
				return t;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 转为json字符串
	public static String toJson(Object obj) {
		Gson gson = getGson();
		String result = gson.toJson(obj);
		gson = null;
		return result;
	}
	// 转为json字符串
	public static String newtoJson(Object obj) {
		Gson gson = new Gson();
		String result = gson.toJson(obj);
		gson = null;
		return result;
	}


	/**
	 * 解析纯Json数组
	 * @author I321533
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> jsonToList(String json, Class<T[]> clazz)
	{
		Gson gson = new Gson();
		T[] array = gson.fromJson(json, clazz);
		return Arrays.asList(array);
	}
}
