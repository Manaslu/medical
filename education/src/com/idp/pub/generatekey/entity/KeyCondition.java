package com.idp.pub.generatekey.entity;

import java.io.Serializable;

/**
 * 生成条件
 * 
 * @author panfei
 * 
 */
public class KeyCondition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -830952344050380042L;

	private int step = 1;

	private String entityName = null;

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
}
