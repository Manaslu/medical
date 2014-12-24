package com.idp.workflow.itf.metadata;

import java.util.Map;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.adapter.IAdapterFactoryBuilder;
import com.idp.workflow.itf.service.ILocater;

/**
 * 工作流元数据接口
 * 
 * @author panfei
 * 
 */
public interface IWorkFlowMetaData<T> {

	/**
	 * 获取元数据类型
	 * 
	 * @return
	 */
	Class<T> getType();

	/**
	 * 获取实际的工作流引擎
	 * 
	 * @return
	 */
	T getWorkFlowObject();

	/**
	 * 获取元数据类型名称，默认根据范型的class类型
	 * 
	 * @see com.idp.workflow.vo.pub.IWorkFlowTypes
	 * @return
	 */
	String getTypeName();

	/***
	 * 元数据上下文
	 * 
	 * @return
	 */
	Map<String, Object> getContext();

	/**
	 * 获取抽象工厂的创建者
	 * 
	 * @return
	 */
	IAdapterFactoryBuilder getAdapterFactoryBuilder();

	/**
	 * 根据服务类型获取对应的实际服务
	 * 
	 * @param classname
	 * @return
	 */
	<E> E getAdapterSerivce(Class<E> classname) throws WfBusinessException;

	/**
	 * 服务上下文获取
	 * 
	 * @return
	 */
	ILocater getLocater();

	/**
	 * 是否服务自动绑定注册
	 */
	boolean isServiceRegAuto();
}
