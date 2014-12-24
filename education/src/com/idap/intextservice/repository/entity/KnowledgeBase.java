package com.idap.intextservice.repository.entity;

import java.util.Date;
import java.util.List;

import com.idp.pub.entity.SmartEntity;
import com.idp.pub.utils.DateUtil;

/**
 * /**
 * 
 * @###################################################
 * @功能描述：
 * @创建日期：2014-4-24 11:12:35
 * @开发人员：bai
 * @修改日志：
 * @###################################################
 */

public class KnowledgeBase extends SmartEntity implements java.io.Serializable {
												   
	private static final long serialVersionUID = 4736511926578100136L;

	// @PrimaryKey(createType = PK.useIDP)
	private String knowledgeId; //
	private String knowledgeName; //
	private String knowledgeType; //
	private String knowledgeDesc; // 描述
	private String applyUser; //
	private Date applyDate; //
	private String applyDateStr;// [附加属性]
	private String approvalUser; //
	private Date approvalDate; //
	private String approvalDateStr;// [附加属性]
	private String approvalStats; //
	private String approvalOpin; // 审批意见
	private String isRelease; // 是否发布
	private String content; //
	private String startTime;// [附加属性]开始时间
	private String endTime;// [附加属性]结束时间
	private String labelName;
	private Integer fileCnt;// 上传附件数
	private String[] typelist = new String[15];
	private String approvalFlag; // 审批标志

	private String authors;
	private String authorsCompany;
	private String professional; // 专业
	private String busiDirection;// 专业方向 BUSI_DIRECTION

	private String userId;
	private String labellibLabelName; // 级联出的标签名
	private List<LabelLib> columns;

	private String remarks;
	private String knowledgeSonType;

	public String getKnowledgeSonType() {
		return knowledgeSonType;
	}

	public void setKnowledgeSonType(String knowledgeSonType) {
		this.knowledgeSonType = knowledgeSonType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getLabellibLabelName() {
		return labellibLabelName;
	}

	public void setLabellibLabelName(String labellibLabelName) {
		this.labellibLabelName = labellibLabelName;
	}

	public List<LabelLib> getColumns() {
		return columns;
	}

	public void setColumns(List<LabelLib> columns) {
		this.columns = columns;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBusiDirection() {
		return busiDirection;
	}

	public void setBusiDirection(String busiDirection) {
		this.busiDirection = busiDirection;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public String getAuthorsCompany() {
		return authorsCompany;
	}

	public void setAuthorsCompany(String authorsCompany) {
		this.authorsCompany = authorsCompany;
	}

	public String getProfessional() {
		return professional;
	}

	public void setProfessional(String professional) {
		this.professional = professional;
	}

	public String getApprovalFlag() {
		return approvalFlag;
	}

	public void setApprovalFlag(String approvalFlag) {
		this.approvalFlag = approvalFlag;
	}

	public String[] getTypelist() {
		return typelist;
	}

	public void setTypelist(String[] typelist) {
		this.typelist = typelist;
	}

	public Integer getFileCnt() {
		return fileCnt;
	}

	public void setFileCnt(Integer fileCnt) {
		this.fileCnt = fileCnt;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public void setApplyDateStr(String applyDateStr) {

		this.applyDate = DateUtil.format(applyDateStr);
		System.out.println(" DateUtil.format(  applyDateStr )[["
				+ DateUtil.format(applyDateStr));
		this.applyDateStr = applyDateStr;
	}

	public void setApprovalDateStr(String approvalDateStr) {

		System.out.println("  setApprovalDateStr approvalDate[["
				+ DateUtil.format(approvalDateStr));

		this.approvalDate = DateUtil.format(approvalDateStr);

		this.approvalDateStr = approvalDateStr;
	}

	/** default constructor */
	public KnowledgeBase() {
	}

	public String getKnowledgeId() {
		return this.knowledgeId;
	}

	public void setKnowledgeId(String knowledgeId) {
		this.knowledgeId = knowledgeId;
	}

	public String getKnowledgeName() {
		return this.knowledgeName;
	}

	public void setKnowledgeName(String knowledgeName) {
		this.knowledgeName = knowledgeName;
	}

	public String getKnowledgeType() {
		return this.knowledgeType;
	}

	public void setKnowledgeType(String knowledgeType) {
		this.knowledgeType = knowledgeType;
	}

	public String getKnowledgeDesc() {
		return this.knowledgeDesc;
	}

	public void setKnowledgeDesc(String knowledgeDesc) {
		this.knowledgeDesc = knowledgeDesc;
	}

	public String getApplyUser() {
		return this.applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	public String getApplyDateStr() {
		return this.applyDate != null ? DateUtil
				.dateTimeToStrDefault(this.applyDate) : "";
	}

	public Date getApplyDate() {
		return this.applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
		this.applyDateStr = this.getApplyDateStr();
	}

	public void setApplyDateStr(Long applyDate) {
		this.applyDate.setTime(applyDate);
		this.applyDateStr = this.getApplyDateStr();
	}

	public String getApprovalUser() {
		return this.approvalUser;
	}

	public void setApprovalUser(String approvalUser) {
		this.approvalUser = approvalUser;
	}

	public String getApprovalDateStr() {

		return this.approvalDate != null ? DateUtil
				.dateTimeToStrDefault(this.approvalDate) : "";
	}

	public void setApprovalDate(Date approvalDate) {

		this.approvalDate = approvalDate;
		this.approvalDateStr = this.getApprovalDateStr();
	}

	public void setApprovalDate(String approvalDate) {
		System.out.println("     approvalDate[" + approvalDate);
		// this.approvalDate.setTime(approvalDate);
		this.approvalDateStr = this.getApprovalDateStr();
	}

	public Date getApprovalDate() {
		return this.approvalDate;
	}

	public String getApprovalStats() {
		return this.approvalStats;
	}

	public void setApprovalStats(String approvalStats) {
		this.approvalStats = approvalStats;
	}

	public String getApprovalOpin() {
		return this.approvalOpin;
	}

	public void setApprovalOpin(String approvalOpin) {
		this.approvalOpin = approvalOpin;
	}

	public String getIsRelease() {
		return this.isRelease;
	}

	public void setIsRelease(String isRelease) {
		this.isRelease = isRelease;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
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