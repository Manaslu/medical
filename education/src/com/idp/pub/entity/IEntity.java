package com.idp.pub.entity;

/**
 * 实体接口
 * 
 * @author panfei
 * 
 */
public interface IEntity {

	public enum EntityType {
		BaseEntity, AggEntity, AggExtEntity
	}

	/**
	 * 获取该业务VO的实体名称，一般返回具体的VO的class类型全称
	 * 
	 * @return
	 */
	String getEntityName();

	/**
	 * 获取实体类型
	 * 
	 * @return
	 */
	EntityType getEntityType();
}
