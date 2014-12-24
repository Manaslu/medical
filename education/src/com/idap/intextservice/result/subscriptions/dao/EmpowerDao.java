package com.idap.intextservice.result.subscriptions.dao;

import org.springframework.stereotype.Repository;

import com.idap.intextservice.result.subscriptions.entity.Empower;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
 * ###################################################
 * 
 * @创建日期：2014-6-19
 * @开发人员：yao
 * @功能描述：订阅管理权限dao
 * @修改日志： ###################################################
 */

@Repository("empowerDao")
public class EmpowerDao extends DefaultBaseDao<Empower, Long> {
	public String getNamespace() {
		return Empower.class.getName();
	}
}
