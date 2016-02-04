package com.act.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.act.services.WxServices;
import com.act.util.Response;
import com.act.util.StringUtil;
import com.act.util.wx.SignUtil;
import com.act.util.wx.WxConfig;


@Controller
@RequestMapping(value="/wx",produces = "application/json; charset=utf-8")
public class WxController {
	
	private Logger logger = LoggerFactory.getLogger(WxController.class);
	
	@Autowired
	WxServices wxService;
	
	
	@ResponseBody
	@RequestMapping(value = "/initWxJsApi",method=RequestMethod.POST)
	public String initWxJsApi(String url){
		logger.info("返回调用微信jsAPI需要的signature签名参数，url:{}", new Object[] {url});
		try {
			long timestamp=System.currentTimeMillis();
			String noncestr=StringUtil.createRandomStr();
			String ticket=wxService.getJsApiTicket();
			String signature= SignUtil.GetSignature( ticket,  noncestr,  timestamp,  url);
			return Response.SUCCESS().put("signature",signature ).put("timestamp",timestamp ).put("noncestr",noncestr ).put("appId",WxConfig.APPID).toJson();
		} catch (Exception e) {
			return Response.FAIL("初始化jsapi失败").toJson();
		}
	}
	
	
	

}
