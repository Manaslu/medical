package com.idap.dataprocess.dataset.entity;

import java.io.File;
import java.util.Date;

import com.idp.pub.utils.DateUtil;

/**
 * @###################################################
 * @功能描述：数据集
 * @创建日期：2014-4-21 16:45:29
 * @开发人员：bai
 * @修改日志：
 * @###################################################
 */
public class DataSet implements java.io.Serializable {
	private Long id; //
	private String dataDefId; //
	private String dataDefName;// [附加属性]
	private String dataSetId; //
	private String dataSetName; //
	private String tableName; //
	private String createUser; //
	private String createUserName; // [附加属性]
	private Date createDate; //
	private String createDateStr;// [附加属性]
	private String autoManu; //
	private Long dataAmount; //
	private String sourceDataSet; //
	private String type; //
	private String dataDesc; //
	private String evaluateState; // 评估状态
	private Date evaluateDate; // 评估状态
	private String validFlag="Y"; //有效标志
	private String startTime;// [附加属性]开始时间
	private String endTime;// [附加属性]结束时间

	private File errorFile;// [附加属性]上传时错误的文本文件
	private Integer impRecoreCount = 0;// [附加属性]导入记录总数
	private Integer impSuccessCount = 0;// [附加属性]导入成功记录总数
	private Integer impFailCount = 0;// [附加属性]导入失败记录总数
	private Integer errorLogCount = 0;// [附加属性]上传时错误文件的下载
	private String systemName;// [附加属性]评估时前台系统名称

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public Date getEvaluateDate() {
		return evaluateDate;
	}

	public String getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

	public void setEvaluateDate(Date evaluateDate) {
		this.evaluateDate = evaluateDate;
	}

	public String getEvaluateState() {
		return evaluateState;
	}

	public String getType() {
		return type;
	}

	public Integer getErrorLogCount() {
		return errorLogCount;
	}

	public void setErrorLogCount(Integer errorLogCount) {
		this.errorLogCount = errorLogCount;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setEvaluateState(String evaluateState) {
		this.evaluateState = evaluateState;
	}

	public String getDataDesc() {
		return dataDesc;
	}

	public void setDataDesc(String dataDesc) {
		this.dataDesc = dataDesc;
	}

	public Integer getImpRecoreCount() {
		return impRecoreCount;
	}

	public void setImpRecoreCount(Integer impRecoreCount) {
		this.impRecoreCount = impRecoreCount;
	}

	public File getErrorFile() {
		return errorFile;
	}

	public void setErrorFile(File errorFile) {
		this.errorFile = errorFile;
	}

	public String getDataDefName() {
		return dataDefName;
	}

	public void setDataDefName(String dataDefName) {
		this.dataDefName = dataDefName;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/** default constructor */
	public DataSet() {
	}

	public String getDataDefId() {
		return this.dataDefId;
	}

	public void setDataDefId(String dataDefId) {
		this.dataDefId = dataDefId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDataSetId() {
		return this.dataSetId;
	}

	public void setDataSetId(String dataSetId) {
		this.dataSetId = dataSetId;
	}

	public String getDataSetName() {
		return this.dataSetName;
	}

	public void setDataSetName(String dataSetName) {
		this.dataSetName = dataSetName;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateDateStr() {
		return this.createDate != null ? DateUtil.dateTimeToStrDefault(this.createDate) : "";
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
		this.createDateStr = this.getCreateDateStr();
	}

	public String getAutoManu() {
		return this.autoManu;
	}

	public void setAutoManu(String autoManu) {
		this.autoManu = autoManu;
	}

	public Long getDataAmount() {
		return this.dataAmount;
	}

	public void setDataAmount(Long dataAmount) {
		this.dataAmount = dataAmount;
	}

	public String getSourceDataSet() {
		return this.sourceDataSet;
	}

	public void setSourceDataSet(String sourceDataSet) {
		this.sourceDataSet = sourceDataSet;
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

	public Integer getImpSuccessCount() {
		return impSuccessCount;
	}

	public void setImpSuccessCount(Integer impSuccessCount) {
		this.impSuccessCount = impSuccessCount;
	}

	public Integer getImpFailCount() {
		return impFailCount;
	}

	public void setImpFailCount(Integer impFailCount) {
		this.impFailCount = impFailCount;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

}