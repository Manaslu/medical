package com.idap.intextservice.result.subscriptions.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.idap.intextservice.result.subscriptions.entity.Empower;
import com.idap.intextservice.result.subscriptions.service.IEmpowerService;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * ###################################################
 * 
 * @创建日期：2014-6-19
 * @开发人员：yao
 * @功能描述：订阅管理权限实现类
 * @修改日志： ###################################################
 */

@Service("empowerService")
public class EmpowerServiceImpl extends DefaultBaseService<Empower, Long>
		implements IEmpowerService {

	@Resource(name = "empowerDao")
	public void setBaseDao(IBaseDao<Empower, Long> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "empowerDao")
	public void setPagerDao(IPagerDao<Empower> pagerDao) {
		super.setPagerDao(pagerDao);
	}
}
