package com.idp.pub.entity.utils;

/**
 * 数据属性
 * 
 * @author panfei
 * 
 */
public class DataAttribute {

	private int dataType;

	private Object value;

	public DataAttribute(int dataType, Object value) {
		this.dataType = dataType;
		this.value = value;
	}

	public DataAttribute(Object value) {
		this(SqlDataConvert.checkDataType(value), value);
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getStringValue() {
		if (value == null) {
			return null;
		}
		return String.valueOf(value);
	}
}
