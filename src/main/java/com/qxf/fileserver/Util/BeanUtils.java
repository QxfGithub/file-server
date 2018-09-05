package com.qxf.fileserver.Util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by BAOJIMIN518 on 2017/10/2.
 */
public class BeanUtils {

    /**
     * Map对象转化成 JavaBean对象
     * @param javaBean JavaBean实例对象
     * @param map Map对象
     * @return
     */
    public static <T> T map2Bean(T javaBean, Map map) {
        try {
            // 获取javaBean属性
            BeanInfo beanInfo = Introspector.getBeanInfo(javaBean.getClass());
            // 创建 JavaBean 对象
            Object obj = javaBean.getClass().newInstance();

            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            if (propertyDescriptors != null && propertyDescriptors.length > 0) {
                String propertyName = null; // javaBean属性名
                Object propertyValue = null; // javaBean属性值
                for (PropertyDescriptor pd : propertyDescriptors) {
                    propertyName = pd.getName();
                    if (map.containsKey(propertyName)) {
                        propertyValue = map.get(propertyName);
                        pd.getWriteMethod().invoke(obj, new Object[] { propertyValue });
                    }
                }
                return (T) obj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * JavaBean对象转化成Map对象
     * @param javaBean
     * @return
     */
    public static Map bean2Map(Object javaBean) {
        Map map = new HashMap();

        try {
            // 获取javaBean属性
            BeanInfo beanInfo = Introspector.getBeanInfo(javaBean.getClass());

            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            if (propertyDescriptors != null && propertyDescriptors.length > 0) {
                String propertyName = null; // javaBean属性名
                Object propertyValue = null; // javaBean属性值
                for (PropertyDescriptor pd : propertyDescriptors) {
                    propertyName = pd.getName();
                    if (!propertyName.equals("class")) {
                        Method readMethod = pd.getReadMethod();
                        propertyValue = readMethod.invoke(javaBean, new Object[0]);
                        map.put(propertyName, propertyValue);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 对象转换为json字符串
     * @param object
     * @return
     */
    public static String toJsonString(Object object){
        String jsonString = "";
        try {
            jsonString = new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            jsonString = new GsonBuilder().serializeNulls().create().toJson(object);
        }
        return jsonString;
    }
    /**
     * <strong> 描述：获取一个对象指定属性的值</strong><br>
     *
     * @param <T> target 对象
     * @param propertyName 属性名
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @author LIUJUN 2018年3月23日 上午10:41:19
     */
    public static <T> Object getPropertyValue(T target, String propertyName)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method method = org.springframework.beans.BeanUtils.findMethod(target.getClass(), "get" + firstCharToUpperCase(propertyName));
        if (method == null) {
            return null;
        }
        return method.invoke(target);
    }


    /**
     * <strong>TODO 描述：首字母大写</strong><br>
     *
     * @param str
     * @return
     */
    public static String firstCharToUpperCase(String str) {
        // name = name.substring(0, 1).toUpperCase() + name.substring(1);
        // return name;
        char[] cs = str.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }
}

