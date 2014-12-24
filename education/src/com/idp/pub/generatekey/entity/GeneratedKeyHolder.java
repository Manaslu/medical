package com.idp.pub.generatekey.entity;

import java.io.Serializable;

/**
 * 主键持有者
 * 
 * @author panfei
 * 
 */
public class GeneratedKeyHolder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8947282355812881426L;

	private String nextKey;

	private String entityName;

	public String getNextKey() {
		return nextKey;
	}

	public void setNextKey(String nextKey) {
		this.nextKey = nextKey;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
}
