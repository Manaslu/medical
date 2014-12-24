package com.idap.intextservice.customer.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * ###############################
 * 
 * @创建日期：2014-5-19 16:36:42
 * @开发人员：hu
 * @功能描述：客户管理实体类
 * @修改日志： #####################
 */

public class Customer implements Serializable{
	
	
	private static final long serialVersionUID = 4736511926578194612L;
	
	private Long custMamId;			//客户id	
	private String custName;		//客户名称
	private String custType;		//客户类型，1：代表个人，2：代表机构
	private String custAddr;		//客户地址
	private String contacts;		//联系人
	private String tel;				//电话
	private String post;			//邮政编码
	private String eMail;			//电子邮箱
	private String custStats;		//客户状态，1：代表启用，2：代表禁用
	private Date createTime = new Date();//创建时间
	private Date updateTime= new Date();//更新时间

	public Long getCustMamId() {
		return custMamId;
	}
	public void setCustMamId(Long custMamId) {
		this.custMamId = custMamId;
	}
	
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustAddr() {
		return custAddr;
	}
	public void setCustAddr(String custAddr) {
		this.custAddr = custAddr;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public String getCustStats() {
		return custStats;
	}
	public void setCustStats(String custStats) {
		this.custStats = custStats;
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
	

}
