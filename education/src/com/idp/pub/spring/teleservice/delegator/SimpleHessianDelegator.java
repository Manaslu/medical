package com.idp.pub.spring.teleservice.delegator;

import java.net.MalformedURLException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;

import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.io.SerializerFactory;

/**
 * 对HessianDelegator实现了简单的jdk代理服务
 * 
 * @author panfei
 * 
 */
public class SimpleHessianDelegator extends HessianDelegator {

	private HessianProxyFactory proxyFactory = new HessianProxyFactory();

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		boolean isNeed = this.isNeedRemoteServerSupport(bean);
		if (isNeed) {
			try {
				Object hessianProxy = proxyFactory.create(
						this.getClassInterFace(bean, beanName),
						this.createRemoteServerUrl(bean, beanName));
				return hessianProxy;
			} catch (MalformedURLException e) {
				throw new BeanCreationException(beanName, "创建代理远程服务类失败!", e);
			}
		}
		return bean;
	}

	@Override
	public void setSerializerFactory(SerializerFactory serializerFactory) {
		this.proxyFactory.setSerializerFactory(serializerFactory);
	}

	@Override
	public void setSendCollectionType(boolean sendCollectionType) {
		this.proxyFactory.getSerializerFactory().setSendCollectionType(
				sendCollectionType);
	}

	@Override
	public void setOverloadEnabled(boolean overloadEnabled) {
		this.proxyFactory.setOverloadEnabled(overloadEnabled);
	}

	@Override
	public void setUsername(String username) {
		this.proxyFactory.setUser(username);
	}

	@Override
	public void setPassword(String password) {
		this.proxyFactory.setPassword(password);
	}

	@Override
	public void setChunkedPost(boolean chunkedPost) {
		this.proxyFactory.setChunkedPost(chunkedPost);
	}

	@Override
	public void setReadTimeout(long timeout) {
		this.proxyFactory.setReadTimeout(timeout);
	}

	@Override
	public void setHessian2(boolean hessian2) {
		this.proxyFactory.setHessian2Request(hessian2);
		this.proxyFactory.setHessian2Reply(hessian2);
	}

}
