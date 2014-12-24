package com.idap.dataprocess.rule.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.idp.pub.utils.DateUtil;

/**
 * 
 * @author Raoxy
 * 
 */
public class RuleScript implements java.io.Serializable {

	private static final long serialVersionUID = 180403952049655701L;

	private Long scriptId; //
	
	private Long parScriptId; //
	
	private String scriptName; //

	private String ruleDesc; //

	private String ruleScript; //

	private String createUser; //

	private Date createDate; //

	private String ruleTypeId; //

	private String scriptCode;// 脚本编码地址清洗匹配(address,post)
	
	private List<RuleScript> chdRuleScript=new ArrayList<RuleScript>();

	/** default constructor */
	public RuleScript() {
	}

	public Long getParScriptId() {
		return parScriptId;
	}

	public void setParScriptId(Long parScriptId) {
		this.parScriptId = parScriptId;
	}

	public String getScriptName() {
		return this.scriptName;
	}

	public List<RuleScript> getChdRuleScript() {
		return chdRuleScript;
	}

	public void setChdRuleScript(List<RuleScript> chdRuleScript) {
		this.chdRuleScript = chdRuleScript;
	}

	public void setScriptName(String scriptName) {
		this.scriptName = scriptName;
	}

	public String getRuleDesc() {
		return this.ruleDesc;
	}

	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}

	public String getRuleScript() {
		return this.ruleScript;
	}

	public void setRuleScript(String ruleScript) {
		this.ruleScript = ruleScript;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateDateStr() {
		return this.createDate != null ? DateUtil
				.dateTimeToStrDefault(this.createDate) : "";
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRuleTypeId() {
		return this.ruleTypeId;
	}

	public void setRuleTypeId(String ruleTypeId) {
		this.ruleTypeId = ruleTypeId;
	}

	public Long getScriptId() {
		return scriptId;
	}

	public void setScriptId(Long scriptId) {
		this.scriptId = scriptId;
	}

	public String getScriptCode() {
		return scriptCode;
	}

	public void setScriptCode(String scriptCode) {
		this.scriptCode = scriptCode;
	}

}