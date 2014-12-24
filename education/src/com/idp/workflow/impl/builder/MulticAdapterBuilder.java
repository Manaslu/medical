package com.idp.workflow.impl.builder;

import com.idp.workflow.itf.builder.AbstractAdapterBuilder;
import com.idp.workflow.itf.metadata.IWorkFlowMetaData;

/**
 * 默认实现
 * 
 * @author panfei
 * 
 */
public class MulticAdapterBuilder extends
		AbstractAdapterBuilder<Object, IWorkFlowMetaData<Object>> {

	@Override
	public Object createAdapterService(IWorkFlowMetaData<Object> targetSource) {
		this.setTargetSource(targetSource);
		return null;
	}

}
