package com.idap.clinic.service;

import java.util.List;
import java.util.Map;
import com.idap.clinic.entity.UploadFile;
 

/**
 * @###################################################
 * @创建日期：2015-1-27 
 * @开发人员： 
 * @功能描述：
 * @修改日志：
 * @###################################################
 */

public interface IUploadFile {

	 
	public List<UploadFile> query(Map<String, Object> params);   
		
}
