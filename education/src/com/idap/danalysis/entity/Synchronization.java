package com.idap.danalysis.entity;


 
import java.util.Date;

 
import com.idp.pub.entity.annotation.MetaTable;
 

@MetaTable
public class Synchronization implements java.io.Serializable {
 
 
 
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 4736511926578194639L;
	
	private Date     startDate; 
    private Date     endDate; 
    private int      rowCount; 
    private String   targetTablename; 
    private String   opUser; 
	private String   sourceTablename; //START_DATE
	private String   tableDesc;//table_desc
	private String   sourceSystem;//source_system
	private String   saveCycle;//save_cycle
	private Date     dataStartTime;//DATA_START_TIME
	private Date     dataEndTime;//DATA_END_TIME
	private String   tableComment;//TABLE_COMMENT
	private Date     applyDate;//APPLY_DATE
	private String   id;//流水号
	private String   approvalOpinion;//流水号
	private String   approvalStats;//NAME
	private String   cheanMethod;//CHEAN_METHOD
	private String   requId;//REQU_ID
	private Date     failureDate;//failure_date
	private String   approvalUser; // APPROVAL_USER 
	private Date     approvalDate;//APPROVAL_DATE
	private String   tablenameen; // APPROVAL_USER 
	
	
	public String getTablenameen() {
		return tablenameen;
	}
	public void setTablenameen(String tablenameen) {
		this.tablenameen = tablenameen;
	}
	public Date getApprovalDate() {
		return approvalDate;
	}
	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}
	public String getApprovalUser() {
		return approvalUser;
	}
	public void setApprovalUser(String approvalUser) {
		this.approvalUser = approvalUser;
	}
	public Date getFailureDate() {
		return failureDate;
	}
	public void setFailureDate(Date failureDate) {
		this.failureDate = failureDate;
	}
	public String getRequId() {
		return requId;
	}
	public void setRequId(String requId) {
		this.requId = requId;
	}
	public String getCheanMethod() {
		return cheanMethod;
	}
	public void setCheanMethod(String cheanMethod) {
		this.cheanMethod = cheanMethod;
	}
	public String getApprovalOpinion() {
		return approvalOpinion;
	}
	public void setApprovalOpinion(String approvalOpinion) {
		this.approvalOpinion = approvalOpinion;
	}
	public String getSourceTablename() {
		return sourceTablename;
	}
	public void setSourceTablename(String sourceTablename) {
		this.sourceTablename = sourceTablename;
	}
	public String getTableDesc() {
		return tableDesc;
	}
	public void setTableDesc(String tableDesc) {
		this.tableDesc = tableDesc;
	}
	public String getSourceSystem() {
		return sourceSystem;
	}
	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}
	public String getSaveCycle() {
		return saveCycle;
	}
	public void setSaveCycle(String saveCycle) {
		this.saveCycle = saveCycle;
	}
	public Date getDataStartTime() {
		return dataStartTime;
	}
	public void setDataStartTime(Date dataStartTime) {
		this.dataStartTime = dataStartTime;
	}
	public Date getDataEndTime() {
		return dataEndTime;
	}
	public void setDataEndTime(Date dataEndTime) {
		this.dataEndTime = dataEndTime;
	}
	public String getTableComment() {
		return tableComment;
	}
	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
 
	public String getApprovalStats() {
		return approvalStats;
	}
	public void setApprovalStats(String approvalStats) {
		this.approvalStats = approvalStats;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
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
	public String getTargetTablename() {
		return targetTablename;
	}
	public void setTargetTablename(String targetTablename) {
		this.targetTablename = targetTablename;
	}
	public String getOpUser() {
		return opUser;
	}
	public void setOpUser(String opUser) {
		this.opUser = opUser;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
 
	
  
	
	 
 
	
	 
 
}
