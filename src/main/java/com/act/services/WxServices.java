package com.act.services;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.act.exception.UeFailException;
import com.act.util.Content;
import com.act.util.HttpUtil;
import com.act.util.JsonUtil;
import com.act.util.wx.WxConfig;



@Service("wxService")
public class WxServices {
	
	private Logger logger = LoggerFactory.getLogger(WxServices.class);
	
	@Autowired
    RedisTemplate redisTemplate;
	
	
	
	//获取openid
	public String getOpenIdByCode(String code) throws UeFailException{
		String url = getFetchOpenIdRemoteUrl(code);
		
		HttpClient client = new HttpClient(); 
		GetMethod method = new GetMethod(url); 
		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");
		try {
			client.executeMethod(method);
			String result =method.getResponseBodyAsString();
			logger.info("获取oepnid返回结果：{}",result);
			Map map = JsonUtil.json2Map(result);
			if(map.get("errcode") != null){
				logger.info("获取openid失败");
				throw new UeFailException("获取openid失败");
			}
			return (String) map.get("openid");
		}catch (Exception e) {
			logger.error("获取openid失败",e);
			throw new UeFailException("获取openid失败");
		}
	}
	
	
	//获取基本信息
	public String getWxUserWxInfo(String openId) throws UeFailException{
		logger.info("获取用户基本信息=={}",openId);
		String accessToken=getWxToken();
		String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+accessToken+"&openid="+openId+"&lang=zh_CN";		// http get 连接
		
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(url);
		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");
		
		try {
			client.executeMethod(method);
			String result =  method.getResponseBodyAsString();
			logger.info("获取微信基本信息{}",result);
			return  result;
		} catch (Exception e) {
			throw new UeFailException("取得微信用户基本信息异常！");
		}
		
	}
	
	
	
	
	
	//拼接获取openid的url
	private String getFetchOpenIdRemoteUrl(String code){
		StringBuilder sb=new StringBuilder();
		sb.append(WxConfig.GETOPENIDURL);
		sb.append("appid=");
		sb.append(WxConfig.APPID);
		sb.append("&secret=");
		sb.append(WxConfig.SECRECT);
		sb.append("&code=");
		sb.append(code);
		sb.append("&grant_type=");
		sb.append(WxConfig.GRANT_TYPE);
		
		return sb.toString();
	}
	
	
	
	
	int jsticket = 0;
	
	public String getJsApiTicket() throws UeFailException{
		
			Object obj = redisTemplate.opsForValue().get(Content.WX_JSAPI_TICKET);
			if(obj != null){
				logger.info("缓存中取得的jsapitticket:{}", obj.toString());
				return obj.toString();
			}
			logger.info("缓存中取得的jsapitticket为空，重新取jsapitticket开始：");
			logger.info("获取jsapitticket请求开始，url:"+WxConfig.JSAPITICKETURL);
		
			String url = WxConfig.JSAPITICKETURL+"access_token="+getWxToken()+"&type=jsapi";
			try {
				String result = HttpUtil.doClientGet(url);
				logger.info("获取jsapitticket返回结果：{}",result);
				Map map = JsonUtil.json2Map(result);
				if(map.get("errcode") != null  && jsticket < 5){
					logger.info("==获取jsticket出错，重新获取");
					jsticket ++;
					getJsApiTicket();
				}else{
					String ticket = (String) map.get("ticket");
					redisTemplate.opsForValue().set(Content.WX_JSAPI_TICKET, ticket, 7000, TimeUnit.SECONDS);//7000秒不到两个小时
					jsticket = 0;
					return ticket;
				}
			} catch(Exception e){
				logger.info("获取令牌ticket错误：",e);
				if(jsticket < 5){
					getJsApiTicket();
				}
				throw new UeFailException("获取ticket失败");
			}
		return null;
	}
	
	
	int tokencount = 0;
	
	//获取token
	public String getWxToken() throws UeFailException{
		
		Object obj = redisTemplate.opsForValue().get(Content.WX_TOKEN);
		if(obj != null){
			logger.info("缓存中取得的token:{}", obj.toString());
			return obj.toString();
		}
			logger.info("缓存中取得的token为空，重新取token开始：");
			logger.info("获取Token请求开始，url:"+WxConfig.GETTOKENURL);
		try {
			String result =  HttpUtil.doClientGet(WxConfig.GETTOKENURL);
			logger.info("获取token返回结果：{}",result);
			Map map = JsonUtil.json2Map(result);
			
			if(map.get("errcode") != null  && tokencount < 5){
				logger.info("==获取token出错，重新获取");
				tokencount ++;
				getWxToken();
			}else{
				String token = (String) map.get("access_token");
				redisTemplate.opsForValue().set(Content.WX_TOKEN, token, 7000, TimeUnit.SECONDS);//7000秒不到两个小时
				tokencount = 0;
				return token;
			}
			 
		}catch(Exception e){
			logger.info("获取令牌AccessToken错误：",e);
			if(tokencount < 5){
				getWxToken();
			}
			throw new UeFailException("获取token失败");
		}
		return null;
	}

}