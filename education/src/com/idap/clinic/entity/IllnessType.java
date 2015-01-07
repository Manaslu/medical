package com.idap.clinic.entity;

import com.idp.pub.entity.SmartEntity;
import com.idp.pub.entity.annotation.MetaTable;
 

/**
 * ###############################
 * 
 * @创建日期：2015-1-7
 * @开发人员：wang
 * @功能描述：疾病类别
 * @修改日志： #####################
 */

@MetaTable
public class IllnessType extends SmartEntity {

	private static final long serialVersionUID = -3609174224055481079L;

 

	private String illnessTypeId;// illness_type_id ILLNESS_TYPE_ID
	private String illnessTypeName;   // illness_type_name ILLNESS_TYPE_NAME

	public String getIllnessTypeId() {
		return illnessTypeId;
	}

	public void setIllnessTypeId(String illnessTypeId) {
		this.illnessTypeId = illnessTypeId;
	}

	public String getIllnessTypeName() {
		return illnessTypeName;
	}

	public void setIllnessTypeName(String illnessTypeName) {
		this.illnessTypeName = illnessTypeName;
	}
 

	 

}
