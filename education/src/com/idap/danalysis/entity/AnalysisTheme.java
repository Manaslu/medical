package com.idap.danalysis.entity;

import java.util.Date;

import com.idp.pub.entity.annotation.MetaTable;

@MetaTable
public class AnalysisTheme implements java.io.Serializable {
	private static final long serialVersionUID = 4736511926578194639L;

	 
	private String  anaMethodId;//ANA_METHOD_ID 分析方法代码
	private String anaMethodName;
	private String  anaThemeId;//ANA_THEME_ID 分析主题代码
	private String  anaThemeName;//ANA_THEME_NAME 分析主题名称
	private String  anaThemeDesc;//ANA_THEME_DESC 分析主题描述
	private String  anaScriptName;//ANA_SCRIPT_NAME 分析脚本名称
	private String  anaScript;//ANA_SCRIPT 分析脚本
	private String  createUser;//CREATE_USER 创建人
	private Date    createDate;//CREATE_DATE 创建时间
	private String  version;//VERSION 版本
	private String  dataSet;//DATA_SET 数据集
	private Date    finishedDate;//FINISHED_DATE 完成时间
	private String  anaStatus;//ANA_STATUS 分析状态
	private String  anaStatusId;//ANA_STATUS 分析状态
	private String  themeValid;// THEME_VALID 有效标示
	
	
	
	
 
 
 
	public String getThemeValid() {
		return themeValid;
	}
	public void setThemeValid(String themeValid) {
		this.themeValid = themeValid;
	}
	public String getAnaStatusId() {
		return anaStatusId;
	}
	public void setAnaStatusId(String anaStatusId) {
		this.anaStatusId = anaStatusId;
	}
	public String getAnaMethodName() {
		return anaMethodName;
	}
	public void setAnaMethodName(String anaMethodName) {
		this.anaMethodName = anaMethodName;
	}
 
	public String getAnaMethodId() {
		return anaMethodId;
	}
	public void setAnaMethodId(String anaMethodId) {
		this.anaMethodId = anaMethodId;
	}
	public String getAnaThemeId() {
		return anaThemeId;
	}
	public void setAnaThemeId(String anaThemeId) {
		this.anaThemeId = anaThemeId;
	}
	public String getAnaThemeName() {
		return anaThemeName;
	}
	public void setAnaThemeName(String anaThemeName) {
		this.anaThemeName = anaThemeName;
	}
	public String getAnaThemeDesc() {
		return anaThemeDesc;
	}
	public void setAnaThemeDesc(String anaThemeDesc) {
		this.anaThemeDesc = anaThemeDesc;
	}
	public String getAnaScriptName() {
		return anaScriptName;
	}
	public void setAnaScriptName(String anaScriptName) {
		this.anaScriptName = anaScriptName;
	}
	public String getAnaScript() {
		return anaScript;
	}
	public void setAnaScript(String anaScript) {
		this.anaScript = anaScript;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
 
	public String getDataSet() {
		return dataSet;
	}
	public void setDataSet(String dataSet) {
		this.dataSet = dataSet;
	}
	public Date getFinishedDate() {
		return finishedDate;
	}
	public void setFinishedDate(Date finishedDate) {
		this.finishedDate = finishedDate;
	}
 
	public String getAnaStatus() {
		return anaStatus;
	}
	public void setAnaStatus(String anaStatus) {
		this.anaStatus = anaStatus;
	}

	
	
	
  
	
	
	
		
 
			
			


}
