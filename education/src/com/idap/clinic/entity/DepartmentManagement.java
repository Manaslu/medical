package com.idap.clinic.entity;
import com.idp.pub.entity.annotation.MetaTable;

@MetaTable
public class DepartmentManagement implements java.io.Serializable {
	private static final long serialVersionUID = 5736511926578194639L;
	 
	private String     departmentId;//ID department_id
	private String     departmentName;//DEPARTMENT_NAME department_name 
	private String     departmentDesc;//DEPARTMENT_DESC department_desc
 
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getDepartmentDesc() {
		return departmentDesc;
	}
	public void setDepartmentDesc(String departmentDesc) {
		this.departmentDesc = departmentDesc;
	}
	 
	
	
	 
	

}
