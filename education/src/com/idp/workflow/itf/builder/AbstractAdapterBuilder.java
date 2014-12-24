package com.idp.workflow.itf.builder;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.metadata.IWorkFlowMetaData;

/**
 * 
 * @author panfei
 * 
 * @param <T>
 * @param <V>
 */
public abstract class AbstractAdapterBuilder<T, V extends IWorkFlowMetaData<?>> {

	private V targetSource;

	public V getTargetSource() {
		return targetSource;
	}

	public void setTargetSource(V targetSource) {
		this.targetSource = targetSource;
	}

	/**
	 * @return
	 */
	public Object createAdapterService() throws WfBusinessException {
		return createAdapterService(this.getTargetSource());
	}

	/**
	 * @param V
	 * @return
	 */
	public abstract T createAdapterService(V targetSource)
			throws WfBusinessException;

	/**
	 * 获取类型key
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Class<T> getClassKey() {
		Type genType = this.getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		return ((Class<T>) params[0]);
	}
}
