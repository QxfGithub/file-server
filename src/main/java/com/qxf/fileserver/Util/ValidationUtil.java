package com.qxf.fileserver.Util;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;


public class ValidationUtil {
	private static final Logger LOG = Logger.getLogger(ValidationUtil.class);

	private ValidationUtil() {

	}

	/**
	 * 判断对象是否为空。
	 * 
	 * @param aObj
	 *            需要判断的对象
	 * @return 如果爱对象为空，则为true，如果对象不为空，则为false
	 */
	public static boolean isEmpty(Object aObj) {
		if (aObj instanceof String) {
			return isEmpty((String) aObj);
		} else if (aObj instanceof Collection) {
			return isEmpty((Collection) aObj);
		} else if (aObj instanceof Map) {
			return isEmpty((Map) aObj);
		} else if (aObj != null && aObj.getClass().isArray()) {
			return isEmptyArray(aObj);
		} else {
			return isNull(aObj);
		}
	}

	/**
	 * 判断不为空
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isNotEmpty(Object aObj) {
		return !isEmpty(aObj);
	}

	/**
	 * 
	 * Description: 判断String对象是否为空。
	 * 
	 * @param aStr
	 *            需要判断的对象
	 * @return boolean
	 */
	public static boolean isEmpty(String aStr) {
		if (aStr == null || aStr.trim().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否是空集合
	 * 
	 * @param c
	 *            需要判断的集合对象
	 * @return 如果爱对象为空，则为true，如果对象不为空，则为false
	 */
	public static boolean isEmpty(Collection c) {
		if (c == null || c.size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是空Map
	 * 
	 * @param m
	 *            需要判断的Map对象
	 * @return 如果爱对象为空，则为true，如果对象不为空，则为false
	 */
	public static boolean isEmpty(Map m) {
		if (m == null || m.size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是空数组
	 * 
	 * @param array
	 *            需要判断的数组对象
	 * @return 如果爱对象为空，则为true，如果对象不为空，则为false
	 */
	private static boolean isEmptyArray(Object array) {
		int length = 0;
		if (array instanceof int[]) {
			length = ((int[]) array).length;
		} else if (array instanceof byte[]) {
			length = ((byte[]) array).length;
		} else if (array instanceof short[]) {
			length = ((short[]) array).length;
		} else if (array instanceof char[]) {
			length = ((char[]) array).length;
		} else if (array instanceof float[]) {
			length = ((float[]) array).length;
		} else if (array instanceof double[]) {
			length = ((double[]) array).length;
		} else if (array instanceof long[]) {
			length = ((long[]) array).length;
		} else if (array instanceof boolean[]) {
			length = ((boolean[]) array).length;
		} else {
			length = ((Object[]) array).length;
		}
		if (length == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断对象是否为Null。
	 * 
	 * @param oStr
	 *            需要判断的对象
	 * @return 如果爱对象为Null，则为true，如果对象不为Null，则为false
	 */
	private static boolean isNull(Object oStr) {
		if (null == oStr) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否是时间格式
	 * 
	 * @param text
	 *            需要判断的时间
	 * @return 验证成功返回true
	 */
	public static boolean isTime(String text) {
		Pattern p = Pattern.compile("(([0][1-9])|([1][0-2]))([\\:](([0-5]\\d)|[0](\\d))){1,2}");
		return p.matcher(text).matches();
	}

	/**
	 * 判断是否是时间格式
	 * 
	 * @param text
	 *            需要判断的时间
	 * @return 验证成功返回true
	 */
	public static boolean isCurrency(String text) {
		Pattern p = Pattern.compile("([+]|[-])?(\\s)*(\\d){1,}([.](\\d){1,2})?");
		return p.matcher(text).matches();
	}

	/**
	 * 是否是数字
	 * 
	 * @param text
	 *            需要判断的字符串
	 * @return 验证成功返回true
	 */
	public static boolean isNumber(String text) {
		if (isEmpty(text)) {
			return false;
		}
		try {
			Double a = Double.parseDouble(text);
			LOG.error(a);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 是否是整数
	 * 
	 * @param text
	 *            需要判断的字符串
	 * @return 验证成功返回true
	 */
	public static boolean isNumeric(String text) {
		if (isEmpty(text)) {
			return false;
		}

		Pattern p = Pattern.compile("[0-9]*");
		return p.matcher(text.trim()).matches();
	}

	/**
	 * 判断两个对象是否相等
	 * 
	 * @param obj1
	 *            判断对象1
	 * @param obj2
	 *            判断对象2
	 * @return 相等为true，不等为false
	 */
	public static boolean isEquals(Object obj1, Object obj2) {
		boolean result;
		if (obj1 != null) {
			result = obj1.equals(obj2);
		} else {
			result = (obj2 == null);
		}
		return result;
	}

	/**
	 * 
	 * Description:fortify黑名单
	 * 
	 * @param str
	 * @return
	 */
	public static String characterModify(String str) {
		if (str == null) {
			return null;
		}
		String strTemp = str;

		while (true) {
			if ((strTemp.indexOf("%0d") == -1) && (strTemp.indexOf("\r") == -1)
					&& (strTemp.indexOf("%0a") == -1) && (strTemp.indexOf("\n") == -1)) {
				break;
			}

			if (strTemp.indexOf("%0d") != -1) {
				strTemp = strTemp.replaceAll("%0d", "");
			}
			if (strTemp.indexOf("\r") != -1) {
				strTemp = strTemp.replaceAll("\r", "");
			}
			if (strTemp.indexOf("%0a") != -1) {
				strTemp = strTemp.replaceAll("%0a", "");
			}
			if (strTemp.indexOf("\n") != -1) {
				strTemp = strTemp.replaceAll("\n", "");
			}
		}

		return strTemp.toString();
	}

	/**
	 * 判断两个对象是否相等
	 * 
	 * @param obj1
	 *            判断对象1
	 * @param obj2
	 *            判断对象2
	 * @return 相等为true，不等为false
	 */
	public static boolean myEquals(Object obj1, Object obj2) {
		boolean result;
		if (obj1 != null) {
			result = obj1.equals(obj2);
		} else {
			result = (obj2 == null);
		}
		return result;
	}

	/**
	 * 判断map中 value 是否有为空，为空返回true,否则返回false
	 * 
	 * @param parm
	 * @return
	 */
	public static Boolean isExistEmptyValue(Map<String, Object> parm) {
		Boolean flag = false;
		Set set = parm.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> enter = (Entry<String, Object>) iterator.next();
			if (isEmpty(enter.getValue())) {
				flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * 判断字符串是否超过规定长度
	 * @param str 要判断的字符串 字符串不能为空
	 * @param length 规定长度
	 * @return true-超过长度 false-没有超过长度
	 */
	public static boolean isOverLengthofString(String str,int length){
		if(length<str.length()){
			return false;
		}else{
			return true;
		}
	}
	
}
