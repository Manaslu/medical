package com.idap.drad.entity;

import java.util.Date;

import com.idp.pub.utils.DateUtil;

/**
 * /**
 * 
 * @###################################################
 * @创建日期�?014-4-10 16:09:37
 * @�?��人员：bai
 * @功能描述�?
 * @修改日志�?
 * @###################################################
 */
public class PortExcp implements java.io.Serializable {

	private String excpId;
	private String systemName; //
	private String logProvince; //
	private String tableNameCh; //
	private String tableNameEn; //
	private String dataSentFreq; //
	private String excpType; //
	private Date excpTime; //
	private String excpTimeStr;// [附加属�?]
	private String excpLocation; //
	private String dateProvinceNo; //
	private String startTime;// [附加属�?]�?��时间
	private String endTime;// [附加属�?]结束时间

	private String modifyDate;

	private String remark;//

	private String userName;

	private Integer state;

	/** default constructor */
	public PortExcp() {
	}

	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getLogProvince() {
		return this.logProvince;
	}

	public void setLogProvince(String logProvince) {
		this.logProvince = logProvince;
	}

	public String getTableNameCh() {
		return this.tableNameCh;
	}

	public void setTableNameCh(String tableNameCh) {
		this.tableNameCh = tableNameCh;
	}

	public String getTableNameEn() {
		return this.tableNameEn;
	}

	public void setTableNameEn(String tableNameEn) {
		this.tableNameEn = tableNameEn;
	}

	public String getDataSentFreq() {
		return this.dataSentFreq;
	}

	public void setDataSentFreq(String dataSentFreq) {
		this.dataSentFreq = dataSentFreq;
	}

	public String getExcpType() {
		return this.excpType;
	}

	public void setExcpType(String excpType) {
		this.excpType = excpType;
	}

	public String getExcpTimeStr() {
		return DateUtil.format(this.excpTime, DateUtil.YYYY_MM_DD_HH_MM);
	}

	public Date getExcpTime() {
		return this.excpTime;
	}

	public void setExcpTime(Date excpTime) {
		this.excpTime = excpTime;
		this.excpTimeStr = this.getExcpTimeStr();
	}

	public String getExcpLocation() {
		return this.excpLocation;
	}

	public void setExcpLocation(String excpLocation) {
		this.excpLocation = excpLocation;
	}

	public String getDateProvinceNo() {
		return this.dateProvinceNo;
	}

	public void setDateProvinceNo(String dateProvinceNo) {
		this.dateProvinceNo = dateProvinceNo;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getExcpId() {
		return excpId;
	}

	public void setExcpId(String excpId) {
		this.excpId = excpId;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
}