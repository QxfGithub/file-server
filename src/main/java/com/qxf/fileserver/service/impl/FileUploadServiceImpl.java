package com.qxf.fileserver.service.impl;


import com.qxf.fileserver.Exception.BizException;
import com.qxf.fileserver.Util.DateUtils;
import com.qxf.fileserver.Util.FileUtil;
import com.qxf.fileserver.service.FileUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;
@Service
public class FileUploadServiceImpl implements FileUploadService {

    /**
     * 文件存储路径
     */
    @Value("${file.store.path:D:/Program Files/File}")
    private String fileStorePath;

    @Override
    public String upload(MultipartFile file) {

        String fileName = file.getOriginalFilename();
        String originalName = fileName.substring(fileName.lastIndexOf("\\") + 1, fileName.length());
        originalName = originalName.substring(originalName.lastIndexOf("/") + 1, originalName.length());
        String key = UUID.randomUUID().toString();
        String ext = originalName.substring(originalName.lastIndexOf(".") + 1, originalName.length());

        /*if(!StringUtils.upperCase(ext).equals("PNG") && !StringUtils.upperCase(ext).equals("JPG")) {
            throw new BizException("file.format.is.not.supported");
        }*/

        int width = 0;
        int height = 0;
        Long size = file.getSize();

        BufferedImage image = null;
        try {
            image = ImageIO.read(file.getInputStream());
        } catch (Exception e) {

            throw new BizException("ImageIO.read has IOException");
        }
        // image=null 表示上传的不是图片格式
        if (image != null) {
            width = image.getWidth();
            height = image.getHeight();
        }

        //文件存储到本地
    	try {
            FileUtil.writeFile(file.getBytes(), fileStorePath + File.separator
                    + DateUtils.formatDate(new Date(), DateUtils.DATETIME_PUBLISH_FORMAT) + File.separator ,
                    key + "_"+  originalName);
    	} catch (Exception e) {
            //LOG.error("FileUtil.writeFile has a error:", e);
            throw new BizException("FileUtil.writeFile.has.a.error");
    	}

        System.out.printf(key);
        return key;
    }

    @Override
    public byte[] read(String date , String key,String name) {

        byte[] fileContent = null;
        String filePath = new StringBuilder(fileStorePath).append(File.separator)
        .append(date).append(File.separator).append(key + "_" + name).toString();

        try {
            fileContent = FileUtil.readFileByBytes(filePath);
        } catch (IOException e) {
           // LOG.error("FileUtil.read has a error:", e);
        }

        return fileContent;
    }
}


