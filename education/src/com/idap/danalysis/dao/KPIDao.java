package com.idap.danalysis.dao;

import org.springframework.stereotype.Repository;

import com.idap.danalysis.entity.KPI;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("kpiDao")
public class KPIDao extends DefaultBaseDao<KPI,String>{

	@Override
	public String getNamespace() {
		return KPI.class.getName();
		
	}
}
