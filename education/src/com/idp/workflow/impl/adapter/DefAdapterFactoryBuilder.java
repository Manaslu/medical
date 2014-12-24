package com.idp.workflow.impl.adapter;

import com.idp.workflow.itf.adapter.AdapterFactoryBuilder;
import com.idp.workflow.itf.builder.AbstractAdapterBuilder;
import com.idp.workflow.itf.metadata.IWorkFlowMetaData;

/**
 * 默认实现
 * 
 * @author panfei
 * 
 */
public class DefAdapterFactoryBuilder extends AdapterFactoryBuilder {

	@SuppressWarnings("unchecked")
	@Override
	public void regAdapterBuilder(Class<?> key,
			AbstractAdapterBuilder<?, ? extends IWorkFlowMetaData<?>> value) {
		this.regMap.put(key,
				((AbstractAdapterBuilder<?, IWorkFlowMetaData<?>>) value));
	}
}
