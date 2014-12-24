package com.idp.pub.entity;

import java.io.Serializable;

/**
 * 业务VO接口实现
 * 
 * @author panfei
 * 
 */
public interface IValueObject extends IEntity, Serializable, Cloneable {

	/**
	 * 获取全部的属性名称列表
	 * 
	 * @return
	 */
	String[] getAttributeNames();

	/**
	 * 根据属性名称获取对应的值
	 * 
	 * @param attributeName
	 *            属性名称，大小写与calss的属性声明保持一致
	 * @return
	 */
	Object getAttributeValue(String attributeName);

	/**
	 * 根据属性名称设置对应的值
	 * 
	 * @param name
	 *            属性名称，大小写与calss的属性声明保持一致
	 * @param value
	 *            需要更新的内容
	 */
	void setAttributeValue(String name, Object value);

	/**
	 * 根据属性名称获取对应的值类型
	 * 
	 * @param attributeName
	 *            属性名称，大小写与calss的属性声明保持一致
	 * @return
	 */
	Class<?> getAttributeType(String attributeName);

}
