package com.idp.pub.entity;

import com.idp.pub.entity.utils.SqlDataConvert;
import com.idp.pub.utils.BeanHelper;
import com.idp.pub.utils.ClassObjUtil;
import com.idp.pub.utils.StringUtils;

/**
 * 基础vo抽象实现
 * 
 * @author panfei
 * 
 */
public abstract class SuperEntity implements IValueObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2112939586949962606L;

	public static final String TS_SHOWNAME = "ts";

	public static final String TS_ATTR = "tsattr";

	public static final String DR_SHOWNAME = "dr";

	public static final String TABLENAME_SHOWNAME = "tablename";

	public static final String PKFIELDNAMES_SHOWNAME = "pkfieldnames";

	public static final String PKFIELDATTRS = "pkfieldattrs";

	public static final String PARENTPKFIELDNAMES_SHOWNAME = "parentpkfieldnames";

	public static final String FOREIGNKEY_SHOWNAME = "foreignkey";

	public static final String FIELDCOLUMN_ATTRS = "fieldcolumnattrs";

	public SuperEntity() {
		super();
	}

	@Override
	public String getEntityName() {
		return this.getClass().getName();
	}

	@Override
	public String[] getAttributeNames() {
		return BeanHelper.getInstance().getPropertiesAry(this);
	}

	@Override
	public Object getAttributeValue(String attributeName) {
		return BeanHelper.getInstance().getPropertyValue(this, attributeName);
	}

	/**
	 * 只支持传入String值根据VO实际数据类型做自动转换，不支持VO属性为String,数据库为Integer，自动进行转换，虽然放开判断条件，
	 * 就可以自动实现，但这样做，在进行数据库保存修改会出现问题
	 */
	@Override
	public void setAttributeValue(String name, Object value) {
		if (!StringUtils.isEmpty(name)) {
			BeanHelper.getInstance().setPropertyValue(
					this,
					name,
					SqlDataConvert.valueConvert(this.getAttributeType(name),
							value));
		}
	}

	@Override
	public Class<?> getAttributeType(String attributeName) {
		return BeanHelper.getInstance()
				.getPropertyDataType(this, attributeName);
	}

	/**
	 * 业务VO状态
	 */
	private Integer status = null;

	/*
	 * 考虑外部可能使用getDeclaredMethods，所以不在基类实现，转为 抽象方法在子类实现 private Integer dr;
	 * private String ts;
	 */

	/**
	 * 浅拷贝 复制
	 */
	@Override
	public Object clone() {
		SuperEntity vo = null;
		try {
			vo = (SuperEntity) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
		String[] names = getAttributeNames();
		for (String name : names) {
			Object value = this.getAttributeValue(name);
			vo.setAttributeValue(name, value);
		}
		return vo;
	}

	/**
	 * 深拷贝克隆
	 * 
	 * @return
	 * @throws Exception
	 */
	public Object cloneObject() throws Exception {
		return ClassObjUtil.cloneObject(this);
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		String[] names = this.getAttributeNames();
		for (String name : names) {
			Object value = this.getAttributeValue(name);
			buffer.append(name);
			buffer.append("=");
			buffer.append(value);
			buffer.append("\r\n");
		}
		return buffer.toString();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
