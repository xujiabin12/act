package com.act.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpUtil {

	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	 static String contentType = "application/json";

	public static String doPost(String url, Map<String, String> params) throws Exception{
		StringBuffer sb = new StringBuffer();
		if (!params.isEmpty()) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
		}
		return sendRequestByPostGiveContentType(url,sb.toString(),contentType,"POST");
		 
	}

	public static String doPost(String url, String param) throws Exception{//application/x-www-form-urlencoded
		return sendRequestByPostGiveContentType(url,param,contentType,"POST");
		 
	}
	
	public static String doGet(String url, String param,String token) throws Exception{//application/x-www-form-urlencoded
		return sendRequestByPostGiveContentTypeAndToken(url,param,token,contentType,"GET");
		 
	}
	
	public static String doPost(String url, String param,String token) throws Exception{//application/x-www-form-urlencoded
		return sendRequestByPostGiveContentTypeAndToken(url,param,token,contentType,"POST");
		 
	}
	
	
	public static void doDelete(String url, String param,String token) throws Exception{//application/x-www-form-urlencoded
		 sendRequestByDeleteAndToken(url,param,token,contentType,"DELETE");
		 
	}

	 

	
	public static String doGet(String url)throws Exception{
		String result = "";
        BufferedReader in = null;
        HttpURLConnection connection =null;
		try {
			 URL u = new URL(url);
			  connection =(HttpURLConnection) u.openConnection();
			 connection.setRequestProperty("Content-Type", contentType);
	         connection.setRequestMethod("GET");
	         connection.setDoOutput(true); //http正文内，因此需要设为true, 默认情况下是false
	         connection.setDoInput(true); //设置是否从httpUrlConnection读入，默认情况下是true
	         connection.setUseCaches(false); //Post 请求不能使用缓存
	         connection.setInstanceFollowRedirects(true); //URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数
	         connection.setConnectTimeout(30000); //设置连接主机超时时间
	         connection.setReadTimeout(30000); //设置从主机读取数据超时
	         in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
	         String line = in.readLine();  
	         while (line != null) {  
	             result += line;  
	             line = in.readLine();  
	         }  
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			 if (connection != null) {
                 connection.disconnect();
             }
			 if (in != null) {
                 in.close();
             }
		}
         return result;
	}
	
	public static String doClientGet(String url)throws Exception{
		logger.info("===={}===",url);
		HttpClient httpClient = new HttpClient(new HttpClientParams(),
				new SimpleHttpConnectionManager(true));
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);//连接响应时间
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);//读取响应时间
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");//设置请求的编码方式
		httpClient.getParams().setContentCharset("UTF-8");
		GetMethod method = new GetMethod(url);
		method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");
		httpClient.executeMethod(method);
		return method.getResponseBodyAsString();
	}
	
	public static void sendRequestByDeleteAndToken(String url, String paramStr, String token, String contentType,String method)throws Exception{
		logger.info("url:{}",url);
		logger.info("paramStr:{}",paramStr);
        BufferedReader in = null;
        HttpURLConnection connection = null;
        try {
            URL paostUrl = new URL(url);
            //参数配置
            connection = (HttpURLConnection) paostUrl.openConnection();
            connection.setRequestProperty("Authorization", "Bearer "+token);
            connection.setRequestProperty("Content-Type", contentType);
            connection.setRequestMethod(method);
//            connection.setDoOutput(true); //http正文内，因此需要设为true, 默认情况下是false
//            connection.setDoInput(true); //设置是否从httpUrlConnection读入，默认情况下是true
//            connection.setUseCaches(false); //Post 请求不能使用缓存
//            connection.setInstanceFollowRedirects(true); //URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数
            connection.setConnectTimeout(30000); //设置连接主机超时时间
            connection.setReadTimeout(30000); //设置从主机读取数据超时
            
          System.out.println( connection.getResponseCode());
            
        } catch (Exception e) {
            logger.error("post请求发生异常"+",url="+url+",paramStr:"+paramStr, e);
            throw e;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (IOException e) {
                logger.error("error", e);
            }
        }
    
	}
	
	
	
	public static String sendRequestByPostGiveContentTypeAndToken(String url, String paramStr, String token, String contentType,String method)throws Exception{
		logger.info("url:{}",url);
		logger.info("paramStr:{}",paramStr);
        String result = "";
        BufferedReader in = null;
        HttpURLConnection connection = null;
        OutputStream out = null;
        try {
            URL paostUrl = new URL(url);
            //参数配置
            connection = (HttpURLConnection) paostUrl.openConnection();
            connection.setRequestProperty("Authorization", "Bearer "+token);
            connection.setRequestProperty("Content-Type", contentType);
            connection.setRequestMethod(method);
            connection.setDoOutput(true); //http正文内，因此需要设为true, 默认情况下是false
            connection.setDoInput(true); //设置是否从httpUrlConnection读入，默认情况下是true
            connection.setUseCaches(false); //Post 请求不能使用缓存
            connection.setInstanceFollowRedirects(true); //URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数
            connection.setConnectTimeout(30000); //设置连接主机超时时间
            connection.setReadTimeout(30000); //设置从主机读取数据超时
            
            //打开连接
            out = connection.getOutputStream();
            if(StringUtil.isNotBlank(paramStr)){
            	out.write(paramStr.getBytes());
            }
            
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line = in.readLine();  
            while (line != null) {  
                result += line;  
                line = in.readLine();  
            }  
        } catch (Exception e) {
            logger.error("post请求发生异常"+",url="+url+",paramStr:"+paramStr, e);
            throw e;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (IOException e) {
                logger.error("error", e);
            }
        }
        logger.info("请求返回结果：{}",result);
        return result;
    
	}
	

	public static String sendRequestByPostGiveContentType(String url, String paramStr,  String contentType,String method)throws Exception{
		logger.info("url:{}",url);
		logger.info("paramStr:{}",paramStr);
        String result = "";
        BufferedReader in = null;
        HttpURLConnection connection = null;
        OutputStream out = null;
        try {
            URL paostUrl = new URL(url);
            //参数配置
            connection = (HttpURLConnection) paostUrl.openConnection();
            connection.setRequestProperty("Content-Type", contentType);
            connection.setRequestMethod(method);
            connection.setDoOutput(true); //http正文内，因此需要设为true, 默认情况下是false
            connection.setDoInput(true); //设置是否从httpUrlConnection读入，默认情况下是true
            connection.setUseCaches(false); //Post 请求不能使用缓存
            connection.setInstanceFollowRedirects(true); //URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数
            connection.setConnectTimeout(30000); //设置连接主机超时时间
            connection.setReadTimeout(30000); //设置从主机读取数据超时
            
            //打开连接
            out = connection.getOutputStream();
            out.write(paramStr.getBytes());
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line = in.readLine();  
            while (line != null) {  
                result += line;  
                line = in.readLine();  
            }  
        } catch (Exception e) {
            logger.error("post请求发生异常"+",url="+url+",paramStr:"+paramStr, e);
            throw e;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (IOException e) {
                logger.error("error", e);
            }
        }
        logger.info("请求返回结果：{}",result);
        return result;
    
	}

	/**
	 * @param url 请求的新接口路径
	 * @param method 请求方式（post）
	 * @return
	 * @throws IOException 
	 */
	public static String getResultMessage(String url, String params, String method) throws IOException {
		String re = "";
		URL sendurl = new URL(url);
		HttpURLConnection httpUrl = (HttpURLConnection) sendurl.openConnection();
		httpUrl.setDoInput(true);
		httpUrl.setDoOutput(true);
		httpUrl.setUseCaches(false);
		httpUrl.setConnectTimeout(300 * 1000);
		httpUrl.setReadTimeout(300 * 1000);
		httpUrl.setInstanceFollowRedirects(true);
		httpUrl.setRequestMethod(method);//POST||GET

		//         logger.info("put-url:"+url+"\n"+httpUrl.getURL()+params+"\n");
		logger.info("----------------------");
		logger.info("url:" + url);
		logger.info("requestmethod:  " + method);
		logger.info("params: " + params);
		httpUrl.connect();
		httpUrl.getOutputStream().write(params.getBytes("UTF-8"));
		PrintWriter out = new PrintWriter(httpUrl.getOutputStream());
		out.flush();
		out.close();
		BufferedReader in = new BufferedReader(new InputStreamReader(httpUrl.getInputStream(), "UTF-8"));
		while (in.ready()) {
			re = re + in.readLine();
			httpUrl.disconnect();
			//	         logger.debug("POST SUBMIT TO SERVICE reString: "+ re);
		}
		return re;

	}
}
