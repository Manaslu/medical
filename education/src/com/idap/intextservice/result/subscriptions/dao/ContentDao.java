package com.idap.intextservice.result.subscriptions.dao;

import org.springframework.stereotype.Repository;

import com.idap.intextservice.result.subscriptions.entity.Content;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
 * ###################################################
 * 
 * @创建日期：2014-6-25
 * @开发人员：yao
 * @功能描述：订阅内容明细dao（订阅管理主题名称，主题报表名称中间表）
 * @修改日志： ###################################################
 */

@Repository("contentDao")
public class ContentDao extends DefaultBaseDao<Content, String> {
	public String getNamespace() {
		return Content.class.getName();
	}
}
