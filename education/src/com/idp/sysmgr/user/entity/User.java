package com.idp.sysmgr.user.entity;

import java.util.Date;
import java.util.List;

import com.idp.pub.service.entity.IAuthority;
import com.idp.pub.service.entity.IUser;
import com.idp.pub.utils.DateUtil;

public class User implements IUser {

	private static final long serialVersionUID = 7450534232075780548L;
	private Long id;		//用户ID
	private String logName;	//登录名
	private String userName;//用户名
	private String password;//密码
	private String phone;	//手机号码
	private String remark;	//备注
	private String email;	//电子邮件
	private String state;	//当前状态 1：代表启用。2：代表禁用
	private Date pwdDate;	//密码有效日期
	private Date createTime = new Date();//创建日期
	private Date updateTime = new Date();//最后更新日期
	
	private String confirmPassword; //密码确认
	private String provName;	//省市名称
	private String provCode;	//省市代码
	private String inRoleId;  	//内置角色id
	private Long roleId;		//角色id 
	private String orgCd;		//机构代码
  
	private String byOrgCd;	//【附加属性】机构排序
	private String orgName; //【附加属性】显示机构名称的
	
	
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}


	public String getConfirmPassword() {
		return confirmPassword;
	}

	public String getOrgCd() {
		return orgCd;
	}

	public void setOrgCd(String orgCd) {
		this.orgCd = orgCd;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getInRoleId() {
		return inRoleId;
	}

	public void setInRoleId(String inRoleId) {
		this.inRoleId = inRoleId;
	}

	public String getProvCode() {
		return provCode;
	}

	public void setProvCode(String provCode) {
		this.provCode = provCode;
	}

	public Long getId() {
		return this.id;
	}

	public String getUserName() {
		return this.userName;
	}

	public String getPassword() {
		return this.password;
	}

	public List<IAuthority> getAuthoritys() {
		return null;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogName() {
		return logName;
	}

	public void setLogName(String logName) {
		this.logName = logName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getPwdDate() {
		return pwdDate;
	}

	public void setPwdDate(Date pwdDate) {
		this.pwdDate = pwdDate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


	public String getUpdateTimeStr() {
		return DateUtil.format(this.updateTime, DateUtil.YYYY_MM_DD_HH_MM_SS);
	}

	public String getProvName() {
		return provName;
	}

	public void setProvName(String provName) {
		this.provName = provName;
	}

	public String getByOrgCd() {
		return byOrgCd;
	}

	public void setByOrgCd(String byOrgCd) {
		this.byOrgCd = byOrgCd;
	}

}
