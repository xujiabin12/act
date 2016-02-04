package com.act.services;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.act.util.Content;
import com.act.util.HttpUtil;
import com.act.util.JsonUtil;
import com.act.util.Properties;


@Service("hxService")
public class HxServices {
	
	private Logger logger = LoggerFactory.getLogger(HxServices.class);
	
	
	@Autowired
	Properties properties;
	
	
	@Autowired
    private RedisTemplate redisTemplate;
	
	
	//删除群组
	public void deleteGroup(String groupid)throws Exception{
		String url = Content.REQUESTURL+"chatgroups/"+groupid;
		
		HttpUtil.doDelete(url, "",getToken());
	}
	

	/**
	 * 从黑名单删除人
	 * @param userName
	 * @throws Exception
	 */
	public void delToBlacklist(String groupId,String userName)throws Exception{
		String url = Content.REQUESTURL+"chatgroups/"+groupId+"/blocks/users/"+userName;
		
		HttpUtil.doDelete(url, "",getToken());
		
	}
	
	
	
	/**
	 * 添加人进入黑名单
	 * @param userName
	 * @throws Exception
	 */
	public void addToBlacklist(String groupId,String userName)throws Exception{
		String url = Content.REQUESTURL+"chatgroups/"+groupId+"/blocks/users/"+userName;
		
		String result = HttpUtil.doPost(url, "",getToken());
		
		logger.info(result);
		
		
	}
	
	
	
	
	/**
	 * 群组增加成员
	 * @param userName
	 * @throws Exception
	 */
	public void addMember(String groupId,String userName)throws Exception{
		
		String url = Content.REQUESTURL+"chatgroups/"+groupId+"/users/"+userName;
		
		String result = HttpUtil.doPost(url, "",getToken());
		
		logger.info(result);
	}
	
	
	/**
	 * 创建群组
	 */
	public String createGroup(String groupName,String owner,String desc)throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String[] m = new String[]{};
		map.put("groupname", groupName);
		map.put("desc", desc);
		map.put("maxusers", 300);
		map.put("public", true);
		map.put("approval", false);
		map.put("owner", owner);
		map.put("members", m);
		
		String result = HttpUtil.doPost(Content.REQUESTURL+"chatgroups", JsonUtil.objectToJson(map),getToken());
		
		logger.info(result);
		
		String groupId =  JSONObject.fromObject(result).getJSONObject("data").getString("groupid");
		
		logger.info("groupId:{}",groupId);
		return groupId;
	}
	
	
	/**
	 * 注册
	 * @param name
	 * @param ps
	 */
	public void register(String name,String ps,String nickName)throws Exception{
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("username", name);
		map.put("password", ps);
		map.put("nickname", nickName);
		
		String result = HttpUtil.doPost(Content.REQUESTURL+"users", JsonUtil.objectToJson(map));
		
		logger.info(result);
	}
	
	
	int tokenCount = 0;
	/**
	 * 获取token
	 */
	public String getToken(){
		try {
			Object obj = redisTemplate.opsForValue().get(Content.CACHEINDEX+"hx:token");
			if(obj != null){
				logger.info("token:{}",obj.toString());
				return obj.toString();
			}else{
				Map<String,String> map = new HashMap<String,String>();
				map.put("grant_type", "client_credentials");
				map.put("client_id", properties.getClientId());
				map.put("client_secret", properties.getClientSecret());
				
				String result = HttpUtil.doPost(Content.REQUESTURL+"token", JsonUtil.objectToJson(map));
				
				logger.info(result);
				
				Map resultMap = JsonUtil.json2Map(result);
				
				String token = String.valueOf(resultMap.get("access_token"));
				
				logger.info("token:{}",token);
				
				redisTemplate.opsForValue().set(Content.CACHEINDEX+"hx:token", token,5,TimeUnit.DAYS);
				
				return token;
			}
		} catch (Exception e) {
			logger.error("getToken error",e);
			e.printStackTrace();
			logger.info("==重新获取=={}",tokenCount);
			if(tokenCount < 3){
				getToken();
			}
			tokenCount ++ ;
		}
		
		return null;
	}

}
