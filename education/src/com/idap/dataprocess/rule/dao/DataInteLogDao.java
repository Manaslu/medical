package com.idap.dataprocess.rule.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.idap.dataprocess.rule.entity.DataInteLog;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
 * 数据整合日志的持久化类
 * 
 * @author Raoxy
 * 
 */
@Repository("dataInteLogDao")
public class DataInteLogDao extends DefaultBaseDao<DataInteLog, String> {

	@Override
	public String getNamespace() {
		return DataInteLog.class.getName();
	}
	
	//查看报告
	public List<DataInteLog> getReport(DataInteLog id) {
		return get("getReport", id);

	}
	//查看报告
	public List<DataInteLog> get(String key, DataInteLog id) {
		return this.getSqlSession().selectList(sqlKey(key), id);

	}

	public List<DataInteLog> findByTree(Map<String, Object> params) {
		return this.find("findByTree", params);
	}

	public List<DataInteLog> findByResult(Map<String, Object> params) {
		return this.find("findByResult", params);
	}

	public DataInteLog findByRuleId(String ruleid) {
		return this.getSqlSession().selectOne(this.sqlKey("findByRuleId"),
				ruleid);
	}

}
