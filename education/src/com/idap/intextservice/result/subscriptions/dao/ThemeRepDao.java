package com.idap.intextservice.result.subscriptions.dao;

import org.springframework.stereotype.Repository;

import com.idap.intextservice.result.subscriptions.entity.ThemeRep;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
 * ###################################################
 * 
 * @创建日期：2014-6-25
 * @开发人员：yao
 * @功能描述：订阅主题报表dao
 * @修改日志： ###################################################
 */

@Repository("themeRepDao")
public class ThemeRepDao extends DefaultBaseDao<ThemeRep, String> {
	public String getNamespace() {
		return ThemeRep.class.getName();
	}
}
