package com.idp.workflow.itf.service.extension;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import com.idp.workflow.exception.pub.ProcessNotFoundException;

/**
 * 
 * 服务注册通知接口 扩展注册点
 * 
 * @author panfei
 * 
 */
public interface IServicesRegInvoker {

	/**
	 * 通知注册扩展点进行服务注册
	 * 
	 * @param beanfactory
	 *            工厂bean
	 * @throws ProcessNotFoundException
	 */
	public void execute(DefaultListableBeanFactory beanfactory)
			throws ProcessNotFoundException;
}
