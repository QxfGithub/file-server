package com.qxf.fileserver.constant;

public class WebConstants {

    /**
     * session中的visitor
     */
    public static final String SESSION_VISITOR_ATTRIBUTE = "visitor";
    
    public static final String ZF_COOKIE = "ZF_COOKIE";



    /**
     * web前缀
     */
    public static final String WEB_PREFIX = "/manage";
    
    /**
     * 微信小程序前缀
     */
    public static final String WXAPP_PREFIX = "/wxapp";

    /**
     * 日期格式化
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时区
     */
    public static final String TIMEZONE = "GMT+8";

    public static final String APPLICATION_NAME="sanya-fund";

    public static final String APPOINT_TIME_PATTERN = "^[0-9][0-9]\\:[0-9][0-9]$";;

    public static final String FORM_DATETIME_FORMAT ="yyyy-MM-dd HH:mm:ss";
    public static final String FORM_MINUTE_FORMAT = "yyyy-MM-dd HH:mm";//具体到分
    public static final String FORM_DATE_FORMAT ="yyyy-MM-dd";
    public static final String TIME_ZONE = "GMT+8";

    /**
     * 手机号正则pattern
     */
    public static final String MOBILE_PATTERN = "^1[3|4|5|6|7|8|9][0-9]{9}$";
    
    /**
     * 密码正则pattern（至少包含数字，字母或者字符中的两种）
     */
    public static final String LOGIN_PASSWORD_PATTERN = "^(?![a-zA-z]+$)(?!\\d+$)(?![!\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~]+$)[a-zA-Z\\d!\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~]{6,20}$";
    /**
     * 密码正则pattern（6数字）
     */
	public static final String PAY_PASSWORD_PATTERN = "^[0-9]*$";
    /**
     * 身份证正则pattern（身份证号(15位、18位数字)，最后一位是校验位，可能为数字或字符X）
     */
    public static final String ID_CARD_PATTERN = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";
	/**
	 * 用户名正则（4-20字母或者数字）
	 */
	public static final String USERNAME_PATTERN = "^[\\d|\\w|_]{4,20}$";
	/**
	 * 公寓品牌（1-30个汉字）
	 */
	public static final String APARTMENTBRAND_PATTERN = "^[\\u4E00-\\u9FA5A-Za-z0-9_]{1,30}";
	/**
	 * 微信小程序的sessionkey
	 */
	public static final String WXAPP_SESSION_KEY = "SESSION";

}
