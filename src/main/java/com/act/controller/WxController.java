package com.act.controller;


import java.io.File;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.act.services.WxServices;
import com.act.util.AmrToMp3;
import com.act.util.Properties;
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
	
	@Autowired
    RedisTemplate redisTemplate;
	
	@Autowired
	Properties util;
	
	@ResponseBody
	@RequestMapping(value = "/placeVoice",method=RequestMethod.POST)
	public String placeVoice(@RequestParam("voiceId") String voiceId){
		logger.info("placeVoice，voiceId:{}",voiceId);
		
		Object obj = redisTemplate.opsForValue().get(voiceId);
		if(obj != null){
			return Response.SUCCESS().put("url", String.valueOf(obj)).toJson();
		}
		
		File file = new File(util.getVoiceUpload()+voiceId+".mp3");
		if(file.exists()){
			logger.info("目录存在该文件");
			return Response.SUCCESS().put("url", util.getVoiceUrl()+voiceId+".mp3").toJson();
		}else{
			logger.info("目录不存在该文件，去下载");
			String url = wxService.getDownVoiceUrl(voiceId);
			logger.info("downUrl:{}",url);
			String webUrl = AmrToMp3.downloadFromUrl(url, util.getVoiceUpload(), voiceId,util.getVoiceUrl());
			logger.info("下载完毕，返回访问的URL：{}",webUrl);
			return Response.SUCCESS().put("url", webUrl).toJson();
		}
	} 
	
	@ResponseBody
	@RequestMapping(value = "/uploadVoiceId",method=RequestMethod.POST)
	public String uploadVoiceId(@RequestParam("voiceId") String voiceId){
		logger.info("uploadVoiceId，voiceId:{}",voiceId);
		
		String url = wxService.getDownVoiceUrl(voiceId);
		
		logger.info("downUrl:",url);
		
		String webUrl = AmrToMp3.downloadFromUrl(url, util.getVoiceUpload(), voiceId,util.getVoiceUrl());
		if(StringUtil.isNotBlank(webUrl)){
			redisTemplate.opsForValue().set(voiceId, webUrl,2,TimeUnit.DAYS);
		}
		return Response.SUCCESS().toJson();
	}
	
	
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
