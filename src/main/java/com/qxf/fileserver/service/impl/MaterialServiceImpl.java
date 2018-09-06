package com.qxf.fileserver.service.impl;

import com.qxf.fileserver.Util.ImageUploadUtils;
import com.qxf.fileserver.dto.UploadMaterialDTO;
import com.qxf.fileserver.service.MaterialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author QIUXUEFU376
 * 2018-03-21
 */
@Service
public class MaterialServiceImpl implements MaterialService {

	@Value("${file.store.path:D:/Program Files/File/}")
	private String filePath;

	private static final Logger LOG = LoggerFactory.getLogger(MaterialServiceImpl.class);

	@Override
	public Map<String,String> uploadMaterial(UploadMaterialDTO dto) {
		Map<String,String> retMap = ImageUploadUtils.saveImagesToLocal(dto.getMaterialMap(), filePath);
		return retMap;
	}

	@Override
	public Map<String, String> queryMaterial(String key, String ext) {
		Map<String, String> map = ImageUploadUtils.getImgStrMap(filePath  + key + "." + ext);
		return map;
	}
	
}
