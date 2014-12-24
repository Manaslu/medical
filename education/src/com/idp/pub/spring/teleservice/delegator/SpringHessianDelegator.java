package com.idp.pub.spring.teleservice.delegator;

import org.springframework.beans.BeansException;

import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import com.caucho.hessian.io.SerializerFactory;

/**
 * 对spring的HessianProxyFactoryBean进行封装，由于HessianDelegator实现了代理，不用自己实现JDK代理。
 * 
 * @author panfei
 * 
 */
public class SpringHessianDelegator extends HessianDelegator {

	HessianProxyFactoryBean hesisproxyFactoryBan = new HessianProxyFactoryBean();

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
			hesisproxyFactoryBan.setServiceUrl(this.createRemoteServerUrl(bean,
					beanName));
			hesisproxyFactoryBan.setServiceInterface(this.getClassInterFace(
					bean, beanName));
			return hesisproxyFactoryBan.getObject();
		}
		return bean;
	}

	@Override
	public void setSerializerFactory(SerializerFactory serializerFactory) {
		hesisproxyFactoryBan.setSerializerFactory(serializerFactory);
	}

	@Override
	public void setSendCollectionType(boolean sendCollectionType) {
		hesisproxyFactoryBan.setSendCollectionType(sendCollectionType);
	}

	@Override
	public void setOverloadEnabled(boolean overloadEnabled) {
		hesisproxyFactoryBan.setOverloadEnabled(overloadEnabled);
	}

	@Override
	public void setUsername(String username) {
		hesisproxyFactoryBan.setUsername(username);
	}

	@Override
	public void setPassword(String password) {
		hesisproxyFactoryBan.setPassword(password);
	}

	@Override
	public void setChunkedPost(boolean chunkedPost) {
		hesisproxyFactoryBan.setChunkedPost(chunkedPost);
	}

	@Override
	public void setReadTimeout(long timeout) {
		hesisproxyFactoryBan.setReadTimeout(timeout);
	}

	@Override
	public void setHessian2(boolean hessian2) {
		hesisproxyFactoryBan.setHessian2(hessian2);
	}
}
