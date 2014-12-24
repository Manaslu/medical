package com.idap.danalysis.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Repository;

import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("dataMapDao")
public class DataMapDao extends DefaultBaseDao<Map<String,Object>, String>{
	
    @SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(DataMapDao.class);
    public static final String NAME_SPACE = "com.idap.danalysis.entity.DataMap";
	
    @Override
	public String getNamespace() {
		return NAME_SPACE;
	}

	public List<Map<String, Object>> find(String key, Map<String, Object> params) {
		return super.find(key, params);
	}
}
