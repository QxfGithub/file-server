package com.qxf.fileserver.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;
/**
 * @author QIUXUEFU376
 * 2018-03-21
 */
@ApiModel(description = "上传材料表单")
public class UploadMaterialForm {
	
	@ApiModelProperty("上传材料map<materialType,materialStr>,materialType为照片类型: socialSecurityImg; "
    		+ "materialStr为base64照片流")
    private Map<String, String> materialMap;

	public Map<String, String> getMaterialMap() {
		return materialMap;
	}

	public void setMaterialMap(Map<String, String> materialMap) {
		this.materialMap = materialMap;
	}
    
}
