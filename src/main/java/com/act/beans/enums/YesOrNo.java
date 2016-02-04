package com.act.beans.enums;

public enum YesOrNo {
	
	
	yes("0","是"),
	
	no("1","否");
	
	
	public String value;
	public String name;
	
	 YesOrNo(String value,String name){
		this.value = value;
		this.name = name;
	}

}
