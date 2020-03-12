package com.ihrm.common.util;

import org.springframework.cglib.beans.BeanMap;

import java.util.HashMap;
import java.util.Map;

public class BeanMapUtils {

    /**
     * 将对象属性转化为map结合
     * key为
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key+"", beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 将map集合中的数据转化为指定对象的同名属性中
     */
    public static <T> T ToBean(Class<T> clazz,Map<String, Object> map) throws Exception {
        T bean = clazz.newInstance();
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }
//    public static <T> T ToBean(Class<T> type, Map map) throws Exception   {
//        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
//        T bean = type.newInstance(); // 创建 JavaBean 对象
//        // 给 JavaBean 对象的属性赋值
//        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
//        for (int i = 0; i < propertyDescriptors.length; i++) {
//            PropertyDescriptor descriptor = propertyDescriptors[i];
//            String propertyName = descriptor.getName();
//            if (map.containsKey(propertyName)) {
//                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
//                try {
//                    Object value = map.get(propertyName);
//                    Object[] args = new Object[1];
//                    args[0] = value;
//                    descriptor.getWriteMethod().invoke(bean, args);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//
//            }
//        }
//        return bean;
//    }

}
