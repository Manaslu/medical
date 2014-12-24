package com.idap.danalysis.entity;
import java.util.Date;

import com.idp.pub.entity.annotation.MetaTable;
@MetaTable
public class ParameterList implements java.io.Serializable {
	private static final long serialVersionUID = 4736511926578194639L;
	
 
	private String   anaMethodId;
	private String   anaThemeName;
	private String   anaThemeDesc;
	private String   anaScriptName;
	private String   createUser;
	private Date     createDate;
	private String   version;
	
	private String    year;
	private String    objectPaper;
	private String    userProvince;
	
	private String   competetorIds; //竞争品种
	private String   samePaperIds; //同类市场
	private String   industryIds; //行业类型
	private String   paraBigClientArrays; //table_nameen
	private String   importantClientFilePath;//大客户文件的地址
	private String   importantProvince;//重点省
	
	
	
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
 
	public String getObjectPaper() {
		return objectPaper;
	}
	public void setObjectPaper(String objectPaper) {
		this.objectPaper = objectPaper;
	}
	public String getUserProvince() {
		return userProvince;
	}
	public void setUserProvince(String userProvince) {
		this.userProvince = userProvince;
	}
	public String getCompetetorIds() {
		return competetorIds;
	}
	public void setCompetetorIds(String competetorIds) {
		this.competetorIds = competetorIds;
	}
	public String getSamePaperIds() {
		return samePaperIds;
	}
	public void setSamePaperIds(String samePaperIds) {
		this.samePaperIds = samePaperIds;
	}
	public String getIndustryIds() {
		return industryIds;
	}
	public void setIndustryIds(String industryIds) {
		this.industryIds = industryIds;
	}
	public String getParaBigClientArrays() {
		return paraBigClientArrays;
	}
	public void setParaBigClientArrays(String paraBigClientArrays) {
		this.paraBigClientArrays = paraBigClientArrays;
	}
	public String getImportantClientFilePath() {
		return importantClientFilePath;
	}
	public void setImportantClientFilePath(String importantClientFilePath) {
		this.importantClientFilePath = importantClientFilePath;
	}
	public String getImportantProvince() {
		return importantProvince;
	}
	public void setImportantProvince(String importantProvince) {
		this.importantProvince = importantProvince;
	}
 
	public String getAnaMethodId() {
		return anaMethodId;
	}
	public void setAnaMethodId(String anaMethodId) {
		this.anaMethodId = anaMethodId;
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
	
	
	
	
	
	 
 
}
