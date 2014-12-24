package com.idap.intextservice.result.subscriptions.dao;

import org.springframework.stereotype.Repository;

import com.idap.intextservice.result.subscriptions.entity.Report;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
 * ###################################################
 * 
 * @创建日期：2014-7-5
 * @开发人员：yao
 * @功能描述：dao
 * @修改日志： ###################################################
 */

@Repository("reportDao")
public class ReportDao extends DefaultBaseDao<Report, String> {
	public String getNamespace() {
		return Report.class.getName();
	}
}
