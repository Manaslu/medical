package com.idap.danalysis.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.idap.danalysis.entity.ParameterList;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("parameterListDao")
public class ParameterListDao extends DefaultBaseDao<ParameterList,String> implements IParameterListDao{
	public static final String EXECUTE_ASSESS = "executeDataAnasis";

	@Override
	public String getNamespace() {
		return ParameterList.class.getName();
		
	}

	@Override
	public void executeDataAnasis(String excuteThemeid) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("excuteThemeid", excuteThemeid);
        this.getSqlSession().selectOne(EXECUTE_ASSESS, params);
	 
	}
	
	

	 
}
