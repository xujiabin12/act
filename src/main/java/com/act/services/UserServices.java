package com.act.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.act.beans.enums.RoleEnums;
import com.act.beans.enums.YesOrNo;
import com.act.dao.CommonDao;
import com.act.dao.bean.Users;
import com.act.util.Content;
import com.act.util.IdBuilder;
import com.act.util.Md5ConverterUtil;
import com.act.util.Response;
import com.act.util.StringUtil;


@Service("userService")
@Transactional
public class UserServices {
	
	
	private Logger logger = LoggerFactory.getLogger(UserServices.class);
	
	private final static String PASSWORD = "123456";
	
	@Autowired
	HxServices hxService;
	
	@Autowired
	CommonDao dao;
	
	@Autowired
    RedisTemplate redisTemplate;
	
	
	public List<Users> userList(String nickName,String userName) throws Exception{
		logger.info("userList:{},{}",nickName,userName);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String sql = "from Users where  1=1 ";
		if(StringUtil.isNotBlank(nickName)){
			sql += " and nickname like :nickname";
			map.put("nickname", "%"+nickName+"%");
		}
		if(StringUtil.isNotBlank(userName)){
			sql += " and username like :username";
			map.put("username", userName);
		}
		
		return dao.queryList(sql, map);
	}
	
	
	public List<Users> teacherList(String nickName,String userName) throws Exception{
		logger.info("teacherList:{},{}",nickName,userName);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String sql = "from Users where  role = '1' ";
		if(StringUtil.isNotBlank(nickName)){
			sql += " and nickname like :nickname";
			map.put("nickname", "'%"+nickName+"%'");
		}
		if(StringUtil.isNotBlank(userName)){
			sql += " and username like :username";
			map.put("username", "'%"+userName+"%'");
		}
		
		return dao.queryList(sql, map);
	}
	
	public void setTeacher(String userid)throws Exception{
		logger.info("setTeacher:{}",userid);
		
		Users u = dao.queryObject(Users.class, userid);
		u.setRole(RoleEnums.teacher.value);
		dao.update(u);
		
	}
	
	 
	
	public Users addUser(Map map) throws Exception{
		logger.info("==adduser=={}",map);
		String userName = getUserName();
		Users u = new Users(IdBuilder.getID(), userName,String.valueOf(map.get("nickname")), "");
		u.setSex(String.valueOf(map.get("sex")));
		u.setHeadimg(String.valueOf(map.get("headimgurl")));
		u.setOpenid(String.valueOf(map.get("openid")));
		u.setState(YesOrNo.yes.value);
		u.setRole(RoleEnums.student.value);
		dao.add(u);
		
		hxService.register(u.getUsername(), PASSWORD,u.getNickname());
		return u;
	}
	
	//获取username
	private String getUserName(){
		String name = IdBuilder.getID();
		name =  "ue"+name.substring(name.length()-8,name.length());
		
		Map<String, Object> maps = new HashMap<String, Object>();
		String sql = "from Users where username like :username  ";
		maps.put("username", name);
		if(dao.queryCount(sql, maps) > 0){
			getUserName();
		}
		return name;
	}
	
	
	
	public Users selectByOpenid(String openid) throws Exception{
		logger.info("selectByOpenid:{}",openid);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String sql = "from Users where openid = :openid ";
		map.put("openid", openid);
		
		List<Users> list = dao.queryList(sql, map);
		
		if(list != null &&  list.size() > 0){
			return list.get(0);
		}
		
		return null;
	}
	
	
	public void setMgrPass(String userid,String pass)throws Exception{
		logger.info("setMgrPass:{},{}",userid,pass);
		
		Users u = dao.queryObject(Users.class, userid);
		u.setMgrpwd(Md5ConverterUtil.Md5(pass));
		
		dao.update(u);
	}
	
	public Users login(String username,String password) throws Exception{
		logger.info("login:{},{}",username,password);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String sql = "from Users where username like :username and mgrpwd = :password ";
		map.put("username", username);
		map.put("password", password);
		
		List<Users> list = dao.queryList(sql, map);
		
		if(list == null || list.size() < 1){
			return null;
		}
		
		return list.get(0);
	}
	
	
	/**
	 * 永久禁言
	 * @return
	 */
	public Response foreverStopSpeak(String userId){
		
		logger.info("==foreverStopSpeak={}",userId);
		
		redisTemplate.opsForValue().set(Content.KEYSTOPSPEAK+userId, YesOrNo.yes.value);

		return Response.SUCCESS();
	}
	
	/**
	 * 禁言10分钟
	 * @return
	 */
	public Response stopSpeak(String userId){
		
		logger.info("==stopSpeak={}",userId);
		
		redisTemplate.opsForValue().set(Content.KEYSTOPSPEAK+userId, YesOrNo.yes.value, 10, TimeUnit.MINUTES);

		return Response.SUCCESS();
	}
	
	//是否被禁言   0是   1否
	public Response isStopSpeak(String userId){
		
		String stopSpeak = "1";
		
		Object obj = redisTemplate.opsForValue().get(Content.KEYSTOPSPEAK+userId);
		
		logger.info("===="+obj);
		
		if(obj != null){
			stopSpeak = "0";
		}
		
		return Response.SUCCESS().put("isStopSpeak", stopSpeak);
	}
	
	
	

}
