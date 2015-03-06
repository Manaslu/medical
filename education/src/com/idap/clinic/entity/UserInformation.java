package com.idap.clinic.entity;

import com.idp.pub.entity.annotation.MetaTable;

@MetaTable
public class UserInformation implements java.io.Serializable {
	private static final long serialVersionUID = 5736511926578194639L;
	 
 	private String		mUserId 	;//	m_user_id 	M_USER_ID 
	private String		mUserName 	;//	m_user_name 	M_USER_NAME 
	private String		mUserTel  	;//	m_user_tel  	M_USER_TEL 
	private String		departmentName  ;  
	
	public String getmUserId() {
		return mUserId;
	}
	public void setmUserId(String mUserId) {
		this.mUserId = mUserId;
	}
	public String getmUserName() {
		return mUserName;
	}
	public void setmUserName(String mUserName) {
		this.mUserName = mUserName;
	}
	public String getmUserTel() {
		return mUserTel;
	}
	public void setmUserTel(String mUserTel) {
		this.mUserTel = mUserTel;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
 
 
	 
	

}
