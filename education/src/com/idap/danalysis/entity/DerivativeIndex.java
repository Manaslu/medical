package com.idap.danalysis.entity;

 
import java.util.Date;

import com.idp.pub.entity.annotation.MetaTable;
 

@MetaTable
public class DerivativeIndex implements java.io.Serializable {
		private static final long serialVersionUID = 4736511926578194639L;

 
	private String   indexId; //indexId
	private String   calMethod; //CAL_METHOD
	private String   createUser; //CREATE_USER
	private Date     createTime ; //CREATE_TIME
	private String   indexVersion; //INDEX_VERSION
	private String   calMethodDesc;//CAL_METHOD_DESC
	private String    indexDirectionId;
	
 
	
	
 
	public String getIndexDirectionId() {
		return indexDirectionId;
	}
	public void setIndexDirectionId(String indexDirectionId) {
		this.indexDirectionId = indexDirectionId;
	}
	public String getCalMethodDesc() {
		return calMethodDesc;
	}
	public void setCalMethodDesc(String calMethodDesc) {
		this.calMethodDesc = calMethodDesc;
	}
	public String getIndexId() {
		return indexId;
	}
	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}
	public String getCalMethod() {
		return calMethod;
	}
	public void setCalMethod(String calMethod) {
		this.calMethod = calMethod;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getIndexVersion() {
		return indexVersion;
	}
	public void setIndexVersion(String indexVersion) {
		this.indexVersion = indexVersion;
	}
 
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
 
	
	
	
	 

 
	 
	
 
 


 
}
