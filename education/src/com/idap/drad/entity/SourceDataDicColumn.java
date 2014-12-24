package com.idap.drad.entity;
import java.util.Date;

/**
/**
* ###################################################
* @创建日期：2014-4-3 16:39:06
* @开发人员：bai
* @功能描述：
* @修改日志：
* ###################################################
*/
public class SourceDataDicColumn implements java.io.Serializable {
		private String tableName; //
		private String colName; //
		private String colAttr; //
		private String colDesc; //
		private String colType; //
		private String length; //
		private String primaryKeyFlag; //
		private String foreignKeyFlag; //
		private String defaultVal; //
		private String isNull; //
		private String startTime;//[附加属性]开始时间
		private String endTime;//[附加属性]结束时间
	
		private String portCode;
  
		    
		public String getPortCode() {
			return portCode;
		}

		public void setPortCode(String portCode) {
			this.portCode = portCode;
		}

		/** default constructor */
		public SourceDataDicColumn() {
		}
	
		public String getTableName() {
			return this.tableName;
		}
	
		public void setTableName(String tableName) {
			this.tableName = tableName;
		}
		
		public String getColName() {
			return this.colName;
		}
	
		public void setColName(String colName) {
			this.colName = colName;
		}
		
		public String getColAttr() {
			return this.colAttr;
		}
	
		public void setColAttr(String colAttr) {
			this.colAttr = colAttr;
		}
		
		public String getColDesc() {
			return this.colDesc;
		}
	
		public void setColDesc(String colDesc) {
			this.colDesc = colDesc;
		}
		
		public String getColType() {
			return this.colType;
		}
	
		public void setColType(String colType) {
			this.colType = colType;
		}
		
		public String getLength() {
			return this.length;
		}
	
		public void setLength(String length) {
			this.length = length;
		}
		
		public String getPrimaryKeyFlag() {
			return this.primaryKeyFlag;
		}
	
		public void setPrimaryKeyFlag(String primaryKeyFlag) {
			this.primaryKeyFlag = primaryKeyFlag;
		}
		
		public String getForeignKeyFlag() {
			return this.foreignKeyFlag;
		}
	
		public void setForeignKeyFlag(String foreignKeyFlag) {
			this.foreignKeyFlag = foreignKeyFlag;
		}
		
		public String getDefaultVal() {
			return this.defaultVal;
		}
	
		public void setDefaultVal(String defaultVal) {
			this.defaultVal = defaultVal;
		}
		
		public String getIsNull() {
			return this.isNull;
		}
	
		public void setIsNull(String isNull) {
			this.isNull = isNull;
		}
		
		public String getStartTime() {
	        return startTime;
	    }
	
	    public void setStartTime(String startTime) {
	        this.startTime = startTime;
	    }
	
	    public String getEndTime() {
	        return endTime;
	    }
	
	    public void setEndTime(String endTime) {
	        this.endTime = endTime;
	    }
}