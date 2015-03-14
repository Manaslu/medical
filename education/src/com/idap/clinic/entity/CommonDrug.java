package com.idap.clinic.entity;
import java.util.Date;

import com.idp.pub.entity.annotation.MetaTable;

@MetaTable
public class CommonDrug implements java.io.Serializable {
	private static final long serialVersionUID = 5736511926578194639L;
	 
	private String     drugId;   //DRUG_ID drug_id
	private String     drugName; //DRUG_NAME drug_name 
	private String     drugContent; //DRUG_CONTENT drug_content  
	private ClinicInformation     clinic;   //CLINIC_ID clinic_id
	private String     drugPic;  //DRUG_PIC drug_pic
	private Date       drugDate;  //DRUG_PIC drug_pic
	private String clinicId;
	private String clinicName;
	private String drugIcon;
	
	
	
	
	public String getClinicId() {
		return clinicId;
	}
	public void setClinicId(String clinicId) {
		this.clinicId = clinicId;
	}
	public Date getDrugDate() {
		return drugDate;
	}
	public void setDrugDate(Date drugDate) {
		this.drugDate = drugDate;
	}
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

	public String getDrugPic() {
		return drugPic;
	}
	public void setDrugPic(String drugPic) {
		this.drugPic = drugPic;
	}
	public ClinicInformation getClinic() {
		return clinic;
	}
	public void setClinic(ClinicInformation clinic) {
		this.clinic = clinic;
	}
	public String getClinicName() {
		return clinicName;
	}
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}
	public String getDrugIcon() {
		return drugIcon;
	}
	public void setDrugIcon(String drugIcon) {
		this.drugIcon = drugIcon;
	}
  
	
	 
	

}
