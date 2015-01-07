package com.idap.drug.entity;

import java.io.Serializable;
import java.util.Date;

import com.idp.pub.entity.annotation.MetaTable;
@MetaTable
public class Drug implements Serializable {
	private String drugId;//药品文章ID
	private String drugName;//药品文章标题
	private String drugContent;//药品文章内容
	private String drupPic;//药品文章头像
	private Date drugDate;//药品发表时间
	private String clinicId;//所属诊所ID
	public String getDrugId() {
		return drugId;
	}
	public void setDrugId(String drugId) {
		this.drugId = drugId;
	}
	public String getDrugName() {
		return drugName;
	}
	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}
	public String getDrugContent() {
		return drugContent;
	}
	public void setDrugContent(String drugContent) {
		this.drugContent = drugContent;
	}
	public String getDrupPic() {
		return drupPic;
	}
	public void setDrupPic(String drupPic) {
		this.drupPic = drupPic;
	}
	public Date getDrugDate() {
		return drugDate;
	}
	public void setDrugDate(Date drugDate) {
		this.drugDate = drugDate;
	}
	public String getClinicId() {
		return clinicId;
	}
	public void setClinicId(String clinicId) {
		this.clinicId = clinicId;
	}

}
