package com.idap.danalysis.dao;

import org.springframework.stereotype.Repository;

import com.idap.danalysis.entity.AnalysisTheme;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("analysisThemeDao")
public class AnalysisThemeDao extends DefaultBaseDao<AnalysisTheme,String>{

	@Override
	public String getNamespace() {
		return AnalysisTheme.class.getName();
		
	}
}
