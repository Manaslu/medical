package com.idap.intextservice.repository.entity;

import java.util.Date;

import com.idp.pub.entity.SmartEntity;
import com.idp.pub.entity.annotation.MetaTable;
import com.idp.pub.entity.annotation.PrimaryKey;
import com.idp.pub.entity.annotation.PrimaryKey.PK;
import com.idp.pub.utils.DateUtil;

/**
 * /**
 * 
 * @###################################################
 * @功能描述：
 * @创建日期：2014-5-8 15:34:52
 * @开发人员：bai
 * @修改日志：
 * @###################################################
 */

@MetaTable
public class LabelLib extends SmartEntity implements java.io.Serializable {

	private static final long serialVersionUID = 4736511926578100139L;

	@PrimaryKey(createType = PK.useIDP)
	private String labelId; //
	private String labelName; //
	private String labelDesc; //
	private String createUser; //
	private Date createDate; // 创建时间
	private String  userId            ;  

	

	private String startTime;// [附加属性]开始时间
	private String endTime;// [附加属性]结束时间

	private String addFlag; // 审批标志

	public String getUserId() {
		String ii;
	
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getAddFlag() {
		return addFlag;
	}

	public void setAddFlag(String addFlag) {
		this.addFlag = addFlag;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/** default constructor */
	public LabelLib() {
	}

	public String getLabelId() {
		return this.labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	public String getLabelName() {
		return this.labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public String getLabelDesc() {
		return this.labelDesc;
	}

	public void setLabelDesc(String labelDesc) {
		this.labelDesc = labelDesc;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateDateStr() {
		return this.createDate != null ? DateUtil
				.dateTimeToStrDefault(this.createDate) : "";
	}

	public Date getCreateDate() {
		return this.createDate;
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