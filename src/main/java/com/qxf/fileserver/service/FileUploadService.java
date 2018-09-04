/**
 * Copyright (C) 2018 Pingan, Inc. All Rights Reserved.
 */
package com.qxf.fileserver.service;


import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传service
 * 
 * @author GUANLIANGYOU515
 * @date   2018年4月18日
 * @since  1.0.0
 */
public interface FileUploadService {

    /**
     * 文件上传
     * 
     * @param
     * @return
     */
    public String upload(MultipartFile file);

    /**
     * 文件读取，默认读取材料路径
     * 
     * @param key
     * @return
     */
    public byte[] read(String date ,String key, String name);


}
