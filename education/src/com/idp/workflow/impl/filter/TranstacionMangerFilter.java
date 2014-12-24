package com.idp.workflow.impl.filter;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.filter.AbstracrFilter;
import com.idp.workflow.util.service.Context;
import com.idp.workflow.vo.service.WfParametersVO;

/**
 * 数据库事务管理 一个事务中只允许close一次，不论是否存在事务的嵌套
 * 
 * @author panfei
 * 
 */
public class TranstacionMangerFilter extends AbstracrFilter<WfParametersVO> {

	@Override
	public void beginInvoke(WfParametersVO context) throws WfBusinessException {
		Context.createBaseDaoMapper();
	}

	@Override
	public void endInvoke(WfParametersVO context) throws WfBusinessException {
		Context.createBaseDaoMapper().commit();
	}

	@Override
	public void finalInvoke(WfParametersVO context) throws WfBusinessException {
		Context.createBaseDaoMapper().close();
	}

	@Override
	public void exceptionInvoke(Throwable erro) {
		Context.createBaseDaoMapper().rollback();
	}

}
