package com.idap.drad.entity;
import java.util.Date;
import com.idp.pub.utils.DateUtil;

/**
/**
* @###################################################
* @功能描述�?
* @创建日期�?014-4-22 9:28:14
* @�?��人员：bai
* @修改日志�?
* @###################################################
*/
public class ProvCode implements java.io.Serializable {
		private String id; //
		private String name; //
		private String startTime;//[附加属�?]�?��时间
		private String endTime;//[附加属�?]结束时间
	
		/** default constructor */
		public ProvCode() {
		}
	
		public String getId() {
			return this.id;
		}
	
		public void setId(String id) {
			this.id = id;
		}
		
		public String getName() {
			return this.name;
		}
	
		public void setName(String name) {
			this.name = name;
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