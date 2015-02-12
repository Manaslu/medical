
package com.idap.clinic.dao;


import org.springframework.stereotype.Repository;
import com.idap.clinic.entity.UserEvaluate;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("userEvaluateDao")
public class UserEvaluateDao extends DefaultBaseDao<UserEvaluate,String>{
	@Override
	public String getNamespace() {
		return UserEvaluate.class.getName();
		
	}
}
