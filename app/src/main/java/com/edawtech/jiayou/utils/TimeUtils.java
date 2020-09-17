package com.edawtech.jiayou.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ClassName:      TimeUtils
 * <p>
 * Author:
 * <p>
 * CreateDate:      2020/9/14 14:22
 * <p>
 * Description:     时间转换
 */
public class TimeUtils {

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param
     * @return
     */
    public static String stampToDate(Long stamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        Date date = new Date(stamp);
        return simpleDateFormat.format(date);
    }

}
