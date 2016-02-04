package com.act.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.act.beans.enums.YesOrNo;
import com.act.dao.CommonDao;
import com.act.dao.bean.Groups;
import com.act.dao.bean.UserGroup;
import com.act.dao.bean.Users;
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
	CommonDao dao;
	
	
	
	public Response deleteGroup(String groupid)throws Exception{
		
		hxService.deleteGroup(groupid);
		
		return Response.SUCCESS(); 
	}
	
	
	
	
	//查询全部群组
	public Response groupList(String groupname)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		
		String sql = "from Groups where state = '1' ";
		
		if(StringUtil.isNotBlank(groupname)){
			sql += " and groupname like :groupname";
			map.put("groupname", "'%"+groupname+"%'");
		}
		
		List<Groups> list = dao.queryList(sql, map);
		
		return Response.SUCCESS().putAll(JsonUtil.Object2Map(list));
	}
	
	//查询群组下的成员
	public Response groupMembers(String groupid)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String sql = "from Users where userid in (select userid from usergroup where groupid = :groupid)";
		
		map.put("groupid", groupid);
		
		List<Users> list = dao.queryList(sql, map);
		
		return Response.SUCCESS().putAll(JsonUtil.Object2Map(list));
	}
	
	
	
	//加入群组
	public Response joinGroup(String groupid,String userid) throws Exception{
		logger.info("==joinGroup=={},{}",groupid,userid);
		
		Users user = dao.queryObject(Users.class, userid);
		
		hxService.addMember(groupid,user.getUsername());
		
		UserGroup ug = new UserGroup();
		ug.setGroupid(groupid);
		ug.setId(IdBuilder.getID());
		ug.setUserid(userid);
		ug.setState(YesOrNo.yes.value);
		
		dao.add(ug);
		
		
		return Response.SUCCESS();
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
		
		String sql = "update Groups set state = '1' where groupid = :groupid and userid = :userid ";
		
		map.put("groupid", groupid);
		map.put("userid", userid);
		
		dao.executeUpdate(sql, map);
		
		Users user = dao.queryObject(Users.class, userid);
		
		hxService.addToBlacklist(groupid,user.getUsername());
		
		return Response.SUCCESS();
	}

}
