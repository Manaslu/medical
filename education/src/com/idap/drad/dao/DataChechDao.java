package com.idap.drad.dao;

import org.springframework.stereotype.Repository;

import com.idap.drad.entity.DataChech;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
 * @###################################################
 * @功能描述:
 * @创建日期:2014-4-21 14:39:43
 * @开发人员：bai
 * @修改日志：
 * @###################################################
 */

@Repository("DataChechDao")
public class DataChechDao extends DefaultBaseDao<DataChech, String> {

	public String getNamespace() {
		return DataChech.class.getName();

	}
	/*
	 * public String getNamespace() { return Role.class.getName(); }
	 */
}
