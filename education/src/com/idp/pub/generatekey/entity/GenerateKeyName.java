package com.idp.pub.generatekey.entity;

import com.idp.pub.entity.SmartEntity;
import com.idp.pub.entity.annotation.Column;
import com.idp.pub.entity.annotation.MetaTable;
import com.idp.pub.entity.annotation.PrimaryKey;

/**
 * 主键策略名称
 * 
 * @author panfei
 * 
 */
@MetaTable(name = "ENTITY_PK_NAME")
public class GenerateKeyName extends SmartEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6770760523030263989L;

	@PrimaryKey(name = "ENTITY_NAME")
	private String entityName;

	@Column(name = "PREFIX_NAME")
	private String prefixName;

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getPrefixName() {
		return prefixName;
	}

	public void setPrefixName(String prefixName) {
		this.prefixName = prefixName;
	}
}
