package com.idap.intextservice.result.subscriptions.dao;

import org.springframework.stereotype.Repository;

import com.idap.intextservice.result.subscriptions.entity.Subscribe;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
 * ###################################################
 * 
 * @创建日期：2014-6-19
 * @开发人员：yao
 * @功能描述：订阅管理dao
 * @修改日志： ###################################################
 */

@Repository("subscribeDao")
public class SubscribeDao extends DefaultBaseDao<Subscribe, Long> {
	public String getNamespace() {
		return Subscribe.class.getName();
	}
}

