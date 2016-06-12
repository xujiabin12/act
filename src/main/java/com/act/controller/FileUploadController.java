package com.act.controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.act.util.Properties;
import com.act.util.Response;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 图片上传
 * 
 * @author jiabin
 *
 */
@Controller
@RequestMapping(value="/upload",produces = "application/json; charset=utf-8")
public class FileUploadController {
	
	private Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	
	@Autowired
	Properties properties;
	
	
	
	
	// 处理文件上传
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	@ResponseBody
	public String fileUpload(@RequestParam("fileToUpload") MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws IOException {
		logger.info("==上传图片==");
		try { 
			File folder = new File(properties.getImgUpload());
			if (!folder.exists() && !folder.isDirectory()) {
				folder.mkdirs();
			}
			String fileName = file.getOriginalFilename();
			fileName = System.currentTimeMillis() + "-" + fileName;//+ StringUtils.substringAfterLast(fileName, ".");
			File localFile = new File(folder, fileName);
			file.transferTo(localFile);
			String smallName = "small-"+fileName;
//			cutImage(localFile,smallName,100,100);
			makeSmallImage(localFile,smallName);
			return Response.SUCCESS().put("url", properties.getImgUrl().concat(fileName)).put("smallUrl", properties.getImgUrl().concat(smallName)).toJson();
		} catch (Exception e) {
			logger.error("上传图片出错",e);
			e.printStackTrace();
			return Response.FAIL("发送图片失败").toJson();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 生成缩略图
	 * @param filePath 图片路径
	 * @throws Exception
	 */
	public  void makeSmallImage(File srcImageFile,String fileName) throws Exception {
        FileOutputStream fileOutputStream = null;
        JPEGImageEncoder encoder = null;
        BufferedImage tagImage = null;
        Image srcImage = null;
        try{
            srcImage = ImageIO.read(srcImageFile);
            int srcWidth = srcImage.getWidth(null);//原图片宽度
            int srcHeight = srcImage.getHeight(null);//原图片高度
            int dstMaxSize = 200;//目标缩略图的最大宽度/高度，宽度与高度将按比例缩写
            int dstWidth = srcWidth;//缩略图宽度
            int dstHeight = srcHeight;//缩略图高度
            float scale = 0;
            //计算缩略图的宽和高
            if(srcWidth>dstMaxSize){
                dstWidth = dstMaxSize;
                scale = (float)srcWidth/(float)dstMaxSize;
                dstHeight = Math.round((float)srcHeight/scale);
            }
            srcHeight = dstHeight;
            if(srcHeight>dstMaxSize){
                dstHeight = dstMaxSize;
                scale = (float)srcHeight/(float)dstMaxSize;
                dstWidth = Math.round((float)dstWidth/scale);
            }
            //生成缩略图
            tagImage = new BufferedImage(dstWidth,dstHeight,BufferedImage.TYPE_INT_RGB);
            tagImage.getGraphics().drawImage(srcImage,0,0,dstWidth,dstHeight,null);
            // 保存缩放后的图片   
            fileOutputStream = new FileOutputStream(new File(srcImageFile.getParent(),fileName));
            encoder = JPEGCodec.createJPEGEncoder(fileOutputStream);
            encoder.encode(tagImage);
            fileOutputStream.close();
            fileOutputStream = null;
        }finally{
            if(fileOutputStream!=null){
                try{
                    fileOutputStream.close();
                }catch(Exception e){
                }
                fileOutputStream = null;
            }
            encoder = null;
            tagImage = null;
            srcImage = null;
            System.gc();
        }
    }
	
	
	
	
	
	
	
	/**
	 * 处理图片
	 * @param srcPath   文件的路径
	 * @param width    自定义缩略图大小
	 * @param height
	 * @throws IOException
	 */
	public static void cutImage(File srcFile, String fileName,int width, int height) throws IOException {   
//	    File srcFile = new File(srcPath);    
	    BufferedImage image = ImageIO.read(srcFile);    
	    int srcWidth = image.getWidth(null);    
	    int srcHeight = image.getHeight(null);    
	    int newWidth = 0, newHeight = 0;    
	    int x = 0, y = 0;    
	    double scale_w = (double)width/srcWidth;    
	    double scale_h = (double)height/srcHeight;    
	    //按原比例缩放图片    
	    if(scale_w < scale_h) {    
	        newHeight = height;    
	        newWidth = (int)(srcWidth * scale_h);    
	        x = (newWidth - width)/2;    
	    } else {    
	        newHeight = (int)(srcHeight * scale_w);    
	        newWidth = width;    
	        y = (newHeight - height)/2;    
	    }    
	    BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);    
	    newImage.getGraphics().drawImage(    
	    image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);    
	    // 保存缩放后的图片   
	    String fileSufix = srcFile.getName().substring(srcFile.getName().lastIndexOf(".") + 1);    
	    File destFile = new File(srcFile.getParent(), fileName); 
	    // ImageIO.write(newImage, fileSufix, destFile);    
	    // 保存裁剪后的图片    
	    ImageIO.write(newImage.getSubimage(x, y, width, height), fileSufix, destFile);    
	} 

}
