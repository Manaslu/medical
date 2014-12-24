package com.idap.danalysis.entity;

import java.util.Date;

import com.idp.pub.entity.SmartEntity;
import com.idp.pub.entity.annotation.MetaTable;
import com.idp.pub.entity.annotation.PrimaryKey;
import com.idp.pub.entity.annotation.PrimaryKey.PK;


@MetaTable
public class SynchronizationApply extends SmartEntity implements
		java.io.Serializable {
	private static final long serialVersionUID = 4736511926578194639L;

		@PrimaryKey(createType = PK.useIDP)
		
		private String  id;//ID流水号
		
		private String  requId;//REQU_ID需求ID
		private Date  dataStartTime;//DATA_START_TIME数据开始时间
		private Date  dataEndTime;//DATA_END_TIME数据结束时间
		private String  cheanMethod;//CHEAN_METHOD清理方式
		private String  syncTablename;//SYNC_TABLENAME同步表名
		private String  syncState;//SYNC_STATE同步状态
		private String  applyId;//APPLY_ID申请单号
		private String  applyUser;//APPLY_USER申请人
		private String  theme;//THEME所属分析专题
		private String  sourceTableName;//SOURCE_TABLE_NAME源表名
		private String  tableComment;//TABLE_COMMENT申请说明
		private String  sourceSystem;//SOURCE_SYSTEM表所属业务系统
		private String  saveCycle;//SAVE_CYCLE同步周期
		private Date  applyDate;//APPLY_DATE申请日期
		private Date  demandDate;//DEMAND_DATE需求日期
		private String  applyUni;//APPLY_UNI申请单位
		private String  applyOrg;//APPLY_ORG申请部门
		private String  receiveUser;//RECEIVE_USER接收人
		private String  applyPhone;//APPLY_PHONE申请人电话
		private String  receivePhone;//RECEIVE_PHONE接收人电话
		private String  approvalUser;//APPROVAL_USER审批人
		private String  apprRoleId;//APPR_ROLE_ID审批角色ID
		private Date  approvalDate;//APPROVAL_DATE审批时间
		private String  approvalStats;//APPROVAL_STATS审批状态
		private String  approvalOpinion;//APPROVAL_OPINION审批意见
		private Date  failureDate;//FAILURE_DATE失效日期
		private String  autoManu;//AUTO_MANU手动/自动
		private String  tableNameen;//AUTO_MANU手动/自动

		private String dataStartTimes;//临时变量
		private String dataEndTimes;
		private String applyDates;
		private String demandDates;
		private String failureDates;
		private String  approvalDates;
		
		
		
		
		public String getTableNameen() {
			return tableNameen;
		}
		public void setTableNameen(String tableNameen) {
			this.tableNameen = tableNameen;
		}


			public String getId() {
				return id;
			}
			public void setId(String id) {
				this.id = id;
			}
			public String getRequId() {
				return requId;
			}
			public void setRequId(String requId) {
				this.requId = requId;
			}
			public Date getDataStartTime() {
				return dataStartTime;
			}
			public void setDataStartTime(Date dataStartTime) {
				this.dataStartTime = dataStartTime;
			}
			public Date getDataEndTime() {
				return dataEndTime;
			}
			public void setDataEndTime(Date dataEndTime) {
				this.dataEndTime = dataEndTime;
			}
			public String getCheanMethod() {
				return cheanMethod;
			}
			public void setCheanMethod(String cheanMethod) {
				this.cheanMethod = cheanMethod;
			}
			public String getSyncTablename() {
				return syncTablename;
			}
			public void setSyncTablename(String syncTablename) {
				this.syncTablename = syncTablename;
			}
			public String getSyncState() {
				return syncState;
			}
			public void setSyncState(String syncState) {
				this.syncState = syncState;
			}
			public String getApplyId() {
				return applyId;
			}
			public void setApplyId(String applyId) {
				this.applyId = applyId;
			}
			public String getApplyUser() {
				return applyUser;
			}
			public void setApplyUser(String applyUser) {
				this.applyUser = applyUser;
			}
			public String getTheme() {
				return theme;
			}
			public void setTheme(String theme) {
				this.theme = theme;
			}
			public String getSourceTableName() {
				return sourceTableName;
			}
			public void setSourceTableName(String sourceTableName) {
				this.sourceTableName = sourceTableName;
			}
			public String getTableComment() {
				return tableComment;
			}
			public void setTableComment(String tableComment) {
				this.tableComment = tableComment;
			}
			public String getSourceSystem() {
				return sourceSystem;
			}
			public void setSourceSystem(String sourceSystem) {
				this.sourceSystem = sourceSystem;
			}
			public String getSaveCycle() {
				return saveCycle;
			}
			public void setSaveCycle(String saveCycle) {
				this.saveCycle = saveCycle;
			}
			public Date getApplyDate() {
				return applyDate;
			}
			public void setApplyDate(Date applyDate) {
				this.applyDate = applyDate;
			}
			public Date getDemandDate() {
				return demandDate;
			}
			public void setDemandDate(Date demandDate) {
				this.demandDate = demandDate;
			}
			public String getApplyUni() {
				return applyUni;
			}
			public void setApplyUni(String applyUni) {
				this.applyUni = applyUni;
			}
			public String getApplyOrg() {
				return applyOrg;
			}
			public void setApplyOrg(String applyOrg) {
				this.applyOrg = applyOrg;
			}
			public String getReceiveUser() {
				return receiveUser;
			}
			public void setReceiveUser(String receiveUser) {
				this.receiveUser = receiveUser;
			}
			public String getApplyPhone() {
				return applyPhone;
			}
			public void setApplyPhone(String applyPhone) {
				this.applyPhone = applyPhone;
			}
			public String getReceivePhone() {
				return receivePhone;
			}
			public void setReceivePhone(String receivePhone) {
				this.receivePhone = receivePhone;
			}
			public String getApprovalUser() {
				return approvalUser;
			}
			public void setApprovalUser(String approvalUser) {
				this.approvalUser = approvalUser;
			}
			public String getApprRoleId() {
				return apprRoleId;
			}
			public void setApprRoleId(String apprRoleId) {
				this.apprRoleId = apprRoleId;
			}
			public Date getApprovalDate() {
				return approvalDate;
			}
			public void setApprovalDate(Date approvalDate) {
				this.approvalDate = approvalDate;
			}
			public String getApprovalStats() {
				return approvalStats;
			}
			public void setApprovalStats(String approvalStats) {
				this.approvalStats = approvalStats;
			}
			public String getApprovalOpinion() {
				return approvalOpinion;
			}
			public void setApprovalOpinion(String approvalOpinion) {
				this.approvalOpinion = approvalOpinion;
			}
			public Date getFailureDate() {
				return failureDate;
			}
			public void setFailureDate(Date failureDate) {
				this.failureDate = failureDate;
			}
			public String getAutoManu() {
				return autoManu;
			}
			public void setAutoManu(String autoManu) {
				this.autoManu = autoManu;
			}
			public String getDataStartTimes() {
				return dataStartTimes;
			}
			public void setDataStartTimes(String dataStartTimes) {
				this.dataStartTimes = dataStartTimes;
			}
			public String getDataEndTimes() {
				return dataEndTimes;
			}
			public void setDataEndTimes(String dataEndTimes) {
				this.dataEndTimes = dataEndTimes;
			}
			public String getApplyDates() {
				return applyDates;
			}
			public void setApplyDates(String applyDates) {
				this.applyDates = applyDates;
			}
			public String getDemandDates() {
				return demandDates;
			}
			public void setDemandDates(String demandDates) {
				this.demandDates = demandDates;
			}
			public String getFailureDates() {
				return failureDates;
			}
			public void setFailureDates(String failureDates) {
				this.failureDates = failureDates;
			}
			public String getApprovalDates() {
				return approvalDates;
			}
			public void setApprovalDates(String approvalDates) {
				this.approvalDates = approvalDates;
			}
			
			
			
			


}
