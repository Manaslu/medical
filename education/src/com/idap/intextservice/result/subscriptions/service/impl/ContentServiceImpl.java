package com.idap.intextservice.result.subscriptions.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.idap.intextservice.result.subscriptions.entity.Content;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * ###################################################
 * 
 * @创建日期：2014-6-19
 * @开发人员：yao
 * @功能描述：订阅管理明细实现类
 * @修改日志： ###################################################
 */

@Service("contentService")
public class ContentServiceImpl extends DefaultBaseService<Content, String>{

	@Resource(name = "contentDao")
	public void setBaseDao(IBaseDao<Content, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "contentDao")
	public void setPagerDao(IPagerDao<Content> pagerDao) {
		super.setPagerDao(pagerDao);
	}
}
