package com.idap.intextservice.dataServiceProcess.entity;

import java.util.Date;

import com.idp.pub.entity.SmartEntity;
import com.idp.pub.entity.annotation.MetaTable;
import com.idp.pub.entity.annotation.PrimaryKey;
import com.idp.pub.entity.annotation.PrimaryKey.PK;

/**
 * #####################################
 * 
 * @创建日期：2014-5-21 16:33:42
 * @开发人员：huhanjiang
 * @功能描述：流程规则定制实体类
 * @修改日志： ###########################
 */
@MetaTable
public class FlowRuleCust extends SmartEntity {

	private static final long serialVersionUID = 4736512922578194612L;

	public static String FLOW_RULE_TYPE_AUTO = "0";

	public static String FLOW_RULE_TYPE_MANUAL = "1";

	@PrimaryKey(createType = PK.useIDP)
	private String flowRuleCustId; 		// 流程规则定制ID
	private String flowRuleCustType; 	// 流程规则定制方式 ,0:代表数据抽取，1：代表手工定义
	private String flowRuleCustName; 	// 流程规则名称
	private String sourceDataSet; 		// 源数据集
	private String resultDataSet; 		// 目标数据集
	private String sourceDataSetName;	//源数据集[附加，用于显示名称]
	private String resultDataSetName;	//目标数据集[附加，用于显示名称]
	private String dataDefId; 			// 数据定义代码
	private String flowStats; 			// 流程状态，0：代表初始化，1：代表已完成
	private String flowDesc; 			// 流程描述
	private String createPs; 			// 创建人
	private String userName;			//用户名[附加，反显创建人的]
	private Date createDt = new Date(); // 创建时间
	private Date editDt = new Date();	// 修改时间

	private String rules; 				//流程规则定制的规则[附加，用于处理手工定义规则]

	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSourceDataSetName() {
		return sourceDataSetName;
	}

	public void setSourceDataSetName(String sourceDataSetName) {
		this.sourceDataSetName = sourceDataSetName;
	}

	public String getResultDataSetName() {
		return resultDataSetName;
	}

	public void setResultDataSetName(String resultDataSetName) {
		this.resultDataSetName = resultDataSetName;
	}

	public String getFlowRuleCustType() {
		return flowRuleCustType;
	}

	public void setFlowRuleCustType(String flowRuleCustType) {
		this.flowRuleCustType = flowRuleCustType;
	}

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

	public String getFlowRuleCustName() {
		return flowRuleCustName;
	}

	public void setFlowRuleCustName(String flowRuleCustName) {
		this.flowRuleCustName = flowRuleCustName;
	}

	public String getDataDefId() {
		return dataDefId;
	}

	public void setDataDefId(String dataDefId) {
		this.dataDefId = dataDefId;
	}

	public String getFlowRuleCustId() {
		return flowRuleCustId;
	}

	public void setFlowRuleCustId(String flowRuleCustId) {
		this.flowRuleCustId = flowRuleCustId;
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

	public String getFlowStats() {
		return flowStats;
	}

	public void setFlowStats(String flowStats) {
		this.flowStats = flowStats;
	}

	public String getFlowDesc() {
		return flowDesc;
	}

	public void setFlowDesc(String flowDesc) {
		this.flowDesc = flowDesc;
	}

	public String getCreatePs() {
		return createPs;
	}

	public void setCreatePs(String createPs) {
		this.createPs = createPs;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public Date getEditDt() {
		return editDt;
	}

	public void setEditDt(Date editDt) {
		this.editDt = editDt;
	}

}
