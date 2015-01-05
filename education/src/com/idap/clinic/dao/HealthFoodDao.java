package com.idap.clinic.dao;

import org.springframework.stereotype.Repository;

import com.idap.clinic.entity.HealthFood;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("healthFoodDao")
public class HealthFoodDao extends DefaultBaseDao<HealthFood,String>{
	@Override
	public String getNamespace() {
		return HealthFood.class.getName();
		
	}
}
