package com.idap.clinic.dao;

import org.springframework.stereotype.Repository;
import com.idap.clinic.entity.DoctorsManagement;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("doctorsManagementDao")
public class DoctorsManagementDao extends DefaultBaseDao<DoctorsManagement,String>{
	@Override
	public String getNamespace() {
		return DoctorsManagement.class.getName();
		
	}
}
