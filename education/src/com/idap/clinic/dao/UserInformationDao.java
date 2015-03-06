
package com.idap.clinic.dao;


import org.springframework.stereotype.Repository;
import com.idap.clinic.entity.UserInformation;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("userInformationDao")
public class UserInformationDao extends DefaultBaseDao<UserInformation,String>{
	@Override
	public String getNamespace() {
		return UserInformation.class.getName();
		
	}
}
