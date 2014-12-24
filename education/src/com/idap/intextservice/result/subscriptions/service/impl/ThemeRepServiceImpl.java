package com.idap.intextservice.result.subscriptions.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.idap.intextservice.result.subscriptions.entity.ThemeRep;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * ###################################################
 * 
 * @创建日期：2014-6-25
 * @开发人员：yao
 * @功能描述：订阅管理主题实现类
 * @修改日志： ###################################################
 */

@Service("themeRepService")
public class ThemeRepServiceImpl extends DefaultBaseService<ThemeRep, String> {

	@Resource(name = "themeRepDao")
	public void setBaseDao(IBaseDao<ThemeRep, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "themeRepDao")
	public void setPagerDao(IPagerDao<ThemeRep> pagerDao) {
		super.setPagerDao(pagerDao);
	}
}
