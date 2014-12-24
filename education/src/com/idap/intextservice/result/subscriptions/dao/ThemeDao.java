package com.idap.intextservice.result.subscriptions.dao;

import org.springframework.stereotype.Repository;

import com.idap.intextservice.result.subscriptions.entity.Theme;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
 * ###################################################
 * 
 * @创建日期：2014-6-25
 * @开发人员：yao
 * @功能描述：订阅主题dao
 * @修改日志： ###################################################
 */

@Repository("themeDao")
public class ThemeDao extends DefaultBaseDao<Theme, String> {
	public String getNamespace() {
		return Theme.class.getName();
	}
}
