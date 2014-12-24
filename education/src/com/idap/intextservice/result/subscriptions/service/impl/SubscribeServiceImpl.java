package com.idap.intextservice.result.subscriptions.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.idap.intextservice.result.subscriptions.entity.Subscribe;
import com.idap.intextservice.result.subscriptions.service.ISubscribeService;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * ###################################################
 * 
 * @创建日期：2014-6-19
 * @开发人员：yao
 * @功能描述：订阅管理实现类
 * @修改日志： ###################################################
 */

@Service("subscribeService")
public class SubscribeServiceImpl extends DefaultBaseService<Subscribe, Long>
		implements ISubscribeService {

	@Resource(name = "subscribeDao")
	public void setBaseDao(IBaseDao<Subscribe, Long> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "subscribeDao")
	public void setPagerDao(IPagerDao<Subscribe> pagerDao) {
		super.setPagerDao(pagerDao);
	}
}
