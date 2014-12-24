package com.idap.danalysis.entity;

 
	import java.util.Date;

 
import com.idp.pub.entity.annotation.MetaTable;
import com.idp.pub.entity.annotation.PrimaryKey;
import com.idp.pub.entity.annotation.PrimaryKey.PK;

@MetaTable
public class SyncLog implements java.io.Serializable {
		private static final long serialVersionUID = 4736511926578194639L;

 
	private String   id; // 
	private String   t04Id; // 
	private Date     endDate; // 
	private Date     startDate; // 
	private int   rowCount; // 
	private String   sourceTablename; // 
	private String   targetTablename; // 
	private String   syncStats; // 
	private String   opUser; // 
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getT04Id() {
		return t04Id;
	}
	public void setT04Id(String t04Id) {
		this.t04Id = t04Id;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
 
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public String getSourceTablename() {
		return sourceTablename;
	}
	public void setSourceTablename(String sourceTablename) {
		this.sourceTablename = sourceTablename;
	}
	public String getTargetTablename() {
		return targetTablename;
	}
	public void setTargetTablename(String targetTablename) {
		this.targetTablename = targetTablename;
	}
 
	public String getSyncStats() {
		return syncStats;
	}
	public void setSyncStats(String syncStats) {
		this.syncStats = syncStats;
	}
	public String getOpUser() {
		return opUser;
	}
	public void setOpUser(String opUser) {
		this.opUser = opUser;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	
	
	
	 
 
	
	 
 
	
	
 

 
	 
	
 
 


 
}
