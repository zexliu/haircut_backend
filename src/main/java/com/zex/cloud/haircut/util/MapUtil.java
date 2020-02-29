package com.zex.cloud.haircut.util;

import org.apache.commons.lang3.StringUtils;
import org.simpleframework.xml.Element;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @company_name 唐山徕思歌科技有限公司
 * @auther liuze
 * @create_date 2018/9/19
 * @description map工具类
 */
public class MapUtil {


    public static Map<String, Object> objectToMap(Object obj){
        if(obj == null)
            return null;

        Map<String, Object> map = new HashMap<String, Object>();

        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (key.compareToIgnoreCase("class") == 0) {
                    continue;
                }
                Method getter = property.getReadMethod();
                Object value = null;
                try {
                    value = getter!=null ? getter.invoke(obj) : null;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                map.put(key, value);
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 对象转map
     * @param obj
     * @return
     */
    public static Map<String, String> buildMap(Object obj) {
        Map<String, String> map = new HashMap<>();

        try {
            Class<?> clazz = obj.getClass();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();

                //如果 element 注解 name 字段设置了内容, 使用其当成字段名
                Element element = field.getAnnotation(Element.class);
                if (element != null && StringUtils.isNotEmpty(element.name())) {
                    fieldName = element.name();
                }
                String value ;
                if (field.get(obj) == null ) {
                    value = "";
                }else {
                    value = String.valueOf(field.get(obj));
                }
                map.put(fieldName, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


}
