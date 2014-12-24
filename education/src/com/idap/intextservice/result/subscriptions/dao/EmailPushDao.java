package com.idap.intextservice.result.subscriptions.dao;

import org.springframework.stereotype.Repository;

import com.idap.intextservice.result.subscriptions.entity.EmailPush;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
 * ###################################################
 * 
 * @创建日期：2014-6-20
 * @开发人员：yao
 * @功能描述：订阅推送记录/日志管理dao
 * @修改日志： ###################################################
 */

@Repository("emailPushDao")
public class EmailPushDao extends DefaultBaseDao<EmailPush, String> {
	public String getNamespace() {
		return EmailPush.class.getName();
	}
}
