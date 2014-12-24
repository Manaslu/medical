package com.idp.pub.spring.teleservice.delegator;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.caucho.hessian.io.SerializerFactory;
import com.idp.pub.service.IBaseService;
import com.idp.pub.service.annotation.RemoteService;
import com.idp.pub.spring.teleservice.IRemoteService;

/**
 * Hessian代理抽象类
 * 
 * @author panfei
 * 
 */
public abstract class HessianDelegator implements BeanPostProcessor {

	public abstract void setSerializerFactory(
			SerializerFactory serializerFactory);

	public abstract void setSendCollectionType(boolean sendCollectionType);

	public abstract void setOverloadEnabled(boolean overloadEnabled);

	public abstract void setUsername(String username);

	public abstract void setPassword(String password);

	public abstract void setChunkedPost(boolean chunkedPost);

	public abstract void setReadTimeout(long timeout);

	public abstract void setHessian2(boolean hessian2);

	protected boolean isNeedRemoteServerSupport(Object bean) {
		if (bean == null) {
			return false;
		}

		if (bean instanceof IRemoteService
				|| bean.getClass().isAnnotationPresent(RemoteService.class)) {
			return true;
		}
		return false;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	/**
	 * 不要配置具体的servlet-name子服务的路径，只配置根路径 例如：http://127.0.0.1:9090/server/
	 */
	private String serviceUrl;

	/**
	 * 默认0 使用beanname，1使用接口类名称，全部小写
	 */
	private int urlCreateType = 0;

	public int getUrlCreateType() {
		return urlCreateType;
	}

	public void setUrlCreateType(int urlcreateType) {
		this.urlCreateType = urlcreateType;
	}

	/**
	 * 动态形成url名称
	 * 
	 * @param bean
	 * @param beanName
	 * @return
	 */
	protected String createRemoteServerUrl(Object bean, String beanName) {
		String baseUrl = this.getServiceUrl().trim();
		if (!baseUrl.endsWith("/")) {
			baseUrl += "/";
		}
		if (this.getUrlCreateType() == 0) {
			baseUrl += beanName.toLowerCase();
		} else {

			baseUrl += getClassInterFace(bean, beanName).toString()
					.toLowerCase();

		}
		return baseUrl;
	}

	/**
	 * 获取接口类型
	 * 
	 * @param bean
	 * @return
	 */
	protected Class<?> getClassInterFace(Object bean, String beanName)
			throws BeansException {
		Class<?>[] itfs = bean.getClass().getInterfaces();
		for (Class<?> temp : itfs) {
			if (!(IRemoteService.class.equals(temp) || IBaseService.class
					.equals(temp))) {
				if (temp.getPackage().getName().trim().endsWith(".service")) {
					return temp;
				}
			}
		}
		throw new BeanCreationException(beanName + ":"
				+ bean.getClass().toString() + "找不到实现服务接口，不能创建远程服务代理类！");
	}
}
