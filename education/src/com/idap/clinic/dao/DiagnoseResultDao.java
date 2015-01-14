
package com.idap.clinic.dao;


import org.springframework.stereotype.Repository;
import com.idap.clinic.entity.DiagnoseResult;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("diagnoseResultDao")
public class DiagnoseResultDao extends DefaultBaseDao<DiagnoseResult,String>{
	@Override
	public String getNamespace() {
		return DiagnoseResult.class.getName();
		
	}
}
