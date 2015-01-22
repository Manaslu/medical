
package com.idap.clinic.dao;


import org.springframework.stereotype.Repository;
import com.idap.clinic.entity.UploadFile;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("uploadFileDao")
public class UploadFileDao extends DefaultBaseDao<UploadFile,String>{
	@Override
	public String getNamespace() {
		return UploadFile.class.getName();
		
	}
}
