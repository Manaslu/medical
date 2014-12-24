package com.idap.intextservice.result.subscriptions.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.idap.intextservice.result.subscriptions.entity.EmailPush;
import com.idap.intextservice.result.subscriptions.service.IEmailPushService;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * ###################################################
 * 
 * @创建日期：2014-6-19
 * @开发人员：yao
 * @功能描述：订阅推送/日志实现类
 * @修改日志： ###################################################
 */

@Service("emailPushService")
public class EmailPushServiceImpl extends DefaultBaseService<EmailPush, String>
		implements IEmailPushService {

	@Resource(name = "emailPushDao")
	public void setBaseDao(IBaseDao<EmailPush, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "emailPushDao")
	public void setPagerDao(IPagerDao<EmailPush> pagerDao) {
		super.setPagerDao(pagerDao);
	}
}
