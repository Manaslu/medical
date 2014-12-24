package com.idap.danalysis.service;

import java.util.List;
import java.util.Map;


public interface IDataMapService{
	public List<Map<String,Object>> findList(Map<String, Object> params) ;
	
	public List<Map<String,Object>> findColumns(String key,Map<String, Object> params) ;

	public String executeScript(String executeScript, Map<String, Object> params);
	
	public void copyScript(String executeScript, Map<String, Object> params);
	
	public void executeSQL(String sql ,List<Object[]> params);
	
	public int queryForInt(String sql ,Object obj);
	
	public List<Map<String,Object>> qeuryForList(String sql,Object... obj);
}
