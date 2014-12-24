package com.idap.danalysis.dao;

import org.springframework.stereotype.Repository;

import com.idap.danalysis.entity.AnalysisProcessSolidify;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("analysisProcessSolidifyDao")
public class AnalysisProcessSolidifyDao extends DefaultBaseDao<AnalysisProcessSolidify,String>{

	@Override
	public String getNamespace() {
		return AnalysisProcessSolidify.class.getName();
		
	}
}
