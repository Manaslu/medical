package com.idp.workflow.itf.filter;

import com.idp.workflow.exception.pub.WfBusinessException;

/**
 * 迭代过滤器抽象类
 * 
 * @author panfei
 * 
 */
public abstract class AbstracrFilter<T> implements IFilter<T> {

	private IFilter<T> next;

	@Override
	public void beginExecute(T context) throws WfBusinessException {
		this.beginInvoke(context);
		if (this.getNextFilter() != null) {
			this.getNextFilter().beginExecute(context);
		}
	}

	@Override
	public void endExecute(T context) throws WfBusinessException {
		this.endInvoke(context);
		if (this.getNextFilter() != null) {
			this.getNextFilter().endExecute(context);
		}
	}

	@Override
	public void handleException(Throwable erro) {
		this.exceptionInvoke(erro);
		if (this.getNextFilter() != null) {
			this.getNextFilter().handleException(erro);
		}
	}

	@Override
	public void finalExecute(T context) throws WfBusinessException {
		this.finalInvoke(context);
		if (this.getNextFilter() != null) {
			this.getNextFilter().finalExecute(context);
		}
	}

	/**
	 * 开始实际触发任务
	 * 
	 * @param context
	 * @throws WfBusinessException
	 */
	public abstract void beginInvoke(T context) throws WfBusinessException;

	/**
	 * 结束实际触发任务
	 * 
	 * @param context
	 * @throws WfBusinessException
	 */
	public abstract void endInvoke(T context) throws WfBusinessException;

	/**
	 * 最终处理触发任务
	 * 
	 * @param context
	 * @throws WfBusinessException
	 */
	public abstract void finalInvoke(T context) throws WfBusinessException;

	/**
	 * 异常处理
	 * 
	 * @param context
	 * @throws WfBusinessException
	 */
	public abstract void exceptionInvoke(Throwable erro);

	@Override
	public IFilter<T> getNextFilter() {
		return next;
	}

	@Override
	public void setNextFilter(IFilter<T> next) {
		this.next = next;
	}
}
