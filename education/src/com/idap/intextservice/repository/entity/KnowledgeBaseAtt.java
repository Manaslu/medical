package com.idap.intextservice.repository.entity;

import java.util.Date;

import com.idp.pub.entity.SmartEntity;
import com.idp.pub.entity.annotation.MetaTable;
import com.idp.pub.entity.annotation.PrimaryKey;
import com.idp.pub.entity.annotation.PrimaryKey.PK;
import com.idp.pub.utils.DateUtil;

/**
 * /**
 * 
 * @###################################################
 * @功能描述：
 * @创建日期：2014-4-24 11:12:35
 * @开发人员：bai
 * @修改日志：
 * @###################################################
 */

@MetaTable
public class KnowledgeBaseAtt extends SmartEntity implements
		java.io.Serializable {
												                   
	private static final long serialVersionUID = 3651192657810880896L;

	@PrimaryKey(createType = PK.useIDP)
	private String id; // 附件 32位ID

	private String knowledgeId; //
	private String fileId; //
	private String fileName; //
	private String fileType; //
	private Date uploadDate; //
	private String uploadDateStr;// [附加属性]
	private String uploadStats; //
	private String startTime;// [附加属性]开始时间
	private String endTime;// [附加属性]结束时间
	private String fileDir; // 保存附件路径
	private String   serverFileDir; //服务器附件路径//
	private String   oldFileName; //   OLD_FILE_NAME
	private String   picFlag; //PIC_FLAG
	
	public String getPicFlag() {
		return picFlag;
	}

	public void setPicFlag(String picFlag) {
		this.picFlag = picFlag;
	}

	public String getOldFileName() {
		return oldFileName;
	}

	public void setOldFileName(String oldFileName) {
		this.oldFileName = oldFileName;
	}

	public String getServerFileDir() {
		return serverFileDir;
	}

	public void setServerFileDir(String serverFileDir) {
		this.serverFileDir = serverFileDir;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileDir() {
		return fileDir;
	}

	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}

	public void setUploadDateStr(String uploadDateStr) {
		this.uploadDateStr = uploadDateStr;  
	}

	/** default constructor */
	public KnowledgeBaseAtt() {
	}

	public String getKnowledgeId() {
		return this.knowledgeId;
	}

	public void setKnowledgeId(String knowledgeId) {
		this.knowledgeId = knowledgeId;
	}

	public String getFileId() {
		return this.fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getUploadDateStr() {
		return this.uploadDate != null ? DateUtil
				.dateTimeToStrDefault(this.uploadDate) : "";
	}

	public Date getUploadDate() {
		return this.uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
		this.uploadDateStr = this.getUploadDateStr();
	}

	public String getUploadStats() {
		return this.uploadStats;
	}

	public void setUploadStats(String uploadStats) {
		this.uploadStats = uploadStats;
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