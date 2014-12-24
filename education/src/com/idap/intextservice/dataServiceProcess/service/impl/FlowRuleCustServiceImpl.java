package com.idap.intextservice.dataServiceProcess.service.impl;


import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.intextservice.dataServiceProcess.entity.FlowRuleCust;
import com.idap.intextservice.dataServiceProcess.service.IFlowRuleCustService;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.entity.Pager;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * ###################################################
 * 
 * @创建日期：2014-5-21 16:53:43
 * @开发人员：huhanjiang
 * @功能描述：流程规则定制实现类
 * @修改日志： #######################################
 */

@Service("flowRuleCustService")
@Transactional
public class FlowRuleCustServiceImpl extends DefaultBaseService<FlowRuleCust, Long> implements
		IFlowRuleCustService {

	@Resource(name = "flowRuleCustDao")
	public void setBaseDao(IBaseDao<FlowRuleCust, Long> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "flowRuleCustDao")
	public void setPagerDao(IPagerDao<FlowRuleCust> pagerDao) {
		super.setPagerDao(pagerDao);
	}
	
	public Pager<FlowRuleCust> query(Pager<FlowRuleCust>  pager, Map<String, Object> params) {
		return	this.findByPager(pager, params);
		    
	}
	
}
