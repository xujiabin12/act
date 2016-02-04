package com.act.util;

import java.util.UUID;

/**
 * ID生成器
 * 
 * @author
 * 
 * @date 2015年1月19日
 */
public class IdBuilder {

	private static IdBuilder instance = null;

	private IdBuilder() {
	}

	/**
	 * 单例模式
	 * 
	 * @return
	 */
	public static IdBuilder getInstance() {
		if (instance == null) {
			synchronized (IdBuilder.class) {
				if (null == instance) {
					instance = new IdBuilder();
				}
			}
		}
		return instance;
	}

	/**
	 * 根据表名获取ID
	 * 
	 * @param tablename
	 *            已经弃用本方法，可直接使用IdBuilder.getID()方法
	 * @return 19位ID号
	 */
	public static String getID(String tablename) {
		UUID id = UUID.randomUUID();
		return id.toString().replaceAll("-", "");
	}

	/**
	 * 根据表名获取ID
	 * 
	 * @return 19位ID号
	 */
	public static String getID() {
		UUID id = UUID.randomUUID();
		return id.toString().replaceAll("-", "");
	}

	public static void main(String[] args) {
	}

}