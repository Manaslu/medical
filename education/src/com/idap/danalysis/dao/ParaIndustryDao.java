package com.idap.danalysis.dao;

import org.springframework.stereotype.Repository;

import com.idap.danalysis.entity.ParaIndustry;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("paraIndustryDao")
public class ParaIndustryDao extends DefaultBaseDao<ParaIndustry,String>{

	@Override
	public String getNamespace() {
		return ParaIndustry.class.getName();
		
	}
}
