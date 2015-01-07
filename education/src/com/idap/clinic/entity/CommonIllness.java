package com.idap.clinic.entity;


import com.idp.pub.entity.annotation.MetaTable;

@MetaTable
public class CommonIllness implements java.io.Serializable {
	private static final long serialVersionUID = 5736511926578194639L;
	
	private String     illnessId;//ILLNESS_ID
	private String     illnessName;//ILLNESS_NAME 
	private String     illnessDesc;//ILLNESS_DESC
	private String     illnessTypeId;//ILLNESS_TYPE_ID
	private String     illnessTypeName;//
	
	
 
	public String getIllnessId() {
		return illnessId;
	}
	public void setIllnessId(String illnessId) {
		this.illnessId = illnessId;
	}
	public String getIllnessName() {
		return illnessName;
	}
	public void setIllnessName(String illnessName) {
		this.illnessName = illnessName;
	}
	public String getIllnessDesc() {
		return illnessDesc;
	}
	public void setIllnessDesc(String illnessDesc) {
		this.illnessDesc = illnessDesc;
	}
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
