package com.idp.workflow.itf.service;

import com.idp.workflow.exception.pub.WfBusinessException;

/**
 * 内部服务查找
 * 
 * @author panfei
 * 
 */
public interface ILocater {

	/**
	 * 注册服务
	 * 
	 * @param itfName
	 * @param implName
	 */
	void servicesBind(Class<?> itfName, Class<?> implName)
			throws WfBusinessException;

	/**
	 * 注册服务
	 * 
	 * @param itfName
	 * @param implInstace
	 */
	void servicesBind(Class<?> itfName, Object implInstace)
			throws WfBusinessException;

	/**
	 * 根据服务名称查找一个服务
	 * 
	 * @param serviceName
	 * @return
	 */
	<E> E lookup(Class<E> serviceName);

	/**
	 * 根据服务名称查找一个服务
	 * 
	 * @param serviceName
	 * @return
	 */
	Object lookup(String serviceName);

	/**
	 * 容器是否已经包含此服务
	 * 
	 * @param itfName
	 * @return
	 */
	boolean contains(Class<?> itfName);

	/**
	 * 容器是否已经包含此服务
	 * 
	 * @param serviceName
	 * @return
	 */
	boolean contains(String serviceName);
}
