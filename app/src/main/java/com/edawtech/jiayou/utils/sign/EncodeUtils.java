package com.edawtech.jiayou.utils.sign;

import android.text.TextUtils;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 各种格式的编码加码工具类.
 * <p>
 * 集成Commons-Codec,Commons-Lang及JDK提供的编解码方法.
 */
public class EncodeUtils {

    private static final String DEFAULT_URL_ENCODING = "UTF-8";

    /**
     * URL 解码, Encode默认为UTF-8.
     */
    public static String urlDecode(String input) {
        return urlDecode(input, DEFAULT_URL_ENCODING);
    }

    /**
     * URL 解码.
     */
    public static String urlDecode(String input, String encoding) {
        try {
            return URLDecoder.decode(input, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Unsupported Encoding Exception", e);
        }
    }

    /**
     * 转码iso-8859-1
     */
    public static String strEncode(String str) {
        try {
            str = new String(str.getBytes(DEFAULT_URL_ENCODING), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 转码utf-8
     */
    public static String strDecode(String str) {
        try {
            str = new String(str.getBytes("ISO-8859-1"), DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 对字符串进行MD5加密
     *
     * @param plainText String
     * @return String
     */
    public static String MD5(String plainText) {
        if (TextUtils.isEmpty(plainText)) {
            return "";
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i & 0xff));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 字符串 转换成为 16进制字符串(无需Unicode编码)：str >> 16_str。
     *
     * @param str
     * @return
     */
    public static String str2HexStr(String str) {
        //String strHex = "0123456789abcdef";
        String strHex = "0123456789ABCDEF";
        char[] chars = strHex.toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            // sb.append(' ');
        }
        //return sb.toString().trim();
        return sb.toString().trim().toUpperCase();// 转大写
    }

    /**
     * 16进制字符串 直接转换成为 字符串(无需Unicode解码)：16_str >> str。
     * JAVA中String类提供了转大写的方法toUpperCase()和转小写的方法toLowerCase()。
     *
     * @param hexStr
     * @return
     */
    public static String hexStr2Str(String hexStr) {
        if (!StringUtils.isEmpty(hexStr)) {
            try {
                hexStr = hexStr.toUpperCase();// 转大写
                //String str = "0123456789abcdef";
                String str = "0123456789ABCDEF";
                char[] hexs = hexStr.toCharArray();
                byte[] bytes = new byte[hexStr.length() / 2];
                int n;
                for (int i = 0; i < bytes.length; i++) {
                    n = str.indexOf(hexs[2 * i]) * 16;
                    n += str.indexOf(hexs[2 * i + 1]);
                    bytes[i] = (byte) (n & 0xff);
                }
                return new String(bytes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return hexStr;
    }


    public static String getSHA256StrJava(String str) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }
    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

//	// 在线测试。
//	public static void main(String[] args) {
//		System.out.println(MD5("123456"));
//
//		String hexStr = "50353331373430353039266970686f6e652036706c75732642313233343536373838353435263130302e3233266b67";
//		String str = hexStr2Str(hexStr);
//		System.out.println("hexStr2Str >>> " + str);
//	}

}
