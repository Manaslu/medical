package com.idap.processmgr.special.entity;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.idp.pub.entity.SmartEntity;
import com.idp.pub.entity.annotation.MetaTable;
import com.idp.pub.entity.annotation.PrimaryKey;
import com.idp.pub.entity.annotation.PrimaryKey.PK;

@MetaTable
public class Processes extends SmartEntity implements Serializable{

	private static final long serialVersionUID = 5818679174837313608L;
	
	@PrimaryKey(createType = PK.useIDP)
	private String fruitCode;//成果编号
	private String id;//实例id
	private String fruitCont;//成果内容
	private String projCode;//项目编号
	private String requCode;//需求编号
	private Date createTime = new Date();//创建时间
	private Date createTime_;//创建时间
	private String creator;//创建人
	private String confirmPerson;//确认人
	private Date confirmTime = new Date();//确认时间
	private String publicPerson;//发布人
	private Date publicTime = new Date();//发布时间
	private String oprId;//操作标识 0待确认，1已确认，2被驳回
	private String publicState;//发布状态0待发布，1已发布
	private String roleId;//角色id
	private String addUrl;//
	private String showUrl;//
	
	private String requName;//需求名称
	private String flowType;//流程类型
	private List<Map<String,Object>> files;//附件
	
	public String getFlowType() {
		return flowType;
	}
	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}
	public List<Map<String, Object>> getFiles() {
		return files;
	}
	public void setFiles(List<Map<String, Object>> files) {
		this.files = files;
	}
	public Date getCreateTime_() {
		return createTime_;
	}
	public void setCreateTime_(Date createTime_) {
		this.createTime_ = createTime_;
	}
	public String getRequName() {
		return requName;
	}
	public void setRequName(String requName) {
		this.requName = requName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFruitCode() {
		return fruitCode;
	}
	public void setFruitCode(String fruitCode) {
		this.fruitCode = fruitCode;
	}
	public String getFruitCont() {
		return fruitCont;
	}
	public void setFruitCont(String fruitCont) {
		this.fruitCont = fruitCont;
	}
	public String getProjCode() {
		return projCode;
	}
	public void setProjCode(String projCode) {
		this.projCode = projCode;
	}
	public String getRequCode() {
		return requCode;
	}
	public void setRequCode(String requCode) {
		this.requCode = requCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getConfirmPerson() {
		return confirmPerson;
	}
	public void setConfirmPerson(String confirmPerson) {
		this.confirmPerson = confirmPerson;
	}
	public Date getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}
	public String getPublicPerson() {
		return publicPerson;
	}
	public void setPublicPerson(String publicPerson) {
		this.publicPerson = publicPerson;
	}
	public Date getPublicTime() {
		return publicTime;
	}
	public void setPublicTime(Date publicTime) {
		this.publicTime = publicTime;
	}
	public String getOprId() {
		return oprId;
	}
	public void setOprId(String oprId) {
		this.oprId = oprId;
	}
	public String getPublicState() {
		return publicState;
	}
	public void setPublicState(String publicState) {
		this.publicState = publicState;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getAddUrl() {
		return addUrl;
	}
	public void setAddUrl(String addUrl) {
		this.addUrl = addUrl;
	}
	public String getShowUrl() {
		return showUrl;
	}
	public void setShowUrl(String showUrl) {
		this.showUrl = showUrl;
	}
	
}
