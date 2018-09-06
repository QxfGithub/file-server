package com.qxf.fileserver.Util;

import com.qxf.fileserver.Exception.BizException;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class ImageUploadUtils {

	private final static String BASE64_PREFIX = "data:image";
	private final static String IMAGE_TYPE = "/jpg;base64,";
	
	
	/**
     * 将图片转换成Base64编码
     * @param imgFile 待处理图片
     * @return
     */
    public static String getImgStr(String imgFile){
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(imgFile);        
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } 
        catch (IOException e){
            e.printStackTrace();
            throw new BizException("get.image.error");
        }
        return new String(Base64.getEncoder().encode(data));
    }
    
    /**
     * 将图片转换成Base64编码,返回图片名字以及对应的图片流
     * @param imgFile
     * @return
     */
    public static Map<String,String> getImgStrMap(String imgFile){
    	Map<String,String> map = new HashMap<String, String>();
    	String[] firstStr = imgFile.split("/");
    	String imgType = firstStr[firstStr.length -1];
    	
    	InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(imgFile);        
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } 
        catch (IOException e){
            e.printStackTrace();
            throw new BizException("get.image.error");
        }
        
        map.put(imgType, BASE64_PREFIX + IMAGE_TYPE + new String(Base64.getEncoder().encode(data)));
        return map;
    }
    
   /**
    * 保存单个图片到本地，返回图片路径
    * @param imgType
    * @param imgStr
    * @return
    */
	public static Map<String,String> saveImageToLocal(String imgType,String imgStr,String filePath){
		//去除插件返回的base64编码的字符串前缀
		String aimImgStr = imgStr;
		if(imgStr.startsWith(BASE64_PREFIX)){
			aimImgStr = imgStr.substring(imgStr.indexOf(",") + 1, imgStr.length());
		}
		
		File file = new File(filePath);
        if(!file.exists()){
        	file.mkdirs();//创建目录
        }
		Map<String,String> retMap = new HashMap<String, String>();
		try{
        	byte[] b = Base64.getDecoder().decode(aimImgStr);
            for(int i=0;i<b.length;++i){
                if(b[i]<0){
                    b[i]+=8192;
                }
            }
            String imgFilePath = filePath + generateTime() + "-" + imgType + ".jpg";
            File f = new File(imgFilePath);
            FileOutputStream out = new FileOutputStream(f);    
            out.write(b);
            out.flush();
            out.close();
            retMap.put(imgType, imgFilePath);
            return retMap;
        } 
        catch (Exception e) {
        	e.printStackTrace();
        	throw new BizException("save.image.to.local.error");
        }
    }
	
	/**
	 * 保存批量图片到本地,返回图片路径
	 * @param imgsMap
	 * @return
	 */
	public static Map<String,String> saveImagesToLocal(Map<String,String> imgsMap, String filePath){
		Map<String,String> retMap = new HashMap<String, String>();
		imgsMap.forEach((imgType,imgStr) -> {
			retMap.putAll(saveImageToLocal(imgType,imgStr,filePath));
		});
		return retMap;
	}
	
	private static String generateTime(){
		return new SimpleDateFormat("yyyyMMddHHmmsss").format(new Date());
	}
	
}
