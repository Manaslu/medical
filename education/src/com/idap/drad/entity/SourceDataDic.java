package com.idap.drad.entity;


/**
 * /** ###################################################
 * 
 * @创建日期�?014-4-4 16:38:42
 * @�?��人员：bai
 * @功能描述�?
 * @修改日志�?###################################################
 */
public class SourceDataDic implements java.io.Serializable {
	private String sourceSystem; //
	private String tableName; //
	private String tableDesc; //
	private String tableType; //
	private String portCode;

	private String startTime;// [附加属�?]�?��时间
	private String endTime;// [附加属�?]结束时间
	  
	private String tableNameEn;
	      
	
	public String getTableNameEn() {
		return tableNameEn;
	}

	public void setTableNameEn(String tableNameEn) {
		this.tableNameEn = tableNameEn;
	}

	/** default constructor */
	public SourceDataDic() {
	}

	public String getSourceSystem() {
		return this.sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	public String getPortCode() {
		return portCode;
	}

	public void setPortCode(String portCode) {
		this.portCode = portCode;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableDesc() {
		return this.tableDesc;
	}

	public void setTableDesc(String tableDesc) {
		this.tableDesc = tableDesc;
	}

	public String getTableType() {
		return this.tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
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