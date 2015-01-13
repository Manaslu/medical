
package com.idap.clinic.dao;


import org.springframework.stereotype.Repository;
import com.idap.clinic.entity.UserAppointment;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("userAppointmentDao")
public class UserAppointmentDao extends DefaultBaseDao<UserAppointment,String>{
	@Override
	public String getNamespace() {
		return UserAppointment.class.getName();
		
	}
}
