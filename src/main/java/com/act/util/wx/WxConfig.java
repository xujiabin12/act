package com.act.util.wx;

public class WxConfig {
	
	
	public final static String TOKENURL = "https://api.weixin.qq.com/cgi-bin/token?";
	
	public final static String APPID = "wx1ffebf2bd2e6fe48";
	
	public final static String SECRECT = "2712e73ca707d7119dd7c0d8882f7de6";
	
	
	public final static String GRANT_TYPE = "authorization_code";
	
	//获取openidurl
	public final static String GETOPENIDURL = "https://api.weixin.qq.com/sns/oauth2/access_token?";
	
	//获取tokenURL
	public final static String GETTOKENURL = TOKENURL + "grant_type=client_credential" + "&appid=" + APPID + "&secret=" + SECRECT;
	
	//获取jsapi ticket
	public final static String JSAPITICKETURL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?";
	
	
	
	public final static String STARTAUTHURL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx1ffebf2bd2e6fe48&redirect_uri=";
	
	public final static String ENDAUTHURL = "&response_type=code&scope=snsapi_base&state=act#wechat_redirect";
	
	

}
