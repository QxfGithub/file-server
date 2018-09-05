/**
 * Copyright (C) 2017-2018 pinganfang, Inc. All Rights Reserved.
 */
package com.qxf.fileserver.Util;

import com.qxf.fileserver.Exception.BizException;
import org.joda.time.DateTime;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {

	public static final int YEAR_RETURN = 0;

	public static final int MONTH_RETURN = 1;

	public static final int DAY_RETURN = 2;

	public static final int HOUR_RETURN = 3;

	public static final int MINUTE_RETURN = 4;

	public static final int SECOND_RETURN = 5;

	public static final String DATETIME_DEFAULT_FORMAT = "yyyy.MM.dd";
	// 审核的时间格式
	public static final String DATETIME_CERTIFICAT_FORMAT = "yyyy-MM-dd HH:mm";
	public static final String DELETE_AT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String LESHAN_DATETIME_FORMAT = "yyyy.MM.dd HH:mm:ss";
	// 合同契约时间格式
	public static final String DATETIME_CONTRACT_FORMAT = "yyyy年MM月dd日";
	// 爬虫团队的发布时间格式
	public static final String DATETIME_PUBLISH_FORMAT = "yyyy-MM-dd";
	// ocr解析文件下载时间格式
	public static final String DATETIME_DOWNLOAD_FORMAT = "yyyyMMdd";

	public static String formatDate(final Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static String getFormatDate(final Date date) {
		return formatDate(date, "yyyy-MM-dd");

	}

	public static String formatDate(final Date date, final String pattern) {
		Assert.notNull(date, "Date");
		Assert.notNull(pattern, "Pattern");
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(date);
	}

	public static int getBetweenDays(final Date beginDate, final Date endDate) {
		Long dateBetween = getBetween(beginDate, endDate, DAY_RETURN);
		return dateBetween.intValue();
	}

	public static Long getBetween(final Date beginDate, final Date endDate, int returnPattern) {
		Assert.notNull(beginDate, "beginDate can not null");
		Assert.notNull(endDate, "endDate can not null");

		Calendar beginCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		beginCalendar.setTime(beginDate);
		endCalendar.setTime(endDate);
		endCalendar.setTime(endDate);
		switch (returnPattern) {
		case YEAR_RETURN:
			return DateUtils.getByField(beginCalendar, endCalendar, Calendar.YEAR);
		case MONTH_RETURN:
			return (DateUtils.getByField(beginCalendar, endCalendar, Calendar.YEAR) * 12) + DateUtils.getByField(beginCalendar, endCalendar, Calendar.MONTH);
		case DAY_RETURN:
			return DateUtils.getTime(beginDate, endDate) / (24 * 60 * 60 * 1000);
		case HOUR_RETURN:
			return DateUtils.getTime(beginDate, endDate) / (60 * 60 * 1000);
		case MINUTE_RETURN:
			return DateUtils.getTime(beginDate, endDate) / (60 * 1000);
		case SECOND_RETURN:
			return DateUtils.getTime(beginDate, endDate) / 1000;
		default:
			return 0L;
		}
	}

	/**
	 * 时间计算
	 *
	 * @param beginDate
	 * @param value
	 *
	 * @return
	 */
	public static Date calculate(final Date beginDate, final int value, int type) {
		Assert.notNull(beginDate, "beginDate can not null");
		Assert.notNull(value, "value can not null");
		Calendar beginCalendar = Calendar.getInstance();
		beginCalendar.setTime(beginDate);
		beginCalendar.add(type, value);
		return beginCalendar.getTime();

	}

	public static Date buildDate(final Date date, int value) {
		Assert.notNull(date, "beginDate can not null");
		Assert.notNull(value, "value can not null");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		Calendar returnDate = Calendar.getInstance();
		returnDate.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), value);
		return returnDate.getTime();
	}

	private static long getByField(Calendar beginCalendar, Calendar endCalendar, int calendarField) {
		return endCalendar.get(calendarField) - beginCalendar.get(calendarField);
	}

	private static long getTime(Date beginDate, Date endDate) {
		return endDate.getTime() - beginDate.getTime();
	}

	public static List<Date> getFullDay(Date date) {
		List<Date> dates = new ArrayList<>();
		Calendar beginCalendar = Calendar.getInstance();
		beginCalendar.setTime(date);
		beginCalendar.set(Calendar.HOUR_OF_DAY, 0);
		beginCalendar.set(Calendar.MINUTE, 0);
		beginCalendar.set(Calendar.SECOND, 0);
		beginCalendar.set(Calendar.MILLISECOND, 0);
		dates.add(beginCalendar.getTime());
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(date);
		endCalendar.set(Calendar.HOUR_OF_DAY, 23);
		endCalendar.set(Calendar.MINUTE, 59);
		endCalendar.set(Calendar.SECOND, 59);
		endCalendar.set(Calendar.MILLISECOND, 999);
		dates.add(endCalendar.getTime());
		return dates;
	}

	/**
	 * 解析
	 *
	 * @param dateStr
	 * @param pattern
	 *
	 * @return
	 */
	public static Date parse(String dateStr, String pattern) {
		try {
			return new SimpleDateFormat(pattern).parse(dateStr);
		} catch (ParseException e) {
			throw new BizException(e);
		}
	}

	public static Date parseDate(Date date) {
		return parse(formatDate(date), "yyyy-MM-dd");
	}

	public static Date calcDay(Date date, Integer delta) {
		DateTime dateTime = new DateTime(date);
		return dateTime.plusDays(delta).dayOfWeek().roundFloorCopy().toDate();
	}

	private static Date getDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 在date级别比较日期字串
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static Long compareOnDate(Date startDate, Date endDate) {
		Date start = getDate(startDate);
		Date end = getDate(endDate);
		return (start.getTime() - end.getTime());
	}

	/**
	 * 最大化查询日期
	 * 
	 * eg：<br>
	 * 输入2017-01-01，输出2017-01-01 23:59:59<br>
	 * 输入2017-01-01 12:12:12，输出2017-01-01 23:59:59<br>
	 * 
	 * @param date
	 */
	public static Date upper(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}

	/**
	 * 最小化查询日期
	 *
	 * @param date
	 * @return
	 */
	public static Date down(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 获取指定Date中的年月日分秒星期单个字段。
	 * 
	 * @param targetDate
	 * @param calendarFieldType
	 * @return
	 */
	public static int getDateFieldReturn(Date targetDate, int calendarFieldType) {
		Calendar c = Calendar.getInstance();
		c.setTime(targetDate);
		return c.get(calendarFieldType);
	}

	/**
	 * 获取本月第一天
	 *
	 * @return
	 */
	public static Date getMonthFirstDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取默认初始时间 : Fri Jan 02 00:00:00 CST 1970
	 * 
	 * @return
	 */
	public static Date getDefaultYear() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 1970);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 2);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取当前时间
	 * 
	 * @return 获取当前时间
	 */
	public static Date getCurrentDate() {
		Calendar agencyCal = Calendar.getInstance();
		return agencyCal.getTime();
	}

	/**
	 * 将字符串按照指定格式转换为日期对象
	 * 
	 * @param strDate
	 *            日期字符串
	 * @param pattern
	 *            日期格式字符串
	 * @return 转换成功的日期对象
	 */
	public static Date format(String strDate, String pattern) {
		Date date = null;
		if (strDate != null && !"".equals(strDate) && pattern != null && !"".equals(pattern)) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat(pattern, java.util.Locale.US);
				date = formatter.parse(strDate);
			} catch (Exception e) {
			}
		}
		return date;
	}

	/**
	 * 将字符串转换为日期对象
	 * 
	 * @param strDate
	 *            日期字符串
	 * @return 转换成功的日期对象
	 */
	public static Date parseDate(String strDate) {

		if (ValidationUtil.isEmpty(strDate)) {
			return null;
		}

		String dateFormat = null;

		strDate = strDate.trim().toUpperCase();
		if (ValidationUtil.isEmpty(strDate)) {
			return null;
		}

		if (strDate.indexOf("AM") > 0 || strDate.indexOf("PM") > 0) {
			if (strDate.indexOf("-") > 0) {
				if (strDate.length() <= 13) {
					dateFormat = "yyyy-MM-dd aa";// yyyy-MM-dd aa
				} else if (strDate.length() <= 19) {
					dateFormat = "yyyy-MM-dd hh:mm aa";//
				} else {
					dateFormat = "yyyy-MM-dd hh:mm:ss aa";
				}
			} else {
				if (strDate.length() <= 13) {
					dateFormat = "yyyy-MM-dd aa";
				} else if (strDate.length() <= 19) {
					dateFormat = "MM/dd/yyyy hh:mm aa";
				} else {
					dateFormat = "MM/dd/yyyy hh:mm:ss aa";
				}
			}
		} else {
			if (strDate.indexOf("-") > 0) {
				if (strDate.length() <= 7) {
					dateFormat = "yyyy-MM";
				} else if (strDate.length() <= 10) {
					dateFormat = "yyyy-MM-dd";
				} else if (strDate.length() <= 16) {
					dateFormat = "yyyy-MM-dd HH:mm";
				} else {
					dateFormat = "yyyy-MM-dd HH:mm:ss";
				}
			} else {
				if (strDate.length() <= 10) {
					dateFormat = "MM/dd/yyyy";
				} else if (strDate.length() <= 16) {
					dateFormat = "MM/dd/yyyy HH:mm";
				} else {
					dateFormat = "MM/dd/yyyy HH:mm:ss";
				}
			}
		}

		return format(strDate, dateFormat);
	}

	/**
	 * 根据format将当前时间转化为字符串
	 * @param format
	 * @return
	 */
	public static String getCurrentDateString(String format){
		return formatDate(new Date(), format);
	}

	/**
	 * 根据days参数获取date的前后几天时间
	 * 正数后延，负数提前
	 * @param date
	 * @param days
	 * @return
	 */
    public static Date getDayOfScale(Date date, int days) {
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);
    	c.set(Calendar.DAY_OF_MONTH, days);
    	return c.getTime();
    }

	/**
	 * 获取某个时间的当天起始时间
	 * @param date
	 * @return
	 */
	public static Date getStartTimeOfDate(Date date) {
		String startTime = formatDate(date,DATETIME_PUBLISH_FORMAT) + " 00:00:00";
		return parse(startTime, DELETE_AT_FORMAT);
	}
}
