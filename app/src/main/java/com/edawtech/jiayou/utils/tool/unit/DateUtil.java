package com.edawtech.jiayou.utils.tool.unit;

import android.annotation.SuppressLint;
import android.text.TextUtils;


import com.edawtech.jiayou.utils.tool.LogUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期时间转换工具类
 */
@SuppressLint("SimpleDateFormat")
public class DateUtil {

	public static String TIMES_HOUR_MIN_PATTERN = "HH:mm";
	public static String TIMES_PATTERN = "HH:mm:ss";
	public static String TIMESTAMP_DAY_HOUR_PATTERNR = "MM-dd HH:mm";
	public static String TIMESTAMP_DAY_HOUR_PATTERNR_2 = "MM/dd HH:mm";
	public static String MonthTimeFormat = "MM月dd日 HH:mm";

	public static String NOCHAR_PATTERNYMD = "yyyyMMdd";
	public static String DEFAULT_PATTERN = "yyyy-MM-dd";
	public static String DIR_PATTERN = "yyyy/MM/dd";
	public static String TIMESTAMP_PATTERNR = "yyyy-MM-dd\nHH:mm";
	public static String NOCHAR_PATTERN = "yyyyMMddHHmmss";
	public static String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static String TIMESTAMP_PATTERN_2 = "yyyy/MM/dd HH:mm:ss";

	/**
	 * 时间戳转换为星期
	 * @param //date
	 */
	public static String getWeek(long timeStamp) {
        int mydate = 0;
        String week = "";
        try {
        	Calendar cd = Calendar.getInstance();
        	cd.setTime(new Date(timeStamp));
        	mydate = cd.get(Calendar.DAY_OF_WEEK);
        } catch (Exception e) {
			e.printStackTrace();
		}
        // 获取指定日期转换成星期几
        if (mydate == 1) {
            week = "周日";
        } else if (mydate == 2) {
            week = "周一";
        } else if (mydate == 3) {
            week = "周二";
        } else if (mydate == 4) {
            week = "周三";
        } else if (mydate == 5) {
            week = "周四";
        } else if (mydate == 6) {
            week = "周五";
        } else if (mydate == 7) {
            week = "周六";
        }
        return week;
    }

	/**
	 * 日期转换为字符串
	 * @param date    日期
	 * @param format  日期格式
	 * @return 指定格式的日期字符串
	 */
	public static String formatDateByFormat(Date date, String format) {
		String result = "";
		if (date != null && !TextUtils.isEmpty(format)) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				result = sdf.format(date);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 转换为默认格式(yyyy-MM-dd)的日期字符串
	 * @param date
	 * @return
	 */
	public static String formatDefaultDate(Date date) {
		return formatDateByFormat(date, DEFAULT_PATTERN);
	}

	/**
	 * 转换为目录格式(yyyy/MM/dd/)的日期字符串
	 * @param date
	 * @return
	 */
	public static String formatDirDate(Date date) {
		return formatDateByFormat(date, DIR_PATTERN);
	}

	/**
	 * 转换为完整格式(yyyy-MM-dd HH:mm:ss)的日期字符串
	 * @param date
	 * @return
	 */
	public static String formatTimesTampDate(Date date) {
		return formatDateByFormat(date, TIMESTAMP_PATTERN);
	}

	/**
	 * 转换为时分秒格式(HH:mm:ss)的日期字符串
	 * @param date
	 * @return
	 */
	public static String formatTimesDate(Date date) {
		return formatDateByFormat(date, TIMES_PATTERN);
	}

	public static String getTimesDate() {
		return formatDateByFormat(new Date(), TIMES_PATTERN);
	}

	/**
	 * 转换为时分秒格式(HH:mm:ss)的日期字符串
	 * @param date
	 * @return
	 */
	public static String formatNoCharDate(Date date) {
		return formatDateByFormat(date, NOCHAR_PATTERN);
	}

	/**
	 * 日期格式字符串转换为日期对象
	 * @param strDate  日期格式字符串
	 * @param pattern  日期对象
	 * @return
	 */
	public static Date parseDate(String strDate, String pattern) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			Date nowDate = format.parse(strDate);
			return nowDate;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 字符串转换为默认格式(yyyy-MM-dd)日期对象
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static Date parseDefaultDate(String date) {
		return parseDate(date, DEFAULT_PATTERN);
	}

	/**
	 * 字符串转换为默认格式(yyyy-MM-dd HH:mm)日期对象
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static Date parseDefaultDate_2(String date) {
		return parseDate(date, "yyyy-MM-dd HH:mm");
	}

	/**
	 * 字符串转换为完整格式(yyyy-MM-dd HH:mm:ss)日期对象
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static Date parseTimesTampDate(String date) {
		return parseDate(date, TIMESTAMP_PATTERN);
	}

	/**
	 * 获得当前时间戳
	 * @return
	 */
	public static String getTimeStamp() {
		long t1 = getTimeStamp_L();
		return String.valueOf(t1);
	}

	/**
	 * java获取当前时间戳 精确到毫秒
	 * @return
	 */
	public static long getTimeStamp_L() {
		return System.currentTimeMillis();
	}

	/**
	 * 获得当前时间
	 * @return
	 */
	public static Date getCurrentDate() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}

	/**
	 * 获取年份
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	/**
	 * 获取月份
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取星期
	 * @param date
	 * @return
	 */
	public static int getWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		dayOfWeek = dayOfWeek - 1;
		if (dayOfWeek == 0) {
			dayOfWeek = 7;
		}
		return dayOfWeek;
	}

	/**
	 * 获取日期(多少号)
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取当前时间(小时)
	 * @param date
	 * @return
	 */
	public static int getHour(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取当前时间(分)
	 * @param date
	 * @return
	 */
	public static int getMinute(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MINUTE);
	}

	/**
	 * 获取当前时间(秒)
	 * @param date
	 * @return
	 */
	public static int getSecond(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.SECOND);
	}

	/**
	 * 获取当前毫秒
	 * @param date
	 * @return
	 */
	public static long getMillis(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	/**
	 * 日期增加
	 * @param date Date
	 * @param day  int
	 * @return Date
	 */
	public static Date addDate(Date date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
		return c.getTime();
	}

	/**
	 * 日期相减(返回天数)
	 * @param date   Date
	 * @param date1  Date
	 * @return int 相差的天数
	 */
	public static int diffDate(Date date, Date date1) {
		return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
	}

	/**
	 * 日期相减(返回秒值)
	 * @param date  Date
	 * @param date1 Date
	 * @return int
	 * @author
	 */
	public static Long diffDateTime(Date date, Date date1) {
		return (Long) ((getMillis(date) - getMillis(date1)) / 1000);
	}
	
	public static String getYearMD(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date d;
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			d = new Date();
		}
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(d);
	}
	/**
	 * 转换为时分秒格式(HH:mm)的日期字符串
	 * @param timeStamp
	 * @return
	 */
	public static String formatTimesDate_hour(String timeStamp) {
		SimpleDateFormat sdf=new SimpleDateFormat(TIMESTAMP_DAY_HOUR_PATTERNR);
		long lcc = Long.valueOf(timeStamp);
		// int i = Integer.parseInt(time);
		String sd = sdf.format(new Date(lcc * 1000L));   // 时间戳转换成时间
		return sd;
	}

	/**
	 * 转换为时分秒格式(HH:mm)的日期字符串
	 * @param timeStamp
	 * @return
	 */
	public static String formatTimesHour_minute(String timeStamp) {
		SimpleDateFormat sdf=new SimpleDateFormat(TIMES_HOUR_MIN_PATTERN);
		long lcc = Long.valueOf(timeStamp);
		// int i = Integer.parseInt(time);
		String sd = sdf.format(new Date(lcc * 1000L));   // 时间戳转换成时间
		return sd;
	}

	/**
	 * 转换为时分秒格式(yyyy-MM-dd HH:mm)的日期字符串
	 * @param timeStamp
	 * @return
	 */
	public static String formatTimesYMD_Hour_minute(String timeStamp) {
		SimpleDateFormat sdf=new SimpleDateFormat(TIMESTAMP_PATTERNR);
		long lcc = Long.valueOf(timeStamp);
		// int i = Integer.parseInt(time);
		String sd = sdf.format(new Date(lcc * 1000L));   // 时间戳转换成时间
		return sd;
	}

	/**
	 * Android/java 仿微信聊天列表时间显示规则：
	 *
	 * 今天 : HH:mm ，             例 8:28
	 * 昨天 : 昨天 HH:mm，         例 昨天 9:27
	 * 近7天: 星期X HH:mm ，       例 星期一 6:25
	 * 今年 : MM月dd日 HH:mm       例 3月2日 9:43
	 * 往年 : yyyy年MM月dd日 HH:mm 例 2018年6月9日 6:52
	 * ————————————————
	 */
	public static String getTimeString(Long timestamp) {
		String result = "";
		String[] weekNames = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
		String hourTimeFormat = "HH:mm";
		String monthTimeFormat = "MM月dd日 HH:mm";
		String yearTimeFormat = "yyyy年MM月dd日 HH:mm";
		try {
			// 获得当前时间
			Calendar todayCalendar = Calendar.getInstance();
			// 获得聊天时间
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(timestamp);
			// 时间判断。
			if (todayCalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {// 当年
				if (todayCalendar.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)) {// 当月
					// 时间差。
					int temp = todayCalendar.get(Calendar.DAY_OF_MONTH) - calendar.get(Calendar.DAY_OF_MONTH);
					switch (temp) {
						case 0:// 今天
							result = getTime(timestamp, hourTimeFormat);
							break;
						case 1:// 昨天
							result = "昨天 " + getTime(timestamp, hourTimeFormat);
							break;
						case 2:
						case 3:
						case 4:
						case 5:
						case 6:
							int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);// 星期几。
							result = weekNames[dayOfWeek - 1] + " " + getTime(timestamp, hourTimeFormat);
							break;
						default:
							result = getTime(timestamp, monthTimeFormat);// 当月时间。
							break;
					}
				} else {
					result = getTime(timestamp, monthTimeFormat);// 隔月时间。
				}
			} else {
				result = getTime(timestamp, yearTimeFormat); // 隔年时间。
			}
			return result;
		} catch (Exception e) {
			LogUtils.p("getTimeString", e.getMessage());
			return "";
		}
	}

	/**
	 * Android/java 仿微信会话列表时间显示规则：
	 *
	 * 今天 : HH:mm ，       例 8:28
	 * 昨天 : 昨天           例 昨天
	 * 近7天: 星期X          例 星期一
	 * 今年 : MM月dd日       例 3月2日
	 * 往年 : yyyy年MM月dd日 例 2018年6月9日
	 * ————————————————
	 */
	public static String getTimeString_MD(Long timestamp) {
		String result = "";
		String[] weekNames = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
		String hourTimeFormat = "HH:mm";
		String monthTimeFormat = "MM月dd日";
		String yearTimeFormat = "yyyy年MM月dd日";
		try {
			// 获得当前时间
			Calendar todayCalendar = Calendar.getInstance();
			// 获得聊天时间
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(timestamp);
			// 时间判断。
			if (todayCalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {// 当年
				if (todayCalendar.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)) {// 当月
					// 时间差。
					int temp = todayCalendar.get(Calendar.DAY_OF_MONTH) - calendar.get(Calendar.DAY_OF_MONTH);
					switch (temp) {
						case 0:// 今天
							long minute = getTimeMinuteDiff(timestamp, todayCalendar.getTimeInMillis());// 时间差。
							if (minute <= 5) {
								result = "刚刚";
							} else {
								result = getTime(timestamp, hourTimeFormat);
							}
							break;
						case 1:// 昨天
							result = "昨天";
							break;
						case 2:
						case 3:
						case 4:
						case 5:
						case 6:
							int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);// 星期几。
							result = weekNames[dayOfWeek - 1];
							break;
						default:
							result = getTime(timestamp, monthTimeFormat);// 当月时间。
							break;
					}
				} else {
					result = getTime(timestamp, monthTimeFormat);// 隔月时间。
				}
			} else {
				result = getTime(timestamp, yearTimeFormat); // 隔年时间。
			}
			return result;
		} catch (Exception e) {
			LogUtils.p("getTimeString", e.getMessage());
			return "";
		}
	}

	// 获取时间格式字符串：例 2018年6月9日 6:52
	public static String getTime(long time, String pattern) {
		Date date = new Date(time);
		return dateFormat(date, pattern);
	}

	// 获取时间格式字符串：例 2018年6月9日 6:52
	public static String dateFormat(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	/**
	 * Android/java 仿微信会话列表时间显示规则：
	 * 计算两个时间分钟差。
	 */
	public static long getTimeMinuteDiff(Long timestamp, Long timestampTwo) {
		try {
			// 时间差。
			long minute = (timestampTwo - timestamp) / 1000 / 60;
			return Math.abs(minute);
		} catch (Exception e) {
			LogUtils.p("getTimeString", e.getMessage());
			return 0;
		}
	}

	/**
	 * Android/java 仿微信会话列表时间显示规则：
	 * 计算两个时间秒钟差。--- 日期相减(返回秒值)
	 */
	public static long getTimeMillisDiff(Long timestamp, Long timestampTwo) {
		try {
			// 时间差。
			long millis = (timestampTwo - timestamp) / 1000;
			return Math.abs(millis);
		} catch (Exception e) {
			LogUtils.p("getTimeString", e.getMessage());
			return 0;
		}
	}

	/**
	 * 转换为时分秒格式(HH:mm:ss)的日期字符串
	 *
	 * @param timeStamp
	 * @return
	 */
	public static String formatTimesHour_minute_second(long timeStamp) {
		SimpleDateFormat sdf=new SimpleDateFormat(TIMES_PATTERN);
		//long lcc = Long.valueOf(timeStamp);
		// int i = Integer.parseInt(time);
		String sd = sdf.format(new Date(timeStamp * 1000L));   // 时间戳转换成时间
		return sd;
	}

	private static SimpleDateFormat msFormat = new SimpleDateFormat("mm:ss");
	/**
	 * 视频时长转换。
	 * @param duration
	 * @return
	 */
	public static String timeParse(long duration) {
		String time = "";
		try {
			if (duration > 1000L) {
				time = timeParseMinute(duration);
			} else {
				long minute = duration / 60000L;
				long seconds = duration % 60000L;
				long second = (long) Math.round((float)seconds / 1000.0F);
				if(minute < 10L) {
					time = time + "0";
				}

				time = time + minute + ":";
				if(second < 10L) {
					time = time + "0";
				}

				time = time + second;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}
	public static String timeParseMinute(long duration) {
		try {
			return msFormat.format(Long.valueOf(duration));
		} catch (Exception var3) {
			var3.printStackTrace();
			return "0:00";
		}
	}

	/**
	 * Java计算两时间相差日期,返回HH:mm:ss 小时分钟格式
	 */
	public static String timeSubtraction(long time1, long time2) throws ParseException {
		/**
		 *@description time2 是大的时间
		 *@param  [time1, time2]
		 *@return java.lang.String
		 */
		Long result = time2 - time1;//获取两时间相差的毫秒数
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		long ns = 1000;
		long hour = result % nd / nh;     //获取相差的小时数
		long min  = result % nd % nh / nm;  //获取相差的分钟数
		long sec  = result % nd % nh % nm / ns;  //获取相差的秒钟数
		long day  = result / nd;

		SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");//初始化Formatter的转换格式。
		long hMiles = hour * 3600000;  //小时数转换成毫秒
		long mMiles = min * 60000;     //分钟数转换成毫秒
		long sMiles = sec * 1000;      //秒钟数转换成毫秒
		long resulMiles = (hMiles + mMiles + sMiles);

		//下面这段很重要 ,计算之后设置时区,不然会差几小时
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
		String resultFormat = formatter.format(sec * 1000);
		//我这段是在一天内计算的 如果大于一天 就把下面的 day*24加到小时上就可以了
		return resultFormat;
	}

	/**
	 * Java计算两时间相差日期,返回HH:mm:ss 天小时分钟缩减格式
	 * @description time2 是大的时间
	 * @param time1, time2
	 * @return java.lang.String
	 */
	public static String timeSubtraction_S(long time1, long time2) throws ParseException {
		Long result = time2 - time1;// 获取两时间相差的毫秒数
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		long ns = 1000;
		long day  = result / nd;// 获取相差的天数
		long hour = result % nd / nh;     //获取相差的小时数
		long min  = result % nd % nh / nm;  //获取相差的分钟数
		long sec  = result % nd % nh % nm / ns;  //获取相差的秒钟数

		StringBuilder sBuffer = new StringBuilder();
		if (day > 0) {
			hour = hour + day * 24;
		}
		if (hour > 0) {
			sBuffer.append(hour).append("h ");
		}
		if (min > 0) {
			sBuffer.append(min).append("m");
		} else {
			if (sec > 0) {
				sBuffer.append(sec).append("s");
			}
		}
		return sBuffer.toString();
	}

	/**
	 * 日期格式字符串转换成时间戳 精确到毫秒
	 * @param date_str 字符串日期
	 * @param format 如：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String date2TimeStamp(String date_str, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return String.valueOf(sdf.parse(date_str).getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 日期格式字符串转换成时间戳 精确到毫秒
	 * @param date_str 字符串日期
	 * @param format 如：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static long date2TimeStamp_L(String date_str, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(date_str).getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

}
