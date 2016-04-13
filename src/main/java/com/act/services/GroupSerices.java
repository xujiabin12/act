package com.act.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.act.beans.enums.ErrorCode;
import com.act.beans.enums.YesOrNo;
import com.act.dao.CommonDao;
import com.act.dao.bean.GroupHistory;
import com.act.dao.bean.Groups;
import com.act.dao.bean.UserGroup;
import com.act.dao.bean.Users;
import com.act.exception.UeFailException;
import com.act.util.Content;
import com.act.util.IdBuilder;
import com.act.util.JsonUtil;
import com.act.util.Response;
import com.act.util.StringUtil;



@Service("groupService")
@Transactional
public class GroupSerices {
	
	
	private Logger logger = LoggerFactory.getLogger(GroupSerices.class);
	
	@Autowired
	HxServices hxService;
	
	@Autowired
    RedisTemplate redisTemplate;
	
	@Autowired
	WxServices wxService;
	
	@Autowired 
	UserServices userService;
	
	@Autowired
	CommonDao dao;
	
	private int pageSize = 10;
	
	public void saveMsg(GroupHistory gh){
		try {
			dao.add(gh);
		} catch (Exception e) {
			logger.error("保存信息失败",e);
			e.printStackTrace();
		}
	}
	
	
	public Response queryHistoryByPage(String groupId,int pageNo){
		logger.info("==queryByPage=={},{}",groupId,pageNo);
		pageNo = (pageNo - 1) * pageSize;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupid", groupId);
		
		String sql = "from GroupHistory where groupid = :groupid order by createdt desc ";
		List<GroupHistory> list = dao.queryListByPage(sql, map, pageNo, pageSize);
		
		return Response.SUCCESS().put("list", list).put("pageNo", pageNo + 1);
	}
	
	
	public void setJoinGroupUrl(String url){
		logger.info("=setJoinGroupUrl:{}",url);
		redisTemplate.opsForValue().set(Content.GROUPURL, url);
	}
	
	public Response getJoinGroupUrl(){
		logger.info("=getJoinGroupUrl=");
		Object obj = redisTemplate.opsForValue().get(Content.GROUPURL);
		if(obj == null){
			return Response.FAIL("请联系老师，设置当前群组连接");
		}
		return Response.SUCCESS().put("url", obj.toString());
	}
	
	
	public void sendJoinGroupUrl(String code)throws Exception{
		logger.info("=sendJoinGroupUrl={}",code);
		Object obj = redisTemplate.opsForValue().get(Content.GROUPURL);
		if(obj == null){
			throw new UeFailException("请联系老师，设置当前群组连接");
		}
		String openId = wxService.getOpenIdByCode(code);
		logger.info("==更新用户信息==");
		String userInfo = wxService.getWxUserWxInfo(openId);
		Map map = JsonUtil.json2Map(userInfo);
		String headImg = MapUtils.getString(map, "headimgurl");
		String nickName = MapUtils.getString(map, "nickname");
		Users user = userService.selectByOpenid(openId);
		if(!user.getHeadimg().equals(headImg) || !user.getNickname().equals(nickName)){
			user.setHeadimg(headImg);
			user.setNickname(nickName);
			dao.update(user);
			logger.info("==更新用户信息结束==");
		}
		logger.info("发送模版消息");
		List<String> datas = new ArrayList<String>();
		datas.add("ACT学堂聊天室");
		datas.add(user.getNickname());
		String msg = wxService.buildMsg(openId,Content.JOINGROUPURL,obj.toString(),"您好，感谢您的分享，点击次消息进入聊天室","若无法加入，请及时联系老师",datas);
		wxService.sendTemplateMsg(msg);
	}
	
	
	
	public Response deleteGroup(String groupid)throws Exception{
		logger.info("=删除群组=");
		hxService.deleteGroup(groupid);
		logger.info("=删除本地群组=");
		dao.remove(Groups.class, groupid);
		logger.info("=删除群成员关系绑定=");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupid", groupid);
		
		String sql = "delete from UserGroup where groupid = :groupid";
		
		dao.executeUpdate(sql, map);
		logger.info("=删除聊天记录=");
		String sql1 = "delete from GroupHistory where groupId = :groupid";
		dao.executeUpdate(sql1, map);
		
		return Response.SUCCESS(); 
	}
	
	
	
	
	//查询全部群组
	public Response groupList(String groupname)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		
		String sql = "from Groups where state = '0' ";
		
		if(StringUtil.isNotBlank(groupname)){
			sql += " and groupname like :groupname";
			map.put("groupname", "'%"+groupname+"%'");
		}
		
		List<Groups> list = dao.queryList(sql, map);
		
		return Response.SUCCESS().put("list", list);
	}
	
	//查询群组下的成员
	public Response groupMembers(String groupid)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String sql = "from Users where userid in (select userid from UserGroup where state ='0' and groupid = :groupid)";
		
		map.put("groupid", groupid);
		
		List list = dao.queryListObj(sql, map);
		
		return Response.SUCCESS().put("list", list);
	}
	
	
	
	//加入群组
	public Response joinGroup(String groupid,String userid) throws Exception{
		logger.info("==joinGroup=={},{}",groupid,userid);
		int count = selectByUserIdAndGroupid(groupid,userid);
		if(count < 1){
			Users user = dao.queryObject(Users.class, userid);
			
			hxService.addMember(groupid,user.getUsername());
			
			UserGroup ug = new UserGroup();
			ug.setGroupid(groupid);
			ug.setId(IdBuilder.getID());
			ug.setUserid(userid);
			ug.setState(YesOrNo.yes.value);
			
			dao.add(ug);
		}
		return Response.SUCCESS();
	}
	
	
	   //查看某个人是否在某个群组
	   public int selectByUserIdAndGroupid(String groupid,String userid) throws Exception{
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			String sql = "from UserGroup where groupid = :groupid and userid = :userid ";
			
			map.put("groupid", groupid);
			map.put("userid", userid);
			
			return dao.queryCount(sql, map);
		}
	
	
	
	//创建群组
	public Response createGroup(String groupName,String userId,String desc) throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "from  Groups where groupname like :groupname";
		
		map.put("groupname", groupName);
		
		int count = dao.queryCount(sql, map);
		
		if(count > 0){
			return Response.FAIL("该群组名称存在！");
		}
		
		Users user = dao.queryObject(Users.class, userId);
		
		String groupId = hxService.createGroup(groupName, user.getUsername(),desc);
		
		dao.add(new Groups(groupId, groupName, desc, userId));
		
		UserGroup ug = new UserGroup();
		ug.setGroupid(groupId);
		ug.setId(IdBuilder.getID());
		ug.setUserid(userId);
		ug.setState(YesOrNo.yes.value);
		dao.add(ug);
		
		return Response.SUCCESS();
	}
	
	//拉入黑名单
	public Response removeUserForGroup(String groupid,String userid) throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String sql = "delete from UserGroup where groupid = :groupid and userid = :userid ";
		
		map.put("groupid", groupid);
		map.put("userid", userid);
		
		dao.executeUpdate(sql, map);
		
		Users user = dao.queryObject(Users.class, userid);
		
		hxService.addToBlacklist(groupid,user.getUsername());
		
		return Response.SUCCESS();
	}

}
