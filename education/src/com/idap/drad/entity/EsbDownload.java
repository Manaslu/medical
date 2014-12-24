package com.idap.drad.entity;

import java.sql.Date;

public class EsbDownload {
	private String systemName;// 业务名称

	private String provinceName;// 省份名称

	private Integer portNum;// 接口总数

	private Integer prePortNum;// 应接收接口数

	private Integer actPortNum;// 实际接收接口数

	private Integer dailyPortNum;// 日接口数

	private String dailyLoadTime;// 日接口加载时间

	private Integer weekPortNum;// 周接口数

	private String weekLoadTime;// 周加载时间

	private Integer monthPortNum;// 月接口数

	private String monthLoadTime;// 月加载时间

	private Integer yearPortNum;// 年接口数

	private String yearLoadTime;// 年加载时间

	private String remark;// 备注

	private Date businessDate;// 业务日期

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public Integer getPortNum() {
		return portNum;
	}

	public void setPortNum(Integer portNum) {
		this.portNum = portNum;
	}

	public Integer getPrePortNum() {
		return prePortNum;
	}

	public void setPrePortNum(Integer prePortNum) {
		this.prePortNum = prePortNum;
	}

	public Integer getActPortNum() {
		return actPortNum;
	}

	public void setActPortNum(Integer actPortNum) {
		this.actPortNum = actPortNum;
	}

	public Integer getDailyPortNum() {
		return dailyPortNum;
	}

	public void setDailyPortNum(Integer dailyPortNum) {
		this.dailyPortNum = dailyPortNum;
	}

	public String getDailyLoadTime() {
		return dailyLoadTime;
	}

	public void setDailyLoadTime(String dailyLoadTime) {
		this.dailyLoadTime = dailyLoadTime;
	}

	public Integer getWeekPortNum() {
		return weekPortNum;
	}

	public void setWeekPortNum(Integer weekPortNum) {
		this.weekPortNum = weekPortNum;
	}

	public String getWeekLoadTime() {
		return weekLoadTime;
	}

	public void setWeekLoadTime(String weekLoadTime) {
		this.weekLoadTime = weekLoadTime;
	}

	public Integer getMonthPortNum() {
		return monthPortNum;
	}

	public void setMonthPortNum(Integer monthPortNum) {
		this.monthPortNum = monthPortNum;
	}

	public String getMonthLoadTime() {
		return monthLoadTime;
	}

	public void setMonthLoadTime(String monthLoadTime) {
		this.monthLoadTime = monthLoadTime;
	}

	public Integer getYearPortNum() {
		return yearPortNum;
	}

	public void setYearPortNum(Integer yearPortNum) {
		this.yearPortNum = yearPortNum;
	}

	public String getYearLoadTime() {
		return yearLoadTime;
	}

	public void setYearLoadTime(String yearLoadTime) {
		this.yearLoadTime = yearLoadTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}
}
