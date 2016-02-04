package com.act.util;

import java.sql.Timestamp;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 重写错误日志打印信息
 * 
 * @author jiabin
 *
 */
public class ErrorLogLayout extends PatternLayout {
	
	StringBuffer sbuf;

	@Override
	public String getContentType() {
		return "text/html;charset=utf-8";  
	}

	public ErrorLogLayout(){
		 sbuf = new StringBuffer(128);  
	}
	@Override
	public String format(LoggingEvent event) {
		StringBuffer sb=new StringBuffer();
		String []s=event.getThrowableStrRep();
		if(s!=null){
			int len=s.length;
			for(int i=0;i<len;i++){
				sb.append(s[i]+"\r\n");
			}
		}
		sbuf.setLength(0);  
        sbuf.append("错误等级："+event.getLevel().toString()+"\r\n");  
        sbuf.append("错误时间："+new Timestamp(System.currentTimeMillis())+"\r\n");  
        sbuf.append("错误所在类："+event.getLocationInformation().getClassName()+"\r\n");  
        sbuf.append("错误所在方法："+event.getLocationInformation().getMethodName()+"\r\n");  
        sbuf.append("错误所在行："+event.getLocationInformation().getLineNumber()+"\r\n");  
        sbuf.append("堆栈信息："+sb+"\r\n");
        sbuf.append("===============================================\r\n");
	   return sbuf.toString();  

	}

	@Override
	public boolean ignoresThrowable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void activateOptions() {
		// TODO Auto-generated method stub
	}

}
