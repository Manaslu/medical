package com.idp.workflow.util.spring;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.service.ILocater;

/**
 * Spring服务动态创建工具 规范
 * 
 * @author panfei
 * 
 */
public class ServiceBeanFactory implements ILocater {

	private static Object locker = new Object();

	private static ServiceBeanFactory instance;

	private ApplicationContext springContext;

	private DefaultListableBeanFactory beanfactory;

	private ServiceBeanFactory() {
	}

	public static ServiceBeanFactory GetInstance() {
		// findbug 也有sb的时候
		if (instance == null) {
			synchronized (locker) {
				if (instance == null) {
					instance = new ServiceBeanFactory();
				}
			}
		}
		return instance;
	}

	/**
	 * 通过junit4测试因为测试框架每运行完一个测试用例，不释放重制全局资源所以特别添加
	 */
	public synchronized void clearSpringContext() {
		this.springContext = null;
		this.beanfactory = null;
	}

	public synchronized void setSpringContext(ApplicationContext context) {
		this.springContext = context;
		try {
			this.setBeanfactory(getBeanFactory(springContext));
		} catch (WfBusinessException e) {
			e.printStackTrace();
		}
	}

	public boolean containsServiceBean(Class<?> beanName) {
		if (springContext == null) {
			return this.getBeanfactory().containsBean(beanName.getName());
		}
		return springContext.containsBean(beanName.getName());
	}

	public boolean containsServiceBeanDefinition(Class<?> beanName) {
		if (springContext == null) {
			return this.getBeanfactory().containsBeanDefinition(
					beanName.getName());
		}
		return springContext.containsBeanDefinition(beanName.getName());
	}

	/**
	 * 根据服务器接口名称和实现类注册服务
	 * 
	 * @param ServiceItfName
	 * @param ServiceImplName
	 * @throws WfBusinessException
	 */
	public synchronized void registerServiceBean(Class<?> ServiceItfName,
			Class<?> ServiceImplName) throws WfBusinessException {
		if (!containsServiceBeanDefinition(ServiceItfName)) {
			// MutablePropertyValues properties = new MutablePropertyValues();
			RootBeanDefinition definition = new RootBeanDefinition(
					ServiceImplName);
			DefaultListableBeanFactory beanFactory = this.getBeanfactory();
			if (beanFactory == null) {
				beanFactory = getBeanFactory(springContext);
			}
			beanFactory.registerBeanDefinition(ServiceItfName.getName(),
					definition);
		}
	}

	/**
	 * 根据服务接口全称获取服务bean
	 * 
	 * @param serviceName
	 * @return
	 */
	public <E> E getBean(Class<E> serviceName) {
		if (springContext == null) {
			return this.getBeanfactory().getBean(serviceName.getName(),
					serviceName);
		}
		return springContext.getBean(serviceName.getName(), serviceName);
	}

	private DefaultListableBeanFactory getBeanFactory(ApplicationContext context)
			throws WfBusinessException {
		if (context instanceof AbstractApplicationContext) {
			AbstractApplicationContext baseappcontext = (AbstractApplicationContext) context;
			return (DefaultListableBeanFactory) baseappcontext.getBeanFactory();
		} else if (context instanceof WebApplicationContext) {
			return (DefaultListableBeanFactory) context
					.getAutowireCapableBeanFactory();
		} else {
			/*
			 * ClassPathXmlApplicationContext appcontext = new
			 * ClassPathXmlApplicationContext( springContext);
			 * appcontext.refresh(); return (DefaultListableBeanFactory)
			 * appcontext.getBeanFactory();
			 */
			throw new WfBusinessException("无法根据spring上下文获取到具体的BeanFactory");
		}
	}

	@Override
	public void servicesBind(Class<?> itfName, Class<?> implName)
			throws WfBusinessException {
		registerServiceBean(itfName, implName);
	}

	@Override
	public <E> E lookup(Class<E> serviceName) {
		return getBean(serviceName);
	}

	@Override
	public void servicesBind(Class<?> itfName, Object implInstace)
			throws WfBusinessException {
		this.servicesBind(itfName, implInstace.getClass());
	}

	@Override
	public Object lookup(String serviceName) {
		if (springContext == null) {
			return this.getBeanfactory().getBean(serviceName.trim());
		}
		return springContext.getBean(serviceName.trim());
	}

	public DefaultListableBeanFactory getBeanfactory() {
		return beanfactory;
	}

	public void setBeanfactory(DefaultListableBeanFactory beanfactory) {
		this.beanfactory = beanfactory;
	}

	@Override
	public boolean contains(Class<?> itfName) {
		return containsServiceBeanDefinition(itfName);
	}

	@Override
	public boolean contains(String serviceName) {
		if (springContext == null) {
			return this.getBeanfactory().containsBeanDefinition(serviceName);
		}
		return springContext.containsBeanDefinition(serviceName);
	}

	/**
	 * 适用于工厂创建后代理模式
	 * 
	 * @param existingBean
	 *            已经存在的bean
	 * @param beanName
	 *            bean名称
	 * @return
	 * @throws WfBusinessException
	 */
	public Object applyBeanPostProcessorsAfterInitialization(
			Object existingBean, String beanName) throws WfBusinessException {
		return this.getBeanfactory()
				.applyBeanPostProcessorsAfterInitialization(existingBean,
						beanName);
	}
}
