package com.act.beans.enums;

public enum RoleEnums {
	
	
	admin("0","管理员"),
	
	teacher("1","老师"),
	
	student("2","学生");
	
	public String value;
	public String name;
	
	RoleEnums(String value,String name){
		this.value = value;
		this.name = name;
	}

}
