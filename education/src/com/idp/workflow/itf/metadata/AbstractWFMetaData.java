package com.idp.workflow.itf.metadata;

import java.util.Map;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.impl.adapter.DefAdapterFactoryBuilder;
import com.idp.workflow.itf.adapter.IAdapterFactoryBuilder;
import com.idp.workflow.itf.builder.AbstractAdapterBuilder;
import com.idp.workflow.itf.service.ILocater;
import com.idp.workflow.util.service.LocaterServiceFactory;

/**
 * 抽象工作流元数据
 * 
 * @author panfei
 * 
 * @param <T>
 */
public abstract class AbstractWFMetaData<T> implements IWorkFlowMetaData<T> {

	private T workFlowSource;

	public void setWorkFlowSource(T workFlowSource) {
		this.workFlowSource = workFlowSource;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<T> getType() {
		return (Class<T>) workFlowSource.getClass();
	}

	@Override
	public T getWorkFlowObject() {
		return workFlowSource;
	}

	@Override
	public String getTypeName() {
		return getType().toString();
	}

	private IAdapterFactoryBuilder adapterFactoryBuilder;

	@Override
	public IAdapterFactoryBuilder getAdapterFactoryBuilder() {
		if (adapterFactoryBuilder == null) {
			adapterFactoryBuilder = new DefAdapterFactoryBuilder();
		}
		return adapterFactoryBuilder;
	}

	public void setAdapterFactoryBuilder(
			IAdapterFactoryBuilder adapterFactoryBuilder) {
		this.adapterFactoryBuilder = adapterFactoryBuilder;
	}

	private Map<String, Object> context;

	@Override
	public Map<String, Object> getContext() {
		return context;
	}

	public void setContext(Map<String, Object> context) {
		this.context = context;
	}

	@Override
	public <E> E getAdapterSerivce(Class<E> classname)
			throws WfBusinessException {
		return ((E) getAdapterFactoryBuilder().createClassicAdapterBuilder(
				classname).createAdapterService(this));
	}

	/**
	 * 添加一个服务构建者到元数据的构建者工厂
	 * 
	 * @param key
	 * @param value
	 */
	public void addAdapterBuilder(Class<?> key,
			AbstractAdapterBuilder<?, ? extends IWorkFlowMetaData<?>> value) {
		IAdapterFactoryBuilder facbuilder = this.getAdapterFactoryBuilder();
		if (facbuilder != null
				&& facbuilder instanceof DefAdapterFactoryBuilder) {
			DefAdapterFactoryBuilder deffacbuilder = (DefAdapterFactoryBuilder) facbuilder;
			deffacbuilder.regAdapterBuilder(key, value);
		}
	}

	public void addAdapterBuilder(
			AbstractAdapterBuilder<?, ? extends IWorkFlowMetaData<?>> value) {
		this.addAdapterBuilder(value.getClassKey(), value);
	}

	@Override
	public String toString() {
		StringBuilder appender = new StringBuilder();
		appender.append(getTypeName());
		appender.append(";");
		appender.append(String.valueOf(getAdapterFactoryBuilder()));
		appender.append(";");
		appender.append(String.valueOf(getContext()));
		return appender.toString();
	}

	private ILocater locater;

	@Override
	public ILocater getLocater() {
		return locater;
	}

	public void setLocater(ILocater locater) {
		LocaterServiceFactory.setInstance(locater);
		this.locater = locater;
	}

	@Override
	public boolean isServiceRegAuto() {
		return serviceRegAuto;
	}

	public void setServiceRegAuto(boolean serviceRegAuto) {
		this.serviceRegAuto = serviceRegAuto;
	}

	private boolean serviceRegAuto = false;
}
