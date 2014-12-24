package com.idap.drad.entity;
import java.util.Date;
import com.idp.pub.utils.DateUtil;

/**
* @###################################################
* @功能描述:
* @创建日期:2014-4-21 14:39:43
* @开发人员：bai
* @修改日志：
* @###################################################
*/
public class DataDownload implements java.io.Serializable {
		private String systemName; //
		private String provinceName; //
		private String sendPattern; //
		private Long hasDownloadNumber; //
		private Date initDate; //
		private String initDateStr;//[附加属�?] 
		private String startTime;//[附加属�?]�?��时间
		private String endTime;//[附加属�?]结束时间
	  
		/** default constructor */
		public DataDownload() {
		}
	
		public String getSystemName() {
			return this.systemName;
		}
	
		public void setSystemName(String systemName) {
			this.systemName = systemName;
		}
		
		public String getProvinceName() {
			return this.provinceName;
		}
	
		public void setProvinceName(String provinceName) {
			this.provinceName = provinceName;
		}
		
		public String getSendPattern() {
			return this.sendPattern;
		}
	
		public void setSendPattern(String sendPattern) {
			this.sendPattern = sendPattern;
		}
		
		public Long getHasDownloadNumber() {
			return this.hasDownloadNumber;
		}
	
		public void setHasDownloadNumber(Long hasDownloadNumber) {
			this.hasDownloadNumber = hasDownloadNumber;
		}
		
		public String getInitDateStr() {
			return this.initDate!=null?DateUtil.dateTimeToStrDefault(this.initDate):"";
		}
		
		public Date getInitDate() {
			return this.initDate;
		}
	
		public void setInitDate(Date initDate) {
			this.initDate = initDate;
			this.initDateStr=this.getInitDateStr();
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