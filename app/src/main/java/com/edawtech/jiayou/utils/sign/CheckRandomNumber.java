package com.edawtech.jiayou.utils.sign;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

public class CheckRandomNumber {

	/**
	 * java生成随机数字和字母组合
	 * @param //length[生成随机数的长度]
	 * @return
	 */
    public static String getCharAndNumr(int length) {
        String val = "";
        Random random = new Random();
        // 参数length，表示生成几位随机数  
        for(int i = 0; i < length; i++) {  
        	// 输出字母还是数字
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 输出字符串 
            if ("char".equalsIgnoreCase(charOrNum)) {  
                // 输出是大写字母还是小写字母  
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;  
                val += (char)(random.nextInt(26) + temp);  
            } else if ("num".equalsIgnoreCase(charOrNum)) { // 数字 
                val += String.valueOf(random.nextInt(10));
            }  
        }  
        return val;  
    }
    
    /**
	 * java生成在[min,max]之间的随机整数
	 * @return
	 */
    public static int getRandomNumber(int min, int max) {
    	Random rand = new Random();
    	// 取值可能性的数量计算是: 最大取值-最小取值+1 ，所以，有最终公式如下：
    	int randNumber = rand.nextInt(max - min + 1) + min; // randNumber将被赋值为一个 MIN 和 MAX 范围内的随机数  
		return randNumber;
    }
    
    /**
	 * HashMap的两种排序方式
	 * 输出组装后的字符串。
	 * @return
	 */
	public static String getHashMapSort(HashMap map) {
    	String mapStr = "";
    	String charStr = "&";
    	if (map != null) {
    		try {
    			List<Entry<String, Object>> list = new ArrayList<Entry<String, Object>>(map.entrySet());
    			Collections.sort(list, new Comparator<Entry<String, Object>>() {
    				// 升序排序
    				public int compare(Entry<String, Object> o1, Entry<String, Object> o2) {
    					// return o1.getValue().compareTo(o2.getValue());// 根据value排序
    					return o1.getKey().compareTo(o2.getKey());// 根据key排序
    				}
    			});
    			// 输出结果。
    			for(Entry<String, Object> mapping : list){
    				// System.out.println(mapping.getKey()+":"+mapping.getValue()); 
    				mapStr += mapping.getKey() + "=" + mapping.getValue() + charStr;
    			}
    			// 截取字符串。
    			mapStr = mapStr.substring(0, mapStr.length() - 1);
			} catch (Exception e) {
			}
		}
		return mapStr;
    }
    
    /**
	 * java获取字符串MD5加密后的字符串中间16位
	 * @return
	 */
    public static String getEncryptionMD5(String str) {
    	String value = "";
    	try {
    		String strMD5 = EncodeUtils.MD5(str);
    		if (strMD5.length() > 24) {
    			value = strMD5.substring(8, 24);
    		}
		} catch (Exception e) {
		}
		return value;
    }
    
    /**
	 * HashMap的两种排序方式，之后加密整个数组。
	 * 输出组装后的新数组。
	 * @return
	 */
	public static HashMap getHashMap(HashMap map) {
    	HashMap<String, Object> hashMap = new HashMap<String, Object>();
    	String iv ="5efd3f6060e20330";
    	if (map != null) {
    		try {
    			List<Entry<String, Object>> list = new ArrayList<Entry<String, Object>>(map.entrySet());
    			Collections.sort(list, new Comparator<Entry<String, Object>>() {
    				// 升序排序
    				public int compare(Entry<String, Object> o1, Entry<String, Object> o2) {
    					// return o1.getValue().compareTo(o2.getValue());// 根据value排序
    					return o1.getKey().compareTo(o2.getKey());// 根据key排序
    				}
    			});
    			// 输出结果。
    			for(Entry<String, Object> mapping : list){
    				// System.out.println(mapping.getKey()+":"+mapping.getValue()); 
    				hashMap.put(mapping.getKey(), AesEncryptionUtil.encrypt(mapping.getValue().toString(), 
    						getEncryptionMD5(mapping.getKey()), iv));
    			}
			} catch (Exception e) {
			}
		}
		return hashMap;
    }
    
    // 加密例子：
	public HashMap<String, Object> getAES() {
    	
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("appType",              "2");
		hashMap.put("ckeckCode",            "df05cfc6a65604f13b1ef576b825b93a");
		hashMap.put("interface",            "Test/test");
		hashMap.put("name",                 "fff");
		hashMap.put("nid",                  "123");
		hashMap.put("public_rcode",         "SDAXMB");
		hashMap.put("public_time",          "1482311286054");
		hashMap.put("title",                "xph");
		hashMap.put("uid",                  "21");
		hashMap.put("verCode",              "JGXVCLILZL");
		hashMap.put("version",              "1.0.6");
		hashMap.put("public_checkcode",     EncodeUtils.MD5(CheckRandomNumber.getHashMapSort(hashMap)));
		
//		String iv ="5efd3f6060e20330";
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("appType",            AesEncryptionUtil.encrypt(hashMap.get("appType").toString(),           CheckRandomNumber.getEncryptionMD5("appType"), iv));
//		map.put("ckeckCode",          AesEncryptionUtil.encrypt(hashMap.get("ckeckCode").toString(),         CheckRandomNumber.getEncryptionMD5("ckeckCode"), iv));
//		map.put("interface",          AesEncryptionUtil.encrypt(hashMap.get("interface").toString(),         CheckRandomNumber.getEncryptionMD5("interface"), iv));
//		map.put("name",               AesEncryptionUtil.encrypt(hashMap.get("name").toString(),              CheckRandomNumber.getEncryptionMD5("name"), iv));
//		map.put("nid",                AesEncryptionUtil.encrypt(hashMap.get("nid").toString(),               CheckRandomNumber.getEncryptionMD5("nid"), iv));
//		map.put("public_checkcode",   AesEncryptionUtil.encrypt(hashMap.get("public_checkcode").toString(),  CheckRandomNumber.getEncryptionMD5("public_checkcode"), iv));
//		map.put("public_rcode",       AesEncryptionUtil.encrypt(hashMap.get("public_rcode").toString(),      CheckRandomNumber.getEncryptionMD5("public_rcode"), iv));
//		map.put("public_time",        AesEncryptionUtil.encrypt(hashMap.get("public_time").toString(),       CheckRandomNumber.getEncryptionMD5("public_time"), iv));
//		map.put("title",              AesEncryptionUtil.encrypt(hashMap.get("title").toString(),             CheckRandomNumber.getEncryptionMD5("title"), iv));
//		map.put("uid",                AesEncryptionUtil.encrypt(hashMap.get("uid").toString(),               CheckRandomNumber.getEncryptionMD5("uid"), iv));
//		map.put("verCode",            AesEncryptionUtil.encrypt(hashMap.get("verCode").toString(),           CheckRandomNumber.getEncryptionMD5("verCode"), iv));
//		map.put("version",            AesEncryptionUtil.encrypt(hashMap.get("version").toString(),           CheckRandomNumber.getEncryptionMD5("version"), iv));
		
		return getHashMap(hashMap);
	}
    
}
