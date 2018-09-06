package com.qxf.fileserver.service;

import com.qxf.fileserver.dto.UploadMaterialDTO;

import java.util.Map;


/**
 * @author QIUXUEFU376
 * 2018-03-21
 */
public interface MaterialService {

	/**
	 * 上传材料
	 * @param dto
	 * @return
	 */
	Map<String,String>  uploadMaterial(UploadMaterialDTO dto);

	/**
	 * 根据预约Id查询上传材料
	 * @param
	 * @return
	 */
	Map<String,String> queryMaterial(String key,String ext);
}
