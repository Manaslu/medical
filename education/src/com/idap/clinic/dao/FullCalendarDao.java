
package com.idap.clinic.dao;


import org.springframework.stereotype.Repository;
import com.idap.clinic.entity.FullCalendar;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("fullCalendarDao")
public class FullCalendarDao extends DefaultBaseDao<FullCalendar,String>{
	@Override
	public String getNamespace() {
		return FullCalendar.class.getName();
		
	}
}
