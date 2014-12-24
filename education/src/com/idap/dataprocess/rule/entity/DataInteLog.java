package com.idap.dataprocess.rule.entity;

import java.util.Date;

import com.idap.dataprocess.dataset.entity.DataSet;
import com.idp.pub.utils.DateUtil;

/**
 * 
 * @author Raoxy
 */
public class DataInteLog implements java.io.Serializable {
	private static final long serialVersionUID = -4329141465326800477L;

	public static final String PROCESS_TYPE_AUTO = "auto";// 由数据服务流程管理增加的整合日志，加工类型定义为：自动

	public static final String PROCESS_TYPE_COPY = "acopy";// 由流程规则拷贝过来的流程

	public static final String PROCESS_TYPE_MANUAL = "manual";// 由数据整合增加的整合日志，加工类型定义为：手工

	private String id; //

	private String ruleId; // 数据整合规则ID

	private TableInteRule inteRule;

	private String processId;// 数据服务流程规则ID

	private Integer indexNo;// 顺序号

	private String opUser; // 执行人

	private String userName;// 操作人的姓名

	private Date startDate; // 执行开始时间

	private Date endDate; // 执行结束时间

	private DataSet sourceDataSet1; // 源数据集1

	private String dataSetId1;//

	private String set1Name;//

	private String dataDefId1;

	private String dataDefName1;

	private DataSet sourceDataSet2; // 源数据集2

	private String dataSetId2;//

	private String set2Name;

	private String dataDefId2;

	private String dataDefName2;

	private Long dataSet1RowCnt; // 源数据集1总数

	private Long dataSet2RowCnt; // 源数据集2总数

	private DataSet resultDataSet; // 结果数据集

	private String resultSetName;

	private String resultDataDefId;

	private String resultDataDefName;

	private String resultSetId;// 结果数据集的ID

	private Long resultDateSetRowCnt; // 结果数据集总数

	private String opStats; // 执行状态

	private String processType;// 加工类型

	private String ruleType;// 规则类型

	private Date createDate;//

	public DataInteLog() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getOpUser() {
		return this.opUser;
	}

	public void setOpUser(String opUser) {
		this.opUser = opUser;
	}

	public String getStartDateStr() {
		return this.startDate != null ? DateUtil
				.dateTimeToStrDefault(this.startDate) : "";
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getEndDateStr() {
		return this.endDate != null ? DateUtil
				.dateTimeToStrDefault(this.endDate) : "";
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getDataSet1RowCnt() {
		return this.dataSet1RowCnt;
	}

	public void setDataSet1RowCnt(Long dataSet1RowCnt) {
		this.dataSet1RowCnt = dataSet1RowCnt;
	}

	public Long getDataSet2RowCnt() {
		return this.dataSet2RowCnt;
	}

	public void setDataSet2RowCnt(Long dataSet2RowCnt) {
		this.dataSet2RowCnt = dataSet2RowCnt;
	}

	public Long getResultDateSetRowCnt() {
		return this.resultDateSetRowCnt;
	}

	public void setResultDateSetRowCnt(Long resultDateSetRowCnt) {
		this.resultDateSetRowCnt = resultDateSetRowCnt;
	}

	public String getOpStats() {
		return this.opStats;
	}

	public void setOpStats(String opStats) {
		this.opStats = opStats;
	}

	public DataSet getSourceDataSet1() {
		return sourceDataSet1;
	}

	public void setSourceDataSet1(DataSet sourceDataSet1) {
		this.sourceDataSet1 = sourceDataSet1;
	}

	public DataSet getSourceDataSet2() {
		return sourceDataSet2;
	}

	public void setSourceDataSet2(DataSet sourceDataSet2) {
		this.sourceDataSet2 = sourceDataSet2;
	}

	public DataSet getResultDataSet() {
		return resultDataSet;
	}

	public void setResultDataSet(DataSet resultDataSet) {
		this.resultDataSet = resultDataSet;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public Integer getIndexNo() {
		return indexNo;
	}

	public void setIndexNo(Integer indexNo) {
		this.indexNo = indexNo;
	}

	public String getResultSetId() {
		return resultSetId;
	}

	public void setResultSetId(String resultSetId) {
		this.resultSetId = resultSetId;
	}

	public TableInteRule getInteRule() {
		return inteRule;
	}

	public void setInteRule(TableInteRule inteRule) {
		this.inteRule = inteRule;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getSet1Name() {
		return set1Name;
	}

	public void setSet1Name(String set1Name) {
		this.set1Name = set1Name;
	}

	public String getSet2Name() {
		return set2Name;
	}

	public void setSet2Name(String set2Name) {
		this.set2Name = set2Name;
	}

	public String getResultSetName() {
		return resultSetName;
	}

	public void setResultSetName(String resultSetName) {
		this.resultSetName = resultSetName;
	}

	public String getDataSetId1() {
		return dataSetId1;
	}

	public void setDataSetId1(String dataSetId1) {
		this.dataSetId1 = dataSetId1;
	}

	public String getDataSetId2() {
		return dataSetId2;
	}

	public void setDataSetId2(String dataSetId2) {
		this.dataSetId2 = dataSetId2;
	}

	public String getDataDefId1() {
		return dataDefId1;
	}

	public void setDataDefId1(String dataDefId1) {
		this.dataDefId1 = dataDefId1;
	}

	public String getDataDefName1() {
		return dataDefName1;
	}

	public void setDataDefName1(String dataDefName1) {
		this.dataDefName1 = dataDefName1;
	}

	public String getDataDefId2() {
		return dataDefId2;
	}

	public void setDataDefId2(String dataDefId2) {
		this.dataDefId2 = dataDefId2;
	}

	public String getDataDefName2() {
		return dataDefName2;
	}

	public void setDataDefName2(String dataDefName2) {
		this.dataDefName2 = dataDefName2;
	}

	public String getResultDataDefId() {
		return resultDataDefId;
	}

	public void setResultDataDefId(String resultDataDefId) {
		this.resultDataDefId = resultDataDefId;
	}

	public String getResultDataDefName() {
		return resultDataDefName;
	}

	public void setResultDataDefName(String resultDataDefName) {
		this.resultDataDefName = resultDataDefName;
	}
}