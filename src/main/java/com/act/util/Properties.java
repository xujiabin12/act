package com.act.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class Properties {
 
			
	
	@Value("${appKey}")
	private String appKey;
	
	@Value("${clientId}")
	private String clientId;
	
	@Value("${clientSecret}")
	private String clientSecret;
	
	@Value("${voiceUpload}")
	private String voiceUpload;

	
	@Value("${voiceUrl}")
	private String voiceUrl;
	
	@Value("${imgUpload}")
	private String imgUpload;
	@Value("${imgUrl}")
	private String imgUrl;


	
	
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getImgUpload() {
		return imgUpload;
	}

	public void setImgUpload(String imgUpload) {
		this.imgUpload = imgUpload;
	}

	public String getVoiceUpload() {
		return voiceUpload;
	}

	public void setVoiceUpload(String voiceUpload) {
		this.voiceUpload = voiceUpload;
	}

	public String getVoiceUrl() {
		return voiceUrl;
	}

	public void setVoiceUrl(String voiceUrl) {
		this.voiceUrl = voiceUrl;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	 

	
	 
	
	
	
	
	
}
