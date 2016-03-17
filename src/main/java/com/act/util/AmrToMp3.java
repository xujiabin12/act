package com.act.util;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncodingAttributes;
import java.io.File;
import java.net.URL;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AmrToMp3 {
	
	private static Logger logger = LoggerFactory.getLogger(AmrToMp3.class);

	
	public static void main(String[] s){
//		downloadFromUrl("http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=vbRF54HKCnmCjYmQc0-KVD1SYblYC6cPr3yLp3oCdYAjpC46ZATp0vi3iUm_khqEQdojYIC2f-OkJgRSj80qTRgMkfA0PsES9eZYSf4ss4BBTsySajp0yjSfEs0_4NngAJIfAAAJSR&media_id=tej_cgZ7IKB42r1478jvgc7J-Ij3TZs2wyhrjYTvX_U-my3efxz8xHFJRDBZ1LWv",
//				"F://","ssss");
	
//	
	}
	
	 

	
	public static String downloadFromUrl(String url,String dir,String name,String httpUrl) {  
		  logger.info("downloadFromUrl,url:{}"+url);
        try {  
            URL httpurl = new URL(url);  
            File fource = new File(dir + name+".amr");  
            FileUtils.copyURLToFile(httpurl, fource);  
            logger.info("下载完毕，开始转换MP3");
            changeToMp3(fource,new File(dir + name+".mp3"));
            logger.info("转换MP3成功");
        } catch (Exception e) {  
//        	logger.error("downloadFromUrl==出错",e);
//            e.printStackTrace();  
        }   
        return httpUrl+name+".mp3";
    }  

		public static void changeToMp3(File source,File target) throws Exception{
			AudioAttributes audio = new AudioAttributes();
			Encoder encoder = new Encoder();
			audio.setCodec("libmp3lame");
			EncodingAttributes attrs = new EncodingAttributes();
			attrs.setFormat("mp3");
			attrs.setAudioAttributes(audio);
			encoder.encode(source, target, attrs);
		}
}
