package com.act.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.act.beans.enums.RoleEnums;
import com.act.dao.bean.Users;
import com.act.exception.UeFailException;
import com.act.services.UserServices;
import com.act.util.JsonUtil;
import com.act.util.Response;
 

/**
 * 
 * @author jiabin
 *
 */

@Controller
@RequestMapping(value="/users",produces = "application/json; charset=utf-8")
public class UsersController extends AbstractController{
	
	
	private Logger logger = LoggerFactory.getLogger(UsersController.class);
	
	
	@Autowired
	UserServices userService;
	
	//mgr登录
	@RequestMapping(value="/loginForMgr",method=RequestMethod.POST)
	@ResponseBody
	public String loginForMgr(@RequestParam(value="username",required=true)String username,
							  @RequestParam(value="pwd",required=true)String pwd){
		logger.info("loginForMgr=={}",username);
		try {
			Users u =  userService.login(username,pwd);
			u.setMgrpwd("");
			if(u.getRole().equals(RoleEnums.student.value)){
				return Response.FAIL("登录人权限不对").toJson();
			}
			return Response.SUCCESS().putAll(JsonUtil.Object2Map(u)).toJson();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("loginForMgr",e);
		}
		return Response.FAIL("登录失败").toJson();
	}
	
	//用户列表
	@RequestMapping(value="/userList",method=RequestMethod.POST)
	@ResponseBody
	public String userList(@RequestParam(value="nickname",required=false)String nickname, 
						   @RequestParam(value="username",required=false)String username){
		try {
			List<Users> list = userService.userList(nickname, username);
			 
			return Response.SUCCESS().put("list", list).toJson();
			
		}catch (UeFailException e) {
			return Response.FAIL(e.getMessage()).toJson();
		}catch(Exception e1){
			return Response.FAIL("查询失败").toJson();
		}
	}
	
	//老师列表
	@RequestMapping(value="/teacherList",method=RequestMethod.POST)
	@ResponseBody
	public String teacherList(@RequestParam(value="nickname")String nickname,
						   @RequestParam(value="username")String username){
		try {
			List<Users> list = userService.teacherList(nickname, username);
			 
			return Response.SUCCESS().put("list", list).toJson();
			
		}catch (UeFailException e) {
			return Response.FAIL(e.getMessage()).toJson();
		}catch(Exception e1){
			return Response.FAIL("查询失败").toJson();
		}
	}
	
	
	@RequestMapping(value="/stopSpeak",method=RequestMethod.POST)
	@ResponseBody
	public String stopSpeak(@RequestParam(value="userId",required=true)String userId){
		
		logger.info("stopSpeak=={}",userId);
		 
		return userService.stopSpeak(userId).toJson();
		
	}
	
	
	@RequestMapping(value="/ss",method=RequestMethod.GET)
	@ResponseBody
	public String ss(){
		
		logger.info("stopSpeak==");
		 
		return "fffffffffffff";
		
	}
	
	
	@RequestMapping(value="/isStopSpeak",method=RequestMethod.POST)
	@ResponseBody
	public String isStopSpeak(@RequestParam(value="userId",required=true)String userId){
		
		logger.info("isStopSpeak=={}",userId);
		 
		return userService.isStopSpeak(userId).toJson();
		
	}
	
 
	

}
