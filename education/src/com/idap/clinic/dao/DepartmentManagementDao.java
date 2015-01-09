
package com.idap.clinic.dao;


import org.springframework.stereotype.Repository;
import com.idap.clinic.entity.DepartmentManagement;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("departmentManagementDao")
public class DepartmentManagementDao extends DefaultBaseDao<DepartmentManagement,String>{
	@Override
	public String getNamespace() {
		return DepartmentManagement.class.getName();
		
	}
}
