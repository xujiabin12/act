package base;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.act.services.GroupSerices;
import com.act.services.HxServices;
import com.act.services.UserServices;


public class TestServices extends TestEnviroment{
	
	/**
	 * 佳彬群组 "groupid": "157015753936077264"
	 * 
	 * 张三群组 "groupid": "157071833605079516"
	 * 
	 * 李四群组 "groupid": "157079443297272296"
	 * 
	 * 
		"username": "uebbef6abf",
		"nickname": "简单佳彬"
		
		ue541d08c1
		简单张三
		
	 */
	
	@Autowired
	GroupSerices groupService;
	
	@Autowired
	UserServices userService;
	
	@Autowired
	HxServices hxService;
	
	@Test
	public void testServer()throws Exception{
		
		JSONObject js = new JSONObject();
		js.put("nickname", "简单张三");
		js.put("sex", "1");
		js.put("headimgurl", "");
		js.put("openid", "12345679");
		
//		userService.addUser(js.toString());
		
//		groupService.createGroup("李四群组", "190d67586f0c4eacbe24d2e53d3b8b8b", "李四聊天群组");
		
//		groupService.joinGroup("157079443297272296", "b14c588e25414519b5d267603af7389c");
		
		hxService.selectHistory();
		
		
	}

}
