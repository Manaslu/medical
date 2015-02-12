package com.idap.clinic.entity;


import java.util.Date;

import com.idp.pub.entity.annotation.MetaTable;

@MetaTable
public class CommonIllness implements java.io.Serializable {
	private static final long serialVersionUID = 5736511926578194639L;
	
	private String     diseaseId;//disease_id DISEASE_ID
	private String     diseaseName;//disease_name DISEASE_NAME 
	private String     diseaseContent;//disease_content DISEASE_CONTENT
	private String     diseasePic;//disease_pic DISEASE_PIC
	private Date       diseaseDate;// disease_date DISEASE_DATE
 	private String     clinicId;//clinic_id CLINIC_ID
 	
	private ClinicInformation     clinic;//CLINIC_ID 
	
	
  
	public Date getDiseaseDate() {
		return diseaseDate;
	}
	public void setDiseaseDate(Date diseaseDate) {
		this.diseaseDate = diseaseDate;
	}
	public String getDiseaseId() {
		return diseaseId;
	}
	public void setDiseaseId(String diseaseId) {
		this.diseaseId = diseaseId;
	}
	public String getDiseaseName() {
		return diseaseName;
	}
	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}
	public String getDiseaseContent() {
		return diseaseContent;
	}
	public void setDiseaseContent(String diseaseContent) {
		this.diseaseContent = diseaseContent;
	}
	public String getDiseasePic() {
		return diseasePic;
	}
	public void setDiseasePic(String diseasePic) {
		this.diseasePic = diseasePic;
	}
	public String getClinicId() {
		return clinicId;
	}
	public void setClinicId(String clinicId) {
		this.clinicId = clinicId;
	}
	public ClinicInformation getClinic() {
		return clinic;
	}
	public void setClinic(ClinicInformation clinic) {
		this.clinic = clinic;
	}
 
 
	
	 
	 
	

}
