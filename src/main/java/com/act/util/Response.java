package com.act.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.act.beans.enums.ErrorCode;





public class Response implements Serializable{

	private static final long serialVersionUID = 1L;
	
	

    private Map<String, Object> result;
    
    
    public static Response SUCCESS(){
        return new Response("0","");
    }
    
    
    public static Response BUSINESSFAIL(String msg){
        return new Response(ErrorCode.BUSINESSFAIL.value,msg);
    }
    
    public static Response ERROR(){
        return new Response(ErrorCode.ERROR.value,ErrorCode.ERROR.memo);
    }
    
    public static Response PARAMSERROR(){
        return new Response(ErrorCode.PARAMTER_ERROR.value,ErrorCode.PARAMTER_ERROR.memo);
    }
    
    public static Response FAIL(ErrorCode code){
        return new Response(code.value,code.memo);
    }
    
    public static Response FAIL(String code,String memo){
        return new Response(code,memo);
    }
    public static Response FAIL(String memo){
        return new Response("1",memo);
    }
    
    public static Response FAIL(Exception e){
        return new Response("1",(e.getCause()!=null)?e.getCause().getMessage():e.getMessage());
    }
    
    
    /**
     * 向result中添加额外的参数
     * @param key
     * @param object
     * @return
     */
    public Response put(String key,Object object){
        result.put(key,object);
        return this;
    }

    public Response putAll(Map<String, String> map){
        result.putAll(map);
        return this;
    }
    
    public String toJson(){
    	return JsonUtil.objectToJson(result);
    }


    public Response(){}
    public Response(String code, String errorMsg) {
        result = new HashMap<String, Object>();
        result.put("code",code);
        result.put("errorMSG",errorMsg);
    }
    

	public Map<String, Object> getResult() {
		return result;
	}

	public void setResult(Map<String, Object> result) {
		this.result = result;
	}
    
    
    

}
