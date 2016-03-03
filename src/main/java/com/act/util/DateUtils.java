package com.act.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期时间工具类
 * 
 * @author
 * 
 * @date 2015年1月20日
 */
public class DateUtils {

	private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);
	private static final SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	private static final SimpleDateFormat dateFormat_Eight = new SimpleDateFormat("yyyyMMdd");
	private static final SimpleDateFormat timeFormat_Six = new SimpleDateFormat("HHmmss");
	private static final SimpleDateFormat allFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	private static final SimpleDateFormat datetimeTwoFormat = new SimpleDateFormat("yyyyMMddHHmmss");

	/** 毫秒级别 */
	public static long DAY_BY_MILLI = 86400000; // 1DAY
	public static long HOUR_BY_MILLI = 3600000; // 1H
	public static long MINUTE_BY_MILLI = 60000; // 1MIN
	public static long SECOND_BY_MILLI = 1000; // 1S

	/** 秒级别 */
	public static long DAYTIME = 86400; // 1DAY
	public static long HOURTIME = 3600; // 1H
	public static long MINUTETIME = 60; // 1MIN
	public static long SECONDTIME = 1; // 1S

	public static void main(String[] s){
		Calendar ca = Calendar.getInstance();
//		ca.add(Calendar.DAY_OF_MONTH, 7);
		
//		ca.set(Calendar.DAY_OF_WEEK, 6);
		ca.add(Calendar.MONTH,1);
//		System.out.println(ca.get(Calendar.DAY_OF_MONTH));
		ca.set(Calendar.DAY_OF_MONTH, 11);
		ca.set(Calendar.HOUR_OF_DAY, 23);
		ca.set(Calendar.MINUTE, 59);
		ca.set(Calendar.SECOND, 59);
		System.out.println(new Timestamp(ca.getTimeInMillis()));
//		System.out.println(daysBetweenTwoDate(ca.getTime(),new Date()));
		
	}
	/**
	 * 获得当前日期时间
	 * <p>
	 * 日期时间格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String currentDatetime() {
		return datetimeFormat.format(now());
	}

	/**
	 * 获得当前日期时间
	 * <p>
	 * 日期时间格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String currentDatetimeAll() {
		return allFormat.format(now());
	}

	/**
	 * 格式化日期时间
	 * <p>
	 * 日期时间格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String formatDatetime(Date date) {
		return datetimeFormat.format(date);
	}

	/**
	 * 格式化日期时间
	 * <p>
	 * 日期时间格式yyyyMMddHHmmssSSS
	 * 
	 * @return
	 */
	public static String formatDatetimeAll(Date date) {
		return allFormat.format(date);
	}

	/**
	 * 格式化日期时间
	 * 
	 * @param date
	 * @param pattern
	 *            格式化模式，详见{@link SimpleDateFormat}构造器
	 *            <code>SimpleDateFormat(String pattern)</code>
	 * @return
	 */
	public static String formatDatetime(Date date, String pattern) {
		SimpleDateFormat customFormat = (SimpleDateFormat) datetimeFormat.clone();
		customFormat.applyPattern(pattern);
		return customFormat.format(date);
	}

	/**
	 * 解析时间字符串为时间
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date parseDateTime(String date, String pattern) {
		try {
			return new SimpleDateFormat(pattern).parse(date);
		} catch (ParseException e) {
			logger.error("", e);
			return null;
		}
	}

	/**
	 * 格式化datetime字符串
	 * 
	 * @param dateStr
	 * @param srcPattern
	 * @param targetPattern
	 * @return
	 */
	public static String format(String dateStr, String srcPattern, String targetPattern) {
		Date date = parseDateTime(dateStr, srcPattern);
		return formatDatetime(date, targetPattern);
	}

	/**
	 * 检查时间字符串是否符合给出的格式
	 * 
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static boolean match(String dateStr, String pattern) {
		try {
			new SimpleDateFormat(pattern).parse(dateStr);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * 获得当前日期
	 * <p>
	 * 日期格式yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String currentDate() {
		return dateFormat.format(now());
	}

	/**
	 * 获取当前日期 日期格式yyyyMMdd
	 */
	public static String currentDateyyMMdd() {
		return dateFormat_Eight.format(now());
	}

	/**
	 * 获得当前日期
	 * <p>
	 * 日期格式yyyyMMdd
	 * 
	 * @return
	 */
	public static String currentEightDate() {
		return dateFormat_Eight.format(now());
	}

	/**
	 * 将给定日期类型转换为8位字符串
	 * 
	 * @param date
	 *            <p>
	 *            日期格式yyyyMMdd
	 * 
	 * @return
	 */
	public static String formatDate2EightDate(Date date) {
		return dateFormat_Eight.format(date);
	}

	/**
	 * 格式化日期
	 * <p>
	 * 日期格式yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String formatDate(Date date) {
		return dateFormat.format(date);
	}

	/**
	 * 获得当前时间
	 * <p>
	 * 时间格式HH:mm:ss
	 * 
	 * @return
	 */
	public static String currentTime() {
		return timeFormat.format(now());
	}

	/**
	 * 获得当前时间
	 * <p>
	 * 时间格式HHmmss
	 * 
	 * @return
	 */
	public static String currentSixTime() {
		return timeFormat_Six.format(now());
	}

	/**
	 * 格式化时间
	 * <p>
	 * 时间格式HH:mm:ss
	 * 
	 * @return
	 */
	public static String formatTime(Date date) {
		return timeFormat.format(date);
	}

	/**
	 * 获得当前时间的<code>java.util.Date</code>对象
	 * 
	 * @return
	 */
	public static Date now() {
		return new Date();
	}

	public static Calendar calendar() {
		Calendar cal = GregorianCalendar.getInstance(Locale.CHINESE);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		return cal;
	}

	/**
	 * 获得当前时间的毫秒数
	 * <p>
	 * 详见{@link System#currentTimeMillis()}
	 * 
	 * @return
	 */
	public static long millis() {
		return System.currentTimeMillis();
	}

	/**
	 * 
	 * 获得当前Chinese月份
	 * 
	 * @return
	 */
	public static int month() {
		return calendar().get(Calendar.MONTH) + 1;
	}

	/**
	 * 获得月份中的第几天
	 * 
	 * @return
	 */
	public static int dayOfMonth() {
		return calendar().get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 今天是星期的第几天
	 * 
	 * @return
	 */
	public static int dayOfWeek() {
		return calendar().get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 今天是年中的第几天
	 * 
	 * @return
	 */
	public static int dayOfYear() {
		return calendar().get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 判断原日期是否在目标日期之前
	 * 
	 * @param src
	 * @param dst
	 * @return
	 */
	public static boolean isBefore(Date src, Date dst) {
		return src.before(dst);
	}

	/**
	 * 判断原日期是否在目标日期之后
	 * 
	 * @param src
	 * @param dst
	 * @return
	 */
	public static boolean isAfter(Date src, Date dst) {
		return src.after(dst);
	}

	/**
	 * 判断两日期是否相同
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isEqual(Date date1, Date date2) {
		return date1.compareTo(date2) == 0;
	}

	/**
	 * 判断某个日期是否在某个日期范围
	 * 
	 * @param beginDate
	 *            日期范围开始
	 * @param endDate
	 *            日期范围结束
	 * @param src
	 *            需要判断的日期
	 * @return
	 */
	public static boolean between(Date beginDate, Date endDate, Date src) {
		return beginDate.before(src) && endDate.after(src);
	}

	/**
	 * 获得当前月的最后一天
	 * <p>
	 * HH:mm:ss为0，毫秒为999
	 * 
	 * @return
	 */
	public static Date lastDayOfMonth() {
		Calendar cal = calendar();
		cal.set(Calendar.DAY_OF_MONTH, 0); // M月置零
		cal.set(Calendar.HOUR_OF_DAY, 0);// H置零
		cal.set(Calendar.MINUTE, 0);// m置零
		cal.set(Calendar.SECOND, 0);// s置零
		cal.set(Calendar.MILLISECOND, 0);// S置零
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);// 月份+1
		cal.set(Calendar.MILLISECOND, -1);// 毫秒-1
		return cal.getTime();
	}

	/**
	 * 获得当前月的第一天
	 * <p>
	 * HH:mm:ss SS为零
	 * 
	 * @return
	 */
	public static Date firstDayOfMonth() {
		Calendar cal = calendar();
		cal.set(Calendar.DAY_OF_MONTH, 1); // M月置1
		cal.set(Calendar.HOUR_OF_DAY, 0);// H置零
		cal.set(Calendar.MINUTE, 0);// m置零
		cal.set(Calendar.SECOND, 0);// s置零
		cal.set(Calendar.MILLISECOND, 0);// S置零
		return cal.getTime();
	}

	private static Date weekDay(int week) {
		Calendar cal = calendar();
		cal.set(Calendar.DAY_OF_WEEK, week);
		return cal.getTime();
	}

	/**
	 * 获得周五日期
	 * <p>
	 * 注：日历工厂方法{@link #calendar()}设置类每个星期的第一天为Monday，US等每星期第一天为sunday
	 * 
	 * @return
	 */
	public static Date friday() {
		return weekDay(Calendar.FRIDAY);
	}

	/**
	 * 获得周六日期
	 * <p>
	 * 注：日历工厂方法{@link #calendar()}设置类每个星期的第一天为Monday，US等每星期第一天为sunday
	 * 
	 * @return
	 */
	public static Date saturday() {
		return weekDay(Calendar.SATURDAY);
	}

	/**
	 * 获得周日日期
	 * <p>
	 * 注：日历工厂方法{@link #calendar()}设置类每个星期的第一天为Monday，US等每星期第一天为sunday
	 * 
	 * @return
	 */
	public static Date sunday() {
		return weekDay(Calendar.SUNDAY);
	}

	/**
	 * 将字符串日期时间转换成java.util.Date类型
	 * <p>
	 * 日期时间格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @param datetime
	 * @return
	 */
	public static Date parseDatetime(String datetime) throws ParseException {
		return datetimeFormat.parse(datetime);
	}

	/**
	 * 将字符串日期时间转换成java.util.Date类型
	 * <p>
	 * 日期时间格式yyyyMMddHHmmss
	 * 
	 * @param datetime
	 * @return
	 */
	public static Date parseDatetimeNew(String datetime) throws ParseException {
		return datetimeTwoFormat.parse(datetime);
	}

	/**
	 * 将字符串日期时间转换成java.util.Date类型
	 * <p>
	 * 日期时间格式HHmmss
	 * 
	 * @param datetime
	 * @return
	 */
	public static Date parseDatetimeHHmmss(String datetime) throws ParseException {
		return timeFormat_Six.parse(datetime);
	}

	/**
	 * 将字符串日期转换成java.util.Date类型
	 * <p>
	 * 日期时间格式yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String date) throws ParseException {
		return dateFormat.parse(date);
	}

	/**
	 * 将字符串日期转换成java.util.Date类型
	 * <p>
	 * 时间格式 HH:mm:ss
	 * 
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static Date parseTime(String time) throws ParseException {
		return timeFormat.parse(time);
	}

	/**
	 * 根据自定义pattern将字符串日期转换成java.util.Date类型
	 * 
	 * @param datetime
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDatetime(String datetime, String pattern) throws ParseException {
		SimpleDateFormat format = (SimpleDateFormat) datetimeFormat.clone();
		format.applyPattern(pattern);
		return format.parse(datetime);
	}

	/**
	 * —— Date类型转换为Timestamp类型
	 * 
	 * @param date
	 * @return
	 * 
	 */
	public static Timestamp date2timestamp(Date date) {
		if (date == null)
			return null;
		return new Timestamp(date.getTime());
	}

	/**
	 * 把String 日期转换成long型日期；单位s
	 * 
	 * @param date
	 *            String 型日期；
	 * @param format
	 *            日期格式；
	 * @return
	 */
	public static long stringToLong(String date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date dt2 = null;
		long lTime = 0;
		try {
			dt2 = sdf.parse(date);
			// 继续转换得到秒数的long型
			lTime = dt2.getTime() / 1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return lTime;
	}

	/**
	 * 经long类型转换的String类型时间转回到String类型时间
	 * 
	 * @param time
	 * @param format
	 * @return
	 */
	public static String longToString(long time, String format) {
		String result = null;
		Date dateOld = new Date(time * 1000); // 根据long类型的毫秒数生命一个date类型的时间
		result = new SimpleDateFormat(format).format(dateOld);
		return result;
	}

	/**
	 * 计算两个日期时间之差
	 * 
	 * @param beforedate
	 * @param lastdate
	 * @return
	 * @throws ParseException
	 */
	public static String countTwoDatetime(String beforedate, String lastdate) throws ParseException {
		return countTwoDatetime(parseDatetimeNew(beforedate).getTime(), parseDatetimeNew(lastdate).getTime());
	}

	/**
	 * 计算两个日期时间之差 可以算 进行起始时间>结束时间 如果结束时间比起始时间差即过24小时是凌晨，则结束时间+24计算
	 * 
	 * @param beforedate
	 * @param lastdate
	 * @return
	 * @throws ParseException
	 */
	public static String countTwoDatetimeHHmmss(String beforedate, String lastdate) throws ParseException {
		int before = Integer.valueOf(beforedate.substring(0, 2));// HH
		int last = Integer.valueOf(lastdate.substring(0, 2));// HH
		int beforeMin = Integer.valueOf(beforedate.substring(2, 4));// mm
		int lastMin = Integer.valueOf(lastdate.substring(2, 4));// mm
		int beforeSec = Integer.valueOf(beforedate.substring(4, 6));// ss
		int lastSec = Integer.valueOf(lastdate.substring(4, 6));// ss

		String lastFour = lastdate.substring(2); // mmss
		if (last <= before) { // 如果结束时间比起始时间小，也就是结束时间超过凌晨
			if (lastMin <= beforeMin) {
				if (lastSec <= beforeSec) {
					last += 24;
					lastdate = String.valueOf(last) + lastFour;
				}
			}
		}
		return countTwoDatetime(parseDatetimeHHmmss(beforedate).getTime(), parseDatetimeHHmmss(lastdate).getTime());
	}

	/**
	 * 计算两个时间段差总时长 , 相差（多少时，分，秒）（单位分钟） ！！结束时间 不能小于起始时间
	 * 
	 * @param beforedate
	 *            起始时间
	 * @param lastdate
	 *            结束时间
	 * @return map
	 */
	public static Map<String, Integer> countTwoDatetimeCountMin(String beforedate, String lastdate) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		int countTimeInt = 0;
		int countHour = 0;
		int countMin = 0;
		int countSec = 0;
		try {
			String countTimeStr = DateUtils.countTwoDatetime(beforedate, lastdate);
			countHour = Integer.valueOf(countTimeStr.substring(0, 2)); // 时
			countMin = Integer.valueOf(countTimeStr.substring(2, 4)); // 分
			countSec = Integer.valueOf(countTimeStr.substring(4, 6)); // 秒
			if (countSec != 0) {
				countTimeInt = countHour * 60 + countMin + 60 / countSec; // 总时长
																			// min
			} else {
				countTimeInt = countHour * 60 + countMin; // 总时长 min
			}

			// 总时间
		} catch (ParseException e) {
			e.printStackTrace();
		}
		map.put("countHour", countHour);
		map.put("countMin", countMin);
		map.put("countSec", countSec);
		map.put("countTimeInt", countTimeInt);
		return map;
	}

	/**
	 * 获取相隔HHmmss时间段的具体 总时长 ， 总小时， 总分钟， 总秒的时间map集合
	 * 
	 * @param currTime
	 * @return
	 */
	public static Map<String, Integer> currTimeCountMin(String currTime) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		int countHour = 0;
		int countMin = 0;
		int countSec = 0;
		int countTimeInt = 0;
		try {
			countHour = Integer.valueOf(currTime.substring(0, 2)); // 时
			countMin = Integer.valueOf(currTime.substring(2, 4)); // 分
			countSec = Integer.valueOf(currTime.substring(4, 6)); // 秒
			if (countSec != 0) {
				countTimeInt = countHour * 60 + countMin + 60 / countSec; // 总时长
																			// min
			} else {
				countTimeInt = countHour * 60 + countMin; // 总时长 min
			}
			// 总时间
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("countHour", countHour);
		map.put("countMin", countMin);
		map.put("countSec", countSec);
		map.put("countTimeInt", countTimeInt);
		return map;
	}

	/**
	 * 计算两个日期时间之差
	 * 
	 * @param beforedate
	 * @param lastdate
	 * @return
	 */
	public static String countTwoDatetime(Date beforedate, Date lastdate) {
		return countTwoDatetime(beforedate.getTime(), lastdate.getTime());
	}

	/**
	 * 计算两个日期时间之差
	 * 
	 * @param beforedate
	 *            String
	 * @param lastdate
	 *            long
	 * @return
	 * @throws ParseException
	 */
	public static String countTwoDatetime(String beforedate, long lastdate) throws ParseException {
		return countTwoDatetime(parseDatetimeNew(beforedate).getTime(), lastdate);
	}

	/**
	 * 计算两个时间之差
	 * 
	 * @param beforedate
	 * @param lastdate
	 * @return
	 */
	public static String countTwoDatetime(long beforedate, long lastdate) {
		long count = lastdate - beforedate;
		long hour = count / (HOUR_BY_MILLI);
		long minute = (count % (DAY_BY_MILLI)) % (HOUR_BY_MILLI) / (MINUTE_BY_MILLI);
		long second = ((count % (DAY_BY_MILLI)) % (HOUR_BY_MILLI)) % (MINUTE_BY_MILLI) / SECOND_BY_MILLI;
		String strhour = "00";
		String strminute = "00";
		String strsecond = "00";
		if (hour < 10) {
			strhour = "0" + String.valueOf(hour);
		} else {
			strhour = String.valueOf(hour);
		}
		if (minute < 10) {
			strminute = "0" + String.valueOf(minute);
		} else {
			strminute = String.valueOf(minute);
		}
		if (second < 10) {
			strsecond = "0" + String.valueOf(second);
		} else {
			strsecond = String.valueOf(second);
		}
		return strhour + strminute + strsecond;
	}

	/**
	 * 字符串的日期相差计算 （精确到天） 结束时间<开始时间 则为-天
	 * 
	 * @param start
	 *            起始时间
	 * @param end
	 *            结束时间
	 * @return
	 * @throws ParseException
	 */
	public static int daysBetweenYYYYMMDD(String start, String end) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(start));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(end));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}
	
	/**
	 * 两个日期时间差
	 * @param start
	 * @param end
	 * @return
	 */
	public static int daysBetweenTwoDate(Date start,Date end){
		long time1 = start.getTime();
		long time2 = end.getTime();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}
	

	/**
	 * 字符串的时间相差计算 （精确到分） start>end 则为 - 分钟
	 * 
	 * @param start
	 *            起始时间
	 * @param end
	 *            结束时间
	 * @return
	 * @throws ParseException
	 */
	public static int daysBetweenHHmmss(String start, String end) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(start));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(end));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 60);
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 字符串日期+时间相差计算 （精确到分） end<start 则为-分钟
	 * 
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */
	public static int daysBetweenYYYYMMDDHHmmss(String start, String end) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(start));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(end));
		long time2 = cal.getTimeInMillis();
		long between_days = time2 - time1;
		if (between_days == 0) {
			if ((time2 - time1) != 0) {
				if (time2 > time1) {
					return 1;
				} else {
					return -1;
				}
				// return Integer.parseInt(String.valueOf((time2-time1)/1000));
			} else {
				return 0;
			}
		}
		if ((time2 - time1) % (1000 * 60) != 0) {
			if ((time2 - time1) > 0) {
				between_days = ((time2 - time1) / (1000 * 60)) + 1;
			} else {
				between_days = ((time2 - time1) / (1000 * 60)) - 1;
			}

		} else {
			between_days = ((time2 - time1) / (1000 * 60));
		}

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * from date--->00:00:00 有问题
	 */
	public static String getTimeFromInt(String date) {
		String h = "00";
		String f = "00";
		String m = "00";
		try {
			int time = Integer.parseInt(date);
			if (time <= 0) {
				return "0:00";
			}
			int hour = time / 3600;
			int secondnd = (time) % 3600;
			int secondnd1 = (secondnd) / 60;
			int million = (secondnd / 1000) % 60;

			h = String.valueOf(hour);
			f = String.valueOf(secondnd1);
			m = String.valueOf(million);

			if (hour < 10) {
				h = "0" + h;
			}

			if (secondnd1 < 10) {
				f = "0" + f;
			}

			if (million < 10) {
				m = "0" + m;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return h + ":" + f + ":" + m;
	}

	/**
	 * 当日时间差计算 in早与out,否则返回00:00:00
	 */
	public static String totalTime(String in, String out) {
		final long HOUR = 3600; // 小时
		final long MINUTE = 60; // 分钟
		String sHour = null;
		String sMinute = null;
		String sSec = null;
		String initTime = "00:00:00";
		try {
			long start = DateUtils.stringToLong(in, "HHmmss");
			long end = DateUtils.stringToLong(out, "HHmmss");
			long total = end - start;
			if (total < 0) {
				return initTime;
			}
			long hour = total / HOUR;
			long minute = (total % HOUR) / MINUTE;
			long sec = total % MINUTE;

			if (hour / 10 == 0) {
				sHour = "0" + hour;
			} else {
				sHour = String.valueOf(hour);
			}
			if (minute / 10 == 0) {
				sMinute = "0" + minute;
			} else {
				sMinute = String.valueOf(minute);
			}
			if (sec / 10 == 0) {
				sSec = "0" + sec;
			} else {
				sSec = String.valueOf(sec);
			}
			initTime = sHour + ":" + sMinute + ":" + sSec;
		} catch (Exception e) {
		}
		return initTime;
	}

	/**
	 * 2015-02-07
	 * 
	 * @param n
	 *            获取系统当前时间+n天时间
	 */
	public static String getDateByNowDate(int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, +n); // 得到+天
		Date date = calendar.getTime();
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		return df.format(date);
	}

	/**
	 * 计算传入时间的中的整天数
	 * 
	 * @param time
	 *            时间长度6位，时间格式 HHmmss
	 * @return 整天数
	 */
	public static int getCompleteDay(String time) {
		int count = 0;
		if (time != null && !"".equals(time) && time.length() == 6) {
			int hour = Integer.valueOf(time.substring(0, 2));
			count = hour / 24;

		}
		return count;

	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str) {

		Date date = null;
		try {
			date = datetimeTwoFormat.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取时间的分钟数，秒数不等于0，多加一分钟
	 * 
	 * @param time
	 *            "HHmmss"
	 * @return int
	 */
	public static int getMinute(String time) {
		int minute = 0;
		if (time != null && !"".equals(time) && time.length() == 6) {
			int hour = Integer.valueOf(time.substring(0, 2)) * 60;
			int minutes = Integer.valueOf(time.substring(2, 4));
			if (0 < Integer.valueOf(time.substring(4, 6))) {// 超过一秒了都算是超过一分钟
				++minutes;
			}
			minute = hour + minutes;
		}
		return minute;
	}

	/**
	 * 计算两个时间之差
	 * 
	 * @param beforedate
	 * @param lastdate
	 * @return String 返回格式"HHmmss"
	 */
	public static String countTwoTime(long beforedate, long lastdate) {
		long count = lastdate - beforedate;
		long hour = count / (HOUR_BY_MILLI);
		long minute = (count % (DAY_BY_MILLI)) % (HOUR_BY_MILLI) / (MINUTE_BY_MILLI);
		long second = ((count % (DAY_BY_MILLI)) % (HOUR_BY_MILLI)) % (MINUTE_BY_MILLI) / SECOND_BY_MILLI;
		String strhour = "00";
		String strminute = "00";
		String strsecond = "00";
		if (hour < 10) {
			strhour = "0" + String.valueOf(hour);
		} else {
			strhour = String.valueOf(hour);
		}
		if (minute < 10) {
			strminute = "0" + String.valueOf(minute);
		} else {
			strminute = String.valueOf(minute);
		}
		if (second < 10) {
			strsecond = "0" + String.valueOf(second);
		} else {
			strsecond = String.valueOf(second);
		}

		return strhour + strminute + strsecond;
	}

	/**
	 * 偏移日期
	 * 
	 * @param date
	 *            需要移动的日期
	 * @param days
	 *            偏移天数 正数为向后偏移，负数向前偏移
	 * @return String 偏移后的日期 格式"yyyyMMdd"
	 */
	public static String mobileDate(String date, int days) {
		String putDate = "";
		try {
			Date cdate = dateFormat_Eight.parse(date);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(cdate);
			calendar.add(Calendar.DATE, days);// 把日期往后增加一天.整数往后推,负数往前移动
			cdate = calendar.getTime(); // 这个时间就是日期往后推一天的结果
			putDate = dateFormat_Eight.format(cdate); // 增加一天后的日期

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return putDate;
	}

	/**
	 * 遍历两个时间段之间的具体日期
	 */
	public static List<Date> dateSplit(Date startDate, Date endDate) {

		List<Date> dateList = new ArrayList<Date>();
		try {

			if (!startDate.before(endDate))
				throw new Exception("开始时间应该在结束时间之后");
			Long spi = endDate.getTime() - startDate.getTime();
			Long step = spi / (24 * 60 * 60 * 1000);// 相隔天数

			dateList.add(endDate);
			for (int i = 1; i <= step; i++) {
				dateList.add(new Date(dateList.get(i - 1).getTime() - (24 * 60 * 60 * 1000)));// 比上一天减一
			}

		} catch (Exception e) {
			dateList.add(startDate);

		}
		return dateList;

	}

	public static List<Date> dateSplitSTL(Date startDate, Date endDate) {

		List<Date> dateList = new ArrayList<Date>();
		try {

			if (!startDate.before(endDate)) {
				throw new Exception("开始时间应该在结束时间之后");
			}
			Long spi = endDate.getTime() - startDate.getTime();
			Long step = spi / (24 * 60 * 60 * 1000);// 相隔天数

			dateList.add(startDate);
			for (int i = 1; i <= step; i++) {
				dateList.add(new Date(dateList.get(i - 1).getTime() + (24 * 60 * 60 * 1000)));// 比上一天加1
			}

		} catch (Exception e) {
			dateList.add(startDate);

		}
		return dateList;
	}

}
