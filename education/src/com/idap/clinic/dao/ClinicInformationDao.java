
package com.idap.clinic.dao;


import org.springframework.stereotype.Repository;
import com.idap.clinic.entity.ClinicInformation;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("clinicInformationDao")
public class ClinicInformationDao extends DefaultBaseDao<ClinicInformation,String>{
	@Override
	public String getNamespace() {
		return ClinicInformation.class.getName();
		
	}
}
