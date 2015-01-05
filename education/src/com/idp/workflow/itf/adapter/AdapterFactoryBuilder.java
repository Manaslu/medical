package com.idp.workflow.itf.adapter;

import java.util.HashMap;
import java.util.Map;

import com.idp.workflow.exception.pub.WfNotSupprotException;
import com.idp.workflow.itf.builder.AbstractAdapterBuilder;
import com.idp.workflow.itf.metadata.IWorkFlowMetaData;

/**
 * 
 * @author panfei
 * 
 */
public abstract class AdapterFactoryBuilder implements IAdapterFactoryBuilder {

	protected Map<Class<?>, AbstractAdapterBuilder<?, IWorkFlowMetaData<?>>> regMap = new HashMap<Class<?>, AbstractAdapterBuilder<?, IWorkFlowMetaData<?>>>();

	@SuppressWarnings("unchecked")
	@Override
	public <E> AbstractAdapterBuilder<E, IWorkFlowMetaData<?>> createClassicAdapterBuilder(
			Class<E> serviceClass) throws WfNotSupprotException {
		if (regMap.containsKey(serviceClass)) {
			return ((AbstractAdapterBuilder<E, IWorkFlowMetaData<?>>) regMap
					.get(serviceClass));
		} else {
			throw new WfNotSupprotException("not found,do not suport this type of service:" + String.valueOf(serviceClass));
		}
	}

	/**
	 * 注册一个服务构建者
	 * 
	 * @param key
	 * @param value
	 */
	public abstract void regAdapterBuilder(Class<?> key,
			AbstractAdapterBuilder<?, ? extends IWorkFlowMetaData<?>> value);
}
