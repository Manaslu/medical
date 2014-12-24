package com.idap.danalysis.entity;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.idp.pub.entity.SmartEntity;
import com.idp.pub.entity.annotation.MetaTable;
import com.idp.pub.entity.annotation.PrimaryKey;
import com.idp.pub.entity.annotation.PrimaryKey.PK;
import com.idp.pub.utils.DateUtil;
@MetaTable
public class AnalysisProcessSolidify   implements java.io.Serializable {
	
	private String   id; //流水号
	private String   anaThemeId; //分析主题代码
	private String   procName; //流程名称
	private String   procDesc; //流程描述
	private String   solidUser;//固化用户
	private Date     solidDate = new Date();//固化时间
	private Date     startDT;//数据开始时间
	private Date     endDT;//数据结束时间
	private String   anaDataSet;//分析数据集
	private String   solidScript;//固化脚本
	private String   status_;//状态
	private String   approvalUser;//审核人
	private String   runStatus; //启动状态
    private String 	 startTime;// [附加属性]开始时间
    private String 	 endTime;// [附加属性]结束时间
    private String   startDTStr;//[附加属性]开始时间
    private String   endDTStr;// [附加属性]结束时间
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAnaThemeId() {
		return anaThemeId;
	}
	public void setAnaThemeId(String anaThemeId) {
		this.anaThemeId = anaThemeId;
	}
	public String getProcName() {
		return procName;
	}
	public void setProcName(String procName) {
		this.procName = procName;
	}
	public String getProcDesc() {
		return procDesc;
	}
	public void setProcDesc(String procDesc) {
		this.procDesc = procDesc;
	}
	public String getSolidUser() {
		return solidUser;
	}
	public void setSolidUser(String solidUser) {
		this.solidUser = solidUser;
	}
	public Date getSolidDate() {
		return solidDate;
	}
	public void setSolidDate(Date solidDate) {
		this.solidDate = solidDate;
	}
	public Date getStartDT() {
		return startDT;
	}
	public void setStartDT(Date startDT) {
		this.startDT = startDT;
	}
	public Date getEndDT() {
		return endDT;
	}
	public void setEndDT(Date endDT) {
		this.endDT = endDT;
	}
	public String getAnaDataSet() {
		return anaDataSet;
	}
	public void setAnaDataSet(String anaDataSet) {
		this.anaDataSet = anaDataSet;
	}
	public String getSolidScript() {
		return solidScript;
	}
	public void setSolidScript(String solidScript) {
		this.solidScript = solidScript;
	}
	public String getStatus_() {
		return status_;
	}
	public void setStatus_(String status_) {
		this.status_ = status_;
	}
	public String getApprovalUser() {
		return approvalUser;
	}
	public void setApprovalUser(String approvalUser) {
		this.approvalUser = approvalUser;
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
	public String getStartDTStr() {
		return this.startDT != null ? DateUtil.format(this.startDT,DateUtil.YYYY_MM_DD) : "";
	}
	public void setStartDTStr(String startDTStr) {
		this.startDT = StringUtils.isNotBlank(startDTStr)?DateUtil.format(startDTStr):null;
	}
	public String getEndDTStr() {
		return this.endDT != null ? DateUtil.format(this.endDT,DateUtil.YYYY_MM_DD) : "";
	}
	public void setEndDTStr(String endDTStr) {
		this.endDT = StringUtils.isNotBlank(endDTStr)?DateUtil.format(endDTStr):null;
	}
	public String getRunStatus() {
		return runStatus;
	}
	public void setRunStatus(String runStatus) {
		this.runStatus = runStatus;
	}
	
}
