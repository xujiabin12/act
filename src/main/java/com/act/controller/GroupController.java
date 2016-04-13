package com.act.controller;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.act.beans.enums.ErrorCode;
import com.act.dao.bean.GroupHistory;
import com.act.dao.bean.Users;
import com.act.exception.UeFailException;
import com.act.services.GroupSerices;
import com.act.services.UserServices;
import com.act.services.WxServices;
import com.act.util.Content;
import com.act.util.IdBuilder;
import com.act.util.JsonUtil;
import com.act.util.Response;
import com.act.util.wx.WxConfig;
 




@Controller
@RequestMapping(value="/groups",produces = "application/json; charset=utf-8")
public class GroupController extends AbstractController{
	
	
	private Logger logger = LoggerFactory.getLogger(GroupController.class);
	
	
	@Autowired
	GroupSerices groupService;
	
	@Autowired 
	UserServices userService;
	
	
	@Autowired
	WxServices wxService;
	
	
	@ResponseBody
	@RequestMapping(value = "/queryHistory",method=RequestMethod.POST)
	public String queryHistory(@RequestParam(value="groupId",required=true)String groupId,
							   @RequestParam(value="pageNo",required=true)int pageNo){
		
		return groupService.queryHistoryByPage(groupId, pageNo).toJson();
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/sendMsg",method=RequestMethod.POST)
	public String sendMsg(@RequestParam(value="groupId",required=true)String groupId,
							   @RequestParam(value="nickName",required=true)String nickName,
							   @RequestParam(value="userName",required=true)String userName,
							   @RequestParam(value="headimg",required=true)String headimg,
							   @RequestParam(value="message",required=true)String message,
							   @RequestParam(value="role",required=true)String role){
		GroupHistory g = new GroupHistory(IdBuilder.getID(),groupId,nickName,userName,headimg,role,message);
		groupService.saveMsg(g);;
		
		return Response.SUCCESS().toJson();
	}
	
	
	
	
	@ResponseBody
	@RequestMapping(value = "/createUrl",method=RequestMethod.POST)
	public String createUrl(@RequestParam(value="groupId",required=true)String groupId){
		logger.info("创建加入群组URL:{}", groupId);
		
		StringBuffer sb = new StringBuffer(WxConfig.STARTAUTHURL);
		
		String domain = Content.INDEX.concat("?groupId="+groupId);
		try {
			domain = URLEncoder.encode(domain, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		sb.append(domain).append(WxConfig.ENDAUTHURL);
		
		return Response.SUCCESS().put("url", sb.toString()).toJson();
	}
	
	
	//点击这个连接，进入群组
	@RequestMapping(value="/joinGroup",method=RequestMethod.POST)
	@ResponseBody
	public String joinGroup(@RequestParam(value="groupId",required=true)String groupId,
							@RequestParam(value="code",required=false)String code,
							@RequestParam(value="openId",required=false)String openId){
		try {
			logger.info("joinGroup===groupid:{}，code:{},openId:{}",new String[]{groupId,code,openId});
			if(StringUtils.isBlank(openId)){
				openId = wxService.getOpenIdByCode(code);
			}
			
			Users user = userService.selectByOpenid(openId);
			if(user == null){
				logger.info("==新增用户==");
				String userInfo = wxService.getWxUserWxInfo(openId);
				Map map = JsonUtil.json2Map(userInfo);
				
				if(String.valueOf(map.get("subscribe")).equals("0")){
					return Response.FAIL(ErrorCode.NOGUANZHU).toJson();
				}
				user = userService.addUser(map);
			}
			logger.info("加入群组");
			groupService.joinGroup(groupId, user.getUserid());
			
			return Response.SUCCESS().putAll(JsonUtil.Object2Map(user)).toJson();
			
		}catch (UeFailException e) {
			return Response.FAIL(e.getMessage()).toJson();
		}catch(Exception e1){
			logger.error("错误",e1);
			return Response.FAIL("进入群组失败").toJson();
		}
		
	}
	
	
	
	 //个人加入群组
	@RequestMapping(value="/addToGroup",method=RequestMethod.POST)
	@ResponseBody
	public String addToGroup(@RequestParam(value="groupid",required=true)String groupid,
							@RequestParam(value="userid",required=true)String userid){
		try {
			logger.info("joinGroup===groupid:{}，userid:{}",groupid,userid);
			 
			logger.info("加入群组");
			
			return groupService.joinGroup(groupid, userid).toJson();
			
		}catch (UeFailException e) {
			return Response.FAIL(e.getMessage()).toJson();
		}catch(Exception e1){
			logger.error("错误",e1);
			return Response.FAIL("加入群组失败").toJson();
		}
		
	}
	
	
	
	    //创建群组
		@RequestMapping(value="/createGroup",method=RequestMethod.POST)
		@ResponseBody
		public String createGroup(@RequestParam(value="groupName",required=true)String groupName,
								@RequestParam(value="userId",required=true)String userId,
								@RequestParam(value="desc",required=true)String desc){
			try {
				
				return groupService.createGroup(groupName, userId, desc).toJson();
				
			}catch (UeFailException e) {
				return Response.FAIL(e.getMessage()).toJson();
			}catch(Exception e1){
				logger.error("错误",e1);
				return Response.FAIL("创建群组失败").toJson();
			}
			
		}
		
		
		//群组列表
		@RequestMapping(value="/groupList",method=RequestMethod.POST)
		@ResponseBody
		public String groupList(@RequestParam(value="groupName",required=false)String groupName){
			try {
				
				return groupService.groupList(groupName).toJson();
				
			}catch (UeFailException e) {
				return Response.FAIL(e.getMessage()).toJson();
			}catch(Exception e1){
				logger.error("错误",e1);
				return Response.FAIL("查询群组失败").toJson();
			}
		}
		
		
		//群组成员
		@RequestMapping(value="/groupMembers",method=RequestMethod.POST)
		@ResponseBody
		public String groupMembers(@RequestParam(value="groupid",required=true)String groupid){
			try {
				
				return groupService.groupMembers(groupid).toJson();
				
			}catch (UeFailException e) {
				return Response.FAIL(e.getMessage()).toJson();
			}catch(Exception e1){
				logger.error("错误",e1);
				e1.printStackTrace();
				return Response.FAIL("查询群组成员失败").toJson();
			}
		}
		
		
		
		
		//删除群组
		@RequestMapping(value="/removeGroup",method=RequestMethod.POST)
		@ResponseBody
		public String removeGroup(@RequestParam(value="groupid",required=true)String groupid){
			try {
				
				return groupService.deleteGroup(groupid).toJson();
				
			}catch (UeFailException e) {
				return Response.FAIL(e.getMessage()).toJson();
			}catch(Exception e1){
				logger.error("错误",e1);
				return Response.FAIL("删除群组失败").toJson();
			}
			
		}
		
		
		//删除群组
		@RequestMapping(value="/outGroup",method=RequestMethod.POST)
		@ResponseBody
		public String outGroup(@RequestParam(value="groupid",required=true)String groupid,
							   @RequestParam(value="userid",required=true)String userid){
			try {
				
				return groupService.removeUserForGroup(groupid, userid).toJson();
				
			}catch (UeFailException e) {
				return Response.FAIL(e.getMessage()).toJson();
			}catch(Exception e1){
				logger.error("错误",e1);
				return Response.FAIL("删除群组失败").toJson();
			}
			
		}
		
		
		//设置加入群组URL
		@RequestMapping(value="/setJoinGroupUrl",method=RequestMethod.POST)
		@ResponseBody
		public String setJoinGroupUrl(@RequestParam(value="url",required=true)String url){
			
			groupService.setJoinGroupUrl(url);
			
			return Response.SUCCESS().toJson();
			
		}
		
		//设置加入群组URL
		@RequestMapping(value="/getJoinGroupUrl",method=RequestMethod.POST)
		@ResponseBody
		public String getJoinGroupUrl(){
			
			return groupService.getJoinGroupUrl().toJson();
			
		}
	
	

}
