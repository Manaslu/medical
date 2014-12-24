package com.idap.dataprocess.addrmatch.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("matchConstants")
public class MatchConstants {
	@Value("${dataproc.datamatch.addrServerIp}")
	private String addrSocketIp;

	@Value("${dataproc.datamatch.addrServerPort}")
	private int addrSocketPort;

	@Value("${dataproc.datamatch.fileMatchInPath}")
	private String fileMatchInPath;// 地址匹配文件存放路径

	@Value("${dataproc.datamatch.fileMatchOutPath}")
	private String fileMatchOutPath;// 地址匹配结果文件输出路径

	@Value("${dataproc.datamatch.fileMatchLog}")
	private String fileMatchLog; // 地址匹配日志

	public String getAddrSocketIp() {
		return addrSocketIp;
	}

	public void setAddrSocketIp(String addrSocketIp) {
		this.addrSocketIp = addrSocketIp;
	}

	public int getAddrSocketPort() {
		return addrSocketPort;
	}

	public void setAddrSocketPort(int addrSocketPort) {
		this.addrSocketPort = addrSocketPort;
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
}
