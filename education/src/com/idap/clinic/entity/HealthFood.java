package com.idap.clinic.entity;

import java.util.Date;

import com.idp.pub.entity.annotation.MetaTable;

@MetaTable
public class HealthFood implements java.io.Serializable {
	private static final long serialVersionUID = 5736511926578194639L;
	
	private String     eatId;//EAT_ID
	private String  eatTitle;//EAT_TITLE 
	private String  eatContent;//EAT_CONTENT
	private String  eatPic;//EAT_PIC --> only address
	private Date    eatDate;//EAT_DATE
	private ClinicInformation     clinic;//CLINIC_ID 
	
	public String getEatId() {
		return eatId;
	}
	public void setEatId(String eatId) {
		this.eatId = eatId;
	}
	public String getEatTitle() {
		return eatTitle;
	}
	public void setEatTitle(String eatTitle) {
		this.eatTitle = eatTitle;
	}
	public String getEatContent() {
		return eatContent;
	}
	public void setEatContent(String eatContent) {
		this.eatContent = eatContent;
	}
	public String getEatPic() {
		return eatPic;
	}
	public void setEatPic(String eatPic) {
		this.eatPic = eatPic;
	}
	public Date getEatDate() {
		return eatDate;
	}
	public void setEatDate(Date eatDate) {
		this.eatDate = eatDate;
	}
	public ClinicInformation getClinic() {
		return clinic;
	}
	public void setClinic(ClinicInformation clinic) {
		this.clinic = clinic;
	}
	
	
	 
	

}
