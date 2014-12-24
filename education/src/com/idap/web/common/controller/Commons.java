package com.idap.web.common.controller;

public class Commons {
	public String addrServerIp; // 地址匹配服务器IP

	public String addrServerPort; // 地址匹配服务器端口

	public String fileUploadPath; // 文件上传路径

	public String fileDownPath; // 文件下载路径

	public String fileMatchInPath; // 地址匹配文件存放路径

	public String fileMatchOutPath;// 地址匹配文件输出路径

	public String fileMatchLog; // 地址匹配日志

	public String systemSaveLog; // 是否记录系统日志

	public String dataProcLog;// 是否记录数据操作日志

	public String roleAll;	//【附加的，为了机构管理那能查到所有的省份】查询所有省份
	
	
	public String getRoleAll() {
		return roleAll;
	}

	public void setRoleAll(String roleAll) {
		this.roleAll = roleAll;
	}

	public String getAddrServerIp() {
		return addrServerIp;
	}

	public void setAddrServerIp(String addrServerIp) {
		this.addrServerIp = addrServerIp;
	}

	public String getFileUploadPath() {
		return fileUploadPath;
	}

	public void setFileUploadPath(String fileUploadPath) {
		this.fileUploadPath = fileUploadPath;
	}

	public String getAddrServerPort() {
		return addrServerPort;
	}

	public void setAddrServerPort(String addrServerPort) {
		this.addrServerPort = addrServerPort;
	}

	public String getFileDownPath() {
		return fileDownPath;
	}

	public void setFileDownPath(String fileDownPath) {
		this.fileDownPath = fileDownPath;
	}

	public String getFileMatchInPath() {
		return fileMatchInPath;
	}

	public void setFileMatchInPath(String fileMatchInPath) {
		this.fileMatchInPath = fileMatchInPath;
	}

	public String getFileMatchOutPath() {
		return fileMatchOutPath;
	}

	public void setFileMatchOutPath(String fileMatchOutPath) {
		this.fileMatchOutPath = fileMatchOutPath;
	}

	public String getFileMatchLog() {
		return fileMatchLog;
	}

	public void setFileMatchLog(String fileMatchLog) {
		this.fileMatchLog = fileMatchLog;
	}

	public String getSystemSaveLog() {
		return systemSaveLog;
	}

	public void setSystemSaveLog(String systemSaveLog) {
		this.systemSaveLog = systemSaveLog;
	}

	public String getDataProcLog() {
		return dataProcLog;
	}

	public void setDataProcLog(String dataProcLog) {
		this.dataProcLog = dataProcLog;
	}

}
