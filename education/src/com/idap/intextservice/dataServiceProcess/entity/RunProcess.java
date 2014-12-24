package com.idap.intextservice.dataServiceProcess.entity;

import java.util.Date;

import com.idp.pub.entity.SmartEntity;
import com.idp.pub.entity.annotation.MetaTable;
import com.idp.pub.entity.annotation.PrimaryKey;
import com.idp.pub.entity.annotation.PrimaryKey.PK;

/**
 * #####################################
 * 
 * @创建日期：2014-5-22
 * @开发人员：huhanjiang
 * @功能描述：运行流程实体类
 * @修改日志： ###########################
 */
@MetaTable
public class RunProcess extends SmartEntity{
	
	
	private static final long serialVersionUID = 4733512922578194612L;
	
	@PrimaryKey(createType=PK.useIDP)
	private String runProcId;			//运行流程ID
	private String runProcName;			//运行流程名称
	private String procRuleCustId;		//流程规则定制ID
	private String flowRuleCustName;	//流程规则名称
	private String dataServiceDesc;		//数据服务描述
	private String sourceDataSet;		//源数据集
	private String resultDataSet;		//结果数据集【执行完规则模板自动填上去】
	private String runPer;				//执行人
	private String userName;			//用户名[附加，反显创建人的]
	private Date startTime = new Date();//开始时间
	private Date overTime = new Date();	//完成时间
	private String dataServiceId;		//服务流程id[保存数据服务的id]
	private String runState;			//运行状态，1：代表未执行，2：代表已执行
	private String resultDataSetName;	//结果数据集名称
	private String dataSetName; //源数据集名称【附加】
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDataServiceId() {
		return dataServiceId;
	}
	public void setDataServiceId(String dataServiceId) {
		this.dataServiceId = dataServiceId;
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
	public String getFlowRuleCustName() {
		return flowRuleCustName;
	}
	public void setFlowRuleCustName(String flowRuleCustName) {
		this.flowRuleCustName = flowRuleCustName;
	}
	public String getRunProcId() {
		return runProcId;
	}
	public void setRunProcId(String runProcId) {
		this.runProcId = runProcId;
	}
	public String getRunProcName() {
		return runProcName;
	}
	public void setRunProcName(String runProcName) {
		this.runProcName = runProcName;
	}
	public String getProcRuleCustId() {
		return procRuleCustId;
	}
	public void setProcRuleCustId(String procRuleCustId) {
		this.procRuleCustId = procRuleCustId;
	}
	public String getDataServiceDesc() {
		return dataServiceDesc;
	}
	public void setDataServiceDesc(String dataServiceDesc) {
		this.dataServiceDesc = dataServiceDesc;
	}
	public String getSourceDataSet() {
		return sourceDataSet;
	}
	public void setSourceDataSet(String sourceDataSet) {
		this.sourceDataSet = sourceDataSet;
	}
	public String getResultDataSet() {
		return resultDataSet;
	}
	public void setResultDataSet(String resultDataSet) {
		this.resultDataSet = resultDataSet;
	}
	public String getRunPer() {
		return runPer;
	}
	public void setRunPer(String runPer) {
		this.runPer = runPer;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getOverTime() {
		return overTime;
	}
	public void setOverTime(Date overTime) {
		this.overTime = overTime;
	}
	public String getRunState() {
		return runState;
	}
	public void setRunState(String runState) {
		this.runState = runState;
	}

}
