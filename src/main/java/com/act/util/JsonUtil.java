package com.act.util;

import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Map;
import org.apache.log4j.Logger;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


/**
 * Json工具类
 * 
 * @author
 * 
 * @date 2015年1月13日
 */
public class JsonUtil {

	static Logger logger = Logger.getLogger("JsonUtil");
	
	public final static String DATE_FORMATE = "yyyyMMddHHmmss";
	
	private static ObjectMapper objectmapper;
	
	 static{
		objectmapper = new ObjectMapper();
		//不序列化为空的值
		objectmapper.setSerializationInclusion(Include.NON_NULL);
		//采用尽量解析的原则，如果JAVA bean中没有相关属性，则不设置。并不抛出异常
		objectmapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectmapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
		objectmapper.setDateFormat(new SimpleDateFormat(DATE_FORMATE));
	}

	/**
	 * 把任何对象转换成Json
	 * @param object
	 * @return Json字符串
	 */
	public static String objectToJson(Object object) {
		Writer strWriter = new StringWriter();
		try {
			objectmapper.writeValue(strWriter, object);
		} catch (Exception e) {
			logger.info("JSON解析出错");
			logger.info(e);
		}
		String json = strWriter.toString();
		return json;
	}


	/**
	 * 把JSON串转换成实体类
	 * 
	 * @param object
	 *            需要转换的实体对象
	 * @param json
	 *            需要转换的JSON串
	 * @return 实体对象
	 */
	public static Object jsonToEntity(Object object, String json) {
		try {
			object = objectmapper.readValue(json, object.getClass());
			return object;
		} catch (Exception e) {
			logger.info("JSON解析出错");
			logger.info(e);
			return null;
		}
	}
	
	/**
	 * Json串转Object
	 * @param json 需要转的JSON
	 * @param object 需要转换的Object
	 * @return 实体对象
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object JsonToObject(String json , Class clazz){
		try {
			return objectmapper.readValue(json, clazz);
		} catch (Exception e) {
			logger.info("JSON解析出错");
			logger.info(e);
			return null;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static Map json2Map(String json){
		return (Map) JsonToObject(json, Map.class);
	}
	
	
	public static Map Object2Map(Object object){
		return json2Map(objectToJson(object));
	}
	
	 
}
