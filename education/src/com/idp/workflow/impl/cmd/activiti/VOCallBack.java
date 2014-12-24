package com.idp.workflow.impl.cmd.activiti;

/**
 * 返回取值
 * 
 * @author panfei
 * 
 */
public class VOCallBack<T> {

	private T retVO;

	public T getRetVO() {
		return retVO;
	}

	public void setRetVO(T retVO) {
		this.retVO = retVO;
	}
}
