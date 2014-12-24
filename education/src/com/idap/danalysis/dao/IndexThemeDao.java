package com.idap.danalysis.dao;

import org.springframework.stereotype.Repository;

import com.idap.danalysis.entity.IndexTheme;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("indexThemeDao")
public class IndexThemeDao extends DefaultBaseDao<IndexTheme,String>{

	@Override
	public String getNamespace() {
		return IndexTheme.class.getName();
		
	}
}
