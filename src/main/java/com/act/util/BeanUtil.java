package com.act.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class BeanUtil {
	
	/**
	 * 给指定的bean的某个字段赋值 ,若 bean中不含有改字段则跳过
	 * 此处若该字段不是String类型，则进行类型转换，此处需要该类型有个参数为String类型的构造方法
	 * @param bean
	 * @param fieldName
	 * @param value
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void setProperty(Object bean,String fieldName,String value) throws Exception{
		PropertyDescriptor propDesc=getPropertyDescriptor(fieldName,bean.getClass());
		if(propDesc==null){
			return;
		}
		Class clazz=propDesc.getPropertyType();
		Method setMethod=propDesc.getWriteMethod();
		//若该字段不是String类型，则进行类型转换，此处需要该类型有个参数为String类型的构造方法
		if(String.class!=clazz){
			setMethod.invoke(bean, clazz.getConstructor(String.class).newInstance(value));
		}else{
			setMethod.invoke(bean, value);
		}
		
	}
	
	@SuppressWarnings("rawtypes")
	public static PropertyDescriptor getPropertyDescriptor(String fieldName,Class clazz){
		try {
			return new PropertyDescriptor(fieldName,clazz);
		} catch (Exception e) {
			return null;
		}
	}


    /**
     *将非Map类型的object的所有字段全部转成String类型
     * @param bean
     * @return
     * @throws IllegalAccessException
     */
    public static Map fieldToString(Object bean) throws IllegalAccessException {
        Map result=new HashMap();
        Class clazz=bean.getClass();
        Field[] fields=clazz.getDeclaredFields();
        for(Field field:fields){
            field.setAccessible(true);
            String name=field.getName();
            Class fieldClazz=field.getType();
            Object fieldValue=field.get(bean);
            if(fieldValue==null){
                result.put(name,fieldValue);
            }else if(isBaseDataType(fieldClazz)){
                result.put(name,fieldValue.toString());
            }else if(Map.class.isInstance(field.get(bean))){
                StringUtil.allAttributesToString((Map<String, Object>) fieldValue);
                result.put(name,fieldValue);
            }else{
                result.put(name,fieldToString(fieldValue));
            }
        }
        return result;
    }

    /**
     * 判断一个类是否为基本数据类型。
     * @param clazz 要判断的类。
     * @return true 表示为基本数据类型。
     */
    public static boolean isBaseDataType(Class clazz)
    {
        return
        (
            clazz.equals(String.class) ||
            clazz.equals(Integer.class)||
            clazz.equals(Byte.class) ||
            clazz.equals(Long.class) ||
            clazz.equals(Double.class) ||
            clazz.equals(Float.class) ||
            clazz.equals(Character.class) ||
            clazz.equals(Short.class) ||
            clazz.equals(BigDecimal.class) ||
            clazz.equals(BigInteger.class) ||
            clazz.equals(Boolean.class) ||
            clazz.equals(Date.class) ||
            clazz.isPrimitive()
        );
    }
}
