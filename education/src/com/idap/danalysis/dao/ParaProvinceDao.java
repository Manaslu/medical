package com.idap.danalysis.dao;

import org.springframework.stereotype.Repository;

import com.idap.danalysis.entity.ParaProvince;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("paraProvinceDao")
public class ParaProvinceDao extends DefaultBaseDao<ParaProvince,String>{

	@Override
	public String getNamespace() {
		return ParaProvince.class.getName();
		
	}
}
