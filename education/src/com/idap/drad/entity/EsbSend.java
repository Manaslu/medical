package com.idap.drad.entity;



import java.sql.Date;

import com.idp.pub.utils.DateUtil;

/**
/**
* @###################################################
* @功能描述�?
* @创建日期�?014-4-19 10:41:15
* @�?��人员：李广彬
* @修改日志�?
* @###################################################
*/
public class EsbSend implements java.io.Serializable {
		private String systemName; //
		private String dataProvince; //
		private String tableNameCh; //
		private String tableNameEn; //
		private String dataSentFreq; //
		private Date businessDate; //
		private String businessDateStr;//[附加属�?] 
		private Date sendDate; //
		private String sendDateStr;//[附加属�?] 
		private String logProvinceNo; //
		private String startTime;//[附加属�?]�?��时间
		private String endTime;//[附加属�?]结束时间
	
		/** default constructor */
		public EsbSend() {
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
			return DateUtil.format(this.businessDate, DateUtil.YYYY_MM_DD);
		}
		
		public Date getBusinessDate() {
			return this.businessDate;
		}
	
		public void setBusinessDate(Date businessDate) {
			this.businessDate = businessDate;
			this.businessDateStr=this.getBusinessDateStr();
		}
		
		public String getSendDateStr() {
			
			return DateUtil.format(this.sendDate, DateUtil.YYYY_MM_DD_HH_MM);
		}
		
		public Date getSendDate() {
			return this.sendDate;
		}
	
		public void setSendDate(Date sendDate) {
			this.sendDate = sendDate;
			this.sendDateStr=this.getSendDateStr();
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