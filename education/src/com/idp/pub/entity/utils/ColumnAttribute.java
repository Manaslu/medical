package com.idp.pub.entity.utils;

import java.io.Serializable;

import com.idp.pub.entity.annotation.PrimaryKey.PK;

/**
 * 公共列属性
 * 
 * @author panfei
 * 
 */
public class ColumnAttribute implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7872284967098239291L;

	private String name;

	private PK pkCreateType = null;

	private String propertyName;

	public ColumnAttribute(String propertyName, String colname, PK colcreateType) {
		this.setName(colname);
		this.setPkCreateType(colcreateType);
		this.setPropertyName(propertyName);
	}

	public ColumnAttribute(String propertyName, String colname) {
		this(propertyName, colname, null);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PK getPkCreateType() {
		return pkCreateType;
	}

	public void setPkCreateType(PK pkCreateType) {
		this.pkCreateType = pkCreateType;
	}

	public boolean isPk() {
		return pkCreateType != null;
	}

	@Override
	public String toString() {
		return this.getName();
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
}
