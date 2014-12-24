package com.idap.intextservice.repository.entity;
import java.util.Date;

import com.idp.pub.entity.annotation.MetaTable;
import com.idp.pub.utils.DateUtil;

/**
/**
* @###################################################
* @功能描述：
* @创建日期：2014-4-23 11:12:35
* @开发人员：bai
* @修改日志：
* @###################################################
*/
@MetaTable
public class KnowledgeLog implements java.io.Serializable {
		private String knowledgeId; //
		private String opType; //
		private String fileId; //
		private Date opDate; //
		private String opDateStr;//[附加属性] 
		private String opStats; //
		private String startTime;//[附加属性]开始时间
		private String endTime;//[附加属性]结束时间
	
		/** default constructor */
		public KnowledgeLog() {
		}
	
		public String getKnowledgeId() {
			return this.knowledgeId;
		}
	
		public void setKnowledgeId(String knowledgeId) {
			this.knowledgeId = knowledgeId;
		}
		
		public String getOpType() {
			return this.opType;
		}
	
		public void setOpType(String opType) {
			this.opType = opType;
		}
		
		public String getFileId() {
			return this.fileId;
		}
	
		public void setFileId(String fileId) {
			this.fileId = fileId;
		}
		
		public String getOpDateStr() {
			return this.opDate!=null?DateUtil.dateTimeToStrDefault(this.opDate):"";
		}
		
		public Date getOpDate() {
			return this.opDate;
		}
	
		public void setOpDate(Date opDate) {
			this.opDate = opDate;
			this.opDateStr=this.getOpDateStr();
		}
		
		public String getOpStats() {
			return this.opStats;
		}
	
		public void setOpStats(String opStats) {
			this.opStats = opStats;
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