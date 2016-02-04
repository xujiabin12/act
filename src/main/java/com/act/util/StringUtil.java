package com.act.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;


public class StringUtil extends StringUtils{
	
	private static String[] strs = new String[] { "a", "b", "c", "d", "e", "f",
		"g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
		"t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F",
		"G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
		"T", "U", "V", "W", "X", "Y", "Z" };
	
	/**
	 * 返回15位的随机数
	 * @return
	 */
	public static String createRandomStr() {
		Random r = new Random();
		StringBuilder sb = new StringBuilder();
		int length = strs.length;
		for (int i = 0; i < 15; i++) {
			sb.append(strs[r.nextInt(length - 1)]);
		}
		return sb.toString();
	}
	
	 /**
     * 集合类型内部属性全部转为String类型
     * @param collection
     * @return
     * @throws IllegalAccessException
     */
    public static Object allToString(Collection collection) throws IllegalAccessException {
        if(collection==null||collection.isEmpty()){
            return collection;
        }
        Object[] objects=collection.toArray();
        for(int i=0;i<objects.length;i++){
            Object obj=objects[i];
            if(Map.class.isInstance(obj)){
                allAttributesToString((Map)obj);
            }else if(Collection.class.isInstance(obj)){
                obj=allToString((Collection)obj);
            }else if(BeanUtil.isBaseDataType(obj.getClass())){
                if(!String.class.isInstance(obj)){
                   obj=obj.toString();
                }
            }else{
                obj=BeanUtil.fieldToString(obj);
            }
            objects[i]=obj;//修改
        }
        return objects;
    }
	
	 /**
     * 将map中的非String类型转成String类型
     * @param map
     * @param key
     * @return
     */
    public static void attributeToString(Map map,String key){
        if(!map.containsKey(key)){
            throw new RuntimeException("该map中不含有"+key);
        }
        Object value=map.get(key);
        if(value!=null){
            if(!String.class.isInstance(value)){
                map.put(key,value.toString());
            }
        }
    }
	
	/**
     * 递归将map中所有非String的基本类型全部转为String类型
     * @param map
     */
    public static void allAttributesToString(Map<String,Object> map) throws IllegalAccessException {
        Iterator<String> iterator=map.keySet().iterator();
        while(iterator.hasNext()){
            String key=iterator.next();
            Object value=map.get(key);
            if(value==null){
                continue;
            }
            if(BeanUtil.isBaseDataType(value.getClass())){//基本类型直接转换
                attributeToString(map,key);
            }else if(Map.class.isInstance(value)){//map类型递归转换
                allAttributesToString((Map<String, Object>) value);
            }else if(Collection.class.isInstance(value)){//集合类型
                map.put(key,allToString((Collection)value));
            }else{
                value=BeanUtil.fieldToString(value);
                map.put(key,value);
            }
        }

    }
	
	/**
	 * 将一组字符串用指定的字符分隔得到一个字符串
	 * 如mgr_test_value
	 * @param split
	 * @param values
	 * @return
	 */
	public static String join(String split, String... values) {
		StringBuilder builder = new StringBuilder();
		for(String s : values) {
			builder.append(s).append(split);
		}
		return removeEndCharacter(builder.toString().trim(), split);
	}

	
	/**
	 * 去掉字符串结尾的字符
	 * @param string
	 * @param endCharacter
	 * @return
	 */
	public static String removeEndCharacter(String string, String endCharacter) {
		if (StringUtils.isNotBlank(string)&&string.endsWith(endCharacter)) {
			string = string.substring(0, string.length()-endCharacter.length());
		}
		return string;
	}
	/**
	 * 所有参数都不为空才返回false,否则返回true
	 * @param strs
	 * @return
	 */
	public static boolean hasEmpty(String ...strs){
		for(String str:strs){
			if(StringUtils.isEmpty(str)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 只要有一个参数不为空就返回false,所有参数都为空返回true
	 * @param strs
	 * @return
	 */
	public static boolean allEmpty(String ...strs){
		for(String str:strs){
			if(StringUtils.isNotEmpty(str)){
				return false;
			}
		}
		return true;
	}
	
	
	
	/**
	 * 首字母转大写
	 * @param oldStr
	 * @return
	 */
	public static String firstUpper(String oldStr){
		byte[] items = oldStr.getBytes();  
		items[0] = (byte)((char)items[0] - ( 'a' - 'A'));  
		return new String(items);  
	}
	
	/**
	 * 将带下划线的值转成骆驼命名法 如 user_name =>userName
	 */
	public static String toCamelCase(String oldStr){
		if(!oldStr.contains("_")){
			return oldStr;
		}
		String [] keys=oldStr.split("\\_");
		StringBuilder sb=new StringBuilder(keys[0]);
		for(int i=1;i<keys.length;i++){
			sb.append(StringUtil.firstUpper(keys[i]));
		}
		return sb.toString();
	}
	
	public static String getUUID(){
		String uuid=UUID.randomUUID().toString();
		return uuid.replaceAll("-","");
	}
}
