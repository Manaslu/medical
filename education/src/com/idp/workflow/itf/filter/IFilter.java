package com.idp.workflow.itf.filter;

import com.idp.workflow.exception.pub.WfBusinessException;

/**
 * 迭代过滤器接口
 * 
 * @author panfei
 * 
 */
public interface IFilter<T> {

	/**
	 * 开始
	 * 
	 * @param context
	 * @throws WfBusinessException
	 */
	void beginExecute(T context) throws WfBusinessException;

	/**
	 * 结束
	 * 
	 * @param context
	 * @throws WfBusinessException
	 */
	void endExecute(T context) throws WfBusinessException;

	/**
	 * 最终操作
	 * 
	 * @param context
	 * @throws WfBusinessException
	 */
	void finalExecute(T context) throws WfBusinessException;

	/**
	 * 异常处理
	 * 
	 * @param erro
	 */
	void handleException(Throwable erro);

	/**
	 * 获取下一个过滤器
	 * 
	 * @return
	 */
	IFilter<T> getNextFilter();

	/**
	 * 设置下一个迭代器
	 * 
	 * @param filter
	 */
	void setNextFilter(IFilter<T> filter);
}
