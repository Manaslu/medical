package com.idap.drad.entity;
import java.util.Date;

import com.idp.pub.utils.DateUtil;

/**
/**
* @###################################################
* @功能描述:
* @开发人员：bai 
* @功能描述：
* @修改日志：
* @###################################################
*/
public class DataChech implements java.io.Serializable {
		private String systemName; //
		private String provinceName; //
		private Date businessDate; //
		private String businessDateStr;//[附加属�?] 
		private Long dataRowCnt1; //
		private Long dataRowCnt2; //
		private String sendType; //
		private String startTime;//[附加属�?]�?��时间
		private String endTime;//[附加属�?]结束时间
		private String logProvinceNo;
	
		/** default constructor */
		public DataChech() {
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
		
		public String getBusinessDateStr() {
			return this.businessDate!=null?DateUtil.dateTimeToStrDefault(this.businessDate):"";
		}
		
		public Date getBusinessDate() {
			return this.businessDate;
		}
	
		public void setBusinessDate(Date businessDate) {
			this.businessDate = businessDate;
			this.businessDateStr=this.getBusinessDateStr();
		}
		
		public Long getDataRowCnt1() {
			return this.dataRowCnt1;
		}
	
		public void setDataRowCnt1(Long dataRowCnt1) {
			this.dataRowCnt1 = dataRowCnt1;
		}
		
		public Long getDataRowCnt2() {
			return this.dataRowCnt2;
		}
	
		public void setDataRowCnt2(Long dataRowCnt2) {
			this.dataRowCnt2 = dataRowCnt2;
		}
		
		public String getSendType() {
			return this.sendType;
		}
	
		public void setSendType(String sendType) {
			this.sendType = sendType;
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

		public String getLogProvinceNo() {
			return logProvinceNo;
		}

		public void setLogProvinceNo(String logProvinceNo) {
			this.logProvinceNo = logProvinceNo;
		}
}