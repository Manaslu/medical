package com.idp.pub.entity;

import java.util.ArrayList;
import java.util.List;

import com.idp.pub.entity.constants.EntityStatus;
import com.idp.pub.entity.utils.ColumnAttribute;
import com.idp.pub.entity.utils.SqlDataConvert;

/**
 * 基础VO实现，所有模块的VO必须继承,适用于 多个主键、外键的业务vo
 * 
 * @author panfei
 * 
 */
public abstract class BaseEntity extends SuperEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 771324261998474119L;

	@Override
	public EntityType getEntityType() {
		return EntityType.BaseEntity;
	}

	public BaseEntity() {
		super();
	}

	/**
	 * 获取主键数据类型
	 * 
	 * @return
	 */
	public int[] getPksDataType() {
		ColumnAttribute[] artnames = this.getPKFieldAttributes();
		if (artnames != null && artnames.length > 0) {
			int[] pktypes = new int[artnames.length];
			int index = 0;
			for (ColumnAttribute tempstr : artnames) {
				pktypes[index] = SqlDataConvert.checkDataType(this
						.getAttributeType(tempstr.getPropertyName()));
				index++;
			}
			return pktypes;
		}
		return null;
	}

	/**
	 * 根据getPKFieldNames的顺序返回主键集合
	 * 
	 * @return
	 */
	public String[] getPrimaryKeys() {
		ColumnAttribute[] artnames = this.getPKFieldAttributes();
		if (artnames != null && artnames.length > 0) {
			List<String> pklist = new ArrayList<String>();
			for (ColumnAttribute tempstr : artnames) {
				Object value = this
						.getAttributeValue(tempstr.getPropertyName());
				if (value == null) {
					pklist.add(null);
				}
				pklist.add(String.valueOf(value));
			}
			return pklist.toArray(new String[pklist.size()]);
		}
		return null;
	}

	/**
	 * 根据getPKFieldNames的顺序对每个主键赋值
	 * 
	 * @param keys
	 */
	public void setPrimaryKeys(String... keys) {
		ColumnAttribute[] artnames = this.getPKFieldAttributes();
		if (artnames != null && artnames.length > 0 && keys != null
				&& keys.length > 0) {
			int index = 0;
			for (ColumnAttribute tempstr : artnames) {
				this.setAttributeValue(tempstr.getPropertyName(),
						SqlDataConvert.valueConvert(this
								.getAttributeType(tempstr.getPropertyName()),
								keys[index]));
				index++;
				if (index > (keys.length - 1)) {
					break;
				}
			}
		}
	}

	@Override
	public Integer getStatus() {
		if (super.getStatus() == null) {
			if (this.getPrimaryKeys() == null
					|| this.getPrimaryKeys().length == 0) {
				return EntityStatus.NEW;
			}
			return EntityStatus.UPDATED;
		}
		return super.getStatus();
	}

	/**
	 * 获取时间戳 必须声明 ts 属性<br>
	 * 必须为 yyyy-MM-dd HH24:mi:ss
	 * 
	 * @return
	 */
	public String getTimeStamp() {
		if (getTimeStampAttribute() == null) {
			return null;
		}
		return (String) this.getAttributeValue(getTimeStampAttribute()
				.getPropertyName());
	}

	/**
	 * 设置时间戳 必须声明 ts 属性<br>
	 * 必须为 yyyy-MM-dd HH24:mi:ss
	 * 
	 * @param ts
	 */
	public void setTimeStamp(String ts) {
		this.setAttributeValue(getTimeStampAttribute().getPropertyName(), ts);
	}

	/**
	 * 获取时间戳名称
	 * 
	 * @return
	 */
	public abstract String getTimeStampName();

	/**
	 * 说去时间戳注解属性
	 * 
	 * @return
	 */
	public abstract ColumnAttribute getTimeStampAttribute();

	/**
	 * 获取主键的名称
	 * 
	 * @return
	 */
	public abstract String[] getPKFieldNames();

	/**
	 * 获取主键注解说明
	 * 
	 * @return
	 */
	public abstract ColumnAttribute[] getPKFieldAttributes();

	/**
	 * 获取外键的名称
	 * 
	 * @return
	 */
	public abstract String[] getParentPKFieldNames();

	/**
	 * 获取实际该VO对应的表名称
	 * 
	 * @return
	 */
	public abstract String getTableName();

	/**
	 * 获取列属性列表
	 * 
	 * @return
	 */
	public abstract ColumnAttribute[] findColFieldAttributes();

}
