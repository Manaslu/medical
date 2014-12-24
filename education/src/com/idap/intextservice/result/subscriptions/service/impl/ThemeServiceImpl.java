package com.idap.intextservice.result.subscriptions.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.idap.intextservice.result.subscriptions.entity.Theme;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * ###################################################
 * 
 * @创建日期：2014-6-25
 * @开发人员：yao
 * @功能描述：订阅管理主题报表实现类
 * @修改日志： ###################################################
 */

@Service("themeService")
public class ThemeServiceImpl extends DefaultBaseService<Theme, String> {

	@Resource(name = "themeDao")
	public void setBaseDao(IBaseDao<Theme, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "themeDao")
	public void setPagerDao(IPagerDao<Theme> pagerDao) {
		super.setPagerDao(pagerDao);
	}
}
