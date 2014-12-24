package com.idp.workflow.impl.filter;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.filter.AbstracrFilter;
import com.idp.workflow.vo.service.WfParametersVO;

/**
 * 无事务控制
 * 
 * @author panfei
 * 
 */
public class NoneTranstacionMangerFilter extends AbstracrFilter<WfParametersVO> {

	@Override
	public void beginInvoke(WfParametersVO context) throws WfBusinessException {

	}

	@Override
	public void endInvoke(WfParametersVO context) throws WfBusinessException {

	}

	@Override
	public void finalInvoke(WfParametersVO context) throws WfBusinessException {

	}

	@Override
	public void exceptionInvoke(Throwable erro) {

	}

}
