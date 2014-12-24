package com.idap.danalysis.entity;


 
 
import java.util.Date;

import com.idp.pub.entity.annotation.MetaTable;
 

@MetaTable
public class KPI implements java.io.Serializable {
	private static final long serialVersionUID = 4736511926578194639L;

 
	private String   indexId; //指标代码
	private String   indexName; //指标名称
	private String   indexDesc; //描述
	private String   sourceSystem;//来源
	private String   indexTheme_name;//分类
	private String   indexTheme_id;
	private String   derIndex_flag;
	private String   indexVersion;
	private String   sourceTable;//源表
	private String   sourceColumn;//字段
	private String   calMethod;//方法
	private String   createUser;//创建人
	private String   userId;//创建人
	private Date   createTime;//创建时间
	private String   failFlag;//创建时间
	private String   calMethodDesc;//方法
	private String    indexDirectionId;
	
 
	

	
	
	 
 
	public String getIndexDirectionId() {
		return indexDirectionId;
	}
	public void setIndexDirectionId(String indexDirectionId) {
		this.indexDirectionId = indexDirectionId;
	}
	public String getFailFlag() {
		return failFlag;
	}
	public void setFailFlag(String failFlag) {
		this.failFlag = failFlag;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getIndexTheme_id() {
		return indexTheme_id;
	}
	public void setIndexTheme_id(String indexTheme_id) {
		this.indexTheme_id = indexTheme_id;
	}
	public String getDerIndex_flag() {
		return derIndex_flag;
	}
	public void setDerIndex_flag(String derIndex_flag) {
		this.derIndex_flag = derIndex_flag;
	}
	public String getIndexId() {
		return indexId;
	}
	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}
	public String getIndexName() {
		return indexName;
	}
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	public String getIndexDesc() {
		return indexDesc;
	}
	
	public String getIndexVersion() {
		return indexVersion;
	}
	public void setIndexVersion(String indexVersion) {
		this.indexVersion = indexVersion;
	}
	public void setIndexDesc(String indexDesc) {
		this.indexDesc = indexDesc;
	}
	public String getSourceSystem() {
		return sourceSystem;
	}
	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}
	public String getIndexTheme_name() {
		return indexTheme_name;
	}
	public void setIndexTheme_name(String indexTheme_name) {
		this.indexTheme_name = indexTheme_name;
	}
	public String getSourceTable() {
		return sourceTable;
	}
	public void setSourceTable(String sourceTable) {
		this.sourceTable = sourceTable;
	}
	public String getSourceColumn() {
		return sourceColumn;
	}
	public void setSourceColumn(String sourceColumn) {
		this.sourceColumn = sourceColumn;
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
 
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCalMethodDesc() {
		return calMethodDesc;
	}
	public void setCalMethodDesc(String calMethodDesc) {
		this.calMethodDesc = calMethodDesc;
	}
	
	 
	 
	
 
 


 
}
