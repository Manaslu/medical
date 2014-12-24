package com.idap.danalysis.dao;

import org.springframework.stereotype.Repository;

import com.idap.danalysis.entity.AnalysisType;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("analysisTypeDao")
public class AnalysisTypeDao extends DefaultBaseDao<AnalysisType,String>{

	@Override
	public String getNamespace() {
		return AnalysisType.class.getName();
		
	}
}
