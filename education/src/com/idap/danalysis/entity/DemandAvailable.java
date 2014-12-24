package com.idap.danalysis.entity;

import java.util.Date;

import com.idp.pub.entity.annotation.MetaTable;


@MetaTable
public class DemandAvailable implements java.io.Serializable {
	private static final long serialVersionUID = 4736511926578194639L;

 
	
	private String   requCode; //REQU_CODE
	private String   requName; //REQU_NAME
	private String   requDesc; //REQU_DESC
	private String   applOrg; //APPL_ORG
	private String   applDept; //APPL_DEPT
	private String   applPer; //APPL_PER
	
	private String   id; //ID
	private String   dataApplTel; //DATA_APPL_TEL
	private String   recvWorker; //RECV_WORKER
	private String   spcOrjName; //SPC_ORJ_NAME
	private Date     createTime; //CREATE_TIME
	


	
	

	
	
	public String getRequCode() {
		return requCode;
	}
	public void setRequCode(String requCode) {
		this.requCode = requCode;
	}
	public String getRequName() {
		return requName;
	}
	public void setRequName(String requName) {
		this.requName = requName;
	}
	public String getRequDesc() {
		return requDesc;
	}
	public void setRequDesc(String requDesc) {
		this.requDesc = requDesc;
	}
	public String getApplOrg() {
		return applOrg;
	}
	public void setApplOrg(String applOrg) {
		this.applOrg = applOrg;
	}
	public String getApplDept() {
		return applDept;
	}
	public void setApplDept(String applDept) {
		this.applDept = applDept;
	}
	public String getApplPer() {
		return applPer;
	}
	public void setApplPer(String applPer) {
		this.applPer = applPer;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDataApplTel() {
		return dataApplTel;
	}
	public void setDataApplTel(String dataApplTel) {
		this.dataApplTel = dataApplTel;
	}
	public String getRecvWorker() {
		return recvWorker;
	}
	public void setRecvWorker(String recvWorker) {
		this.recvWorker = recvWorker;
	}
	public String getSpcOrjName() {
		return spcOrjName;
	}
	public void setSpcOrjName(String spcOrjName) {
		this.spcOrjName = spcOrjName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
	
 
	 
	
	
	 
	
 
	

	
	
 
	
	 
	 
	
 
 


 
}
