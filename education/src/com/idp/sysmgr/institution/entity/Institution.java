package com.idp.sysmgr.institution.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @创建日期：2014-5-08 16:36:42
 * @开发人员：hu
 * @功能描述：机构管理实体类
 * 
 */

public class Institution implements Serializable {

	private static final long serialVersionUID = 4736511926578194637L;

	private String orgCd; // 机构编码
	private String orgName; // 机构名称
	private String parentOrgCd; // 上级机构代码
	private Long parentLeveCd; // 机构级别代码
	private String orgId; // 行政区划代码
	private String state; // 机构状态，0：代表启用，1：代表禁用
	private String postCode; // 邮政编码
	private Date updateTime = new Date(); // 更新日期
	private String orgOname; // 机构别名
	private Long provCode; // 省市代码

	
	public Long getProvCode() {
		return provCode;
	}

	public void setProvCode(Long provCode) {
		this.provCode = provCode;
	}

	public String getOrgCd() {
		return orgCd;
	}

	public void setOrgCd(String orgCd) {
		this.orgCd = orgCd;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getParentOrgCd() {
		return parentOrgCd;
	}

	public void setParentOrgCd(String parentOrgCd) {
		this.parentOrgCd = parentOrgCd;
	}

	public Long getParentLeveCd() {
		return parentLeveCd;
	}

	public void setParentLeveCd(Long parentLeveCd) {
		this.parentLeveCd = parentLeveCd;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getOrgOname() {
		return orgOname;
	}

	public void setOrgOname(String orgOname) {
		this.orgOname = orgOname;
	}

	public String toString() {
		return this.orgCd + "-" + this.orgId;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof Institution)) {
			return false;
		}

		Institution temp = (Institution) obj;
		
		return this.orgCd.equals(temp.getOrgCd());
	}

	public int hashCode() {
		return this.orgCd.hashCode();
	}
}
