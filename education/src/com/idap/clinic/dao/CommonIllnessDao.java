package com.idap.clinic.dao;

import org.springframework.stereotype.Repository;
import com.idap.clinic.entity.CommonIllness;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("commonIllnessDao")
public class CommonIllnessDao extends DefaultBaseDao<CommonIllness,String>{
	@Override
	public String getNamespace() {
		return CommonIllness.class.getName();
		
	}
}
