package com.idp.workflow.itf.adapter;

import com.idp.workflow.exception.pub.WfNotSupprotException;
import com.idp.workflow.itf.builder.AbstractAdapterBuilder;
import com.idp.workflow.itf.metadata.IWorkFlowMetaData;

/**
 * 服务构建者工厂接口
 * 
 * @author panfei
 * 
 */
public interface IAdapterFactoryBuilder {

	/**
	 * 根据服务的类型构建获取对应的服务创建者
	 * 
	 * @param serviceClass
	 * @return
	 */
	<E> AbstractAdapterBuilder<E, IWorkFlowMetaData<?>> createClassicAdapterBuilder(
			Class<E> serviceClass) throws WfNotSupprotException;
}
