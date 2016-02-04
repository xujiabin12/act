package com.act.beans.enums;

public enum ErrorCode {

    OK("0", "成功"),
    
    BUSINESSFAIL("1","业务失败提醒"),
    
    ERROR("500", "系统繁忙"), 
	
    NOGUANZHU("3001","请先关注ACT学堂"),
    
	
	
	PARAMTER_ERROR("9999", "参数错误");
	
	
	public String value;
	
	public String memo;
	
	ErrorCode(String value, String memo) {
		
		this.value = value;
		this.memo = memo;
	}
}
