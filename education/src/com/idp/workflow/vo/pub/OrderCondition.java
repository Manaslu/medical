package com.idp.workflow.vo.pub;

import java.io.Serializable;

/**
 * 查询条件
 * 
 * @author panfei
 * 
 */
public class OrderCondition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4900970323997224742L;

	public OrderCondition(IWorkFlowTypes.OrderType orderType,
			String... orderName) {
		this.setOrderNames(orderName);
		this.setOrderType(orderType);
	}

	private String[] orderNames;

	private IWorkFlowTypes.OrderType orderType;

	public String[] getOrderNames() {
		return orderNames;
	}

	public void setOrderNames(String... orderName) {
		this.orderNames = orderName;
	}

	public IWorkFlowTypes.OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(IWorkFlowTypes.OrderType orderType) {
		this.orderType = orderType;
	}

}
