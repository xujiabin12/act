package base;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UrlEn {

	
	
	public static void main(String[] args) throws UnsupportedEncodingException{
		
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx1ffebf2bd2e6fe48&redirect_uri=";
		
		String s = "http://mobile.u-ef.cn/index.html?groupId=157079443297272296";
	
		s = URLEncoder.encode(s, "utf-8");
		
		url = url + s;
	
		url  = url + "&response_type=code&scope=snsapi_base&state=act#wechat_redirect";
		
		System.out.println(url);
	
	}
	
	
	
}
