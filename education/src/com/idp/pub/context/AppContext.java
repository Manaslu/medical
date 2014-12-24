package com.idp.pub.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public final class AppContext implements ApplicationContextAware {

	/**
	 * spring上下文 不包含controller
	 */
	private static ApplicationContext springApplicationContext;

	/**
	 * 是否调试模式
	 */
	private boolean debuging = false;

	/**
	 * 系统版本
	 */
	private String version = "1.0.0";

	/**
	 * 产品编号
	 */
	private String siteCode;

	private String fileBasePath;// 文件存放父路径

	/**
	 * 权限是否控制
	 */
	private boolean authecation = false;

	public boolean isDebuging() {
		return debuging;
	}

	public void setDebuging(boolean debuging) {
		this.debuging = debuging;
	}

	public boolean isAuthecation() {
		return authecation;
	}

	public void setAuthecation(boolean authecation) {
		this.authecation = authecation;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	/**
	 * 应用名称
	 */
	private String appName = "";

	/**
	 * 基础服务的Url
	 */
	private String baseUrl = "";

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * 数据库登录名
	 */
	private String userName;
	/**
	 * 数据库密码
	 */
	private String password;

	/**
	 * 数据库连接url
	 */
	private String dbUrl;

	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("AppContext:[");
		str.append("appName:").append(appName).append(",\n");
		str.append("siteCode:").append(this.siteCode).append(",");
		str.append("version:").append(this.version).append(",");
		str.append("debuging:").append(this.debuging).append(",");
		str.append("authecation:").append(this.authecation).append(",");
		str.append("baseUrl:").append(this.baseUrl).append(",");
		str.append("fileBasePath:").append(this.fileBasePath).append(",");
		str.append("userName:").append(this.userName).append(",");
		str.append("password:").append(this.password).append(",");
		str.append("dbUrl:").append(this.dbUrl);
		str.append("]");
		return str.toString();
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public String getFileBasePath() {
		return fileBasePath;
	}

	public void setFileBasePath(String fileBasePath) {
		this.fileBasePath = fileBasePath;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		springApplicationContext = applicationContext;
	}

	public static ApplicationContext getSpringApplicationContext() {
		return springApplicationContext;
	}
}