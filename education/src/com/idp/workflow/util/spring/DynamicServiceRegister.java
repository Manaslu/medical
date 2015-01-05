package com.idp.workflow.util.spring;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionVisitor;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.util.StringValueResolver;

import com.idp.workflow.exception.pub.ProcessNotFoundException;
import com.idp.workflow.itf.metadata.IWorkFlowMetaData;
import com.idp.workflow.itf.service.extension.IServicesRegInvoker;
import com.idp.workflow.util.common.StringUtil;
import com.idp.workflow.vo.pub.IWorkFlowTypes;
 
/**
 * spring动态注册bean的定义
 * 
 * @author panfei
 * 
 */
public abstract class DynamicServiceRegister implements
		BeanFactoryPostProcessor {

	private List<IServicesRegInvoker> reglist;

	public List<IServicesRegInvoker> getReglist() {
		return reglist;
	}

	public void setReglist(List<IServicesRegInvoker> reglist) {
		this.reglist = reglist;
	}

	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {

		try {
			this.registerServiceBeansDefine((DefaultListableBeanFactory) beanFactory);
			if (getReglist() != null) {
				for (IServicesRegInvoker temp : getReglist()) {
					temp.execute((DefaultListableBeanFactory) beanFactory);
				}
			}
			// 如果没有抛出异常，更新metadata的autoreg属性为true
			this.setWorkFlowMetaDataAuto(beanFactory, true);
		} catch (ProcessNotFoundException e) {
			throw new NoSuchBeanDefinitionException(e.getMessage());
		}
	}

	/**
	 * 注册工作流的serivce
	 * 
	 * @param beanfactory
	 * @throws ProcessNotFoundException
	 */
	public abstract void registerServiceBeansDefine(
			DefaultListableBeanFactory beanfactory)
			throws ProcessNotFoundException;

	/**
	 * 设置serviceRegAuto为true
	 * 
	 * @param beanFactory
	 * @param auto
	 */
	protected void setWorkFlowMetaDataAuto(
			ConfigurableListableBeanFactory beanFactory, boolean auto) {
		if (beanFactory != null) {
			String metaBeanName = null;
			// 第一步根据元数据接口获取可能的beanname
			String[] beanNames = beanFactory
					.getBeanNamesForType(IWorkFlowMetaData.class);
			if (beanNames != null && beanNames.length > 0) {
				metaBeanName = beanNames[0];
			}
			// 如果取不到，遍历按照缩写规范
			if (StringUtil.isEmpty(metaBeanName)) {
				beanNames = beanFactory.getBeanDefinitionNames();
				if (beanNames != null) {
					for (String beanName : beanNames) {
						if (StringUtil.isEndWith(IWorkFlowTypes.Meta_Name,
								beanName)) {
							metaBeanName = beanName;
							break;
						}
					}
				}
			}
			// 设置serviceRegAuto为true 目前先简单处理
			if (!StringUtil.isEmpty(metaBeanName)) {
				BeanDefinition beandefine = beanFactory
						.getBeanDefinition(metaBeanName);
				StringValueResolver autoreg = new StringValueResolver() {
					@Override
					public String resolveStringValue(String provalue) {
						if ("false".equals(provalue)) {
							return "true";
						}
						return provalue;
					}
				};
				BeanDefinitionVisitor chekvistor = new BeanDefinitionVisitor(
						autoreg);
				chekvistor.visitBeanDefinition(beandefine);
			}
		}
	}
}
