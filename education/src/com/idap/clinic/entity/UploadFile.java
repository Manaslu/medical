package com.idap.clinic.entity;

 
import com.idp.pub.entity.annotation.MetaTable;
 

/**
 * /**
 * 
 * @###################################################
 * @功能描述：
 * @创建日期：2015-1-22  
 * @开发人员： 
 * @修改日志：
 * @###################################################
 */

@MetaTable
 
public class UploadFile implements java.io.Serializable {
	private static final long serialVersionUID = 5736511926578194639L;

 
	private String  id; //  id 
	private String  fileName; // fileName FILENAME
	private String  fileType; //fileType FILETYPE
	private String  filePath; //filePath FILEPATH
	private String  orgFileName; // orgFileName ORGFILENAME  
	private String  success; //success SUCCESS
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getOrgFileName() {
		return orgFileName;
	}
	public void setOrgFileName(String orgFileName) {
		this.orgFileName = orgFileName;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	
	 
}