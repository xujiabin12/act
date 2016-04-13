package base;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UrlEn {

	
	
	public static void main(String[] args) throws UnsupportedEncodingException{
		
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx1ffebf2bd2e6fe48&redirect_uri=";
		
		String content = "http://mobile.u-ef.cn/ACT/index.html";
	
		content = URLEncoder.encode(content, "utf-8");
		
		url = url + content;
	
		url  = url + "&response_type=code&scope=snsapi_base&state=act#wechat_redirect";
		
		System.out.println(url);
	
	}
	
	
	
}
