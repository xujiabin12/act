package com.act.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;


public class Md5ConverterUtil {
	
	public static String Md5(String plainText) {
		String afterStr = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			afterStr = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return afterStr;
	}
	
   public static String MD5Encode(String origin,String charset){
        try {
            return DigestUtils.md5Hex(origin.getBytes(charset));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
   
   
   public static void main(String[] args){
	   System.out.println(Md5("tjw"+IdBuilder.getID()));
   }

}
