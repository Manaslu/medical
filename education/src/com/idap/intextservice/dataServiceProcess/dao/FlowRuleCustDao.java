package com.idap.intextservice.dataServiceProcess.dao;

import org.springframework.stereotype.Repository;

import com.idap.intextservice.dataServiceProcess.entity.FlowRuleCust;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
 * #############################
 * 
 * @创建日期：2014-5-21 16:36:12
 * @开发人员：huhanjiang
 * @功能描述：流程规则定制dao
 * @修改日志： ###################
 */

@Repository("flowRuleCustDao")
public class FlowRuleCustDao extends DefaultBaseDao<FlowRuleCust, Long> {
	public String getNamespace() {
		return FlowRuleCust.class.getName();
	}
}
