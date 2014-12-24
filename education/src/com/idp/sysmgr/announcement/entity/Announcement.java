package com.idp.sysmgr.announcement.entity;


import java.util.Date;

import com.idp.pub.entity.SmartEntity;
import com.idp.pub.entity.annotation.MetaTable;
import com.idp.pub.entity.annotation.PrimaryKey;
import com.idp.pub.entity.annotation.PrimaryKey.PK;
import com.idp.sysmgr.institution.entity.Institution;

/**
 * /** ########################
 * 
 * @创建日期：2014-5-13 09:21:42
 * @开发人员：huhanjiang
 * @功能描述：公告管理
 * @修改日志： ################
 */
@MetaTable
public class Announcement extends SmartEntity{

	private static final long serialVersionUID = 4736511926578194639L;

	@PrimaryKey(createType=PK.useIDP)
	private String annoId;					//公告编号
	private String annoName;				//公告名称
	private String annoPer;					//发布人
	private Date annoDate=new Date();		//发布日期
	private Date annoValidDate;				//最后有效期	  
	private String annoState;				//公告状态，1：代表待审核，2：代表发布，3：代表驳回，4：代表下线 ,5代表草稿
	private String annoDesc;				//公告描述
	private String apprComment;				//审批意见
	private String remards;					//备注
	private String userName;				//用户名[附加，反显创建人的]
	
	private String orgCd;					//发布机构
	private Institution institution;		//用于反显机构名称的
	private String setTime;					//【修改发布日期的】
	private String orgName; //【附加属性】显示机构名称的
	
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getSetTime() {
		return setTime;
	}
	public void setSetTime(String setTime) {
		this.setTime = setTime;
	}
	public String getOrgCd() {
		return orgCd;
	}
	public void setOrgCd(String orgCd) {
		this.orgCd = orgCd;
	}
	public Institution getInstitution() {
		return institution;
	}
	public void setInstitution(Institution institution) {
		this.institution = institution;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAnnoId() {
		return annoId;
	}
	public void setAnnoId(String annoId) {
		this.annoId = annoId;
	}
	public String getAnnoName() {
		return annoName;
	}
	public void setAnnoName(String annoName) {
		this.annoName = annoName;
	}
	public String getAnnoPer() {
		return annoPer;
	}
	public void setAnnoPer(String annoPer) {
		this.annoPer = annoPer;
	}
	public Date getAnnoDate() {
		return annoDate;
	}
	public void setAnnoDate(Date annoDate) {
		this.annoDate = annoDate;
	}

	public Date getAnnoValidDate() {
		return annoValidDate;
	}
	public void setAnnoValidDate(Date annoValidDate) {
		this.annoValidDate = annoValidDate;
	}
	public String getAnnoState() {
		return annoState;
	}
	public void setAnnoState(String annoState) {
		this.annoState = annoState;
	}
	public String getAnnoDesc() {
		return annoDesc;
	}
	public void setAnnoDesc(String annoDesc) {
		this.annoDesc = annoDesc;
	}
	public String getApprComment() {
		return apprComment;
	}
	public void setApprComment(String apprComment) {
		this.apprComment = apprComment;
	}
	public String getRemards() {
		return remards;
	}
	public void setRemards(String remards) {
		this.remards = remards;
	}
	
}
