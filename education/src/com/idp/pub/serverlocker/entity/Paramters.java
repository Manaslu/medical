package com.idp.pub.serverlocker.entity;

import java.io.Serializable;

/**
 * 锁参数
 * 
 * @author panfei
 * 
 */
public class Paramters implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4742244462266590301L;

	/**
	 * 业务VO类型
	 */
	private String VoType;

	/**
	 * 数据源名称
	 */
	private String dsName = "local";

	/**
	 * 可以重复加锁
	 */
	private boolean repeatLock = false;

	public Paramters(String VoType) {
		this(VoType, "local");
	}

	public Paramters(String VoType, String dsName) {
		this(VoType, dsName, false);
	}

	public Paramters(String VoType, String dsName, boolean repeatLock) {
		this.VoType = VoType;
		this.dsName = dsName;
		this.repeatLock = repeatLock;
	}

	public String getDsName() {
		return dsName;
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	public String getVoType() {
		return VoType;
	}

	public void setVoType(String voType) {
		VoType = voType;
	}

	public boolean isRepeatLock() {
		return repeatLock;
	}

	public void setRepeatLock(boolean repeatLock) {
		this.repeatLock = repeatLock;
	}

	@Override
	public String toString() {
		StringBuffer appender = new StringBuffer();
		appender.append(dsName);
		appender.append(VoType);
		return appender.toString();
	}
}
