package com.idap.intextservice.result.subscriptions.dao;

import org.springframework.stereotype.Repository;

import com.idap.intextservice.result.subscriptions.entity.DataRepor;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
 * ###################################################
 * 
 * @创建日期：2014-6-19
 * @开发人员：yao
 * @功能描述：订阅主题dao
 * @修改日志： ###################################################
 */

@Repository("dataReporDao")
public class DataReporDao extends DefaultBaseDao<DataRepor, Long> {
	public String getNamespace() {
		return DataRepor.class.getName();
	}
}
