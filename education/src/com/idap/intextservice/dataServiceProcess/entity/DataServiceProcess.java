package com.idap.intextservice.dataServiceProcess.entity;

import java.util.Date;

import com.idp.pub.entity.SmartEntity;
import com.idp.pub.entity.annotation.MetaTable;
import com.idp.pub.entity.annotation.PrimaryKey;
import com.idp.pub.entity.annotation.PrimaryKey.PK;

/**
 * ###################################################
 * 
 * @创建日期：2014-5-20 14:36:42
 * @开发人员：huhanjiang
 * @功能描述：数据服务流程管理实体类
 * @修改日志： ###################################################
 */
@MetaTable
public class DataServiceProcess extends SmartEntity{
	
	
	private static final long serialVersionUID = 4736511922578194612L;
	
	@PrimaryKey(createType=PK.useIDP)
	private String dataServiceId;			//数据服务ID
	private Long custMamId;					//客户管理ID
	private String flowRuleCustId;			//流程规则定制ID
	private String runProcId;				//运行流程ID
	private String dataServiceName;			//数据服务名称
	private String createPer;				//创建人
	private String userName;				//用户名[附加，反显创建人的]
	private Date createTime = new Date();	//创建时间
	private Date createTimeStr = new Date();//创建时间【附加，用于查询时间段】
	private String dataServiceDesc;			//数据服务描述
	private String serviceState;			//服务状态：EXEC-已执行；UNEXEC-未执行
	private String sourceDataSetId;			//源数据集id
	private String resultDataSetId;			//结果数据集id
	
	
	private String sourceDataSet;			//源数据集【附加】
	private String runProcName;				//运行流程名称【附加】
	private String custName;				//客户名称【附加】
	private String resultDataSetName;		//结果数据集名称【附加】
	private String dataSetName; 			//源数据集名称【附加】

	
	public String getSourceDataSetId() {
		return sourceDataSetId;
	}
	public void setSourceDataSetId(String sourceDataSetId) {
		this.sourceDataSetId = sourceDataSetId;
	}
	public String getResultDataSetId() {
		return resultDataSetId;
	}
	public void setResultDataSetId(String resultDataSetId) {
		this.resultDataSetId = resultDataSetId;
	}
	public String getServiceState() {
		return serviceState;
	}
	public void setServiceState(String serviceState) {
		this.serviceState = serviceState;
	}
	public Date getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(Date createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public String getResultDataSetName() {
		return resultDataSetName;
	}
	public void setResultDataSetName(String resultDataSetName) {
		this.resultDataSetName = resultDataSetName;
	}
	public String getDataSetName() {
		return dataSetName;
	}
	public void setDataSetName(String dataSetName) {
		this.dataSetName = dataSetName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSourceDataSet() {
		return sourceDataSet;
	}
	public void setSourceDataSet(String sourceDataSet) {
		this.sourceDataSet = sourceDataSet;
	}
	public String getRunProcName() {
		return runProcName;
	}
	public void setRunProcName(String runProcName) {
		this.runProcName = runProcName;
	}
	public String getDataServiceId() {
		return dataServiceId;
	}
	public void setDataServiceId(String dataServiceId) {
		this.dataServiceId = dataServiceId;
	}
	public Long getCustMamId() {
		return custMamId;
	}
	public void setCustMamId(Long custMamId) {
		this.custMamId = custMamId;
	}
	public String getFlowRuleCustId() {
		return flowRuleCustId;
	}
	public void setFlowRuleCustId(String flowRuleCustId) {
		this.flowRuleCustId = flowRuleCustId;
	}
	public String getRunProcId() {
		return runProcId;
	}
	public void setRunProcId(String runProcId) {
		this.runProcId = runProcId;
	}
	public String getDataServiceName() {
		return dataServiceName;
	}
	public void setDataServiceName(String dataServiceName) {
		this.dataServiceName = dataServiceName;
	}
	public String getCreatePer() {
		return createPer;
	}
	public void setCreatePer(String createPer) {
		this.createPer = createPer;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getDataServiceDesc() {
		return dataServiceDesc;
	}
	public void setDataServiceDesc(String dataServiceDesc) {
		this.dataServiceDesc = dataServiceDesc;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}


}
