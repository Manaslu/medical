package com.idap.danalysis.dao;

import org.springframework.stereotype.Repository;

import com.idap.danalysis.entity.ParaYearList;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("paraYearListDao")
public class ParaYearListDao extends DefaultBaseDao<ParaYearList,String>{

	@Override
	public String getNamespace() {
		return ParaYearList.class.getName();
		
	}
}
