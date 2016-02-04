package com.act.util.wx;

import org.springframework.data.redis.core.script.DigestUtils;

public class SignUtil {
	
	
	
	/**
	 * sha1签名算法
	 * @param jsapi_ticket
	 * @param noncestr
	 * @param timestamp
	 * @param url
	 * @return
	 */
   public static String GetSignature(String jsapi_ticket, String noncestr, long timestamp, String url)
   {
   	StringBuilder string1Builder = new StringBuilder();
       string1Builder.append("jsapi_ticket=").append(jsapi_ticket).append("&")
                     .append("noncestr=").append(noncestr).append("&")
                     .append("timestamp=").append(timestamp).append("&")
                     .append("url=").append(url.indexOf("#") >= 0 ? url.substring(0, url.indexOf("#")) : url);
       String string1 = string1Builder.toString();
       return DigestUtils.sha1DigestAsHex(string1);
   }

}
