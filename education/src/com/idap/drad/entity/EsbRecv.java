package com.idap.drad.entity;

import java.util.Date;

import com.idp.pub.utils.DateUtil;

/**
 * /**
 * 
 * @###################################################
 * @创建日期:2014-4-10 16:09:36
 * @开发人员：bai
 * @功能描述:
 * @修改日志:
 * @###################################################
 */
public class EsbRecv implements java.io.Serializable {
	private String systemName; //
	private String dataProvince; //
	private String tableNameCh; //
	private String tableNameEn; //
	private String dataSentFreq; //
	private Date businessDate; //
	private String businessDateStr;// [附加属�?]
	private Date receiveDate; //
	private String receiveDateStr;// [附加属�?]
	private Date loadingDate; //
	private String loadingDateStr;// [附加属�?]
	private Integer loadingRowCnt; //
	private String logProvinceNo; //
	private String startTime;// [附加属�?]�?��时间
	private String endTime;// [附加属�?]结束时间

	/** default constructor */
	public EsbRecv() {
	}

	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getDataProvince() {
		return this.dataProvince;
	}

	public void setDataProvince(String dataProvince) {
		this.dataProvince = dataProvince;
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

	public String getBusinessDateStr() {
		return DateUtil.format(this.businessDate, DateUtil.YYYY_MM_DD_HH_MM);
	}

	public Date getBusinessDate() {
		return this.businessDate;
	}

	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
		this.businessDateStr = this.getBusinessDateStr();
	}

	public String getReceiveDateStr() {
		return DateUtil.format(this.receiveDate, DateUtil.YYYY_MM_DD_HH_MM);
	}

	public Date getReceiveDate() {
		return this.receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
		this.receiveDateStr = this.getReceiveDateStr();
	}

	public String getLoadingDateStr() {
		return DateUtil.format(this.loadingDate, DateUtil.YYYY_MM_DD_HH_MM);
	}

	public Date getLoadingDate() {
		return this.loadingDate;
	}

	public void setLoadingDate(Date loadingDate) {
		this.loadingDate = loadingDate;
		this.loadingDateStr = this.getLoadingDateStr();
	}

	public Integer getLoadingRowCnt() {
		return this.loadingRowCnt;
	}

	public void setLoadingRowCnt(Integer loadingRowCnt) {
		this.loadingRowCnt = loadingRowCnt;
	}

	public String getLogProvinceNo() {
		return this.logProvinceNo;
	}

	public void setLogProvinceNo(String logProvinceNo) {
		this.logProvinceNo = logProvinceNo;
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